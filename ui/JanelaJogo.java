package ui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import jogo.Jogador;
import jogo.Jogo;
import persistencia.SaveGame;

public class JanelaJogo extends JFrame implements KeyListener {
    private final PainelTabuleiro painelTabuleiro;
    private JLabel labelTurno;
    private JLabel labelInfo;
    private final Jogo jogo;

    @SuppressWarnings("LeakingThisInConstructor")
    public JanelaJogo(Jogo jogo) {
        this.jogo = jogo;
        setTitle("Xadrez dos Crias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Painel principal
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        painelPrincipal.setBackground(new Color(50, 50, 50));

        // Painel do tabuleiro
        painelTabuleiro = new PainelTabuleiro(jogo);
        painelPrincipal.add(painelTabuleiro, BorderLayout.CENTER);

        // Painel lateral com informações e botões
        JPanel painelLateral = criarPainelLateral();
        painelPrincipal.add(painelLateral, BorderLayout.EAST);

        add(painelPrincipal);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        addKeyListener(this);
    }

    private JPanel criarPainelLateral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(50, 50, 50));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(200, 400));

        // Label de turno
        labelTurno = new JLabel("Turno");
        labelTurno.setFont(new Font("Arial", Font.BOLD, 14));
        labelTurno.setForeground(Color.WHITE);
        labelTurno.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        panel.add(labelTurno);
        panel.add(Box.createVerticalStrut(10));

        // Label de informações
        labelInfo = new JLabel("<html>Clique em uma peça<br>e depois no destino</html>");
        labelInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        labelInfo.setForeground(Color.WHITE);
        labelInfo.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        panel.add(labelInfo);
        panel.add(Box.createVerticalStrut(20));

        // Botões
        JButton btnSalvar = new JButton("Salvar Partida (S)");
        btnSalvar.addActionListener(e -> salvarPartida());

        JButton btnNovaPartida = new JButton("Nova Partida");
        btnNovaPartida.addActionListener(e -> novaPartida());

        JButton btnSair = new JButton("Sair");
        btnSair.addActionListener(e -> System.exit(0));

        panel.add(btnSalvar);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnNovaPartida);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnSair);

        panel.add(Box.createVerticalGlue());

        // Instruções
        JLabel labelInstr = new JLabel("<html><b>Instruções:</b><br>" +
                "• Clique nas peças<br>" +
                "• Aperte S para salvar<br>" +
                "• ESC para nova partida</html>");
        labelInstr.setFont(new Font("Arial", Font.PLAIN, 10));
        labelInstr.setForeground(new Color(200, 200, 200));
        panel.add(labelInstr);

        return panel;
    }

    public void atualizar() {
        // Verifica fim de jogo
        if (jogo.isJogoEncerrado()) {
            painelTabuleiro.repaint();
            JOptionPane.showMessageDialog(this,
                "Fim de jogo! Vencedor: " + jogo.getVencedor(),
                "Fim de Jogo",
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
            SwingUtilities.invokeLater(() -> TelaInicial.main(new String[]{}));
            return;
        }

        Jogador jogadorAtual = jogo.getJogadorAtual();
        String cor = jogadorAtual.getCor().equals("branco") ? "⚪ Branco" : "⚫ Preto";
        labelTurno.setText("Turno: " + cor);
        labelInfo.setText("<html>Escolha uma peça</html>");
        painelTabuleiro.repaint();
    }

    private void salvarPartida() {
        SaveGame.salvar(jogo);
        JOptionPane.showMessageDialog(this, "Partida salva com sucesso!");
    }

    private void novaPartida() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Deseja iniciar uma nova partida?",
                "Nova Partida",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> TelaInicial.main(new String[]{}));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            salvarPartida();
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            novaPartida();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
