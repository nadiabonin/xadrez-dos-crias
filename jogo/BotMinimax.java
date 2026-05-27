package jogo;

import tabuleiro.Tabuleiro;
import tabuleiro.Casa;
import pecas.Peca;
import pecas.Rei;
import java.util.ArrayList;
import java.util.List;

public class BotMinimax extends Jogador {
    private static final long serialVersionUID = 1L;

    private int profundidade;

    public BotMinimax(String nome, String cor, int profundidade) {
        super(nome, cor);
        this.profundidade = profundidade;
    }

    public void jogarDificil(Jogo jogo) {
        int[] melhorMovimento = minimax(jogo.getTabuleiro(), profundidade, true);
        if (melhorMovimento != null) {
            jogo.selecionar(melhorMovimento[0], melhorMovimento[1]);
            jogo.selecionar(melhorMovimento[2], melhorMovimento[3]);
        }
    }

    private int[] minimax(Tabuleiro tabuleiro, int prof, boolean maximizando) {
        if (prof == 0) return new int[]{-1, -1, -1, -1, avaliar(tabuleiro)};

        List<int[]> movimentos = gerarMovimentos(tabuleiro, maximizando ? getCor() : corAdversaria());
        int[] melhor = null;

        if (maximizando) {
            int melhorValor = Integer.MIN_VALUE;
            for (int[] mov : movimentos) {
                Tabuleiro copia = copiarTabuleiro(tabuleiro);
                aplicarMovimento(copia, mov);
                int[] resultado = minimax(copia, prof - 1, false);
                int valor = resultado[4];
                if (valor > melhorValor) {
                    melhorValor = valor;
                    melhor = new int[]{mov[0], mov[1], mov[2], mov[3], valor};
                }
            }
        } else {
            int melhorValor = Integer.MAX_VALUE;
            for (int[] mov : movimentos) {
                Tabuleiro copia = copiarTabuleiro(tabuleiro);
                aplicarMovimento(copia, mov);
                int[] resultado = minimax(copia, prof - 1, true);
                int valor = resultado[4];
                if (valor < melhorValor) {
                    melhorValor = valor;
                    melhor = new int[]{mov[0], mov[1], mov[2], mov[3], valor};
                }
            }
        }
        return melhor != null ? melhor : new int[]{-1, -1, -1, -1, avaliar(tabuleiro)};
    }

    private int avaliar(Tabuleiro tabuleiro) {
        int pontos = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casa = tabuleiro.getCasa(i, j);
                if (!casa.estaVazia()) {
                    int valor = valorPeca(casa);
                    if (casa.getPeca().getCor().equals(getCor())) {
                        pontos += valor;
                    } else {
                        pontos -= valor;
                    }
                }
            }
        }
        return pontos;
    }

    private int valorPeca(Casa casa) {
        switch (casa.getPeca().getClass().getSimpleName()) {
            case "Rainha": return 900;
            case "Torre":  return 500;
            case "Bispo":  return 300;
            case "Cavalo": return 300;
            case "Peao":   return 100;
            case "Rei":    return 10000;
            default:       return 0;
        }
    }

    private List<int[]> gerarMovimentos(Tabuleiro tabuleiro, String cor) {
        List<int[]> movimentos = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa origem = tabuleiro.getCasa(i, j);
                if (!origem.estaVazia() && origem.getPeca().getCor().equals(cor)) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            Casa destino = tabuleiro.getCasa(k, l);
                            if (ValidacaoDeMovimento.movimentoValido(tabuleiro, origem, destino)) {
                                movimentos.add(new int[]{i, j, k, l});
                            }
                        }
                    }
                }
            }
        }
        return movimentos;
    }

    private void aplicarMovimento(Tabuleiro tabuleiro, int[] mov) {
        Casa origem = tabuleiro.getCasa(mov[0], mov[1]);
        Casa destino = tabuleiro.getCasa(mov[2], mov[3]);
        destino.setPeca(origem.getPeca());
        origem.setPeca(null);
    }

    private Tabuleiro copiarTabuleiro(Tabuleiro original) {
        Tabuleiro copia = new Tabuleiro();
        // Limpa o tabuleiro copiado
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copia.getCasa(i, j).setPeca(null);
            }
        }
        // Copia as peças do tabuleiro original
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Casa casaOriginal = original.getCasa(i, j);
                if (!casaOriginal.estaVazia()) {
                    try {
                        // Cria uma nova peça do mesmo tipo
                        Peca pecaOriginal = casaOriginal.getPeca();
                        Peca pecaCopia = pecaOriginal.getClass()
                            .getConstructor(String.class)
                            .newInstance(pecaOriginal.getCor());
                        copia.getCasa(i, j).setPeca(pecaCopia);
                    } catch (Exception e) {
                        System.err.println("Erro ao copiar peça: " + e.getMessage());
                    }
                }
            }
        }
        return copia;
    }

    private String corAdversaria() {
        return getCor().equals("branco") ? "preto" : "branco";
    }
}
