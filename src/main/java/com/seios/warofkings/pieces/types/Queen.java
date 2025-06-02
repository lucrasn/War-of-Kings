package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rainha
 *
 * @author allan
 * @version 1.2
 * @since 2025-05-24
 */
public class Queen extends ChessPiece {
    public Queen(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("Tipo inválido para rainha. Esperado QUEEN_WHITE (4) ou QUEEN_BLACK (10).");
        }
        this.position =position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private Queen(int position, Type type) {
        super();
        this.position =position;
        this.type = type;
    }

    public static Queen createQueen(int position, Type type) {
        if (!(type.getValor() == 4) && !(type.getValor() == 10)) {
            throw new IllegalArgumentException("Tipo inválido para rainha. Esperado QUEEN_WHITE (4) ou QUEEN_BLACK (10).");
        }
        return new Queen(position, type);
    }


    /**
     * Retorna posições disponíveis para onde a Rainha pode se movimentar
     * <ul>
     *     <li>Movimentação na horizontal e vertical</li>
     *     <li>Movimentação nas laterais</li>
     * </ul>
     *
     * @param board matriz representando o estado atual do tabuleiro
     * @return lista de posições inteiras possíveis para o movimento
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
        while(PieceUtils.isWithinBounds(forwardPos) && board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)] == null){
            possibleMoves.add(forwardPos);
            forwardPos += pos;
        }
        if(PieceUtils.isWithinBounds(forwardPos) && isOpponent(board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)]) ){
            possibleMoves.add(forwardPos);
        }


        int backwardPos = backward + pos;
        while(PieceUtils.isWithinBounds(backwardPos) && board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)] == null){
            possibleMoves.add(backwardPos);
            backwardPos += pos;
        }
        if(PieceUtils.isWithinBounds(backwardPos) && isOpponent(board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)]) ){
            possibleMoves.add(backwardPos);
        }


        int rightPos = right + pos;
        while(PieceUtils.isWithinBounds(rightPos) && board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)] == null){
            possibleMoves.add(rightPos);
            rightPos += pos;
        }
        if(PieceUtils.isWithinBounds(rightPos) && isOpponent(board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)]) ){
            possibleMoves.add(rightPos);
        }


        int leftPos = left + pos;
        while(PieceUtils.isWithinBounds(leftPos) && board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)] == null){
            possibleMoves.add(leftPos);
            leftPos += pos;
        }
        if(PieceUtils.isWithinBounds(leftPos) && isOpponent(board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)]) ){
            possibleMoves.add(leftPos);
        }


        int northwestPos = northwest + pos;
        while(PieceUtils.isWithinBounds(northwestPos) && board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)] == null){
            possibleMoves.add(northwestPos);
            northwestPos += pos;
        }
        if(PieceUtils.isWithinBounds(northwestPos) && isOpponent(board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)]) ){
            possibleMoves.add(northwestPos);
        }


        int southwestPos = southwest + pos;
        while(PieceUtils.isWithinBounds(southwestPos) && board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)] == null){
            possibleMoves.add(southwestPos);
            southwestPos += pos;
        }
        if(PieceUtils.isWithinBounds(southwestPos) && isOpponent(board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)]) ){
            possibleMoves.add(southwestPos);
        }


        int northeastPos = northeast + pos;
        while(PieceUtils.isWithinBounds(northeastPos) && board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)] == null){
            possibleMoves.add(northeastPos);
            northeastPos += pos;
        }
        if(PieceUtils.isWithinBounds(northeastPos) && isOpponent(board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)]) ){
            possibleMoves.add(northeastPos);
        }


        int southeastPos = southeast + pos;
        while(PieceUtils.isWithinBounds(southeastPos) && board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)] == null){
            possibleMoves.add(southeastPos);
            southeastPos += pos;
        }
        if(PieceUtils.isWithinBounds(southeastPos) && isOpponent(board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)]) ){
            possibleMoves.add(southeastPos);
        }

        // Usar o kingCheck antes de retornar a lista

        return possibleMoves;
    }

    // Faltou isso
    @Override
    public boolean moveTo(int position, List<Integer> listMoves, ChessPiece[][] board) {
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc
        return super.moveTo(position, listMoves, board);
    }
}
