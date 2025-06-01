package com.seios.warofkings.board;

import com.seios.warofkings.pieces.ChessPiece;

public interface Boardable {
    void setupBoard();

    boolean isPositionOccupied(int var1, int var2);

    ChessPiece getPieceAt(int var1, int var2);

    boolean isValidPosition(int var1, int var2);
}