package tabuleiro;

import pecas.*;
import java.io.Serializable;

public class Tabuleiro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Casa[][] casas = new Casa[8][8];

    public Tabuleiro() {
        inicializar();
        posicionarPecas();
    }

    private void inicializar() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                casas[i][j] = new Casa(i, j);
            }
        }
    }

    private void posicionarPecas() {
        // peças pretas (linha 0 e 1)
        casas[0][0].setPeca(new Torre("preto"));
        casas[0][1].setPeca(new Cavalo("preto"));
        casas[0][2].setPeca(new Bispo("preto"));
        casas[0][3].setPeca(new Rainha("preto"));
        casas[0][4].setPeca(new Rei("preto"));
        casas[0][5].setPeca(new Bispo("preto"));
        casas[0][6].setPeca(new Cavalo("preto"));
        casas[0][7].setPeca(new Torre("preto"));
        for (int i = 0; i < 8; i++) casas[1][i].setPeca(new Peao("preto"));

        // peças brancas (linha 6 e 7)
        casas[7][0].setPeca(new Torre("branco"));
        casas[7][1].setPeca(new Cavalo("branco"));
        casas[7][2].setPeca(new Bispo("branco"));
        casas[7][3].setPeca(new Rainha("branco"));
        casas[7][4].setPeca(new Rei("branco"));
        casas[7][5].setPeca(new Bispo("branco"));
        casas[7][6].setPeca(new Cavalo("branco"));
        casas[7][7].setPeca(new Torre("branco"));
        for (int i = 0; i < 8; i++) casas[6][i].setPeca(new Peao("branco"));
    }

    public Casa[][] getCasas() { return casas; }
    public Casa getCasa(int linha, int coluna) { return casas[linha][coluna]; }
}