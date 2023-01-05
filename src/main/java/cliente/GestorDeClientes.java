package cliente;

import basededados.GestorDeBaseDeDados;
import java.util.List;

public class GestorDeClientes {

    public GestorDeClientes(){
    }

    public Cliente procurarClientePorNIF(int nif, GestorDeBaseDeDados gestorBD){
        String query = String.format( "SELECT * FROM cliente WHERE nif =  %d", nif);
        List<String> dadosCliente = gestorBD.tryQueryDatabase(query);

        if( dadosCliente.isEmpty() ) return null;

        String [] dados = dadosCliente.get(0).split(",");
        Cliente cliente = new Cliente(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
        return cliente;
    }

    public boolean adicionarCliente(int nif, String nome, int telefone, GestorDeBaseDeDados gestorBD){
        String query = String.format("REPLACE INTO cliente VALUES ('%d', '%s', '%d')", nif, nome, telefone);

        try {
            gestorBD.tryUpdateDatabase(query);
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
