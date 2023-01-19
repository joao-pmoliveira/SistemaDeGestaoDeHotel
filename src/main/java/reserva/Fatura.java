package reserva;

public class Fatura {
    private final int faturaId;
    private final float montanteFinal;

    public Fatura(int id, float montanteFinal){
        this.faturaId = id;
        this.montanteFinal = montanteFinal;
    }

    public int getFaturaID(){ return faturaId; }
    public float getMontanteFinal(){ return montanteFinal; }

    @Override
    public String toString() {
        return String.format("Fatura #%d | Montante pago: %.2f euros", faturaId, montanteFinal);
    }
}
