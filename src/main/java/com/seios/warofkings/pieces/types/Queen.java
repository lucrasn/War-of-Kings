package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa a peça Rainha no xadrez, com movimentação combinada da torre e do bispo.
 * <p>
 * A rainha pode se mover em qualquer direção (vertical, horizontal e diagonal)
 * por qualquer número de casas, desde que o caminho esteja livre.
 * </p>
 *
 * <p><b>Nota:</b> Esta classe valida o {@link Type} para garantir que apenas rainhas
 * (branca ou preta) sejam instanciadas corretamente.</p>
 *
 * @author Allan
 * @version 1.3
 * @since 2025-05-24
 */
public class Queen extends ChessPiece {
    /**
     * Construtor completo da peça Rainha.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça, deve ser {@code QUEEN_WHITE (4)} ou {@code QUEEN_BLACK (10)}.
     * @param n_moves  número de movimentos realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não for uma rainha válida.
     */
    public Queen(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("Tipo inválido para rainha. Esperado QUEEN_WHITE (4) ou QUEEN_BLACK (10).");
        }
        this.position =position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Construtor privado usado pelo método fábrica.
     *
     * @param position posição da peça.
     * @param type     tipo da peça.
     */
    private Queen(int position, Type type) {
        super();
        this.position =position;
        this.type = type;
    }

    /**
     * Método fábrica para criar uma instância de {@code Queen}.
     *
     * @param position posição inicial no tabuleiro.
     * @param type     tipo da peça.
     * @return nova instância de {@code Queen}.
     * @throws IllegalArgumentException se o tipo informado não for válido.
     */
    public static Queen createQueen(int position, Type type) {
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("Tipo inválido para rainha. Esperado QUEEN_WHITE (4) ou QUEEN_BLACK (10).");
        }
        return new Queen(position, type);
    }

    /**
     * Retorna as posições válidas para onde a rainha pode se mover, considerando:
     * <ul>
     *     <li>Movimentação vertical (cima e baixo)</li>
     *     <li>Movimentação horizontal (esquerda e direita)</li>
     *     <li>Movimentação diagonal (noroeste, nordeste, sudoeste, sudeste)</li>
     * </ul>
     *
     * A rainha pode capturar peças adversárias que estiverem no caminho.
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de posições válidas como inteiros.
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<>();
        int pos = this.position;

        int forward = -10;
        int backward = 10;
        int right = 1;
        int left = -1;
        int northwest = -11;
        int southwest = 9;
        int northeast = -9;
        int southeast = 11;

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
            rightPos += right;
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

        int northwestPos = northwest + pos;
        while(BoardUtils.isWithinBounds(northwestPos) && board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)] == null){
            possibleMoves.add(northwestPos);
            northwestPos += northwest;
        }
        if(BoardUtils.isWithinBounds(northwestPos) && isOpponent(board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)]) ){
            possibleMoves.add(northwestPos);
        }

        int southwestPos = southwest + pos;
        while(BoardUtils.isWithinBounds(southwestPos) && board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)] == null){
            possibleMoves.add(southwestPos);
            southwestPos += southwest;
        }
        if(BoardUtils.isWithinBounds(southwestPos) && isOpponent(board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)]) ){
            possibleMoves.add(southwestPos);
        }

        int northeastPos = northeast + pos;
        while(BoardUtils.isWithinBounds(northeastPos) && board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)] == null){
            possibleMoves.add(northeastPos);
            northeastPos += northeast;
        }
        if(BoardUtils.isWithinBounds(northeastPos) && isOpponent(board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)]) ){
            possibleMoves.add(northeastPos);
        }

        int southeastPos = southeast + pos;
        while(BoardUtils.isWithinBounds(southeastPos) && board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)] == null){
            possibleMoves.add(southeastPos);
            southeastPos += southeast;
        }
        if(BoardUtils.isWithinBounds(southeastPos) && isOpponent(board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)]) ){
            possibleMoves.add(southeastPos);
        }

        return possibleMoves;
    }
}
