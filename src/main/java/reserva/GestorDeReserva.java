package reserva;

import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;

public class GestorDeReserva {

    public GestorDeReserva(){
    }

    public List<Reserva> getTodasReservasPorClienteNIF(int nifCliente, GestorDeBaseDeDados gestorDeBaseDeDados) {
        if(gestorDeBaseDeDados == null)
            throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        String queryVerificarClienteNIFValido = "SELECT * from PTDA_BD_03.cliente where nif = %d";
        List<String> resultadosClienteNIF = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificarClienteNIFValido, nifCliente));
        if (resultadosClienteNIF.isEmpty())
            throw new InvalidParameterException("Não existe cliente associado ao NIF fornecido");

        HashMap<Integer, Reserva> reservasEncontradas = new HashMap<>();

        String query = String.format("SELECT reserva.id, reserva.cliente_nif, reserva.empregado_id, reserva.estado_pagamento, " +
                " reserva.fatura_id, fatura.montante_total, " +
                " dia_reserva.quarto_id, quarto.layout_id, layout.preco_base" +
                " from reserva left join fatura on fatura.id = reserva.fatura_id" +
                " left join dia_reserva on dia_reserva.reserva_id = reserva.id" +
                " left join quarto on quarto.id = dia_reserva.quarto_id" +
                " left join layout on layout.id = quarto.layout_id" +
                " where reserva.cliente_nif = %d ", nifCliente);

        List<String> linhasReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if(linhasReserva.isEmpty()) return null;


        for( String linha : linhasReserva){
            String[] colunas = linha.split(",");
            int reservaID = Integer.parseInt(colunas[0]);
            int clienteNIF = Integer.parseInt(colunas[1]);
            int empregadoID = Integer.parseInt(colunas[2]);
            boolean estadoPagamento = colunas[3].equals("1");
            int faturaID;
            float faturaMontante;
            int quartoID = Integer.parseInt(colunas[6]);
            int quartoLayoutID = Integer.parseInt(colunas[7]);
            float quartoLayoutPrecoBase = Float.parseFloat(colunas[8]);
            Fatura fatura = null;

            if (estadoPagamento){
                faturaID = Integer.parseInt(colunas[4]);
                faturaMontante = Float.parseFloat(colunas[5]);

                fatura = new Fatura(faturaID, faturaMontante);
            }

            if(!reservasEncontradas.containsKey(reservaID)){
                Reserva reserva = new Reserva(reservaID, clienteNIF, empregadoID, quartoLayoutPrecoBase,estadoPagamento, fatura);
                reservasEncontradas.put(reservaID, reserva);
            }

            reservasEncontradas.get(reservaID).adicionarQuarto(quartoID);
            reservasEncontradas.get(reservaID).somarAoPrecoAtual(quartoLayoutPrecoBase);
        }

        return new ArrayList<>(reservasEncontradas.values());

    }

    public List<Reserva> getReservasPorFaturarPorClienteNif(int nifCliente, GestorDeBaseDeDados gestorDeBaseDeDados){
        if(gestorDeBaseDeDados == null)
            throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        String queryVerificarClienteNIFValido = "SELECT * from PTDA_BD_03.cliente where nif = %d";
        List<String> resultadosClienteNIF = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificarClienteNIFValido, nifCliente));
        if (resultadosClienteNIF.isEmpty())
            throw new InvalidParameterException("Não existe cliente associado ao NIF fornecido");

        HashMap<Integer, Reserva> reservasEncontradas = new HashMap<>();

        String query = String.format("SELECT reserva.id, reserva.cliente_nif, reserva.empregado_id," +
                " dia_reserva.quarto_id, layout.preco_base" +
                " from reserva left join fatura on fatura.id = reserva.id" +
                " left join dia_reserva on dia_reserva.reserva_id = reserva.id" +
                " left join quarto on quarto.id = dia_reserva.quarto_id" +
                " left join layout on layout.id = quarto.layout_id" +
                " where reserva.cliente_nif = %d and reserva.fatura_id is null", nifCliente);

        List<String> linhasReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if(linhasReserva.isEmpty())
            throw new InvalidParameterException("Cliente não tem reservas por faturar");


        for( String linha : linhasReserva){
            String[] colunas = linha.split(",");
            int reservaID = Integer.parseInt(colunas[0]);
            int clienteNIF = Integer.parseInt(colunas[1]);
            int empregadoID = Integer.parseInt(colunas[2]);
            int quartoID = Integer.parseInt(colunas[3]);
            float quartoLayoutPrecoBase = Float.parseFloat(colunas[4]);

            if(!reservasEncontradas.containsKey(reservaID)){
                Reserva reserva = new Reserva(reservaID, clienteNIF, empregadoID, quartoLayoutPrecoBase,false, null);
                reservasEncontradas.put(reservaID, reserva);
            }

            reservasEncontradas.get(reservaID).adicionarQuarto(quartoID);
            reservasEncontradas.get(reservaID).somarAoPrecoAtual(quartoLayoutPrecoBase);
        }

        return new ArrayList<>(reservasEncontradas.values());
    }

    public void adicionarReserva(int clienteNIF, int empregadoID, List<LocalDate> datas, HashSet<Integer> quartos, GestorDeBaseDeDados gestorDeBaseDeDados){

        if(gestorDeBaseDeDados == null)
            throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        if(datas == null || datas.isEmpty() || datas.contains(null))
            throw new InvalidParameterException("Lista de datas vazia. Impossível registar reserva.");
        if(quartos == null || quartos.isEmpty() || quartos.contains(null))
            throw new InvalidParameterException("Lista de quartos vazia. Impossível registar reserva.");

        String queryVerificaEmpregadoIdValido = "SELECT * FROM PTDA_BD_03.empregado where id = %d and cargo_id = 1";
        List<String> resultadosEmpregadoID = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificaEmpregadoIdValido, empregadoID));
        if(resultadosEmpregadoID.isEmpty())
            throw new InvalidParameterException("Não existe empregado associado ao ID fornecido ou empregado não tem acesso a registar novas reservas. Impossível registar reserva.");

        String queryVerificarClienteNIFValido = "SELECT * from PTDA_BD_03.cliente where nif = %d";
        List<String> resultadosClienteNIF = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificarClienteNIFValido, clienteNIF));
        if (resultadosClienteNIF.isEmpty())
            throw new InvalidParameterException("Não existe cliente associado ao NIF fornecido. Impossível registar reserva.");

        String baseQueryInsertReserva = "INSERT INTO reserva(cliente_nif, empregado_id, estado_pagamento, fatura_id) VALUES ";
        String baseQueryInsertDiasReserva = "INSERT INTO dia_reserva(data_reserva, quarto_id, reserva_id) VALUES ";
        StringBuilder stringBuilderInsertReserva = new StringBuilder();
        StringBuilder stringBuilderInsertDiasReserva = new StringBuilder(baseQueryInsertDiasReserva);

        String dadosReserva = String.format("('%d', '%d', '0', NULL)", clienteNIF, empregadoID);
        stringBuilderInsertReserva.append(baseQueryInsertReserva);
        stringBuilderInsertReserva.append(dadosReserva);

        String finalInsertReservaQuery = stringBuilderInsertReserva.toString();
        gestorDeBaseDeDados.tryUpdateDatabase(finalInsertReservaQuery);

        List<String> resultados = gestorDeBaseDeDados.tryQueryDatabase("select last_insert_id()");
        int reservaID = Integer.parseInt(resultados.get(0));

        Iterator<Integer> iterator = quartos.iterator();

        while(iterator.hasNext()){
            for(int i = 0; i < datas.size(); i++){
                String linhaDeValores = String.format("('%s', %s, %s)", datas.get(i), iterator.next(), reservaID);
                stringBuilderInsertDiasReserva.append(linhaDeValores);

                if ( !iterator.hasNext() && i == datas.size() - 1 ) continue;
                stringBuilderInsertDiasReserva.append(",");
            }
        }

        /*for(int i = 0; i < quartos.size(); i++){
            for (int j = 0; j < datas.size(); j++) {
                String linhaDeValores = String.format("('%s', %s, %s)", datas.get(j), quartos[i], reservaID);
                stringBuilderInsertDiasReserva.append(linhaDeValores);

                if ( i == quartos.size() -1 && j == datas.size()-1 ) continue;
                stringBuilderInsertDiasReserva.append(",");
            }
        }*/

        String finalInsertDiaReservaQuery = stringBuilderInsertDiasReserva.toString();
        gestorDeBaseDeDados.tryUpdateDatabase(finalInsertDiaReservaQuery);
    }

    public Fatura gerarFaturaParaReserva(Reserva reserva, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (reserva == null)
            throw new InvalidParameterException("Reserva nula.");

        if(gestorDeBaseDeDados == null)
            throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        float faturaMontante = reserva.getPrecoAtual();
        String sqlAdicionarFatura = "insert into fatura(montante_total) values (" + faturaMontante + ")";

        gestorDeBaseDeDados.tryUpdateDatabase(sqlAdicionarFatura);
        List<String> resultado = gestorDeBaseDeDados.tryQueryDatabase("select last_insert_id()");

        int faturaID = Integer.parseInt(resultado.get(0));

        String sqlAtualizarFaturaIDEmReserva = String.format("UPDATE reserva SET reserva.fatura_id = %d, reserva.estado_pagamento='1' WHERE reserva.id = %d", faturaID, reserva.getReservaID());
        gestorDeBaseDeDados.tryUpdateDatabase(sqlAtualizarFaturaIDEmReserva);

        Fatura fatura = new Fatura(faturaID, faturaMontante);
        reserva.setFatura(fatura);
        reserva.setReservaPaga(true);
        return fatura;
    }
}
