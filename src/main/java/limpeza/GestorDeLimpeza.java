package limpeza;


import basededados.GestorDeBaseDeDados;


import java.util.HashMap;
import java.util.List;

public class GestorDeLimpeza {
    private HashMap<Integer, RegistoDeLimpeza> registos;

    public GestorDeLimpeza(){
        this.registos = new HashMap<Integer, RegistoDeLimpeza>();
    }

    public RegistoDeLimpeza procurarRegistosPorQuarto(int numeroQuarto, GestorDeBaseDeDados gestorBD){


        String query= String.format("SELECT * FROM registo_limpeza WHERE quarto_id = %d ",numeroQuarto );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        if( registoslimpeza.isEmpty() ) return null;
        String [] dados = registoslimpeza.get(0).split(",");
        RegistoDeLimpeza valores = new RegistoDeLimpeza((dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
        return valores;

    }

    public RegistoDeLimpeza procurarRegistosPorEmpregadoId(GestorDeBaseDeDados gestorBD, int idEmpregado){



        String query= String.format("SELECT * FROM registo_limpeza WHERE empregado_id = %d ",idEmpregado );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        if( registoslimpeza.isEmpty() ) return null;
        String [] dados = registoslimpeza.get(0).split(",");
        RegistoDeLimpeza valores = new RegistoDeLimpeza((dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
        return valores;

    }

    protected RegistoDeLimpeza procurarRegistosPorData(GestorDeBaseDeDados gestorBD, String data){


        String query= String.format("SELECT * FROM registo_limpeza WHERE data_hora = %d ",data );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        if( registoslimpeza.isEmpty() ) return null;
        String [] dados = registoslimpeza.get(0).split(",");
        RegistoDeLimpeza valores = new RegistoDeLimpeza((dados[0]), Integer.parseInt(dados[1]), Integer.parseInt(dados[2]));
        return valores;

        }

    protected boolean adicionarRegisto(String data, int numeroQuarto, int idEmpregado, GestorDeBaseDeDados gestorBD){
        String query = String.format
                ("INSERT INTO registo_limpeza (`data_hora`, `quarto_id`, `empregado_id`)  VALUES ('%d', '%s', '%d')", data, numeroQuarto, idEmpregado);

        try {
            gestorBD.tryUpdateDatabase(query);
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
