package reserva;

import java.util.ArrayList;

public class Reserva {

    private final int reservaID;
    private final boolean reservaPaga;
    private Fatura fatura;
    private ArrayList<Integer> quartos;
    private final int clienteNIF;
    private final int empregadoNIF;

    public Reserva(int reservaID, int clienteNIF, int empregadoId, boolean estadoPagamento, Fatura fatura){
        this.reservaID = reservaID;
        this.clienteNIF = clienteNIF;
        this.empregadoNIF = empregadoId;
        this.reservaPaga = estadoPagamento;
        this.fatura = fatura;
    }

    public int getClienteNIF(){ return clienteNIF; }
    public int getReservaID(){ return reservaID; }
    public int getEmpregadoNIF(){ return empregadoNIF; }
    public boolean getEstadoPagamento(){ return reservaPaga; }
    public Fatura getFatura(){ return fatura; }

    protected Fatura gerarFatura(){ return null; }


    @Override
    public String toString() {
        String estadoFatura;
        if(reservaPaga){
            if(fatura == null) estadoFatura = "Reserva paga. Informação da fatura indisponível.";
            else estadoFatura = fatura.toString();
        }else estadoFatura = "Pagamento não efetuado.";

        return String.format("Reseva de Quarto #%d | NIF do Cliente: %d | Empregado ID: %d | %s",
                reservaID, clienteNIF, empregadoNIF,estadoFatura);
    }
}
