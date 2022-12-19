package reserva;

public class Fatura {
    private final int faturaId;
    private final float montanteFinal;

    public Fatura(int id, float montanteFinal){
        this.faturaId = id;
        this.montanteFinal = montanteFinal;
    }

    @Override
    public String toString() {
        return String.format("Fatura #%d | Montante pago: %.2f€", faturaId, montanteFinal);
    }
}
