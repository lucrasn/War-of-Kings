package com.seios.warofkings.pieces;

import java.util.List;

public interface Movable {
    boolean moveTo(int place, ChessPiece[][] board);
    List<Integer> getPossibleMoves(ChessPiece[][] board);

}
