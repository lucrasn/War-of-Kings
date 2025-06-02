package com.seios.warofkings.pieces.types;
import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

/**
 * Esta classe é responsável pela implementação dos movimentos da peça Torre
 *
 * @author bia
 * @version 1.0
 * @since YYYY-MM-DD
 */
public class Rook extends ChessPiece {
    private Rook(int position, Type type){
        super();
        this.position =position;
        this.type = type;
    }

    public Rook(int position, Type type, int n_moves) {
        if (!(type.getValor() == 1) && !(type.getValor() == 7)) {
            throw new IllegalArgumentException("Tipo inválido para torre. Esperado ROOK_WHITE (1) ou ROOK_BLACK (7).");
        }
        this.position =position;
        this.type = type;
        this.n_moves = n_moves;
    }

    public static Rook createRook(int position, Type type) {
        if (!(type.getValor() == 1) && !(type.getValor() == 7)) {
            throw new IllegalArgumentException("Tipo inválido para torre. Esperado ROOK_WHITE (1) ou ROOK_BLACK (7).");
        }
        return new Rook(position, type);
    }

    /**
     * Retorna as posições válidas para onde o peão pode se mover, considerando:
     * <ul>
     *   <li>Movimentação nas laterais</li>
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


        //int x = getX();
      //  int y = getY();

        //for (int col = 0; col < 8; col++) {
          //  if (col != x) {
            //    possibilities.add((col * 10) + y);
            //}
        //}

        //for (int row = 0; row < 8; row++) {
          //  if (row != y) {
            //    possibilities.add((x * 10) + row);
            //}
        //}

        // tem que fazer a verificacao ainda de se tem peça e se deixa o meu rei em xeque p poder andar ou não.
            // seria o uso do kingCheck?

        return possibleMoves;
    }

    // Faltou isso
    @Override
    public boolean moveTo(int position, List<Integer> listMoves, ChessPiece[][] board) {
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc
        return super.moveTo(position, listMoves, board);
    }
}