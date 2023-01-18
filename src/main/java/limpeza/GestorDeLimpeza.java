package limpeza;


import basededados.GestorDeBaseDeDados;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GestorDeLimpeza {




    public ArrayList<RegistoDeLimpeza> procurarRegistosPorQuarto(int quartoId, GestorDeBaseDeDados gestorBD){
        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE quarto_id = %d ",quartoId );
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

        String query = String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE empregado_id = %d ", empregadoId);
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

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE data_hora = %s",data );
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
                ("INSERT INTO registo_limpeza (`data_hora`, `quarto_id`, `empregado_id`)  VALUES ('%s', '%d', '%d')", data, quartoId, empregadoId);

        try {
            gestorBD.tryUpdateDatabase(query);
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
