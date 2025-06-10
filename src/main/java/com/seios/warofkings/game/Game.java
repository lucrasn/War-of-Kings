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
        ChessPiece p = board.getPieceAt(1,4);
        if (p != null) {
            System.out.println("Peça encontrada: " + p.getType().displayName());
        }

        List<int[]> posicoesBispoPreto = BoardUtils.findPieces(board.getPieces(), Type.BISHOP_BLACK);
        for (int[] pos : posicoesBispoPreto) {
            System.out.println(Type.BISHOP_BLACK.displayName() + " encontrada(o) na(s) posição(ões): (" + pos[0] + ", " + pos[1] + ")");
        }

       List<int[]> posicaoRainhaBranco = BoardUtils.findPieces(board.getPieces(), Type.QUEEN_WHITE);
        for (int[] pos : posicaoRainhaBranco) {
            System.out.println(Type.QUEEN_WHITE.displayName() + " encontrada(o) na(s) posição(ões): (" + pos[0] + ", " + pos[1] + ")");
        }


    }
}