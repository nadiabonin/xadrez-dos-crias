package pecas;

public class Cavalo extends Peca {
    private static final long serialVersionUID = 1L;
    
    public Cavalo(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♘" : "♞";
    }
}