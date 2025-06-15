package com.seios.warofkings.pieces.types;

import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

/**
 * Representa a peça Peão no xadrez, incluindo suas regras de movimentação e promoção.
 * <p>
 * O peão move-se para frente, captura na diagonal e pode avançar duas casas no primeiro movimento.
 * Também pode ser promovido ao alcançar a última linha do tabuleiro.
 * </p>
 *
 * <p><b>Nota:</b> Esta classe valida o {@link Type} para garantir que apenas peões válidos
 * (branco ou preto) sejam instanciados corretamente.</p>
 *
 * @author Lucas
 * @version 1.5
 * @since 2025-05-14
 */
public class Pawn extends ChessPiece {
    /**
     * Construtor completo da peça Peão.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça, deve ser {@code PAWN_WHITE (0)} ou {@code PAWN_BLACK (6)}.
     * @param n_moves  número de movimentos realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não for um peão válido.
     */
    public Pawn(int position, Type type, int n_moves) {
        if (!(type.getValor() == 0) && !(type.getValor() == 6)) {
            throw new IllegalArgumentException("Tipo inválido para peão. Esperado PAWN_WHITE (0) ou PAWN_BLACK (6).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Construtor privado usado pelo método fábrica.
     *
     * @param position posição inicial.
     * @param type     tipo da peça.
     */
    private Pawn(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    /**
     * Método fábrica para criação de instâncias de {@code Pawn}.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça.
     * @return nova instância de {@code Pawn}.
     * @throws IllegalArgumentException se o tipo informado não for um peão válido.
     */
    public static Pawn createPawn(int position, Type type) {
        if (!(type.getValor() == 0) && !(type.getValor() == 6)) {
            throw new IllegalArgumentException("Tipo inválido para peão. Esperado PAWN_WHITE (0) ou PAWN_BLACK (6).");
        }
        return new Pawn(position, type);
    }

    /**
     * Retorna as posições possíveis para onde o peão pode se mover.
     * Considera:
     * <ul>
     *   <li>Avanço de uma casa para frente</li>
     *   <li>Avanço de duas casas se ainda não moveu</li>
     *   <li>Captura em diagonais</li>
     * </ul>
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de posições válidas.
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
        if (BoardUtils.isWithinBounds(fwdPos) && board[PieceUtils.getX(fwdPos)][PieceUtils.getY(fwdPos)] == null) {
            possibleMoves.add(fwdPos);

            // Duplo avanço inicial
            int dblFwdPos = pos + doubleForward;
            if (n_moves == 0 && BoardUtils.isWithinBounds(dblFwdPos) && board[PieceUtils.getX(dblFwdPos)][PieceUtils.getY(dblFwdPos)] == null) {
                possibleMoves.add(dblFwdPos);
            }
        }

        // Diagonal esquerda
        int diagLPos = pos + diagLeft;
        if (BoardUtils.isWithinBounds(diagLPos)) {
            if (isOpponent(board[PieceUtils.getX(diagLPos)][PieceUtils.getY(diagLPos)])) {
                possibleMoves.add(diagLPos);
            }
        }

        // Diagonal direita
        int diagRPos = pos + diagRight;
        if (BoardUtils.isWithinBounds(diagRPos)) {
            if (isOpponent(board[PieceUtils.getX(diagRPos)][PieceUtils.getY(diagRPos)])) {
                possibleMoves.add(diagRPos);
            }
        }

        return possibleMoves;
    }

    /**
     * Realiza a promoção do peão ao atingir a última fileira.
     * Substitui o peão por uma nova peça do tipo especificado.
     *
     * @param promotionType tipo da peça resultante (rainha, torre, bispo ou cavalo).
     * @return nova peça promovida ou o próprio peão se não for elegível.
     * @throws IllegalArgumentException se o tipo for inválido ou incompatível com a cor do peão.
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
