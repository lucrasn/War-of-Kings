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
            throw new IllegalArgumentException("Tipo inválido para peão. Esperado PAWN_WHITE (0) ou PAWN_BLACK (6).");
        }

        return new Pawn(position, type);
    }

    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<Integer>();
        int pos = this.position;

        boolean isWhite = this.type == Type.PAWN_WHITE;
        int forward = isWhite ? -10 : 10;
        int doubleForward = isWhite ? -20 : 20;
        int diagLeft = isWhite ? -11 : 9;
        int diagRight = isWhite ? -9 : 11;

        int fwdPos = pos + forward;
        if (ChessPiece.isWithinBounds(fwdPos) && board[getX(fwdPos)][getY(fwdPos)] == null) {
            possibleMoves.add(fwdPos);

            // Duplo avanço inicial
            int dblFwdPos = pos + doubleForward;
            if (n_moves == 0 && ChessPiece.isWithinBounds(dblFwdPos) && board[getX(dblFwdPos)][getY(dblFwdPos)] == null) {
                possibleMoves.add(dblFwdPos);
            }
        }

        // Diagonal esquerda
        int diagLPos = pos + diagLeft;
        if (ChessPiece.isWithinBounds(diagLPos)) {
            if (isOpponent(board[getX(diagLPos)][getY(diagLPos)])) {
                possibleMoves.add(diagLPos);
            }
        }

        // Diagonal direita
        int diagRPos = pos + diagRight;
        if (ChessPiece.isWithinBounds(diagRPos)) {
            if (isOpponent(board[getX(diagRPos)][getY(diagRPos)])) {
                possibleMoves.add(diagRPos);
            }
        }

        return possibleMoves;
    }

    //TODO: Implementar lógica específica de promoção para peões ao alcançarem o fim do tabuleiro
    @Override
    public boolean moveTo(int position, ChessPiece[][] board) {
        return super.moveTo(position, board);
    }
}
