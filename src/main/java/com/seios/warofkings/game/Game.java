package com.seios.warofkings.game;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;

public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        ChessPiece p = board.getPieceAt(1,4);
        if (p != null) {
            System.out.println("Peça encontrada: " + p.getType().displayName());
        }


    }
}