package com.seios.warofkings.pieces.types;

import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

public class Pawn extends ChessPiece {
    private Pawn(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static Pawn createPawn(int position, Type type) throws IllegalArgumentException {
        if (!(type.getValor() == 0) && !(type.getValor() == 6)) {
            throw new IllegalArgumentException("");
        }

        return new Pawn(position, type);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        // TODO: falta a verificação se o peão pode comer alguma outra peça;
        List<Integer> possibilities = new ArrayList<Integer>();
        if (super.n_moves == 0) {
            possibilities.add(super.position + 1);
            possibilities.add(super.position + 2);
        } else {
            possibilities.add(super.position + 1);
        }

        return possibilities;
    }

    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        // TODO: falta a verificação se o peão pode comer alguma outra peça;
        List<Integer> possibilities = new ArrayList<Integer>();
        if (super.n_moves == 0) {
            possibilities.add(super.position + 1);
            possibilities.add(super.position + 2);
        } else {
            possibilities.add(super.position + 1);
        }

        return possibilities;
    }

    //TODO: fazer os coiso
    @Override
    public boolean moveTo(int position, int[][] board) {
        return super.moveTo(position, board);
    }
}
