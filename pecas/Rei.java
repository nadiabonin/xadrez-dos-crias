package pecas;

public class Rei extends Peca {
    private static final long serialVersionUID = 1L;

    public Rei(String cor) {
        super(cor);
        this.simbolo = cor.equals("branco") ? "♔" : "♚";
    }
}