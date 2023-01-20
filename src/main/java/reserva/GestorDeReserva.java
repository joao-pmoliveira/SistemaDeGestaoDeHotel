package reserva;

import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.*;

public class GestorDeReserva {

    public GestorDeReserva(){
    }

    /**
     * Esta função é para procurar todas as reservas pelo NIF do cliente
     * @param nifCliente: NIF do cliente que fez a reserva
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return retorna as reservas por NIF do cliente pesquisadas pelo utilizador
     */
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
                " where reserva.cliente_nif = %d " +
                "order by reserva.id asc", nifCliente);

        List<String> linhasReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if(linhasReserva.isEmpty()) throw new InvalidParameterException("Não existe reservas associadas ao NIF fornecido");


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
                continue;
            }

            reservasEncontradas.get(reservaID).adicionarQuarto(quartoID);
            reservasEncontradas.get(reservaID).somarAoPrecoAtual(quartoLayoutPrecoBase);
        }

        return new ArrayList<>(reservasEncontradas.values());

    }

    /**
     * Esta função é para procurar todas as as reservas por faturar pelo NIF do cliente
     * @param nifCliente: NIF do cliente que fez a reserva
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return retorna as reservas por faturar por NIF do cliente pesquisadas pelo utilizador
     */
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
            throw new InvalidParameterException("Não existem reservas por faturar para o NIF dado");

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
                continue;
            }

            reservasEncontradas.get(reservaID).adicionarQuarto(quartoID);
            reservasEncontradas.get(reservaID).somarAoPrecoAtual(quartoLayoutPrecoBase);
        }

        return new ArrayList<>(reservasEncontradas.values());
    }


    /**
     * Esta função serve para Adicionar uma Reserva na base de dados
     * @param clienteNIF: NIF do cliente que fez a reserva
     * @param empregadoID: ID do Empregado que irá efetuar a reserva
     * @param datas: Intervalo de Datas da reserva
     * @param quartos: Quartos que prentende reservar
     * @param gestorDeBaseDeDados conexão há base de dados
     */
    public void adicionarReserva(int clienteNIF, int empregadoID, HashSet<LocalDate> datas, HashSet<Integer> quartos, GestorDeBaseDeDados gestorDeBaseDeDados){
        if(gestorDeBaseDeDados == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        if(datas == null) throw new InvalidParameterException("Lista de datas nula");
        if(datas.isEmpty()) throw new InvalidParameterException("Lista de datas vazia");
        if(datas.contains(null)) throw new InvalidParameterException("Lista de datas com elemento nulo");
        LocalDate[] datasOrdenadas = datas.toArray(new LocalDate[datas.size()]);
        if(datas.size() > 1){
            Arrays.sort(datasOrdenadas);
            for( int i = 1; i < datasOrdenadas.length; i++){
                LocalDate dataAtual = datasOrdenadas[i];
                LocalDate dataAnterior = datasOrdenadas[i-1];
                if(dataAtual.isAfter(dataAnterior.plusDays(1)))
                    throw new InvalidParameterException("Lista de datas inclui datas não consecutivas");
            }
        }

        if(quartos == null) throw new InvalidParameterException("Lista de quarto é nula");
        if(quartos.isEmpty()) throw new InvalidParameterException("Lista de quartos vazia");
        if(quartos.contains(null)) throw new InvalidParameterException("Lista de quartos com elemento nulo");

        LocalDate dataInicial = datasOrdenadas[0];
        LocalDate dataFinal = datasOrdenadas[ datasOrdenadas.length - 1 ];
        if(verificarSeQuartosIndisponiveisParaDatas(quartos, dataInicial, dataFinal, gestorDeBaseDeDados))
            throw new InvalidParameterException("Lista de quartos inclui quartos indisponíveis para as datas fornecidas");

        String queryVerificaEmpregadoIdValido = "SELECT * FROM PTDA_BD_03.empregado where id = %d and cargo_id = 1";
        List<String> resultadosEmpregadoID = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificaEmpregadoIdValido, empregadoID));
        if(resultadosEmpregadoID.isEmpty())
            throw new InvalidParameterException("Não existe empregado associado ao ID fornecido ou empregado não tem acesso a registar novas reservas");

        String queryVerificarClienteNIFValido = "SELECT * from PTDA_BD_03.cliente where nif = %d";
        List<String> resultadosClienteNIF = gestorDeBaseDeDados.tryQueryDatabase(String.format(queryVerificarClienteNIFValido, clienteNIF));
        if (resultadosClienteNIF.isEmpty())
            throw new InvalidParameterException("Não existe cliente associado ao NIF fornecido");

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

        Iterator<Integer> iteratorQuarto = quartos.iterator();

        while(iteratorQuarto.hasNext()){
            Integer quarto = iteratorQuarto.next();
            Iterator<LocalDate> iteratorData = datas.iterator();

            while (iteratorData.hasNext()){
                LocalDate data = iteratorData.next();
                String linhaDeValores = String.format("('%s', %s, %s)", data, quarto, reservaID);
                stringBuilderInsertDiasReserva.append(linhaDeValores);

                if(!iteratorQuarto.hasNext() && !iteratorData.hasNext()) continue;
                stringBuilderInsertDiasReserva.append(",");
            }
        }

        String finalInsertDiaReservaQuery = stringBuilderInsertDiasReserva.toString();
        gestorDeBaseDeDados.tryUpdateDatabase(finalInsertDiaReservaQuery);
    }

    /**
     * Esta função serve para gerar Faturas para uma determinada Reserva
     * @param reserva: Número da reserva
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return  retorna a fatura gerada para a reserva pedida
     */
    public Fatura gerarFaturaParaReserva(Reserva reserva, GestorDeBaseDeDados gestorDeBaseDeDados){
        if(gestorDeBaseDeDados == null)
            throw new InvalidParameterException("Gestor de Base de Dados nulo.");

        if (reserva == null)
            throw new InvalidParameterException("Reserva nula.");
        if (reserva.getFatura() != null)
            throw new InvalidParameterException("Não é possível gerar um fatura para a reserva fornecido, reserva já se encontra faturada");


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

    /**
     * Esta função serve para verificar se os quartos estão disponíveis para as datas pretendidas pelo cliente
     * @param quartos: Número do Quarto
     * @param dataInicial: Data Inicial (Data de Check-In)
     * @param dataFinal: Data Final (Data de Check-Out)
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return retorna se está disponível nas datas pretendidas
     */
    protected static boolean verificarSeQuartosIndisponiveisParaDatas(HashSet<Integer> quartos, LocalDate dataInicial, LocalDate dataFinal, GestorDeBaseDeDados gestorDeBaseDeDados){
        if(gestorDeBaseDeDados == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        if(quartos == null) throw new InvalidParameterException("Lista de quartos nula");
        if(quartos.isEmpty()) throw new InvalidParameterException("Lista de quartos vazia");
        if(quartos.contains(null)) throw new InvalidParameterException("Lista de quarto contem elemento nulo");
        if(dataFinal.isBefore(dataInicial)) throw new InvalidParameterException("Data final vem antes da data inicial");

        if(contemQuartoInexistentes(quartos, gestorDeBaseDeDados))
            throw new InvalidParameterException("Lista de quarto contêm quartos inexistentes");

        String baseQueryQuartosIndisponiveis = "SELECT quarto_id FROM PTDA_BD_03.dia_reserva " +
                "where quarto_id in %s and " +
                "data_reserva between '%s' and '%s' " +
                "order by quarto_id asc";

        String finalQueryQuartosIndisponiveis = String.format( baseQueryQuartosIndisponiveis,
                quartos.toString().replace("[", "(").replace("]",")"),
                dataInicial,
                dataFinal);

        List<String> resultadoQuartosIndisponiveis = gestorDeBaseDeDados.tryQueryDatabase(finalQueryQuartosIndisponiveis);
        return !resultadoQuartosIndisponiveis.isEmpty();
    }

    /**
     * Esta função serve para verificar se existe o quarto pretendido pelo Cliente
     * @param quartos: Número do Quarto
     * @param gestorDeBaseDeDados conexão há base de dados
     * @return retorna se existe o quarto pedido pelo utilizador
     */
    protected static boolean contemQuartoInexistentes(HashSet<Integer> quartos, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (gestorDeBaseDeDados == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        if (quartos == null) throw new InvalidParameterException("Lista de quartos nula");
        if (quartos.isEmpty()) throw  new InvalidParameterException("Lista de quartos vazia");
        if (quartos.contains(null)) throw new InvalidParameterException("Lista de quartos com elementos nulos");

        String baseQueryQuartosValidos = "SELECT count(*) FROM PTDA_BD_03.quarto where id in %s";

        String finalQueryQuartosValidos = String.format( baseQueryQuartosValidos,
                quartos.toString().replace("[", "(").replace("]",")"));

        List<String> resultado = gestorDeBaseDeDados.tryQueryDatabase(finalQueryQuartosValidos);
        int quartosComID = Integer.parseInt(resultado.get(0));

        return quartosComID < quartos.size();
    }

}
