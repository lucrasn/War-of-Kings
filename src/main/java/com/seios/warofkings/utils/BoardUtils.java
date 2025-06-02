package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils {

    public static List<int[]> findPieces(ChessPiece[][] pieces, Type type) {
        List<int[]> positions = new ArrayList<>();

        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                ChessPiece piece = pieces[i][j];
                if (piece != null && piece.getType() == type) {
                    positions.add(new int[]{i, j});
                }
            }
        }

        return positions;
    }
}
