package com.seios.warofkings.pieces.types;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rei
 *
 * @author bia
 * @version 2.5
 * @since 2025-06-09
 */
public class King extends ChessPiece {
    public King(int position, Type type, int n_moves) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private King(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static King createKing(int position, Type type) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        return new King(position, type);
    }

    private boolean isValidCastling(ChessPiece rook, ChessPiece[][] board) {
        if (this.getN_moves() > 0 || rook.getN_moves() > 0) return false;

        int kingPos = this.getPosition();
        int rookPos = rook.getPosition();

        return BoardUtils.isPathClearHorizontally(kingPos, rookPos, board) && PieceUtils.pathSafeForKing(rookPos, this, board);
    }

    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<Integer>();

        int forward = -10;
        int backward = 10;
        int right = 1;
        int left = -1;
        int northwest = -11;
        int southwest = 9;
        int northeast = -9;
        int southeast = 11;

        // movimentos normais do rei
        int[] directions = {forward, backward, left, right, northwest, northeast, southwest, southeast};

        for (int dir : directions) {
            int target = this.position + dir;

            if (BoardUtils.isWithinBounds(target)) {
                ChessPiece targetPiece = board[PieceUtils.getX(target)][PieceUtils.getY(target)];
                if (targetPiece == null || isOpponent(targetPiece)) {
                    possibleMoves.add(target);
                }
            }
        }

        // roque do rei
        if (this.getN_moves() == 0) {
            List<ChessPiece> rooks = BoardUtils.findPieces(board, this.isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

            for (ChessPiece rook : rooks) {
                if (isValidCastling(rook, board)) {
                    int kingPos = this.getPosition();
                    int rookPos = rook.getPosition();
                    int direction = (kingPos > rookPos) ? -1 : 1;

                    int castlingTarget = kingPos + 2 * direction;
                    possibleMoves.add(castlingTarget);
                }
            }
        }

        return possibleMoves; // possibleMoves = {..., positionRoqueLeft, positionRoqueRight}
    }

    @Override
    public boolean moveTo(int position, List<Integer> listMoves, Board board) {
        if (!listMoves.contains(position)) return false;

        // Verifica se o movimento é um roque — os dois últimos da lista são reservados para isso
        boolean isCastlingMove = listMoves.size() >= 2 &&
                (position == listMoves.getLast() || position == listMoves.get(listMoves.size() - 2));

        if (isCastlingMove) {
            int kingPos = this.getPosition();
            int direction = (kingPos > position) ? -1 : 1;
            int rookTargetPos = kingPos + (direction > 0 ? 1 : -1); // posição final da torre

            ChessPiece[][] pieces = board.getPieces();

            // Checa se o roque deixa o rei em xeque
            if (this.kingCheck(position, pieces)) {
                System.out.println("Movimento de roque deixaria o rei em xeque!");
                return false;
            }

            // Encontra a torre correta
            List<ChessPiece> rooks = BoardUtils.findPieces(pieces, this.isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

            for (ChessPiece rook : rooks) {
                if (rook.getN_moves() > 0) continue;

                int rookPos = rook.getPosition();
                boolean correta = (direction > 0 && rookPos > kingPos) || (direction < 0 && rookPos < kingPos);
                if (correta) {
                    // Executa movimento da torre
                    pieces[PieceUtils.getX(rookPos)][PieceUtils.getY(rookPos)] = null;
                    pieces[PieceUtils.getX(rookTargetPos)][PieceUtils.getY(rookTargetPos)] = rook;
                    board.setPieces(pieces);
                    rook.setPosition(rookTargetPos);
                    rook.setN_moves(rook.getN_moves() + 1);
                    break;
                }
            }
        }

        // Se não for roque, ou após o roque, executa movimento normal
        return super.moveTo(position, listMoves, board);
    }
}