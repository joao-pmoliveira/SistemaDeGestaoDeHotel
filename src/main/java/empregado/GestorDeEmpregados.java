package empregado;

import basededados.GestorDeBaseDeDados;
import java.util.List;
import java.util.Locale;

public class GestorDeEmpregados {

    public GestorDeEmpregados(){}

    public Empregado procurarEmpregadoPorNIF(int nif, GestorDeBaseDeDados gestorDeBaseDeDados){
        String pesquisa = String.format("SELECT (id, nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida) FROM empregado WHERE nif = %d",nif);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())return null;

        String [] dados = dadosEmpregado.get(0).split(",");
        Empregado empregado= new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
        return empregado;
    }


    public Empregado procurarEmpregadoPorID(int id, GestorDeBaseDeDados gestorDeBaseDeDados){
        String pesquisa = String.format("SELECT (id, nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida) FROM empregado WHERE id = %d", id);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())return null;

        String [] dados = dadosEmpregado.get(0).split(",");

        Empregado empregado = new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
        return empregado;
    }


    public boolean adicionarEmpregado(String nome, int cargo, String morada, int telefone, int nif, float salario, String horaEntrada, String horaSaida, String passe, GestorDeBaseDeDados gestorDeBaseDeDados){
        String query = String.format(Locale.US, "REPLACE INTO empregado(nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida, palavra_passe) VALUES ('%s', %d, '%s', %d, %d, %f, '%s', '%s', '%s')",
                nome, cargo, morada, telefone, nif, salario, horaEntrada, horaSaida, passe);
       try {
           gestorDeBaseDeDados.tryUpdateDatabase(query);
           return true;
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }
}
