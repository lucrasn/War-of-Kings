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

    /**
     * Metodo para verificar se a posição especificada do tabuleiro está ocupada por uma peça.
     * @author Raffael Wagner
     * @param position int - A posição a ser analisada.
     * @return {@code true} se houver uma peça na posição especificada, {@code false} caso contrário.*/
    public static boolean isPositionOccupied(ChessPiece[][] pieces, int position) {
        int x = position / 10;
        int y = position % 10;
        return BoardUtils.isWithinBounds(position) && pieces[x][y] != null;
    }

    /**
     * Caso tenha, retorna a peça na posição informada do tabuleiro.
     * @author Raffael Wagner
     * @param position int - A posição a ser analisada.
     * @return a peça na posição especificada ou {@code false} caso a posição seja inválida ou não tenha peça.*/
    public static ChessPiece getPieceAt(ChessPiece[][] pieces, int position) {
        int x = position / 10;
        int y = position % 10;
        if (!BoardUtils.isWithinBounds(position)) return null;
        return pieces[x][y];
    }

    public static boolean isWithinBounds(int position) {
        return PieceUtils.getX(position) >= 0 && PieceUtils.getX(position) < 8 && PieceUtils.getY(position) >= 0 && PieceUtils.getY(position) < 8;
    }


    public static ChessPiece[][] copyBoard(ChessPiece[][] original) {
        ChessPiece[][] copy = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copy[i][j] = original[i][j]; // apenas copia a referência
            }
        }
        return copy;
    }
}
