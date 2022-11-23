package reserva;

import java.util.ArrayList;

public class Reserva {

    private int reservaId;
    private boolean reservaPaga;
    private Fatura fatura;
    private ArrayList<Integer> quartos;
    private int clientNIF;

    protected Fatura gerarFatura(){ return null; }
}
