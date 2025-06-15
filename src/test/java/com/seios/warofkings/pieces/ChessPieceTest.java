package com.seios.warofkings.pieces;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;

class ChessPieceTest {

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