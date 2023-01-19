package limpeza;


import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GestorDeLimpeza {



    /**
     * Este método é para procurar o número do quarto nos registos de limpeza pretendido pelo utilizador e gerar uma lista
     * @param quartoId : número do quarto registado na base de dados
     * @param gestorBD : conexão há base de dados
     * @return gera uma lista dos registos de limpeza com o ID(Número de Quarto) pretendido pelo uilizador
     */
    public ArrayList<RegistoDeLimpeza> procurarRegistosPorQuarto(int quartoId, GestorDeBaseDeDados gestorBD){
        if (gestorBD==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }

        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE quarto_id = %d ",quartoId );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        if(registoslimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para o quarto pedido!");

       for (String ficheiro:registoslimpeza) {
           String[] registos = ficheiro.split(",");
           RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
           limpezas.add(registo);
       }
        return limpezas;

    }

    /**
     * Este método serve para procurar na base de dados nos registos de limpeza os quartos limpos por Id do empregado escolhido pelo utilizador
     * @param empregadoId : número do empregado na base de dados
     * @param gestorBD
     * @return retorna uma lista dos registos de limpeza de quarto com o Id do empregado procurado pelo utilizador
     */
    public ArrayList<RegistoDeLimpeza> procurarRegistosPorEmpregadoId( int empregadoId, GestorDeBaseDeDados gestorBD) {
        if (gestorBD==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }

        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query = String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE empregado_id = %d ", empregadoId);
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);

        if(registoslimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para o empregado pedido!");

        for (String  ficheiro : registoslimpeza){
            String[] registos = ficheiro.split(",");
        RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
        limpezas.add(registo);
    }
        return limpezas;

    }

    /**
     * Este parametro serve para procurar no registo de limpezas as datas pretendidas pelo utilizador
     * @param data : data do dia do registo de limpeza de cada quarto
     * @param gestorBD
     * @return gera uma lista dos registos de limpeza da data escolhida pelo utilizador
     */

    public ArrayList<RegistoDeLimpeza> procurarRegistosPorData(String data, GestorDeBaseDeDados gestorBD){
        if (gestorBD==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }

        ArrayList<RegistoDeLimpeza> limpezas = new ArrayList<RegistoDeLimpeza>();

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,layout.nome,\n" +
                "registo_limpeza.empregado_id,empregado.nome FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE data_hora = %s",data );
        List<String> registoslimpeza = gestorBD.tryQueryDatabase(query);


        if(registoslimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para a data pedida!");

        for (String  ficheiro:registoslimpeza) {
            String[] registos =  ficheiro.split(",");
            RegistoDeLimpeza registo = new RegistoDeLimpeza((registos[0]), Integer.parseInt(registos[1]), Integer.parseInt(registos[2]));
            limpezas.add(registo);
        }
            return limpezas;


        }


    /**
     * Serve para adiconar um novo registo de limpezas numa data especifica, num quarto e o empregado
     * @param data : data da limpeza do quarto
     * @param quartoId : quarto que foi limpo
     * @param empregadoId : empregado/a que o limpou
     * @param gestorBD
     * @return
     */
    public boolean adicionarRegisto(String data, int quartoId, int empregadoId, GestorDeBaseDeDados gestorBD){

        if (gestorBD==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }

        //Verifica se a data introduzida é valida
        String datainvalida=String.valueOf(data);
        if (datainvalida == null)
            throw new InvalidParameterException("Data Inválida");

        //Verifica se o quarto ID é valido e compara se existe um quarto inserido na base de dados com esse ID
        String idquarto =String.valueOf(quartoId);
        int quartovalido=quartoId;
        if (idquarto == null )
            throw new InvalidParameterException("ID do Quarto Inválido");
        String pesquisarquarto = String.format("SELECT quarto.id from quarto WHERE quarto.id = %d",quartovalido);
        List<String> quartosdados = gestorBD.tryQueryDatabase(pesquisarquarto);
        if (!quartosdados.isEmpty())
            throw new InvalidParameterException("Quarto escolhido não existe!");


        //Verifica se o empregado ID é valido e compara se existe um empregado inserido na base de dados com esse ID
        String IdInvalido =String.valueOf(empregadoId);
        int empregadoValido=empregadoId;
        if (IdInvalido == null )
            throw new InvalidParameterException("ID do Empregado Inválido");
        String pesquisarempregado = String.format("SELECT empregado.id from empregado WHERE empregado.id = %d",empregadoValido);
        List<String> emregadodados = gestorBD.tryQueryDatabase(pesquisarempregado);
        if (!emregadodados.isEmpty())
            throw new InvalidParameterException("Empregado escolhido não existe!");




        String query = String.format
                (Locale.US,"INSERT INTO registo_limpeza (`data_hora`, `quarto_id`, `empregado_id`)  VALUES ('%s', '%d', '%d')", data, quartovalido, empregadoValido);

            return true;
    }
}
