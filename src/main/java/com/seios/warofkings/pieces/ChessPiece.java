package com.seios.warofkings.pieces;

import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class ChessPiece implements Movable, Positionable {
    protected int position;
    protected Type type;
    protected int n_moves;

    public ChessPiece() {
        this.n_moves = 0;
    }

    protected boolean kingCheck(ChessPiece king, ChessPiece piece, int toPosition, ChessPiece[][] piecesMap) {
        if (king.getType().getValor() / 6 == piece.getType().getValor() / 6) return false; // TODO: podemos transformar isso em uma exeception, eu acho até melhor do que retornar false

        ChessPiece[][] simulatedMap = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                simulatedMap[i][j] = piecesMap[i][j];
            }
        }

        int fromX = piece.getX();
        int fromY = piece.getY();
        int toX = getX(toPosition);
        int toY = getY(toPosition);

        simulatedMap[fromX][fromY] = null;
        simulatedMap[toX][toY] = piece;

        int kingX = king.getX();
        int kingY = king.getY();
        if (piece == king) {
            kingX = toX;
            kingY = toY;
        }

        int kingPos = kingX * 10 + kingY;

        for (ChessPiece[] row : simulatedMap) {
            for (ChessPiece other : row) {
                if (other != null && other.getType().getValor() / 6 != king.getType().getValor() / 6) {
                    List<Integer> moves = other.getPossibleMoves(simulatedMap);
                    if (moves.contains(kingPos)) {
                        return true;
                    }
                }
            }
        }

        return false; // movimento é seguro
    }

    public static boolean isWithinBounds(int position) {
        return getX(position) >= 0 && getX(position) < 8 && getY(position) >= 0 && getY(position) < 8;
    }

    protected boolean isOpponent(ChessPiece other) {
        if (other == null) return false;
        return (this.type.getValor() <= 5 && other.getType().getValor() >= 6) ||
                (this.type.getValor() >= 6 && other.getType().getValor() <= 5);
    }

    @Override
    public int getX() {
        return this.position / 10; // Dezena
    }

    public static int getX(int position) {
        return position / 10;
    }

    @Override
    public int getY() {
        return this.position % 10; // Unidade
    }

    public static int getY(int position) {
        return position % 10;
    }

    @Override
    public String toString() {
        return "Tipo da peça: " + type.displayName() + ". " +
                "Na posição : (" + this.getX() +  ", " + this.getY() + ")";
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(int position) { // muda a posição
        this.position = position;
    }

    // o setPosition é pra uso interno de moveTo,
    // mas n pode ser private nem protected, somente public ai é peso.
    @Override
    public boolean moveTo(int position, ChessPiece[][] board) { // faz verificações antes de mudar
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc

        boolean exercito = this.type.getValor() <= 5; // if -> exercito branco; else -> exercito preto
        if (board[getX(position)][getY(position)].getType().getValor() <= 5 && !exercito) {
            setPosition(position);
            this.n_moves++;
            return true;
        } else if (board[getX(position)][getY(position)].getType().getValor() >= 6 && exercito) {
            setPosition(position);
            this.n_moves++;
            return true;
        }
        return false;
    }

    public Type getType() {
        return this.type;
    }

    public int getN_moves() {
        return this.n_moves;
    }

    public void setN_moves(int n_moves) {
        this.n_moves = n_moves;
    }
}
