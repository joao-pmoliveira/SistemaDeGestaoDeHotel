package quarto;

import java.util.ArrayList;
import java.util.HashMap;

public class GestorDeQuartos {
    private HashMap<Integer, Quarto> quartos;

    public GestorDeQuartos(){   }

    protected Quarto procurarQuartoPorID(){return null;}

    protected ArrayList<Quarto> procurarQuartoPorLayout(){return null;}

    protected ArrayList<Quarto> procurarQuartosDisponiveis(){return null;}
}
