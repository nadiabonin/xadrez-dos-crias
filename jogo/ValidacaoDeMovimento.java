package jogo;

import tabuleiro.Casa;
import tabuleiro.Tabuleiro;
import pecas.Peca;

public class ValidacaoDeMovimento {

    public static boolean movimentoValido(Tabuleiro tabuleiro, Casa origem, Casa destino) {

        if (origem.estaVazia()) return false;

        if (!destino.estaVazia()) {
            if (destino.getPeca().getCor().equals(origem.getPeca().getCor())) {
                return false;
            }
        }

        Peca peca = origem.getPeca();
        int linhaOrigem = origem.getLinha();
        int colunaOrigem = origem.getColuna();
        int linhaDestino = destino.getLinha();
        int colunaDestino = destino.getColuna();

        int dl = linhaDestino - linhaOrigem;
        int dc = colunaDestino - colunaOrigem;

        String tipo = peca.getClass().getSimpleName();

        switch (tipo) {
            case "Rei":
                return Math.abs(dl) <= 1 && Math.abs(dc) <= 1 && (dl != 0 || dc != 0);

            case "Rainha":
                if (dl == 0 || dc == 0 || Math.abs(dl) == Math.abs(dc)) {
                    return caminhoLivre(tabuleiro, origem, destino);
                }
                return false;

            case "Torre":
                if (dl == 0 || dc == 0) {
                    return caminhoLivre(tabuleiro, origem, destino);
                }
                return false;

            case "Bispo":
                if (Math.abs(dl) == Math.abs(dc)) {
                    return caminhoLivre(tabuleiro, origem, destino);
                }
                return false;

            case "Cavalo":
                return (Math.abs(dl) == 2 && Math.abs(dc) == 1) ||
                       (Math.abs(dl) == 1 && Math.abs(dc) == 2);

            case "Peao":
                return validarPeao(tabuleiro, peca, dl, dc, origem, destino);

            default:
                return false;
        }
    }

    private static boolean validarPeao(Tabuleiro tabuleiro, Peca peca, int dl, int dc, Casa origem, Casa destino) {
        int direcao = peca.getCor().equals("branco") ? -1 : 1;
        int linhaInicial = peca.getCor().equals("branco") ? 6 : 1;

        // Movimento para frente de 1 casa
        if (dc == 0 && dl == direcao && destino.estaVazia()) {
            return true;
        }

        // Movimento inicial de 2 casas
        if (dc == 0 && dl == 2 * direcao && origem.getLinha() == linhaInicial && destino.estaVazia()) {
            // Verifica se a casa intermediária também está vazia
            Casa casaIntermediaria = tabuleiro.getCasa(origem.getLinha() + direcao, origem.getColuna());
            return casaIntermediaria.estaVazia();
        }

        // Captura na diagonal
        if (Math.abs(dc) == 1 && dl == direcao && !destino.estaVazia()) {
            return true;
        }

        return false;
    }

    private static boolean caminhoLivre(Tabuleiro tabuleiro, Casa origem, Casa destino) {
        int linhaOrigem = origem.getLinha();
        int colunaOrigem = origem.getColuna();
        int linhaDestino = destino.getLinha();
        int colunaDestino = destino.getColuna();

        int dl = linhaDestino - linhaOrigem;
        int dc = colunaDestino - colunaOrigem;

        // Normaliza para -1, 0 ou 1
        int stepL = Integer.compare(dl, 0);
        int stepC = Integer.compare(dc, 0);

        int linha = linhaOrigem + stepL;
        int coluna = colunaOrigem + stepC;

        // Verifica todas as casas intermediárias
        while (linha != linhaDestino || coluna != colunaDestino) {
            if (!tabuleiro.getCasa(linha, coluna).estaVazia()) {
                return false;
            }
            linha += stepL;
            coluna += stepC;
        }

        return true;
    }
}