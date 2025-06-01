package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;

/**
 * Esta classe é responsável pela abstração dos movimentos das peças de xadrez
 *
 * @author lucas
 * @version 1.0
 * @since 2025-06-01
 */
public class PieceUtils {
    public static boolean isWithinBounds(int position) {
        return ChessPiece.getX(position) >= 0 && ChessPiece.getX(position) < 8 && ChessPiece.getY(position) >= 0 && ChessPiece.getY(position) < 8;
    }

    public static boolean isSameType(ChessPiece a, ChessPiece b) {
        if (a == null || b == null) return false;
        return a.getType().getValor() / 6 == b.getType().getValor() / 6;
    }
}
