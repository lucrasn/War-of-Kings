package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;

import java.util.List;

/**
 * Esta Interface é responsável pela abstração do movimento
 *
 * @author lucas
 * @version 1.0
 * @since 2025-05-14
 */
public interface Movable {
    boolean moveTo(int place, List<Integer> listMoves, Board board);
    List<Integer> getPossibleMoves(ChessPiece[][] board);

}
