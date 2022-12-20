package cliente;

import basededados.GestorDeBaseDeDados;

import java.util.HashMap;
import java.util.List;

public class GestorDeClientes {
    private HashMap<Integer, Cliente> clientes;

    public GestorDeClientes(){
        this.clientes = new HashMap<Integer, Cliente>();
    }

    protected Cliente procurarClientePorNIF(int nif, GestorDeBaseDeDados gestorBD){
        if(clientes.containsKey(nif)){
            return clientes.get(nif);
        }else{
            String query = String.format("SELECT * FROM Clientes WHERE Nif=%d", nif);
            List<String> dadosCliente = gestorBD.tryQueryDatabase(query);
            if(dadosCliente.isEmpty()){
                return null;
            }else{
                String[] dados = dadosCliente.get(0).split(",");
                Cliente cliente = new Cliente(Integer.parseInt(dados[0]), dados[1], Integer.parseInt(dados[2]));
                adicionarClienteCache(cliente);
                return cliente;
            }
        }
    }

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
