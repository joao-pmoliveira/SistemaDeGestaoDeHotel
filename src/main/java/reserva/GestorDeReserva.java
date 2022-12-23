package reserva;

import basededados.GestorDeBaseDeDados;

import java.time.LocalDate;
import java.util.*;

public class GestorDeReserva {

    public GestorDeReserva(){
    }

    public List<Reserva> getReservasPorClienteNIF(int nifCliente, GestorDeBaseDeDados gestorDeBaseDeDados) {
        if(gestorDeBaseDeDados == null) return null;
        List<Reserva> reservasEncontradas = new ArrayList<>();

        String query = String.format("SELECT * FROM reserva WHERE cliente_nif = %d", nifCliente);
        List<String> linhasRelacaoReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if (linhasRelacaoReserva.isEmpty()) return null;

        for(String linha : linhasRelacaoReserva){
            String[] dadosLinha = linha.split(",");
            int reservaId = Integer.parseInt(dadosLinha[0]);
            int clienteNIF = Integer.parseInt(dadosLinha[1]);
            int empregadoId = Integer.parseInt(dadosLinha[2]);
            boolean estadoPagamento = !dadosLinha[3].equals("0");
            Fatura fatura = null;
            if(estadoPagamento){
                int faturaId = Integer.parseInt(dadosLinha[4]);
                String faturaQuery = String.format("SELECT * FROM fatura WHERE id = %d", faturaId);
                List<String> faturaResultado = gestorDeBaseDeDados.tryQueryDatabase(faturaQuery);
                if (!faturaResultado.isEmpty()) {
                    String[] faturaLinha = faturaResultado.get(0).split(",");

                    float faturaMontanteFinal = Float.parseFloat(faturaLinha[1]);
                    fatura = new Fatura(faturaId, faturaMontanteFinal);
                }
            }
            Reserva reserva = new Reserva(reservaId, clienteNIF, empregadoId, estadoPagamento, fatura);
            reservasEncontradas.add(reserva);
        }

        return reservasEncontradas;
    }

    public List<Reserva> getReservasPorFaturarPorClienteNif(int nifCliente, GestorDeBaseDeDados gestorDeBaseDeDados){
        if(gestorDeBaseDeDados == null) return null;
        HashMap<Integer, Reserva> reservasEncontradas = new HashMap<>();

        String query = String.format("SELECT reserva.id, reserva.cliente_nif, reserva.empregado_id," +
                " dia_reserva.quarto_id, layout.preco_base" +
                " from reserva left join fatura on fatura.id = reserva.id" +
                " left join dia_reserva on dia_reserva.reserva_id = reserva.id" +
                " left join quarto on quarto.id = dia_reserva.quarto_id" +
                " left join layout on layout.id = quarto.layout_id" +
                " where reserva.cliente_nif = %d and reserva.fatura_id is null", nifCliente);

        System.out.println(query);

        List<String> linhasReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if(linhasReserva.isEmpty()) return null;


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

    public void adicionarReserva(int clienteNIF, int empregadoID, List<LocalDate> datas, String[] quartos, GestorDeBaseDeDados gestorDeBaseDeDados){
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

        for(int i = 0; i < quartos.length; i++){
            for (int j = 0; j < datas.size(); j++) {
                String linhaDeValores = String.format("('%s', %s, %s)", datas.get(j), quartos[i], reservaID);
                stringBuilderInsertDiasReserva.append(linhaDeValores);

                if ( i == quartos.length -1 && j == datas.size()-1 ) continue;
                stringBuilderInsertDiasReserva.append(",");
            }
        }
        String finalInsertDiaReservaQuery = stringBuilderInsertDiasReserva.toString();
        gestorDeBaseDeDados.tryUpdateDatabase(finalInsertDiaReservaQuery);
    }

    public void gerarFaturaParaReserva(Reserva reserva, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (reserva == null) return;

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
    }
}
