package com.seios.warofkings.pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;

/**
 * Classe de testes unitários para os comportamentos comuns das peças de xadrez
 * definidos na classe {@link ChessPiece}, incluindo movimentação, verificação de oponente,
 * e detecção de xeque.
 *
 * <p>Estes testes asseguram a integridade das operações fundamentais de jogo como:</p>
 * <ul>
 *   <li>Identificação de peças adversárias;</li>
 *   <li>Movimentação condicional baseada em lista de movimentos válidos;</li>
 *   <li>Verificação de xeque ao rei após movimentos simulados.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class ChessPieceTest {

    /**
     * Testa o método {@link ChessPiece#isOpponent(ChessPiece)} para validar corretamente
     * se duas peças pertencem a jogadores diferentes.
     *
     * <p>Também garante que o método retorne {@code false} ao comparar com {@code null}
     * ou com a própria peça.</p>
     */
    @Test
    void testIsOpponent() {
        ChessPiece white = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece black = Pawn.createPawn(20, Type.PAWN_BLACK);
        assertTrue(white.isOpponent(black));
        assertTrue(black.isOpponent(white));
        assertFalse(white.isOpponent(white));
        assertFalse(black.isOpponent(black));
        assertFalse(white.isOpponent(null));
        assertFalse(black.isOpponent(null));
    }

    /**
     * Testa o método {@link ChessPiece#moveTo(int, List, Board)} para simular
     * uma movimentação válida contida na lista de movimentos permitidos.
     */
    @Test
    void testMoveToSuccess() {
        Board board = new Board();
        ChessPiece piece = Pawn.createPawn(10, Type.PAWN_WHITE);
        List<Integer> moves = new ArrayList<>();
        moves.add(20);
        boolean moved = piece.moveTo(20, moves, board);
        assertTrue(moved);
        assertEquals(20, piece.getPosition());
    }

    /**
     * Testa o método {@link ChessPiece#moveTo(int, List, Board)} com um destino inválido,
     * não presente na lista de movimentos permitidos.
     */
    @Test
    void testMoveToFail() {
        Board board = new Board();
        ChessPiece piece = Pawn.createPawn(10, Type.PAWN_WHITE);
        List<Integer> moves = new ArrayList<>();
        moves.add(21); // posição diferente
        boolean moved = piece.moveTo(22, moves, board);
        assertFalse(moved);
        assertEquals(10, piece.getPosition());
    }

    /**
     * Testa o método {@link ChessPiece#kingCheck(int, ChessPiece[][])} para verificar
     * se um movimento coloca o rei em situação de xeque (caso verdadeiro).
     */
    @Test
    public void testKingCheckTrue() {
        Board board = new Board();
        ChessPiece king = King.createKing(42, Type.KING_WHITE);
        ChessPiece pawn = Pawn.createPawn(43, Type.PAWN_WHITE);
        ChessPiece rook = Rook.createRook(44, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][2] = king;
        pieces[4][3] = pawn;
        pieces[4][4] = rook;

        assertTrue(pawn.kingCheck(33, pieces));
    }

    /**
     * Testa o método {@link ChessPiece#kingCheck(int, ChessPiece[][])} quando a jogada
     * não coloca o rei sob ameaça de xeque (caso falso).
     */
    @Test
    void testKingCheckFalse() {
        Board board = new Board();
        ChessPiece king = King.createKing(42, Type.KING_WHITE);
        ChessPiece pawn = Pawn.createPawn(53, Type.PAWN_WHITE);
        ChessPiece rook = Rook.createRook(44, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][2] = king;
        pieces[5][3] = pawn;
        pieces[4][4] = rook;

        assertFalse(pawn.kingCheck(43, pieces));
    }

    /**
     * Testa o método {@link ChessPiece#kingCheck(int, ChessPiece[][])} quando o próprio
     * rei realiza um movimento que resulta em xeque.
     */
    @Test
    void testSelfKingCheckTrue() {
        Board board = new Board();
        ChessPiece king = King.createKing(42, Type.KING_WHITE);
        ChessPiece rook = Rook.createRook(34, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][2] = king;
        pieces[3][4] = rook;

        assertTrue(king.kingCheck(32, pieces));
    }

    /**
     * Testa o método {@link ChessPiece#kingCheck(int, ChessPiece[][])} quando o rei
     * realiza um movimento seguro, sem entrar em xeque.
     */
    @Test
    void testSelfKingCheckFalse() {
        Board board = new Board();
        ChessPiece king = King.createKing(42, Type.KING_WHITE);
        ChessPiece rook = Rook.createRook(44, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][2] = king;
        pieces[4][4] = rook;

        assertFalse(king.kingCheck(32, pieces));
    }
}
