package limpeza;

import java.time.LocalDate;

public class RegistoDeLimpeza {
    private LocalDate data;
    private int quartoId;
    private int empregadoId;

    public RegistoDeLimpeza(LocalDate data, int quartoId, int empregadoId){
        this.data=data;
        this.quartoId=quartoId;
        this.empregadoId =empregadoId;
    }

    public LocalDate getData(){return data;}

    public int getQuartoId(){return quartoId;}

    public int getEmpregadoId(){return empregadoId;}
}
