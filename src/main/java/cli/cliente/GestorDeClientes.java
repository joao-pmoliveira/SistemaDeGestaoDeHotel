package cli.cliente;

import basededados.GestorDeBaseDeDados;
import utils.Validador;

import java.security.InvalidParameterException;
import java.util.List;

public class GestorDeClientes {

    public GestorDeClientes(){
    }

    public Cliente procurarClientePorNIF(int nif, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String query = String.format( "SELECT * FROM cliente WHERE nif =  %d", nif);
        List<String> dadosCliente = gestorBD.tryQueryDatabase(query);

        if( dadosCliente.isEmpty() ) throw new InvalidParameterException("Não existe cliente associado ao NIF fornecido");

        String [] dados = dadosCliente.get(0).split(",");
        Cliente cliente = new Cliente(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
        return cliente;
    }

    public boolean adicionarCliente(int nif, String nome, int telefone, GestorDeBaseDeDados gestorBD){
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String NIFValido = Validador.validaNIF(String.valueOf(nif));
        if(NIFValido == null) throw new InvalidParameterException("NIF Inválido");
        String queryNif = String.format( "SELECT * FROM cliente WHERE nif =  %d", nif);
        if(!gestorBD.tryQueryDatabase(queryNif).isEmpty()) throw new InvalidParameterException("NIF já existente");
        String NomeValido = Validador.validoNome(nome);
        if(NomeValido == null) throw new InvalidParameterException("Nome Inválido");
        String TelefoneValido = Validador.validaTelefone(String.valueOf(telefone));
        if(TelefoneValido == null) throw new InvalidParameterException("Telefone Inválido");

        String query = String.format("REPLACE INTO cliente VALUES ('%d', '%s', '%d')", Integer.valueOf(NIFValido), NomeValido, Integer.valueOf(TelefoneValido));
        gestorBD.tryUpdateDatabase(query);
        return true;
    }
}
