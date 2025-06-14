package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.List;
import java.util.Map;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rei
 *
 * @author bia
 * @version 1.0
 * @since YYYY-MM-DD
 */
public class King extends ChessPiece {
    boolean roque = false; // true: listmoves tem roque. false: n tem roque

    public King(int position, Type type, int n_moves) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {
        // List<ChessPiece> rooks = BoardUtils.finsPieces(isWhite ? Type.ROOK_WHITE : Type.ROOK_BLACK);
        // if (rooks.getFist.getNMoves == 0 && this.getNMoves == 0) {
        // roque = true;
        // ...
        // }

        return List.of();
    }

    // Faltou isso
    //@Override
    public boolean moveTo(int position, List<Integer> listMoves, ChessPiece[][] board) {
        // List<ChessPiece> rooks = BoardUtils.finsPieces(isWhite ? Type.ROOK_WHITE : Type.ROOK_BLACK);
        // listMoves.in(position) -> true
        // roque == true
        // List<Integer> ED = new ArrayList<>() {{add(rooks.getIndexLast()) /n add(rooks.getIndexAntepenultimo())}};
        // listMoves.index(position) -> x
        // ED.in(x):
            // if (x == ED.getFist()) {
                // this.setPosition(position)
                // rooks.getLast().setPostion(position + 1)
            // } else {
                // this.setPosition(position)
                // rooks.getLast().setPostion(position - 1)
            // }

        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc
        return true; //super.moveTo(position, listMoves, board);
    }
}
