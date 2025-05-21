package com.seios.warofkings.pieces;

import java.util.List;

public interface Movable {
    boolean moveTo(int place, int[][] board);
    List<Integer> getPossibleMoves();

}
