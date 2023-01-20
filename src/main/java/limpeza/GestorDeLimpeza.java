package limpeza;


import basededados.GestorDeBaseDeDados;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GestorDeLimpeza {
    /**
     * Esta função é para procurar o número do quarto nos registos de limpeza pretendido pelo utilizador e gerar uma lista
     * @param quartoId : número do quarto registado na base de dados
     * @param gestorDeBaseDeDados : conexão há base de dados
     * @return gera uma lista dos registos de limpeza com o ID(Número de Quarto) pretendido pelo uilizador
     */
    public List<RegistoDeLimpeza> procurarRegistosPorQuarto(int quartoId, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (gestorDeBaseDeDados==null)
            throw new InvalidParameterException("Gestor de base de dados nulo");

        List<RegistoDeLimpeza> registosDeLimpeza = new ArrayList<>();
        String queryQuartoId= String.format("Select * From quarto Where id= %d", quartoId);
        if (gestorDeBaseDeDados.tryQueryDatabase(queryQuartoId).isEmpty())
            throw  new InvalidParameterException("Não existe Quarto associado a esse ID!");

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,\n" +
                "registo_limpeza.empregado_id FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE quarto_id = %d ",quartoId );
        List<String> resultadosRegistoDeLimpeza = gestorDeBaseDeDados.tryQueryDatabase(query);

        if(resultadosRegistoDeLimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para o quarto pedido!");

       for (String linha : resultadosRegistoDeLimpeza) {
           String[] dados = linha.split(",");
           String[] dataInput = dados[0].split("-");
           int ano = Integer.parseInt(dataInput[0]);
           int mes = Integer.parseInt(dataInput[1]);
           int dia = Integer.parseInt(dataInput[2]);
           LocalDate data=LocalDate.of(ano,mes,dia);

           int quartoID = Integer.parseInt(dados[1]);
           int empregadoID = Integer.parseInt(dados[2]);

           RegistoDeLimpeza registo = new RegistoDeLimpeza(data, quartoID, empregadoID);
           registosDeLimpeza.add(registo);
       }
        return registosDeLimpeza;
    }

    /**
     * Esta função serve para procurar na base de dados nos registos de limpeza os quartos limpos por Id do empregado escolhido pelo utilizador
     * @param empregadoId : número do empregado na base de dados
     * @param gestorBD
     * @return retorna uma lista dos registos de limpeza de quarto com o Id do empregado procurado pelo utilizador
     */
    public List<RegistoDeLimpeza> procurarRegistosPorEmpregadoId( int empregadoId, GestorDeBaseDeDados gestorBD) {
        if (gestorBD==null)
            throw new InvalidParameterException("Gestor de base de dados nulo");

        List<RegistoDeLimpeza> registosDeLimpeza = new ArrayList<>();

        String queryEmpregadoId= String.format("Select * From empregado Where id= %d", empregadoId);
        if (gestorBD.tryQueryDatabase(queryEmpregadoId).isEmpty())
            throw  new InvalidParameterException("Não existe Empregado associado a esse ID!");

        String query = String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,\n" +
                "registo_limpeza.empregado_id FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE empregado_id = %d ", empregadoId);
        List<String> resultadosRegistoDeLimpeza = gestorBD.tryQueryDatabase(query);

        if(resultadosRegistoDeLimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para o empregado pedido!");

        for (String  linha : resultadosRegistoDeLimpeza){
            String[] dados = linha.split(",");
            String[] dataInput = dados[0].split("-");
            int ano= Integer.parseInt(dataInput[0]);
            int mes= Integer.parseInt(dataInput[1]);
            int dia= Integer.parseInt(dataInput[2]);
            LocalDate data=LocalDate.of(ano,mes,dia);

            int quartoID = Integer.parseInt(dados[1]);
            int empregadoID = Integer.parseInt(dados[2]);

            RegistoDeLimpeza registo = new RegistoDeLimpeza(data, quartoID, empregadoID);
            registosDeLimpeza.add(registo);
        }
        return registosDeLimpeza;
    }

    /**
     * Esta função serve para procurar no registo de limpezas as datas pretendidas pelo utilizador
     * @param data : data do dia do registo de limpeza de cada quarto
     * @param gestorBD
     * @return gera uma lista dos registos de limpeza da data escolhida pelo utilizador
     */

    public List<RegistoDeLimpeza> procurarRegistosPorData(LocalDate data, GestorDeBaseDeDados gestorBD){
        if (gestorBD==null)
            throw new InvalidParameterException("Gestor de base de dados nulo");

        List<RegistoDeLimpeza> registosDeLimpeza = new ArrayList<>();

        String query= String.format("SELECT registo_limpeza.data_hora,registo_limpeza.quarto_id,\n" +
                "registo_limpeza.empregado_id FROM registo_limpeza \n" +
                "INNER JOIN quarto on quarto.id=registo_limpeza.quarto_id \n" +
                "INNER JOIN layout on layout.id=quarto.layout_id\n" +
                "INNER JOIN empregado on empregado.id=registo_limpeza.empregado_id\n" +
                " WHERE data_hora = '%s'",data );
        List<String> resultadosRegistoDeLimpeza = gestorBD.tryQueryDatabase(query);


        if(resultadosRegistoDeLimpeza.isEmpty())
            throw new InvalidParameterException("Não encontrado registo de limpeza para a data pedida!");

        for (String  linha : resultadosRegistoDeLimpeza) {
            String[] dados =  linha.split(",");
            String[] dataInput = dados[0].split("-");
            int ano = Integer.parseInt(dataInput[0]);
            int mes = Integer.parseInt(dataInput[1]);
            int dia = Integer.parseInt(dataInput[2]);
            LocalDate dataRegisto = LocalDate.of(ano,mes,dia);

            int quartoID = Integer.parseInt(dados[1]);
            int empregadoID = Integer.parseInt(dados[2]);

            RegistoDeLimpeza registo = new RegistoDeLimpeza(dataRegisto, quartoID, empregadoID);
            registosDeLimpeza.add(registo);
        }

        return registosDeLimpeza;
    }


    /**
     * Esta função serve para adiconar um novo registo de limpezas numa data especifica, num quarto e o empregado
     * @param data : data da limpeza do quarto
     * @param quartoId : quarto que foi limpo
     * @param empregadoId : empregado/a que o limpou
     * @param gestorDeBaseDeDados
     * @return
     */
    public boolean adicionarRegisto(LocalDate data, int quartoId, int empregadoId, GestorDeBaseDeDados gestorDeBaseDeDados){

        if (gestorDeBaseDeDados==null)
            throw new InvalidParameterException("Gestor de base de dados nulo");

        //Verifica se a data introduzida é valida
        if (data == null)
            throw new InvalidParameterException("Data Inválida");

        //Verifica se o quarto ID é valido e compara se existe um quarto inserido na base de dados com esse ID
        String pesquisarquarto = String.format("SELECT quarto.id from quarto WHERE quarto.id = %d", quartoId);
        List<String> quartosdados = gestorDeBaseDeDados.tryQueryDatabase(pesquisarquarto);
        if (quartosdados.isEmpty())
            throw new InvalidParameterException("Não existe Quarto associado a esse ID!");

        //Verifica se o empregado ID é valido e compara se existe um empregado inserido na base de dados com esse ID
        String pesquisarempregado = String.format("SELECT empregado.id from empregado WHERE empregado.id = %d", empregadoId);
        List<String> emregadodados = gestorDeBaseDeDados.tryQueryDatabase(pesquisarempregado);
        if (emregadodados.isEmpty())
            throw new InvalidParameterException("Não existe Empregado associado a esse ID!");

        String query = String.format(Locale.US,"INSERT INTO registo_limpeza (`data_hora`, `quarto_id`, `empregado_id`) " +
                "VALUES ('%s', '%d', '%d')", data, quartoId, empregadoId);
        gestorDeBaseDeDados.tryUpdateDatabase(query);
        return true;
    }
}
