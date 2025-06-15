package com.seios.warofkings.pieces.types;
import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

/**
 * Representa a peça Torre no xadrez, com movimentação ortogonal.
 * <p>
 * A torre pode se mover quantas casas quiser na horizontal ou vertical,
 * desde que o caminho esteja livre. Pode capturar peças adversárias no final do caminho.
 * </p>
 *
 * <p><b>Nota:</b> Esta classe valida o {@link Type} para garantir que apenas torres
 * (branca ou preta) sejam instanciadas corretamente.</p>
 *
 * @author Beatriz
 * @version 1.0
 * @since 2024-05-28
 */
public class Rook extends ChessPiece {
    /**
     * Construtor privado usado pelo método fábrica.
     *
     * @param position posição da torre no tabuleiro.
     * @param type     tipo da peça.
     */
    private Rook(int position, Type type){
        super();
        this.position =position;
        this.type = type;
    }

    /**
     * Construtor completo da peça Torre.
     *
     * @param position posição inicial da peça.
     * @param type     tipo da peça, deve ser {@code ROOK_WHITE (1)} ou {@code ROOK_BLACK (7)}.
     * @param n_moves  número de movimentos realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não for uma torre válida.
     */
    public Rook(int position, Type type, int n_moves) {
        if (!(type.getValor() == 1) && !(type.getValor() == 7)) {
            throw new IllegalArgumentException("Tipo inválido para torre. Esperado ROOK_WHITE (1) ou ROOK_BLACK (7).");
        }
        this.position =position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Método fábrica para criação de uma instância de {@code Rook}.
     *
     * @param position posição inicial da torre no tabuleiro.
     * @param type     tipo da peça.
     * @return nova instância de {@code Rook}.
     * @throws IllegalArgumentException se o tipo informado não for uma torre válida.
     */
    public static Rook createRook(int position, Type type) {
        if (!(type.getValor() == 1) && !(type.getValor() == 7)) {
            throw new IllegalArgumentException("Tipo inválido para torre. Esperado ROOK_WHITE (1) ou ROOK_BLACK (7).");
        }
        return new Rook(position, type);
    }

    /**
     * Retorna todas as posições possíveis para onde a torre pode se mover,
     * considerando:
     * <ul>
     *   <li>Movimentações verticais: para cima e para baixo</li>
     *   <li>Movimentações horizontais: para esquerda e para direita</li>
     *   <li>Interrupção ao encontrar uma peça aliada ou adversária</li>
     * </ul>
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de posições válidas (como inteiros) para o movimento da torre.
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
            List<Integer> possibleMoves = new ArrayList<>();
            int pos = this.position;

            int forward = -10;
            int backward = 10;
            int right = 1;
            int left = -1;

            int forwardPos = forward + pos;
            while(BoardUtils.isWithinBounds(forwardPos) && board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)] == null){
                possibleMoves.add(forwardPos);
                forwardPos += forward;
            }
            if(BoardUtils.isWithinBounds(forwardPos) && isOpponent(board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)]) ){
                possibleMoves.add(forwardPos);
            }

            int backwardPos = backward + pos;
            while(BoardUtils.isWithinBounds(backwardPos) && board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)] == null){
                possibleMoves.add(backwardPos);
                backwardPos += backward;
            }
            if(BoardUtils.isWithinBounds(backwardPos) && isOpponent(board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)]) ){
                possibleMoves.add(backwardPos);
            }

            int rightPos = right + pos;
            while(BoardUtils.isWithinBounds(rightPos) && board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)] == null){
                possibleMoves.add(rightPos);
                rightPos += right ;
            }
            if(BoardUtils.isWithinBounds(rightPos) && isOpponent(board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)]) ){
                possibleMoves.add(rightPos);
            }

            int leftPos = left + pos;
            while(BoardUtils.isWithinBounds(leftPos) && board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)] == null){
                possibleMoves.add(leftPos);
                leftPos += left;
            }
            if(BoardUtils.isWithinBounds(leftPos) && isOpponent(board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)]) ){
                possibleMoves.add(leftPos);
            }

        return possibleMoves;
    }

}