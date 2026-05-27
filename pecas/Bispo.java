package pecas;

public class Bispo extends Peca {
    private static final long serialVersionUID = 1L;
    
    public Bispo(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♗" : "♝";
    }
}