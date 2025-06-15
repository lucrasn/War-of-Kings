package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RookTest {

    @Test
    void testCreateRookValid() {
        Rook queen = Rook.createRook(22, Type.ROOK_WHITE);
        assertEquals(22, queen.getPosition());
        assertEquals(Type.ROOK_WHITE, queen.getType());
    }

    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;

        List<Integer> moves = rook.getPossibleMoves(board);
        assertTrue(moves.contains(23)); //acima
        assertTrue(moves.contains(43)); //abaixo
        assertTrue(moves.contains(32)); //esquerda
        assertTrue(moves.contains(34)); //direita
        assertFalse(moves.contains(33)); // onde ele ja ta
    }

    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;
        board[3][2] = Pawn.createPawn(32,Type.PAWN_WHITE);

        List<Integer> moves = rook.getPossibleMoves(board);
        assertFalse(moves.contains(32));// n pode capturar peça propria
    }

    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;
        board[3][2] = Pawn.createPawn(32,Type.PAWN_BLACK);

        List<Integer> moves = rook.getPossibleMoves(board);
        assertTrue(moves.contains(32)); //pode capturar inimigo
    }
}