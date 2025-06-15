package com.seios.warofkings.pieces.types;

import com.seios.warofkings.board.Board;
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

        int forwardPos = forward + pos;
        if (BoardUtils.isWithinBounds(forwardPos) &&
                (board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)] == null ||
                        isOpponent(board[PieceUtils.getX(forwardPos)][PieceUtils.getY(forwardPos)]))) {
            possibleMoves.add(forwardPos);
        }


        int backwardPos = backward + pos;
        if (BoardUtils.isWithinBounds(backwardPos) &&
                (board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)] == null ||
                        isOpponent(board[PieceUtils.getX(backwardPos)][PieceUtils.getY(backwardPos)]))) {
            possibleMoves.add(backwardPos);
        }


        int rightPos = right + pos;
        if (BoardUtils.isWithinBounds(rightPos) &&
                (board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)] == null ||
                        isOpponent(board[PieceUtils.getX(rightPos)][PieceUtils.getY(rightPos)]))) {
            possibleMoves.add(rightPos);
        }


        int leftPos = left + pos;
        if (BoardUtils.isWithinBounds(leftPos) &&
                (board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)] == null ||
                        isOpponent(board[PieceUtils.getX(leftPos)][PieceUtils.getY(leftPos)]))) {
            possibleMoves.add(leftPos);
        }


        int northwestPos = northwest + pos;
        if (BoardUtils.isWithinBounds(northwestPos) &&
                (board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)] == null ||
                        isOpponent(board[PieceUtils.getX(northwestPos)][PieceUtils.getY(northwestPos)]))) {
            possibleMoves.add(northwestPos);
        }


        int southwestPos = southwest + pos;
        if (BoardUtils.isWithinBounds(southwestPos) &&
                (board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)] == null ||
                        isOpponent(board[PieceUtils.getX(southwestPos)][PieceUtils.getY(southwestPos)]))) {
            possibleMoves.add(southwestPos);
        }


        int northeastPos = northeast + pos;
        if (BoardUtils.isWithinBounds(northeastPos) &&
                (board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)] == null ||
                        isOpponent(board[PieceUtils.getX(northeastPos)][PieceUtils.getY(northeastPos)]))) {
            possibleMoves.add(northeastPos);
        }


        int southeastPos = southeast + pos;
        if (BoardUtils.isWithinBounds(southeastPos) &&
                (board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)] == null ||
                        isOpponent(board[PieceUtils.getX(southeastPos)][PieceUtils.getY(southeastPos)]))) {
            possibleMoves.add(southeastPos);
        }


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

                    roque = true;
                    possibleMoves.add(roqueDireira);

            }

            if (board[PieceUtils.getX(roqueEsquerda)][PieceUtils.getY(roqueEsquerda)] == null) {

                    roque = true;
                    possibleMoves.add(roqueEsquerda);

            }

        }
        return possibleMoves;
    }


        // Faltou isso
        @Override
        public boolean moveTo(int position, List<Integer> listMoves, Board board) {
            ChessPiece[][] pieces = board.getPieces();
            if (!listMoves.contains(position)) {
                return false;
            }

            if (roque) {
                int kingPos = this.getPosition();
                int direction = position - kingPos;
                int novaPosTorre; // pos final da torre
                if (direction > 0) {
                    novaPosTorre = position - 1; // roque pequeno
                } else {
                    novaPosTorre = position + 1; // roque grande
                }
                int passo1 = kingPos + (direction > 0 ? 1 : -1); // tem que conferir isso aqui porque na regra do xadrez mesmo que na
                // casa fnal n deixe o rei em xeque mas a casa que ele for passar deixar n pode fazer roque
                int passo2 = position;
                if (kingCheck(passo1, pieces) || kingCheck(passo2, pieces)) {
                    System.out.println("Movimento deixaria o rei em xeque!");
                    return false;
                }


                List<ChessPiece> rooks = BoardUtils.findPieces(board.getPieces(), isWhite() ? Type.ROOK_WHITE : Type.ROOK_BLACK);

                for (ChessPiece rook : rooks) {
                    if (rook.getN_moves() == 0) {
                        int rookPos = rook.getPosition();

                        if ((direction > 0 && rookPos > kingPos) || (direction < 0 && rookPos < kingPos)) {
                            // Mover a torre também
                            pieces[PieceUtils.getX(rookPos)][PieceUtils.getY(rookPos)] = null;
                            pieces[PieceUtils.getX(novaPosTorre)][PieceUtils.getY(novaPosTorre)] = rook;
                            rook.setPosition(novaPosTorre);
                            rook.setN_moves(rook.getN_moves() + 1);

                        }
                    }
                }
            }

            return super.moveTo(position, listMoves, board);
        }

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


        //  return super.moveTo(position, listMoves, board);
        //}

