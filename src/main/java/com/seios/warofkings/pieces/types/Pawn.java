package com.seios.warofkings.pieces.types;

import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.PieceUtils;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Peão
 *
 * @author lucas
 * @version 1.5
 * @since 2025-05-14
 */
public class Pawn extends ChessPiece {
    public Pawn(int position, Type type, int n_moves) {
        if (!(type.getValor() == 0) && !(type.getValor() == 6)) {
            throw new IllegalArgumentException("Tipo inválido para peão. Esperado PAWN_WHITE (0) ou PAWN_BLACK (6).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private Pawn(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static Pawn createPawn(int position, Type type) {
        if (!(type.getValor() == 0) && !(type.getValor() == 6)) {
            throw new IllegalArgumentException("Tipo inválido para peão. Esperado PAWN_WHITE (0) ou PAWN_BLACK (6).");
        }
        return new Pawn(position, type);
    }

    /**
     * Retorna as posições válidas para onde o peão pode se mover, considerando:
     * <ul>
     *   <li>Movimento para frente se a casa estiver livre</li>
     *   <li>Duplo avanço no primeiro movimento</li>
     *   <li>Capturas nas diagonais</li>
     * </ul>
     *
     * @param board matriz representando o estado atual do tabuleiro
     * @return lista de posições inteiras possíveis para o movimento
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<Integer>();
        int pos = this.position;

        boolean isWhite = (this.type == Type.PAWN_WHITE);
        int forward = isWhite ? -10 : 10;
        int doubleForward = isWhite ? -20 : 20;
        int diagLeft = isWhite ? -11 : 9;
        int diagRight = isWhite ? -9 : 11;

        int fwdPos = pos + forward;
        if (PieceUtils.isWithinBounds(fwdPos) && board[PieceUtils.getX(fwdPos)][PieceUtils.getY(fwdPos)] == null) {
            if (!kingCheck(fwdPos, board)) {
                possibleMoves.add(fwdPos);
            }

            // Duplo avanço inicial
            int dblFwdPos = pos + doubleForward;
            if (n_moves == 0 && PieceUtils.isWithinBounds(dblFwdPos) && board[PieceUtils.getX(dblFwdPos)][PieceUtils.getY(dblFwdPos)] == null) {
                if (!kingCheck(dblFwdPos, board)) {
                    possibleMoves.add(dblFwdPos);
                }
            }
        }

        // Diagonal esquerda
        int diagLPos = pos + diagLeft;
        if (PieceUtils.isWithinBounds(diagLPos)) {
            if (isOpponent(board[PieceUtils.getX(diagLPos)][PieceUtils.getY(diagLPos)])) {
                if (!kingCheck(diagLPos, board)) {
                    possibleMoves.add(diagLPos);
                }
            }
        }

        // Diagonal direita
        int diagRPos = pos + diagRight;
        if (PieceUtils.isWithinBounds(diagRPos)) {
            if (isOpponent(board[PieceUtils.getX(diagRPos)][PieceUtils.getY(diagRPos)])) {
                if (!kingCheck(diagRPos, board)) {
                    possibleMoves.add(diagRPos);
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Substitui um peão por uma nova peça (rainha, torre, bispo ou cavalo) ao atingir a linha final.
     * A peça resultante é baseada no tipo de promoção passado como parâmetro.
     *
     * @param promotionType Tipo da peça para promoção (deve ser compatível com a cor do peão)
     * @return Nova peça promovida
     * @throws IllegalArgumentException se o tipo de promoção for inválido ou incompatível com a cor do peão
     */
    public ChessPiece promoteIfEligible(Type promotionType) {
        boolean isWhite = (this.type == Type.PAWN_WHITE);
        int finalRow = isWhite ? 0 : 7;

        if (this.getX() == finalRow) {
            return switch (promotionType) {
                case QUEEN_WHITE, QUEEN_BLACK -> new Queen(this.position, promotionType, this.n_moves);
                case ROOK_WHITE, ROOK_BLACK -> new Rook(this.position, promotionType, this.n_moves);
                case BISHOP_WHITE, BISHOP_BLACK -> new Bishop(this.position, promotionType, this.n_moves);
                case KNIGHT_WHITE, KNIGHT_BLACK -> new Knight(this.position, promotionType, this.n_moves);
                default -> throw new IllegalArgumentException("Tipo de promoção inválido: " + promotionType);
            };
        }

        return this;
    }
}
