package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.board.enums.Turn;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.board.enums.Turn;

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

        piecesMap[this.getX()][this.getY()] = null;
        piecesMap[PieceUtils.getX(toPosition)][PieceUtils.getY(toPosition)] = this;

        boolean isWhite = (this.type == Type.PAWN_WHITE);
        ChessPiece king = BoardUtils.findPieces(piecesMap ,isWhite ? Type.KING_WHITE : Type.KING_BLACK).getFirst();

        int kingX = king.getX();
        int kingY = king.getY();

        int kingPos = kingX * 10 + kingY;

        for (ChessPiece[] row : piecesMap) {
            for (ChessPiece other : row) {
                if (other != null && other.getType().getValor() / 6 != king.getType().getValor() / 6) {
                    List<Integer> moves = other.getPossibleMoves(piecesMap);
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

    public String getImgName() {
        return switch (type) {
            case PAWN_BLACK -> "Pawn_Black.png";
            case ROOK_BLACK -> "Rook_Black.png";
            case KNIGHT_BLACK -> "Knight_Black.png";
            case BISHOP_BLACK -> "Bishop_Black.png";
            case QUEEN_BLACK -> "Queen_Black.png";
            case KING_BLACK -> "King_Black.png";
            case PAWN_WHITE -> "Pawn_White.png";
            case ROOK_WHITE -> "Rook_White.png";
            case KNIGHT_WHITE -> "Knight_White.png";
            case BISHOP_WHITE -> "Bishop_White.png";
            case QUEEN_WHITE -> "Queen_White.png";
            case KING_WHITE -> "King_White.png";

            default -> null;
        };

    }

    public Turn getColor(){
        if(type.name().endsWith("WHITE")){
            return Turn.WHITE;
        }
        else {
            return Turn.BLACK;
        }
    }
}
