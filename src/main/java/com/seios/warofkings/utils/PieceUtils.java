package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

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

    public static boolean isWithinBounds(int position) {
        return getX(position) >= 0 && getX(position) < 8 && getY(position) >= 0 && getY(position) < 8;
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
}
