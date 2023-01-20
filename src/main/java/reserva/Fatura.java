package reserva;

public class Fatura {
    private final int faturaId;
    private final float montanteFinal;

    public Fatura(int id, float montanteFinal){
        this.faturaId = id;
        this.montanteFinal = montanteFinal;
    }

    /**
     *
     * @return Return do id da fatura
     */
    public int getFaturaID(){ return faturaId; }

    /**
     *
     * @return Return do montante final
     */
    public float getMontanteFinal(){ return montanteFinal; }

    /**
     *
     * @return Return da classe em string formatada
     */
    @Override
    public String toString() {
        return String.format("Fatura #%d | Montante pago: %.2f euros", faturaId, montanteFinal);
    }
}
