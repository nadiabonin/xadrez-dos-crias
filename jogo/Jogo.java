package jogo;
import java.io.Serializable;
import pecas.Rei;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public class Jogo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorAtual;
    private Casa casaSelecionada;
    private boolean modoBot;
    private boolean modoBotDificil;
    private boolean jogoEncerrado = false;
    private String vencedor = null;

    public Jogo(Jogador j1, Jogador j2, boolean modoBot, boolean modoBotDificil) {
        this.tabuleiro = new Tabuleiro();
        this.jogador1 = j1;
        this.jogador2 = j2;
        this.jogadorAtual = j1;
        this.casaSelecionada = null;
        this.modoBot = modoBot;
        this.modoBotDificil = modoBotDificil;
    }

    public boolean selecionar(int linha, int coluna) {
        if (jogoEncerrado) return false;

        Casa casa = tabuleiro.getCasa(linha, coluna);

        if (casaSelecionada == null) {
            if (!casa.estaVazia() && casa.getPeca().getCor().equals(jogadorAtual.getCor())) {
                casaSelecionada = casa;
            }
            return false;
        }

        if (ValidacaoDeMovimento.movimentoValido(tabuleiro, casaSelecionada, casa)) {
            boolean capturouRei = !casa.estaVazia() && casa.getPeca() instanceof Rei;
            mover(casaSelecionada, casa);
            casaSelecionada = null;

            if (capturouRei) {
                jogoEncerrado = true;
                vencedor = jogadorAtual.getNome();
                return true;
            }

            trocarTurno();

            if (modoBot && jogadorAtual == jogador2 && !jogoEncerrado) {
                executarJogadaBot();
            }

            return true;
        }

        casaSelecionada = null;
        return false;
    }

    // Método separado para o bot mover sem passar pelo selecionar()
    public void moverDiretamente(Casa origem, Casa destino) {
        boolean capturouRei = !destino.estaVazia() && destino.getPeca() instanceof Rei;
        mover(origem, destino);

        if (capturouRei) {
            jogoEncerrado = true;
            vencedor = jogadorAtual.getNome();
            return;
        }

        trocarTurno();
    }

    private void executarJogadaBot() {
        Bot bot = (Bot) jogador2;
        bot.jogarFacil(this);
    }

    private void mover(Casa origem, Casa destino) {
        destino.setPeca(origem.getPeca());
        origem.setPeca(null);
    }

    private void trocarTurno() {
        jogadorAtual = jogadorAtual == jogador1 ? jogador2 : jogador1;
    }

    public Tabuleiro getTabuleiro() { return tabuleiro; }
    public Jogador getJogadorAtual() { return jogadorAtual; }
    public Casa getCasaSelecionada() { return casaSelecionada; }
    public boolean isModoBot() { return modoBot; }
    public boolean isModoBotDificil() { return modoBotDificil; }
    public boolean isJogoEncerrado() { return jogoEncerrado; }
    public String getVencedor() { return vencedor; }
}