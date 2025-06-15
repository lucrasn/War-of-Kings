package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueenTest {

    @Test
    void testCreateQueenValid() {
        Queen queen = Queen.createQueen(22, Type.QUEEN_WHITE);
        assertEquals(22, queen.getPosition());
        assertEquals(Type.QUEEN_WHITE, queen.getType());
    }

    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;

        List<Integer> moves = queen.getPossibleMoves(board);
        assertTrue(moves.contains(23)); //acima
        assertTrue(moves.contains(43)); //abaixo
        assertTrue(moves.contains(32)); //esquerda
        assertTrue(moves.contains(34)); //direita
        assertTrue(moves.contains(22)); // noroeste
        assertTrue(moves.contains(44)); // sudeste
        assertTrue(moves.contains(24)); // nordeste
        assertTrue(moves.contains(42)); // sudoeste
        assertFalse(moves.contains(33)); // onde ela ja ta
    }

    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE); // mesma cor
        board[3][2] = Pawn.createPawn(32,Type.PAWN_WHITE);

        List<Integer> moves = queen.getPossibleMoves(board);
        assertFalse(moves.contains(32));
        assertFalse(moves.contains(22)); // n pode capturar peça propria
    }

    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK); // inimigo
        board[3][2] = Pawn.createPawn(32,Type.PAWN_BLACK);

        List<Integer> moves = queen.getPossibleMoves(board);
        assertTrue(moves.contains(32));
        assertTrue(moves.contains(22)); // pode capturar inimigo
    }
}