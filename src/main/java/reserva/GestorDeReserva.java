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
        ArrayList<Reserva> reservasEncontradas = new ArrayList<>();

        String query = String.format("SELECT * FROM reserva WHERE reserva.cliente_nif = %d and reserva.fatura_id IS NULL", nifCliente);
        List<String> linhasRelacaoReserva = gestorDeBaseDeDados.tryQueryDatabase(query);
        if(linhasRelacaoReserva.isEmpty()) return null;

        for (String linha : linhasRelacaoReserva){
            String[] dadosLinhas = linha.split(",");
            int reservaID = Integer.parseInt(dadosLinhas[0]);
            int clienteNIF = Integer.parseInt(dadosLinhas[1]);
            int empregadoID = Integer.parseInt(dadosLinhas[2]);
            boolean estadoPagamento = !dadosLinhas[3].equals("0");
            Fatura fatura = null;

            Reserva reserva = new Reserva(reservaID, clienteNIF, empregadoID, estadoPagamento, fatura);
            reservasEncontradas.add(reserva);
        }
        return reservasEncontradas;
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

    protected float calculaPrecoReserva(){ return 0.0f; }


}
