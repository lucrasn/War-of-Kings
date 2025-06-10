package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.List;

/**
 * Esta classe é responsável pela abstração dos movimentos das peças de xadrez
 *
 * @author lucas
 * @version 1.3
 * @since 2025-05-14
 */
public abstract class ChessPiece implements Movable, Positionable {
    protected int position;
    protected Type type;
    protected int n_moves;

    public ChessPiece() {
        this.n_moves = 0;
    }

    public ChessPiece(int n_moves) {
        this.n_moves = n_moves;
    }

    protected boolean kingCheck(int toPosition, ChessPiece[][] piecesMap) {
        // TODO: não precisa clonar os parametros pois ja trabalhamos com copias
        ChessPiece[][] simulatedMap = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                simulatedMap[i][j] = piecesMap[i][j];
            }
        }

        int fromX = this.getX();
        int fromY = this.getY();
        int toX = PieceUtils.getX(toPosition);
        int toY = PieceUtils.getY(toPosition);

        simulatedMap[fromX][fromY] = null;
        simulatedMap[toX][toY] = this;

        boolean isWhite = (this.type == Type.PAWN_WHITE);
        ChessPiece king = BoardUtils.findPieces(piecesMap ,isWhite ? Type.KING_WHITE : Type.KING_BLACK).getFirst();

        int kingX = king.getX();
        int kingY = king.getY();

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

    protected boolean isOpponent(ChessPiece other) {
        if (other == null) return false;
        return (this.type.getValor() <= 5 && other.getType().getValor() >= 6) ||
                (this.type.getValor() >= 6 && other.getType().getValor() <= 5);
    }

    @Override
    public int getX() {
        return this.position / 10; // Dezena
    }

    @Override
    public int getY() {
        return this.position % 10; // Unidade
    }

    @Override
    public String toString() {
        return "Tipo da peça: " + type.displayName() + ". " +
                "Na posição : (" + this.getX() +  ", " + this.getY() + ")";
    }

    public int getPosition() {
        return this.position;
    }

    // o setPosition é pra uso interno de moveTo,
    // mas n pode ser private nem protected, somente public ai é peso.
    @Override
    public void setPosition(int position) { // muda a posição
        this.position = position;
    }

    @Override
    public boolean moveTo(int position, List<Integer> listMoves, Board board) {
        if (BoardUtils.isWithinBounds(position) && listMoves.contains(position)) {
            ChessPiece[][] pieces = board.getPieces();
            pieces[getX()][getY()] = null; // onde estava a peça
            pieces[PieceUtils.getX(position)][PieceUtils.getY(position)] = this; // onde a peça foi

            board.setPieces(pieces); // altera de fato o movimento
            board.setTurn(board.getTurn().next()); // muda a vez

            setPosition(position);
            this.n_moves++;
            return true;
        }
        return false;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getN_moves() {
        return this.n_moves;
    }

    public void setN_moves(int n_moves) {
        this.n_moves = n_moves;
    }
}
