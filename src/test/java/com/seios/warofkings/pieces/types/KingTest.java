package com.seios.warofkings.pieces.types;

import com.seios.warofkings.board.Board;
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
    void testPossibleMovesWithCastling() {
        ChessPiece[][] board = new ChessPiece[8][8];

        King king = King.createKing(74, Type.KING_WHITE);
        Rook rook = Rook.createRook(70, Type.ROOK_WHITE);
        board[7][4] = king;
        board[7][0] = rook;

        List<Integer> moves = king.getPossibleMoves(board);

        // roque de 74 p 72
        assertTrue(moves.contains(72));
    }

    @Test
    void testPossibleMovesNoCastlingKingMoved() {
        ChessPiece[][] board = new ChessPiece[8][8];

        King king = new King(74,Type.KING_WHITE, 1);
        Rook rook = Rook.createRook(77, Type.ROOK_WHITE);
        board[7][4] = king;
        board[7][7] = rook;

        List<Integer> moves = king.getPossibleMoves(board);

        assertFalse(moves.contains(76));
    }

    @Test
    void testMoveToNormalMove() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();

        King king = King.createKing(74, Type.KING_WHITE);
        pieces[7][4] = king;
        board.setPieces(pieces);

        List<Integer> possibleMoves = List.of(75); // rei p direita, sem roque

        boolean result = king.moveTo(75, possibleMoves, board);

        assertTrue(result);
        assertEquals(75, king.getPosition());
    }

    @Test
    void testMoveToValidCastling() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();

        ChessPiece[][] empty = new ChessPiece[8][8];
        board.setPieces(empty);

        King king = King.createKing(34, Type.KING_WHITE);
        Rook rook = Rook.createRook(30, Type.ROOK_WHITE);

        pieces[3][4] = king;
        pieces[3][0] = rook;
        board.setPieces(pieces);

        king.setRoqueLeft(true);

        List<Integer> moves = king.getPossibleMoves(pieces);

        boolean result = king.moveTo(32, moves, board);

        assertTrue(result);
        assertEquals(32, king.getPosition()); //checa se o rei ta na posicao certa

        ChessPiece rookAfterMove = board.getPieces()[3][3];
        assertNotNull(rookAfterMove); //ve se a torre se moveu tb
        assertEquals(rook, rookAfterMove); //ve se foi a torre certa
    }

    @Test
    void testMoveToInvalidCastling() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();

        King king = King.createKing(34, Type.KING_WHITE);
        Rook rook = Rook.createRook(37, Type.ROOK_WHITE);
        Pawn pawn = Pawn.createPawn(35, Type.PAWN_WHITE);

        pieces[3][4] = king;
        pieces[3][7] = rook;
        pieces[3][5] = pawn;
        board.setPieces(pieces);

        king.setRoqueRight(false);

        List<Integer> moves = king.getPossibleMoves(pieces);

        boolean result = king.moveTo(36, moves, board);

        assertFalse(result);
        assertEquals(34,king.getPosition());
    }



}