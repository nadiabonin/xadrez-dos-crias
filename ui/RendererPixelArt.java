package ui;

import java.awt.*;
import pecas.*;

public class RendererPixelArt {
    private final int tamanhoBloco;

    public RendererPixelArt(int tamanhoBloco) {
        this.tamanhoBloco = tamanhoBloco;
    }

    public void desenharPeca(Graphics2D g, Peca peca, int x, int y) {
        int padding = 4;
        int area = tamanhoBloco - padding * 2;

        // Cor da peça
        Color cor = peca.getCor().equals("branco") ? Color.WHITE : new Color(40, 40, 40);
        Color borda = peca.getCor().equals("branco") ? Color.BLACK : Color.WHITE;

        // Desenha cada tipo de peça
        String tipo = peca.getClass().getSimpleName();
        switch (tipo) {
            case "Rei" -> desenharRei(g, x + padding, y + padding, area, cor, borda);
            case "Rainha" -> desenharRainha(g, x + padding, y + padding, area, cor, borda);
            case "Torre" -> desenharTorre(g, x + padding, y + padding, area, cor, borda);
            case "Bispo" -> desenharBispo(g, x + padding, y + padding, area, cor, borda);
            case "Cavalo" -> desenharCavalo(g, x + padding, y + padding, area, cor, borda);
            case "Peao" -> desenharPeao(g, x + padding, y + padding, area, cor, borda);
        }
    }

    private void desenharRei(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo (quadrado maior)
        desenharQuadrado(g, cx - 2 * sz, cy - 2 * sz, 4 * sz, 4 * sz, cor, borda, 2);

        // Topo (cruz)
        desenharQuadrado(g, cx - sz, cy - 4 * sz, 2 * sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx - 3 * sz, cy - 3 * sz, 2 * sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx + 2 * sz, cy - 3 * sz, 2 * sz, 2 * sz, cor, borda, 1);
    }

    private void desenharRainha(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo
        desenharQuadrado(g, cx - 2 * sz, cy - 2 * sz, 4 * sz, 4 * sz, cor, borda, 2);

        // Topo (5 pontos)
        for (int i = -2; i <= 2; i++) {
            desenharQuadrado(g, cx + i * sz, cy - 4 * sz, sz, sz, cor, borda, 1);
        }
    }

    private void desenharTorre(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo retangular
        desenharQuadrado(g, cx - 2 * sz, cy - 2 * sz, 4 * sz, 4 * sz, cor, borda, 2);

        // Almenas (topo quadrado)
        desenharQuadrado(g, cx - 3 * sz, cy - 4 * sz, sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx + 2 * sz, cy - 4 * sz, sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx - sz, cy - 3 * sz, sz, sz, cor, borda, 1);
        desenharQuadrado(g, cx, cy - 3 * sz, sz, sz, cor, borda, 1);
    }

    private void desenharBispo(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo
        desenharQuadrado(g, cx - sz, cy - 2 * sz, 2 * sz, 4 * sz, cor, borda, 2);

        // Topo (ponto)
        desenharQuadrado(g, cx - sz, cy - 4 * sz, 2 * sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx, cy - 5 * sz, sz, sz, cor, borda, 1);
    }

    private void desenharCavalo(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo (L invertido)
        desenharQuadrado(g, cx - 2 * sz, cy - sz, 4 * sz, 3 * sz, cor, borda, 2);

        // Cabeça (cavalo)
        desenharQuadrado(g, cx - 3 * sz, cy - 3 * sz, 2 * sz, 2 * sz, cor, borda, 1);
        desenharQuadrado(g, cx - 2 * sz, cy - 4 * sz, 2 * sz, sz, cor, borda, 1);
    }

    private void desenharPeao(Graphics2D g, int x, int y, int tamanho, Color cor, Color borda) {
        int sz = tamanho / 8;
        int cx = x + tamanho / 2;
        int cy = y + tamanho / 2;

        // Corpo pequeno
        desenharQuadrado(g, cx - sz, cy - 2 * sz, 2 * sz, 3 * sz, cor, borda, 2);

        // Topo redondo (simples, usando quadrado)
        desenharQuadrado(g, cx - sz, cy - 3 * sz, 2 * sz, sz, cor, borda, 1);
    }

    private void desenharQuadrado(Graphics2D g, int x, int y, int largura, int altura,
                                   Color preenchimento, Color borda, int espessuraBorda) {
        // Preenchimento
        g.setColor(preenchimento);
        g.fillRect(x, y, largura, altura);

        // Borda
        g.setColor(borda);
        g.setStroke(new BasicStroke(espessuraBorda));
        g.drawRect(x, y, largura, altura);
    }
}
