package com.seios.warofkings.pieces.types;


import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    void testCreateBishopValid() {
        Bishop bishop = Bishop.createBishop(22, Type.BISHOP_WHITE);
        assertEquals(22, bishop.getPosition());
        assertEquals(Type.BISHOP_WHITE, bishop.getType());
    }

    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertTrue(moves.contains(22)); // noroeste
        assertTrue(moves.contains(44)); // sudeste
        assertTrue(moves.contains(24)); // nordeste
        assertTrue(moves.contains(42)); // sudoeste
        assertFalse(moves.contains(33)); // onde ele ja ta
    }

    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE); // mesma cor

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertFalse(moves.contains(22)); // n pode capturar peça propria
    }

    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK); // inimigo

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertTrue(moves.contains(22)); // pode capturar inimigo
    }
}
