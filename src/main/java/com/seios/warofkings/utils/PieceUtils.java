package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.List;

/**
 * Classe utilitária para operações envolvendo peças de xadrez.
 * <p>
 * Contém métodos estáticos para cálculo de posição, comparação entre peças,
 * verificação de ameaças e segurança para movimentos especiais (como roque).
 * </p>
 *
 * <p><b>Nota:</b> Esta classe não deve ser instanciada.</p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-06-01
 */
public class PieceUtils {
    /**
     * Construtor privado para prevenir instanciamento.
     *
     * @throws UnsupportedOperationException sempre.
     */
    private PieceUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Classe utilitária - não deve ser instanciada.");
    }

    /**
     * Retorna a coordenada X (linha) da posição inteira.
     *
     * @param position posição no formato XY (ex: 52).
     * @return linha correspondente (0 a 7).
     */
    public static int getX(int position) {
        return position / 10;
    }

    /**
     * Retorna a coordenada Y (coluna) da posição inteira.
     *
     * @param position posição no formato XY (ex: 52).
     * @return coluna correspondente (0 a 7).
     */
    public static int getY(int position) {
        return position % 10;
    }

    /**
     * Verifica se duas referências de peças apontam para o mesmo objeto.
     *
     * @param a primeira peça.
     * @param b segunda peça.
     * @return true se forem o mesmo objeto em memória.
     */
    public static boolean isSamePiece(ChessPiece a, ChessPiece b) {
        return a == b;
    }

    /**
     * Verifica se a peça fornecida é do tipo especificado.
     *
     * @param piece peça a ser analisada.
     * @param type  tipo esperado.
     * @return true se corresponder, false se for nula ou diferente.
     */
    public static boolean isType(ChessPiece piece, Type type) {
        if (piece == null) return false;
        return piece.getType() == type;
    }

    /**
     * Verifica se duas peças são do mesmo "tipo lógico", ou seja, ambas brancas ou ambas pretas.
     * Isso é feito através da divisão do valor do tipo por 6 (grupos: 0–5 = branco, 6–11 = preto).
     *
     * @param a primeira peça.
     * @param b segunda peça.
     * @return true se forem da mesma "família de cor".
     */
    public static boolean isSameType(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) return false;
        return a.getType().getValor() / 6 == b.getType().getValor() / 6;
    }

    /**
     * Verifica se duas peças estão na mesma posição do tabuleiro.
     *
     * @param a primeira peça.
     * @param b segunda peça.
     * @return true se estiverem na mesma posição.
     */
    public static boolean isSameBoardPosition(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) return false;
        return a.getPosition() == b.getPosition();
    }

    /**
     * Verifica se duas peças possuem a mesma cor (ambas brancas ou ambas pretas).
     *
     * @param a primeira peça.
     * @param b segunda peça.
     * @return true se forem da mesma cor.
     */
    public static boolean isSameColor(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) {
            return false;
        }
        return a.isWhite() == b.isWhite();
    }

    /**
     * Verifica se a peça informada está sob ataque no tabuleiro atual.
     * Percorre todas as peças adversárias e analisa se alguma delas possui
     * a posição da peça em sua lista de movimentos válidos.
     *
     * @param piece peça a ser analisada.
     * @param board matriz 8x8 representando o tabuleiro.
     * @return true se a peça está ameaçada por uma adversária.
     */
    public static boolean isPieceUnderAttack(ChessPiece piece, ChessPiece[][] board) {
        if (piece == null) return false;

        for (ChessPiece[] row : board) {
            for (ChessPiece other : row) {
                 if (other != null && !PieceUtils.isSameColor(other, piece)) {
                    List<Integer> moves = other.getPossibleMoves(board);
                    if (moves.contains(piece.getPosition())) {
                        return true; // peça está sendo ameaçada
                    }
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o caminho do rei entre sua posição atual e a posição de destino
     * é seguro (não será atacado em nenhuma das casas durante o trajeto).
     * <p>Usado para validar o roque.</p>
     *
     * @param to    posição final desejada.
     * @param king  instância da peça rei.
     * @param board estado atual do tabuleiro.
     * @return true se o caminho for seguro.
     */
    public static boolean pathSafeForKing(int to, ChessPiece king, ChessPiece[][] board) {
        int from = king.getPosition(); // posição do rei

        ChessPiece[][] boardCopy = BoardUtils.copyBoard(board);
        boardCopy[getX(from)][getY(from)] = null;

        int step = (from > to) ? -1 : 1;
        for (int i = from + step; i != to + step; i += step) {
            boardCopy[getX(i)][getY(i)] = king;
            king.setPosition(i);
            if (isPieceUnderAttack(king, boardCopy)) {
                king.setPosition(from);
                return false;
            }
            boardCopy[getX(i)][getY(i)] = null;
        }
        king.setPosition(from);
        return true;
    }
}
