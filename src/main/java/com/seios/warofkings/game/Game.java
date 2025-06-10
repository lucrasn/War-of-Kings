package com.seios.warofkings.game;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;

import java.util.List;

public class Game {
    public static void main(String[] args) {
        Board board = new Board();
        board.printBoard();

        //Só testando para ver se está funcionando a lógica
            // by allan: faz o teste dnv hehe
        ChessPiece p = BoardUtils.getPieceAt(board.getPieces() ,14);
        if (p != null) {
            System.out.println("Peça encontrada: " + p.getType().displayName());
        }

        List<ChessPiece> bisposPretos = BoardUtils.findPieces(board.getPieces(), Type.BISHOP_BLACK);
        System.out.println("Bispos pretos encontrados:");
        for (ChessPiece piece : bisposPretos) {
            System.out.println(piece);
        }

    }
}