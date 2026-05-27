package jogo;

import tabuleiro.Tabuleiro;
import tabuleiro.Casa;
import java.io.Serializable;

public class Jogo implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Tabuleiro tabuleiro;
    private Jogador jogador1;
    private Jogador jogador2;
    private Jogador jogadorAtual;
    private Casa casaSelecionada;
    private boolean modoBot;
    private boolean modoBotDificil;

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
        Casa casa = tabuleiro.getCasa(linha, coluna);

        if (casaSelecionada == null) {
            if (!casa.estaVazia() && casa.getPeca().getCor().equals(jogadorAtual.getCor())) {
                casaSelecionada = casa;
                return false;
            }
            return false;
        }

        if (ValidacaoDeMovimento.movimentoValido(tabuleiro, casaSelecionada, casa)) {
            mover(casaSelecionada, casa);
            casaSelecionada = null;
            trocarTurno();

            // se é modo bot e agora é a vez do bot, ele joga automaticamente
            if (modoBot && jogadorAtual == jogador2) {
                if (modoBotDificil) {
                    BotMinimax bot = (BotMinimax) jogador2;
                    bot.jogarDificil(this);
                } else {
                    Bot bot = (Bot) jogador2;
                    bot.jogarFacil(this);
                }
                trocarTurno();
            }

            return true;
        }

        casaSelecionada = null;
        return false;
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
}