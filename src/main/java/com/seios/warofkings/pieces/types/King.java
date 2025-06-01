package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rei
 *
 * @author bia
 * @version 1.0
 * @since YYYY-MM-DD
 */
public class King extends ChessPiece {
    // Faltou isso
    @Override
    public boolean moveTo(int position, ChessPiece[][] board) {
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc
        return super.moveTo(position, board);
    }
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.List;

public class King extends ChessPiece {
    public King(int position, Type type) throws IllegalArgumentException {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("");
            return new King(position, type);
        }

        this.position = position;
        this.type = type;
        this.n_moves = 0;


    }


    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        return List.of();
    }
}
