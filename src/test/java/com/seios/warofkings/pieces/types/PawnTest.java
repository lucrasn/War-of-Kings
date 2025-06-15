package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PawnTest {

    @Test
    void testCreatePawnValid() {
        Pawn pawn = Pawn.createPawn(22, Type.PAWN_WHITE);
        assertEquals(22, pawn.getPosition());
        assertEquals(Type.PAWN_WHITE, pawn.getType());
    }

    @Test
    void testPossibleMovesWhiteEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(23)); //acima
        assertTrue(moves.contains(13)); //2 acima
        assertFalse(moves.contains(33)); // onde ela ja ta
    }

    @Test
    void testPossibleMovesBlackEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(43)); //abaixo
        assertTrue(moves.contains(53)); //2 abaixo
        assertFalse(moves.contains(33)); // onde ela ja ta
    }


    @Test
    void testPossibleWhiteInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;
        board[2][2] = Pawn.createPawn(22,Type.PAWN_WHITE);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertFalse(moves.contains(22)); //aliado
    }
    @Test
    void testPossibleBlackInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;
        board[4][4] = Pawn.createPawn(44,Type.PAWN_BLACK);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertFalse(moves.contains(44)); //aliado
    }

    @Test
    void testPossibleWhiteMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;
        board[2][2] = Pawn.createPawn(22,Type.PAWN_BLACK);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(22)); //pode capturar inimigo
    }

    @Test
    void testPossibleBlackMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;
        board[4][4] = Pawn.createPawn(44,Type.PAWN_WHITE);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(44)); //pode capturar inimigo
    }

    @Test
    void testPawnPromotionToQueen() {
        Pawn pawn = Pawn.createPawn(4, Type.PAWN_WHITE);
        ChessPiece promoted = pawn.promoteIfEligible(Type.QUEEN_WHITE);
        assertTrue(promoted instanceof Queen);
        assertEquals(Type.QUEEN_WHITE, promoted.getType());
    }

    @Test
    void testBlackPawnPromotesToKnight() {
        Pawn pawn = Pawn.createPawn(72, Type.PAWN_BLACK); // linha 7, coluna 2
        ChessPiece promoted = pawn.promoteIfEligible(Type.KNIGHT_BLACK);

        assertTrue(promoted instanceof Knight);
        assertEquals(Type.KNIGHT_BLACK, promoted.getType());
    }

    @Test
    void testWhitePawnNotPromotedIfNotOnLastRow() {
        Pawn pawn = Pawn.createPawn(64, Type.PAWN_WHITE); //linha 6
        ChessPiece result = pawn.promoteIfEligible(Type.QUEEN_WHITE);

        assertNotEquals(Type.QUEEN_WHITE, result.getType()); // n deve ser promovido
    }

    @Test
    void testInvalidPromotionThrowsException() {
        Pawn pawn = Pawn.createPawn(4, Type.PAWN_WHITE);

        assertThrows(IllegalArgumentException.class, () -> {
            pawn.promoteIfEligible(Type.KING_WHITE); // tipo invalido para promoção
        });
    }
}
