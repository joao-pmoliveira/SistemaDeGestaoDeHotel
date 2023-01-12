package limpeza;


import basededados.GestorDeBaseDeDados;
import quarto.Quarto;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GestorDeLimpeza {




    public ArrayList<RegistoDeLimpeza> procurarRegistosPorQuarto(int quartoId, GestorDeBaseDeDados gestorBD){
        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query= String.format("SELECT * FROM registo_limpeza WHERE quarto_id = %d ",quartoId );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

       for (String q:registoslimpeza) {
           String[] registos = q.split(",");
           RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
           limpezas.add(registo);
       }
        return limpezas;

    }

    public ArrayList<RegistoDeLimpeza> procurarRegistosPorEmpregadoId( int empregadoId, GestorDeBaseDeDados gestorBD) {
        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query = String.format("SELECT * FROM registo_limpeza WHERE empregado_id = %d ", empregadoId);
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        for (String q : registoslimpeza){
            String[] registos = q.split(",");
        RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
        limpezas.add(registo);
    }
        return limpezas;

    }

    public ArrayList<RegistoDeLimpeza> procurarRegistosPorData(String data, GestorDeBaseDeDados gestorBD){
        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query= String.format("SELECT * FROM registo_limpeza WHERE data_hora = %d ",data );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);


        for (String q:registoslimpeza) {
            String[] registos = q.split(",");
            RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
            limpezas.add(registo);
        }
            return limpezas;


        }

    public boolean adicionarRegisto(String data, int quartoId, int empregadoId, GestorDeBaseDeDados gestorBD){
        String query = String.format
                ("INSERT INTO registo_limpeza (`data_hora`, `quarto_id`, `empregado_id`)  VALUES ('%d', '%s', '%d')", data, quartoId, empregadoId);

        try {
            gestorBD.tryUpdateDatabase(query);
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
