package cliente;

public class Cliente {
    private int nif;
    private String nome;
    private int telefone;

    public Cliente(int nif, String nome, int telefone){
        this.nif = nif;
        this.nome = nome;
        this.telefone = telefone;
    }

    public int getNif(){return nif;}
    public String getNome(){return nome;}
    public int getTelefone(){return telefone;}
}
