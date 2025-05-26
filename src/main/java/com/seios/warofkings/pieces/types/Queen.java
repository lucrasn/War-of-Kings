package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;


/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rainha
 *
 * @author allan
 * @version 1.0
 * @since 2025-05-24
 */

public class Queen extends ChessPiece {

    private Queen(int position, Type type){
        super();
        super.position =position;
        super.type = type;

    }

    public Queen(int position, Type type, int n_moves){
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("");
        }
        super.position =position;
        super.type = type;
        super.n_moves = n_moves;

    }

    public static Queen createQueen(int position, Type type) {
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("");
        }
        return new Queen(position, type);
    }


    /** Retorna posições disponíveis para onde a Rainha pode se movimentar
     *<ul>
     *     <li> Movimentação na horizontal e vertical
     *     <li> Movimentação nas laterais
     *</ul>
     *
     * @param board matriz representando o estado atual do tabuleiro
     * @return lista de posições inteiras possíveis para o movimento
     */


    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<>();
        int pos = this.position;

        int forward = -10;
        int backward = 10;

        int right = 1;
        int left = -1;

        int northwest = -11;
        int southwest = 9;

        int northeast = -9;
        int southeast = 11;

        int forwardPos = forward + pos;
        while(ChessPiece.isWithinBounds(forwardPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(forwardPos);
        }

        int backwardPos = backward + pos;
        while(ChessPiece.isWithinBounds(backwardPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(backwardPos);
        }

        int rightPos = right + pos;
        while(ChessPiece.isWithinBounds(rightPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(rightPos);
        }

        int leftPos = left + pos;
        while(ChessPiece.isWithinBounds(leftPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(leftPos);
        }

        int northwestPos = northwest + pos;
        while(ChessPiece.isWithinBounds(northwestPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(northwestPos);
        }

        int southwestPos = southwest + pos;
        while(ChessPiece.isWithinBounds(southwestPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(southwestPos);
        }

        int northeastPos = northeast + pos;
        while(ChessPiece.isWithinBounds(northeastPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(northeastPos);
        }

        int southeastPos = southeast + pos;
        while(ChessPiece.isWithinBounds(southeastPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(southeastPos);
        }

        return possibleMoves;
    }
}
