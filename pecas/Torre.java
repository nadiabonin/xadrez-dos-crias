package pecas;

public class Torre extends Peca {
    private static final long serialVersionUID = 1L;
    
    public Torre(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♖" : "♜";
    }
}