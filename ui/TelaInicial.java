package ui;

import javax.swing.*;
import jogo.Bot;
import jogo.BotMinimax;
import jogo.Jogador;
import jogo.Jogo;
import persistencia.SaveGame;

@SuppressWarnings({"ResultOfObjectAllocationIgnored", "unused"})
public class TelaInicial {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Xadrez dos Crias - Menu");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel titulo = new JLabel("XADREZ DOS CRIAS");
            titulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
            titulo.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            panel.add(titulo);
            panel.add(Box.createVerticalStrut(20));

            // Botões
            JButton btnPvsP = new JButton("Humano vs Humano");
            JButton btnPvsBot = new JButton("Humano vs Bot (Fácil)");
            JButton btnPvsBotDificil = new JButton("Humano vs Bot (Difícil)");
            JButton btnCarregar = new JButton("Carregar Jogo");

            btnPvsP.addActionListener(e -> {
                iniciarJogo(frame, false, false);
            });

            btnPvsBot.addActionListener(e -> {
                iniciarJogo(frame, true, false);
            });

            btnPvsBotDificil.addActionListener(e -> {
                iniciarJogo(frame, true, true);
            });

            btnCarregar.addActionListener(e -> {
                Jogo jogo = SaveGame.carregar();
                if (jogo != null) {
                    frame.dispose();
                    new InterfaceGrafica(jogo);
                } else {
                    JOptionPane.showMessageDialog(frame, "Nenhuma partida salva encontrada!");
                }
            });

            panel.add(btnPvsP);
            panel.add(Box.createVerticalStrut(10));
            panel.add(btnPvsBot);
            panel.add(Box.createVerticalStrut(10));
            panel.add(btnPvsBotDificil);
            panel.add(Box.createVerticalStrut(10));
            panel.add(btnCarregar);

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static void iniciarJogo(JFrame frameMenu, boolean modoBot, boolean modoBotDificil) {
        frameMenu.dispose();

        Jogador jogador1 = new Jogador("Humano", "branco");
        Jogador jogador2;

        if (modoBot) {
            if (modoBotDificil) {
                jogador2 = new BotMinimax("Bot Difícil", "preto", 4);
            } else {
                jogador2 = new Bot("Bot Fácil", "preto");
            }
        } else {
            jogador2 = new Jogador("Humano 2", "preto");
        }

        Jogo jogo = new Jogo(jogador1, jogador2, modoBot, modoBotDificil);
        new InterfaceGrafica(jogo);
    }
}
