package empregado;

import basededados.GestorDeBaseDeDados;
import utils.Validador;

import java.security.InvalidParameterException;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class GestorDeEmpregados {

    public GestorDeEmpregados(){}

    /**
     * Esta função é para procurar o empregado através do seu NIF
     * @param nif: NIF do Empregado já registado na base de dados
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return retorna o empregado com o NIF pesquisado pelo utilizador
     */
    public Empregado procurarEmpregadoPorNIF(int nif, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (gestorDeBaseDeDados==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }
        String pesquisa = String.format("SELECT id, nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida FROM empregado WHERE nif = %d",nif);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())
            throw new InvalidParameterException("Não encontrado cliente para o nif fornecido");

        String [] dados = dadosEmpregado.get(0).split(",");
        Empregado empregado= new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
        return empregado;
    }

    /**
     * Esta função é para procurar o empregado através do seu ID
     * @param id: ID do Empregado já registado na base de dados
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return retorna o empregado com o id pesquisado pelo utilizador
     */
    public Empregado procurarEmpregadoPorID(int id, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (gestorDeBaseDeDados==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }
        String pesquisa = String.format("SELECT id, nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida FROM empregado WHERE id = %d", id);
        List<String> dadosEmpregado = gestorDeBaseDeDados.tryQueryDatabase(pesquisa);

        if(dadosEmpregado.isEmpty())
            throw new InvalidParameterException("Não econtrado cliente para o ID fornecido");

        String [] dados = dadosEmpregado.get(0).split(",");

        Empregado empregado = new Empregado(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]), dados[3], Integer.parseInt(dados[4]), Integer.parseInt(dados[5]), Float.parseFloat(dados[6]), dados[7], dados[8]);
        return empregado;
    }

    /**
     * Esta função serve para Adicionar um empregado na base de dados
     * @param nome: Nome do Empregado
     * @param cargo: Cargo do Empregado
     * @param morada: Morada do Empregado
     * @param telefone: Telefone do Empregado
     * @param nif: NIF do Empregado
     * @param salario: Salario do Empregado
     * @param horaEntrada: Hora De Entrada no trabalho do Empregado
     * @param horaSaida: Hora De Saida do trabalho do Empregado
     * @param passe: Password para aceder há aplicação
     * @param gestorDeBaseDeDados: conexão há base de dados
     * @return
     */
    public boolean adicionarEmpregado(String nome, int cargo, String morada, int telefone, int nif, float salario, LocalTime horaEntrada, LocalTime horaSaida, String passe, GestorDeBaseDeDados gestorDeBaseDeDados){
        if (gestorDeBaseDeDados==null){
            throw new InvalidParameterException("Gestor de base de dados nulo");
        }

        String nomeValido = Validador.validoNome(nome);
        if (nomeValido == null)
            throw new InvalidParameterException("Nome inválido");

        String pesquisaCargoId = String.format("SELECT id FROM cargo WHERE id = %d",cargo);
        List<String> dadosCargo = gestorDeBaseDeDados.tryQueryDatabase(pesquisaCargoId);
        if (dadosCargo.isEmpty())
            throw new InvalidParameterException("Cargo de empregado inválido");

        String moradaValido = Validador.validaMorada(morada);
        if (moradaValido == null)
            throw new InvalidParameterException("Morada inválida");


        String telefoneValido = Validador.validaTelefone(String.valueOf(telefone));
        if(telefoneValido == null)
            throw new InvalidParameterException("Número telefone inválido");


        String nifValido = Validador.validaNIF(String.valueOf(nif));
        if (nifValido == null)
            throw new InvalidParameterException("Nif introduzido inválido");
        String pesquisaEmpregadoNif = String.format("SELECT nif from empregado WHERE nif = %s",nifValido);
        List<String> dadosNif = gestorDeBaseDeDados.tryQueryDatabase(pesquisaEmpregadoNif);
        if (!dadosNif.isEmpty())
            throw new InvalidParameterException("Nif introduzido já existe");


        if(salario < 0)
            throw new InvalidParameterException("Salário não pode ter valor negativo");


        String passeValida = Validador.validaPassword(passe);
        if (passeValida == null)
            throw new InvalidParameterException("Campo de Palavra-Passe inválido");


        String query = String.format(Locale.US, "REPLACE INTO empregado(nome, cargo_id, morada, telefone, nif, salario, hora_entrada, hora_saida, palavra_passe) VALUES ('%s', %d, '%s', %s, %s, %f, '%s', '%s', aes_encrypt('%s', '%s'))",
                nomeValido, cargo, moradaValido, telefoneValido, nifValido, salario, horaEntrada, horaSaida, passeValida, GestorDeBaseDeDados.getEncryptKey());
            gestorDeBaseDeDados.tryUpdateDatabase(query);
            return true;

    }
}
