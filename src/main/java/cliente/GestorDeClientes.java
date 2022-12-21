package cliente;

import java.util.HashMap;

public class GestorDeClientes {
    private HashMap<Integer, Cliente> clientes;

    public GestorDeClientes(){}

    protected boolean adicionarCliente(int nif, String nome, int telefone, GestorDeBaseDeDados gestorBD){
        String query = String.format("REPLACE INTO Clientes VALUES ('%d', '%s', '%d')", nif, nome, telefone);

        try {
            gestorBD.tryUpdateDatabase(query);
            adicionarClienteCache(nif, nome, telefone);
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void adicionarClienteCache(int nif, String nome, int telefone){
        Cliente cliente = new Cliente(nif, nome, telefone);
        clientes.put(nif, cliente);
    }

    private void adicionarClienteCache(Cliente cliente){
        if(cliente == null){
            return;
        }else {
            clientes.put(cliente.getNif(), cliente);
        }
    }
}
