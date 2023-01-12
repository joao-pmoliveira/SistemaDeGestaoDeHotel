package limpeza;

public class RegistoDeLimpeza {
    private String data;
    private int quartoId;
    private int empregadoId;

    public RegistoDeLimpeza( String data, int quartoId, int empregadoId){
        this.data=data;
        this.quartoId=quartoId;
        this.empregadoId =empregadoId;
    }

    public String getData(){return data;}

    public int getQuartoId(){return quartoId;}

    public int getEmpregadoId(){return empregadoId;}
}
