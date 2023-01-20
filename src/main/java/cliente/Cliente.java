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

    /**
     *
     * @return Cada um retorna a variavel correspondente
     */
    public int getNIF(){return nif;}
    public String getNome(){return nome;}
    public int getTelefone(){return telefone;}
}
