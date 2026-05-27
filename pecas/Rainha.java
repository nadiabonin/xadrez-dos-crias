package pecas;

public class Rainha extends Peca {
    private static final long serialVersionUID = 1L;
    
    public Rainha(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♕" : "♛";
    }
}