package quarto;

public class Quarto {
    private int quartoId;
    private int layoutId;
    private float precoBase;
    private String layoutNome;
    private String layoutDescricao;

    public Quarto(int quartoId, int layoutId, float precoBase, String layoutNome, String layoutDescricao){
        this.quartoId = quartoId;
        this.layoutId = layoutId;
        this.precoBase = precoBase;
        this.layoutNome = layoutNome;
        this.layoutDescricao = layoutDescricao;
    }

    /**
     *
     * @return Cada um retorna a variavel correspondente
     */
    public int getQuartoId(){return quartoId;}
    public int getLayoutId(){return layoutId;}
    public float getPrecoBase(){return precoBase;}
    public String getLayoutNome(){return layoutNome;}
    public String getLayoutDescricao(){return layoutDescricao;}
}
