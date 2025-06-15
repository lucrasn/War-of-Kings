package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a peça Bispo no xadrez, incluindo sua lógica de movimentação.
 * <p>
 * O bispo se movimenta livremente pelas diagonais enquanto não encontrar obstáculos,
 * podendo capturar peças adversárias.
 * </p>
 *
 * <p><b>Nota:</b> Esta classe valida o {@link Type} para garantir que apenas bispos
 * (branco ou preto) sejam instanciados corretamente.</p>
 *
 * @author Allan
 * @version 1.3
 * @since 2025-05-24
 */
public class Bishop extends ChessPiece {
    /**
     * Construtor completo da peça Bispo.
     *
     * @param position posição inteira da peça no tabuleiro (ex: 42).
     * @param type     tipo da peça, deve ser {@code BISHOP_WHITE} ou {@code BISHOP_BLACK}.
     * @param n_moves  número de movimentos realizados pela peça.
     * @throws IllegalArgumentException se o tipo informado não for um bispo válido.
     */
    public Bishop(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 3) && !(type.getValor() == 9)) {
            throw new IllegalArgumentException("Tipo inválido para bispo. Esperado BISHOP_WHITE (3) ou BISHOP_BLACK (9).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    /**
     * Construtor privado utilizado pelo método fábrica.
     *
     * @param position posição no tabuleiro.
     * @param type     tipo da peça.
     */
    private Bishop(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    /**
     * Método fábrica para criar uma instância de {@code Bishop}.
     *
     * @param position posição inicial da peça.
     * @param type     tipo da peça, deve ser {@code BISHOP_WHITE} ou {@code BISHOP_BLACK}.
     * @return instância de {@code Bishop}.
     * @throws IllegalArgumentException se o tipo informado não for um bispo válido.
     */
    public static Bishop createBishop(int position, Type type) {
        if (!(type.getValor() == 3) && !(type.getValor() == 9)) {
            throw new IllegalArgumentException("Tipo inválido para bispo. Esperado BISHOP_WHITE (3) ou BISHOP_BLACK (9).");
        }
        return new Bishop(position, type);
    }

    /**
     * Calcula as possíveis posições para as quais o bispo pode se mover no tabuleiro.
     * O movimento é feito nas diagonais, até que encontre uma peça ou o limite do tabuleiro.
     * <p>
     * Se encontrar uma peça adversária, ela pode ser capturada.
     * </p>
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de inteiros representando posições válidas de destino.
     */
    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        List<Integer> possibleMoves = new ArrayList<>();
        int pos = this.position;

        int northwest = -11;
        int southwest = 9;
        int northeast = -9;
        int southeast = 11;

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
