package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;


/**
 * Esta classe é responsável pela implementação dos movimentos da peça Bispo
 *
 * @author allan
 * @version 1.0
 * @since 2025-05-24
 */

public class Bishop extends ChessPiece{

    public Bishop(int positon, Type type){
        if (!(type.getValor() == 3) && !(type.getValor() == 9)) {
            throw new IllegalArgumentException("");
        }
            super.position =position;
            super.type = type;
            super.n_moves = 0;
        }

    /** Retorna posições disponíveis para onde os Bispos podem se movimentar
     *<ul>
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

            int northwest = -11;
            int southwest = 9;

            int northeast = -9;
            int southeast = 11;

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
