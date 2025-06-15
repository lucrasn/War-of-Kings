package com.seios.warofkings.utils;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.List;

/**
 * Esta classe é responsável pela abstração dos movimentos das peças de xadrez
 *
 * @author lucas
 * @version 1.0
 * @since 2025-06-01
 */
public class PieceUtils {
    private PieceUtils() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Classe utilitária - não deve ser instanciada.");
    }

    public static int getX(int position) {
        return position / 10;
    }

    public static int getY(int position) {
        return position % 10;
    }

    public static boolean isSamePiece(ChessPiece a, ChessPiece b) {
        return a == b;
    }

    public static boolean isType(ChessPiece piece, Type type) {
        if (piece == null) return false;
        return piece.getType() == type;
    }

    public static boolean isSameType(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) return false;
        return a.getType().getValor() / 6 == b.getType().getValor() / 6;
    }

    public static boolean isSameBoardPosition(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) return false;
        return a.getPosition() == b.getPosition();
    }

    public static boolean isSameColor(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) {
            return false;
        }
        return a.isWhite() == b.isWhite();
    }

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
