package reserva;

import java.util.ArrayList;

public class Reserva {


    private final int reservaID;
    private final int clienteNIF;
    private final int empregadoNIF;
    private boolean reservaPaga;
    private float precoAtual;
    private Fatura fatura;
    private final ArrayList<Integer> quartos;


    public Reserva(int reservaID, int clienteNIF, int empregadoId, float precoAtual, boolean reservaPaga,Fatura fatura){
        this.reservaID = reservaID;
        this.clienteNIF = clienteNIF;
        this.empregadoNIF = empregadoId;
        this.precoAtual = precoAtual;
        this.reservaPaga = reservaPaga;
        this.fatura = fatura;
        quartos = new ArrayList<>();
    }

    public int getClienteNIF(){ return clienteNIF; }
    public int getReservaID(){ return reservaID; }
    public int getEmpregadoID(){ return empregadoNIF; }
    public boolean getEstadoPagamento(){ return reservaPaga; }
    public float getPrecoAtual(){ return precoAtual; }
    public Fatura getFatura(){ return fatura; }

    public void setReservaPaga(boolean estado){
        this.reservaPaga = estado;
    }
    public void setFatura(Fatura fatura){
        if(fatura == null) return;
        this.fatura = fatura;
    }
    public void somarAoPrecoAtual(float precoAdicional){
        if(precoAdicional <= 0) return;
        precoAtual += precoAdicional;
    }

    public void adicionarQuarto(int quartoID){
        if (quartos.contains(quartoID)) return;
        quartos.add(quartoID);
    }


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
