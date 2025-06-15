package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KingTest {

    @Test
    void testIsWhiteReturnsTrueForWhitePiece() {
        ChessPiece whiteKing = King.createKing(74, Type.KING_WHITE); // Valor <= 6
        assertTrue(whiteKing.isWhite());
    }

    @Test
    void testIsWhiteReturnsFalseForBlackPiece() {
        ChessPiece blackKing = King.createKing(04, Type.KING_BLACK); // Valor >= 7
        assertFalse(blackKing.isWhite());
    }

    @Test
    void testCreateKingValid() {
        King king = King.createKing(22, Type.KING_WHITE);
        assertEquals(22, king.getPosition());
        assertEquals(Type.KING_WHITE, king.getType());
    }

    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;

        List<Integer> moves = king.getPossibleMoves(board);
        assertTrue(moves.contains(23)); //acima
        assertTrue(moves.contains(43)); //abaixo
        assertTrue(moves.contains(32)); //esquerda
        assertTrue(moves.contains(34)); //direita
        assertTrue(moves.contains(22)); // noroeste
        assertTrue(moves.contains(44)); // sudeste
        assertTrue(moves.contains(24)); // nordeste
        assertTrue(moves.contains(42)); // sudoeste
        assertFalse(moves.contains(33)); // onde ele ja ta
    }

    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE); // mesma cor
        board[3][2] = Pawn.createPawn(32,Type.PAWN_WHITE);

        List<Integer> moves = king.getPossibleMoves(board);
        assertFalse(moves.contains(32));
        assertFalse(moves.contains(22)); // n pode capturar peça propria
    }

    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK); // inimigo


        List<Integer> moves = king.getPossibleMoves(board);
        assertTrue(moves.contains(32));
        assertTrue(moves.contains(22)); // pode capturar inimigo
    }

    @Test
    void moveTo() {
    }
}