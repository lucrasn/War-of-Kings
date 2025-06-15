package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KnightTest {

    @Test
    void testCreateKnightValid() {
        Knight knight = Knight.createKnight(22, Type.KNIGHT_WHITE);
        assertEquals(22, knight.getPosition());
        assertEquals(Type.KNIGHT_WHITE, knight.getType());
    }

    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;

        List<Integer> moves = knight.getPossibleMoves(board);
        assertTrue(moves.contains(21)); //acima
        assertTrue(moves.contains(25)); //abaixo
        assertTrue(moves.contains(12)); //esquerda
        assertTrue(moves.contains(14)); //direita
        assertTrue(moves.contains(41)); // noroeste
        assertTrue(moves.contains(52)); // sudeste
        assertTrue(moves.contains(54)); // nordeste
        assertTrue(moves.contains(45)); // sudoeste
        assertFalse(moves.contains(33)); // onde ela ja ta
    }

    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;
        board[2][1] = Pawn.createPawn(21, Type.PAWN_WHITE); // mesma cor

        List<Integer> moves = knight.getPossibleMoves(board);
        assertFalse(moves.contains(21)); // n pode capturar peça propria
    }

    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;
        board[2][1] = Pawn.createPawn(21, Type.PAWN_BLACK); // inimigo


        List<Integer> moves = knight.getPossibleMoves(board);
        assertTrue(moves.contains(21)); // pode capturar inimigo
    }
}