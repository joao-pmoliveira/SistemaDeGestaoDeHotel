package limpeza;

import java.util.ArrayList;
import java.util.HashMap;

public class GestorDeLimpeza {
    private HashMap<Integer, RegistoDeLimpeza> registos;

    public GestorDeLimpeza(){}

    protected ArrayList<RegistoDeLimpeza> procurarRegistosPorQuarto(){ return null;}

    protected ArrayList<RegistoDeLimpeza> procurarRegistosPorEmpregadoId(){ return null;}

    protected ArrayList<RegistoDeLimpeza> procurarRegistosPorData(){ return null;}

    protected boolean adicionarRegisto(){ return false;}
}
