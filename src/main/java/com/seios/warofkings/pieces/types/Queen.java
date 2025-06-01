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
 * @version 1.0
 * @since 2025-05-24
 */
public class Queen extends ChessPiece {
    public Queen(int position, Type type, int n_moves)  throws IllegalArgumentException {
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

    public static Queen createQueen(int position, Type type) throws IllegalArgumentException {
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
        while(PieceUtils.isWithinBounds(forwardPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(forwardPos);
        }

        int backwardPos = backward + pos;
        while(PieceUtils.isWithinBounds(backwardPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(backwardPos);
        }

        int rightPos = right + pos;
        while(PieceUtils.isWithinBounds(rightPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(rightPos);
        }

        int leftPos = left + pos;
        while(PieceUtils.isWithinBounds(leftPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(leftPos);
        }

        int northwestPos = northwest + pos;
        while(PieceUtils.isWithinBounds(northwestPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(northwestPos);
        }

        int southwestPos = southwest + pos;
        while(PieceUtils.isWithinBounds(southwestPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(southwestPos);
        }

        int northeastPos = northeast + pos;
        while(PieceUtils.isWithinBounds(northeastPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(northeastPos);
        }

        int southeastPos = southeast + pos;
        while(PieceUtils.isWithinBounds(southeastPos) && isOpponent(board[getX()][getY()])){
            possibleMoves.add(southeastPos);
        }

        // Usar o kingCheck antes de retornar a lista

        return possibleMoves;
    }

    // Faltou isso
    @Override
    public boolean moveTo(int position, ChessPiece[][] board) {
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc
        return super.moveTo(position, board);
    }
}
