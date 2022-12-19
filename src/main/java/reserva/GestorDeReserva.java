package reserva;

import basededados.GestorDeBaseDeDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    protected boolean adicionarReserva(){ return false; }

    protected float calculaPrecoReserva(){ return 0.0f; }


}
