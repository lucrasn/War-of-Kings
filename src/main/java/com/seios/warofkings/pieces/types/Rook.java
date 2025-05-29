package com.seios.warofkings.pieces.types;
import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

public class Rook extends ChessPiece {

    public Rook(int position, Type type, int n_moves) throws IllegalArgumentException {
        if (!(type.getValor() == 1) && !(type.getValor() == 7)) {
            throw new IllegalArgumentException("");
        }

        this.position = position;
        this.type = type;
        this.n_moves = 0;


    }



    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibilities = new ArrayList<>();

        int x = getX();
        int y = getY();

        for (int col = 0; col < 8; col++) {
            if (col != x) {
                possibilities.add((col * 10) + y);
            }
        }

        for (int row = 0; row < 8; row++) {
            if (row != y) {
                possibilities.add((x * 10) + row);
            }
        }

        // tem que fazer a verificacao ainda de se tem peça e se deixa o meu rei em xeque p poder andar ou não.

        return possibilities;
    }
}