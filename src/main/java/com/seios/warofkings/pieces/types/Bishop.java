package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Bispo
 *
 * @author allan
 * @version 1.2
 * @since 2025-05-24
 */
public class Bishop extends ChessPiece {

    public Bishop(int position, Type type, int n_moves)  {
        if (!(type.getValor() == 3) && !(type.getValor() == 9)) {
            throw new IllegalArgumentException("Tipo inválido para bispo. Esperado BISHOP_WHITE (3) ou BISHOP_BLACK (9).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private Bishop(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static Bishop createBishop(int position, Type type) {
        if (!(type.getValor() == 3) && !(type.getValor() == 9)) {
            throw new IllegalArgumentException("Tipo inválido para bispo. Esperado BISHOP_WHITE (3) ou BISHOP_BLACK (9).");
        }
        return new Bishop(position, type);
    }

    /**
     * Retorna posições disponíveis para onde os Bispos podem se movimentar
     * <ul>
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

        int northwest = -11;
        int southwest = 9;

        int northeast = -9;
        int southeast = 11;

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
