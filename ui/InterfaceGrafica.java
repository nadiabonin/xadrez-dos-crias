package ui;

import jogo.Jogo;

public class InterfaceGrafica {
    private final JanelaJogo janelaJogo;

    public InterfaceGrafica(Jogo jogo) {
        janelaJogo = new JanelaJogo(jogo);
        janelaJogo.atualizar();
    }

    public void atualizar() {
        janelaJogo.atualizar();
    }

    public void fechar() {
        janelaJogo.dispose();
    }
}
