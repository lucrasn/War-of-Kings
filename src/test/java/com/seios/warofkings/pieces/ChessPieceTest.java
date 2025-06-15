package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChessPieceTest {

    @Test
    void testIsOpponent() {
        ChessPiece white = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece black = Pawn.createPawn(20, Type.PAWN_BLACK);
        assertTrue(white.isOpponent(black));
        assertTrue(black.isOpponent(white));
        assertFalse(white.isOpponent(white));
        assertFalse(white.isOpponent(null));
    }

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

    @Test
    void testKingCheckTrue() {
        Board board = new Board();
        ChessPiece king = King.createKing(44, Type.KING_WHITE);
        ChessPiece rook = Rook.createRook(32, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][4] = king;
        pieces[5][0] = rook;

        assertTrue(rook.kingCheck(40, pieces));
    }

    @Test
    void testKingCheckFalse() {
        Board board = new Board();
        ChessPiece king = King.createKing(44, Type.KING_WHITE);
        ChessPiece rook = Rook.createRook(30, Type.ROOK_BLACK);

        ChessPiece[][] pieces = board.getPieces();
        pieces[4][4] = king;
        pieces[3][0] = rook;

        assertFalse(rook.kingCheck(30, pieces));
    }
}