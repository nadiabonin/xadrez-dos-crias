package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import pecas.*;

public class RendererPixelArt {

    private final int tamanhoBloco;
    private final Map<String, BufferedImage> imagens = new HashMap<>();

    private static final Map<String, String> NOMES_ARQUIVOS = new HashMap<>();
    static {
        NOMES_ARQUIVOS.put("Rei_branco",    "REIB");
        NOMES_ARQUIVOS.put("Rei_preto",     "REIP");
        NOMES_ARQUIVOS.put("Rainha_branco", "RAINHAB");
        NOMES_ARQUIVOS.put("Rainha_preto",  "RAINHAP");
        NOMES_ARQUIVOS.put("Torre_branco",  "TORREB");
        NOMES_ARQUIVOS.put("Torre_preto",   "TORREP");
        NOMES_ARQUIVOS.put("Bispo_branco",  "BISPOB");
        NOMES_ARQUIVOS.put("Bispo_preto",   "BISPOP");
        NOMES_ARQUIVOS.put("Cavalo_branco", "CAVALOB");
        NOMES_ARQUIVOS.put("Cavalo_preto",  "CAVALOP");
        NOMES_ARQUIVOS.put("Peao_branco",   "PEAOB");
        NOMES_ARQUIVOS.put("Peao_preto",    "PEAOP");
    }

    public RendererPixelArt(int tamanhoBloco) {
        this.tamanhoBloco = tamanhoBloco;
        carregarImagens();
    }

    private void carregarImagens() {
        String pastaImagens = "imagens/";
        for (Map.Entry<String, String> entrada : NOMES_ARQUIVOS.entrySet()) {
            String caminho = pastaImagens + entrada.getValue() + ".png";
            try {
                BufferedImage img = ImageIO.read(new java.io.File(caminho));
                if (img != null) {
                    imagens.put(entrada.getKey(), img);
                } else {
                    System.err.println("Imagem não encontrada: " + caminho);
                }
            } catch (IOException e) {
                System.err.println("Erro ao carregar: " + caminho + " — " + e.getMessage());
            }
        }
    }

    public void desenharPeca(Graphics2D g, Peca peca, int x, int y) {
        String chave = peca.getClass().getSimpleName() + "_" + peca.getCor();
        BufferedImage img = imagens.get(chave);

        if (img != null) {
            // Centraliza a imagem na casa
            int padding = 2;
            int largura = tamanhoBloco - padding * 2;
            int altura  = tamanhoBloco - padding * 2;
            g.drawImage(img, x + padding, y + padding, largura, altura, null);
        } else {
            // Fallback: desenha um retângulo colorido se a imagem falhar
            Color cor = peca.getCor().equals("branco") ? Color.WHITE : new Color(40, 40, 40);
            g.setColor(cor);
            g.fillRect(x + 4, y + 4, tamanhoBloco - 8, tamanhoBloco - 8);
            g.setColor(cor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 10));
            g.drawString(peca.getClass().getSimpleName().substring(0, 1), x + 14, y + 24);
        }
    }
}