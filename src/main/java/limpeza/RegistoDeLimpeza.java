package limpeza;

public class RegistoDeLimpeza {
    private String data;
    private int numeroQuarto;
    private int idEmpregado;

    public RegistoDeLimpeza( String data, int numeroQuarto, int idEmpregado){
        this.data=data;
        this.numeroQuarto=numeroQuarto;
        this.idEmpregado=idEmpregado;
    }

    public String getData(){return data;}

    public int getNumeroQuarto(){return numeroQuarto;}

    public int getIdEmpregado(){return idEmpregado;}
}
