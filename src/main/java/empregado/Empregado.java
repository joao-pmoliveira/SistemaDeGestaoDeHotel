package empregado;

public class Empregado {

    private int id;
    private String nome;
    private int cargo;
    private String morada;
    private int telefone;
    private int nif;
    private float salario;
    private String horaEntrada;
    private String horaSaida;



    public Empregado(int id, String nome, int cargo, String morada, int telefone, int nif, float salario, String horaEntrada, String horaSaida){
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.telefone = telefone;
        this.cargo = cargo;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.morada = morada;
        this.salario = salario;

    }

    public int getId (){return id;}
    public String getNome(){return nome;}
    public int getNif(){return nif;}
    public int getTelefone(){return telefone;}
    public int getCargo(){return cargo;}
    public String getHoraEntrada(){return horaEntrada;}
    public String getHoraSaida(){return horaSaida;}
    public String getMorada(){return morada;}
    public float getSalario(){return salario;}



}
