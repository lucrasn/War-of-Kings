package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.utils.BoardUtils;
import com.seios.warofkings.utils.PieceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Rei
 *
 * @author bia
 * @version 1.0
 * @since YYYY-MM-DD
 */
public class King extends ChessPiece {
    boolean roque = false; // true: listmoves tem roque. false: n tem roque

    public boolean isWhite() {

        return this.type.getValor() <= 6;   // brancas até 6 pretas depois de 7


    }

    public King(int position, Type type, int n_moves) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        this.position = position;
        this.type = type;
        this.n_moves = n_moves;
    }

    private King(int position, Type type) {
        super();
        this.position = position;
        this.type = type;
    }

    // Metodo de fábrica
    public static King createKing(int position, Type type) {
        if (!(type.getValor() == 5) && !(type.getValor() == 11)) {
            throw new IllegalArgumentException("Tipo inválido para rei. Esperado KING_WHITE (5) ou KING_BLACK (11).");
        }
        return new King(position, type);
    }

    @Override
    public List<Integer> getPossibleMoves(ChessPiece[][] board) {



        int pos = this.position;

        int forward = -10;
        int backward = 10;

        int right = 1;
        int left = -1;

        int northwest = -11;
        int southwest = 9;

        int northeast = -9;
        int southeast = 11;
        List<Integer> possibleMoves = new ArrayList<Integer>();


        List<ChessPiece> rooks = BoardUtils.findPieces(board, isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

        ChessPiece rook = rooks.get(0); // o getFirt n é um metodo d lista
        if (rook.getN_moves() == 0 && this.getN_moves() == 0) { // torrei e rei sem movimentos
            int kingPos = this.getPosition();
            int rookPos = rook.getPosition();

            int direction;
            if (rookPos > kingPos) {
                direction = 1; // roque da direita
            } else {
                direction = -1; // roque da esquerda
            }
            int roqueDireira = kingPos + direction;
            int roqueEsquerda = kingPos + 2 * direction;


            //if(board[PieceUtils.getX(roqueDireira)][PieceUtils.getY(roqueDireira)] == null ||
            //          board[PieceUtils.getX(roqueEsquerda)][PieceUtils.getY(roqueEsquerda)] == null) {
            //if (!kingCheck(roqueEsquerda, board) || !kingCheck(roqueDireira, board) ) {
            //  roque = true;

            //}
            //}
            if (board[PieceUtils.getX(roqueDireira)][PieceUtils.getY(roqueDireira)] == null) {
                if (!kingCheck(roqueDireira, board)) {
                    roque = true;
                    possibleMoves.add(roqueDireira);
                }
            }

            if (board[PieceUtils.getX(roqueEsquerda)][PieceUtils.getY(roqueEsquerda)] == null) {
                if (!kingCheck(roqueEsquerda, board)) {
                    roque = true;
                    possibleMoves.add(roqueEsquerda);
                }
            }

        }
        return possibleMoves;


        // Faltou isso
        //@Override
        //public boolean moveTo(int position, List<Integer> listMoves, ChessPiece[][] board) {

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

        //return true; //super.moveTo(position, listMoves, board);
    }
}

        //  return super.moveTo(position, listMoves, board);
        //}

