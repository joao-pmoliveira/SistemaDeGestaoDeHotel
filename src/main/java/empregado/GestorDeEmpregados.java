package empregado;

import basededados.GestorDeBaseDeDados;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class GestorDeEmpregados {

    private HashMap <Integer,Empregado> empregados;

    public GestorDeEmpregados(){this.empregados = new HashMap<>(); }

    public Empregado procurarEmpregadoPorNIF(int nif, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (empregados.containsKey(nif))return empregados.get(nif);

        String pesquisa = String.format("SELECT * FROM empregado WHERE nif = %d",nif);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())return null;

        String [] dados = dadosEmpregado.get(0).split(",");
        return new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
    }


    public Empregado procurarEmpregadoPorID(int id, GestorDeBaseDeDados gestorDeBaseDeDados){

        for(Map.Entry<Integer,Empregado> setEmpregado: empregados.entrySet()){
            Empregado empregado = setEmpregado.getValue();
            if (empregado.getId()== id){
                return empregado;
            }
        }

        String pesquisa = String.format("SELECT * FROM empregado WHERE id = %d", id);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())return null;

        String [] dados = dadosEmpregado.get(0).split(",");

        Empregado empregado = new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
        adicionarEmpregadoCache(empregado);
        return empregado;
    }


    public boolean adicionarEmpregado(String nome, int cargo, String morada, int telefone, int nif, float salario, String horaEntrada, String horaSaida, String passe, GestorDeBaseDeDados gestorDeBaseDeDados){
        String query = String.format(Locale.US, "REPLACE INTO empregado(nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida, palavra_passe) VALUES ('%s', %d, '%s', %d, %d, %f, '%s', '%s', '%s')",
                nome, cargo, morada, telefone, nif, salario, horaEntrada, horaSaida, passe);
       try {
           gestorDeBaseDeDados.tryUpdateDatabase(query);
           List<String> resultados = gestorDeBaseDeDados.tryQueryDatabase("select last_insert_id()");

           int clienteID = Integer.parseInt(resultados.get(0));
           adicionarEmpregadoCache(clienteID, nome, cargo, morada, telefone, nif, salario, horaEntrada,horaSaida);
           return true;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }


    private void adicionarEmpregadoCache(int id, String nome, int cargo, String morada, int telefone, int nif, float salario, String horaEntrada, String horaSaida){
        Empregado empregado = new Empregado(id,nome, cargo, morada, telefone, nif, salario, horaEntrada,horaSaida);
        empregados.put(nif , empregado);
    }


    private void adicionarEmpregadoCache(Empregado empregado){
        if(empregado == null)return;
        empregados.put(empregado.getNif(), empregado);
    }
}
