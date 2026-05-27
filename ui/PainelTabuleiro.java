package ui;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import jogo.Jogo;
import tabuleiro.Casa;
import tabuleiro.Tabuleiro;

public class PainelTabuleiro extends JPanel {
    private static final int TAMANHO_BLOCO = 40;
    private static final int TAMANHO_TABULEIRO = TAMANHO_BLOCO * 8;
    private static final int PADDING = 20;
    private static final int ALTURA_INFO = 60;

    private final Jogo jogo;
    private final RendererPixelArt renderer;

    public PainelTabuleiro(Jogo jogo) {
        this.jogo = jogo;
        this.renderer = new RendererPixelArt(TAMANHO_BLOCO);

        setPreferredSize(new Dimension(
            TAMANHO_TABULEIRO + PADDING * 2,
            TAMANHO_TABULEIRO + PADDING * 2 + ALTURA_INFO
        ));
        setBackground(new Color(50, 50, 50));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = (e.getX() - PADDING) / TAMANHO_BLOCO;
                int y = (e.getY() - PADDING) / TAMANHO_BLOCO;

                if (x >= 0 && x < 8 && y >= 0 && y < 8) {
                    jogo.selecionar(y, x);
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        desenharTabuleiro(g2d);
        desenharPecas(g2d);
        desenharCasaSelecionada(g2d);
        desenharInfo(g2d);
    }

    private void desenharTabuleiro(Graphics2D g) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int x = PADDING + j * TAMANHO_BLOCO;
                int y = PADDING + i * TAMANHO_BLOCO;

                // Cores alternadas: claro e escuro
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(220, 200, 180)); // Claro
                } else {
                    g.setColor(new Color(100, 80, 60)); // Escuro
                }

                g.fillRect(x, y, TAMANHO_BLOCO, TAMANHO_BLOCO);

                // Bordas
                g.setColor(new Color(50, 50, 50));
                g.setStroke(new BasicStroke(1));
                g.drawRect(x, y, TAMANHO_BLOCO, TAMANHO_BLOCO);
            }
        }
    }

    private void desenharPecas(Graphics2D g) {
        Tabuleiro tabuleiro = jogo.getTabuleiro();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = tabuleiro.getCasa(i, j);
                if (!casa.estaVazia()) {
                    int x = PADDING + j * TAMANHO_BLOCO;
                    int y = PADDING + i * TAMANHO_BLOCO;
                    renderer.desenharPeca(g, casa.getPeca(), x, y);
                }
            }
        }
    }

    private void desenharCasaSelecionada(Graphics2D g) {
        Casa casaSelecionada = jogo.getCasaSelecionada();
        if (casaSelecionada != null) {
            int x = PADDING + casaSelecionada.getColuna() * TAMANHO_BLOCO;
            int y = PADDING + casaSelecionada.getLinha() * TAMANHO_BLOCO;

            g.setColor(new Color(255, 255, 0, 100)); // Amarelo semi-transparente
            g.fillRect(x, y, TAMANHO_BLOCO, TAMANHO_BLOCO);

            g.setColor(new Color(255, 255, 0));
            g.setStroke(new BasicStroke(3));
            g.drawRect(x, y, TAMANHO_BLOCO, TAMANHO_BLOCO);
        }
    }

    private void desenharInfo(Graphics2D g) {
        int y = PADDING + TAMANHO_TABULEIRO + 15;
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));

        String jogador = jogo.getJogadorAtual().getNome();
        String cor = jogo.getJogadorAtual().getCor().equals("branco") ? "Branco ♔" : "Preto ♚";
        String modo = jogo.isModoBot() ? (jogo.isModoBotDificil() ? " [Bot Difícil]" : " [Bot Fácil]") : "";

        g.drawString("Turno: " + jogador + " - " + cor + modo, PADDING, y);
    }
}
