package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

    public Queen(int position, Type type){
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("");
        }
        super.position =position;
        super.type = type;
        super.n_moves = 0;
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibilities = new ArrayList<>();


        while (true) { // verificacao linda, enquanto a posicao existir E nao estiver ocupada
            possibilities.add(getX() - 1, getY());
        }

        while(true){
            possibilities.add(getX() + 1, getY());
        }

        while (true){
            possibilities.add(getX(), getY() - 1);
        }

        while (true){
            possibilities.add(getX(), getY() + 1);

        }

        while (true){
            possibilities.add(getX() - 1, getY() - 1);
        }

        while (true){
            possibilities.add(getX() - 1, getY() + 1);

        }

        while (true){
            possibilities.add(getX() + 1, getY() + 1);

        }

        while (true){
            possibilities.add(getX() + 1, getY() - 1);
        }
    }
}
