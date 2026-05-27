package pecas;

public class Peao extends Peca {
    private static final long serialVersionUID = 1L;
    
    public Peao(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♙" : "♟";
    }
}