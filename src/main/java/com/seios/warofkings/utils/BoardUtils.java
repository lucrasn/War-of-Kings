package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils {

    public static List<ChessPiece> findPieces(ChessPiece[][] pieces, Type type) {
        List<ChessPiece> positions = new ArrayList<>();

        for (ChessPiece[] chessPieces : pieces) {
            for (ChessPiece piece : chessPieces) {
                if (piece != null && piece.getType() == type) {
                    positions.add(piece);
                }
            }
        }
        return positions;
    }
}
