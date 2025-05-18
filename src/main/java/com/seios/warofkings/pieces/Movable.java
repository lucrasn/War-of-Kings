package com.seios.warofkings.pieces;

import java.util.List;

public interface Movable {
    public boolean moveTo(int place, int[][] board);
    public List<Integer> getPossibleMoves();

}
