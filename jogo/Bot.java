package jogo;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public class Bot extends Jogador {
    private static final long serialVersionUID = 1L;
    private transient Random random;

    public Bot(String nome, String cor) {
        super(nome, cor);
    }

    private Random getRandom() {
        if (random == null) random = new Random();
        return random;
    }

    public void jogarFacil(Jogo jogo) {
        Tabuleiro tabuleiro = jogo.getTabuleiro();

        // Coleta todos os movimentos válidos de uma vez
        List<Casa[]> movimentosValidos = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa origem = tabuleiro.getCasa(i, j);
                if (origem.estaVazia() || !origem.getPeca().getCor().equals(getCor())) continue;

                for (int di = 0; di < 8; di++) {
                    for (int dj = 0; dj < 8; dj++) {
                        Casa destino = tabuleiro.getCasa(di, dj);
                        if (ValidacaoDeMovimento.movimentoValido(tabuleiro, origem, destino)) {
                            movimentosValidos.add(new Casa[]{origem, destino});
                        }
                    }
                }
            }
        }

        if (!movimentosValidos.isEmpty()) {
            Casa[] movimento = movimentosValidos.get(getRandom().nextInt(movimentosValidos.size()));
            // Usa moverDiretamente para evitar loop com selecionar()
            jogo.moverDiretamente(movimento[0], movimento[1]);
        }
    }
}