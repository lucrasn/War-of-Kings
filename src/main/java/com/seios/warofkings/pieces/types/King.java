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
}
