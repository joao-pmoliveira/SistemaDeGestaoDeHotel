package cliente;

import basededados.GestorDeBaseDeDados;

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
        if(nif < 1 || nif > Integer.MAX_VALUE) throw new InvalidParameterException("NIF Inválido");
        if(nome.isEmpty() || nome.isBlank()) throw new InvalidParameterException("Nome Inválido");
        if(telefone < 1 || telefone > Integer.MAX_VALUE) throw new InvalidParameterException("Telefone Inválido");
        if(gestorBD == null) throw new InvalidParameterException("Gestor de Base de Dados nulo.");
        String query = String.format("REPLACE INTO cliente VALUES ('%d', '%s', '%d')", nif, nome, telefone);
        gestorBD.tryUpdateDatabase(query);
        return true;
    }
}
