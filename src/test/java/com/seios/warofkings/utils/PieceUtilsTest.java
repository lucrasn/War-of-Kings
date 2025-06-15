package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceUtilsTest {

    @Test
    void testIsSamePiece_trueForSameInstance() {
        ChessPiece p = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertTrue(PieceUtils.isSamePiece(p, p));
    }

    @Test
    void testIsSamePiece_falseForDifferentInstances() {
        ChessPiece p1 = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece p2 = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertFalse(PieceUtils.isSamePiece(p1, p2));
    }

    @Test
    void testIsType_withValidPiece() {
        ChessPiece pawn = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertTrue(PieceUtils.isType(pawn, Type.PAWN_WHITE));
        assertFalse(PieceUtils.isType(pawn, Type.KNIGHT_WHITE));
    }

    @Test
    void testIsType_withNullPiece() {
        assertFalse(PieceUtils.isType(null, Type.PAWN_WHITE));
    }

    @Test
    void testIsSameType_trueWhenSameColor() {
        ChessPiece whitePawn  = Pawn.createPawn(11, Type.PAWN_WHITE);
        ChessPiece whiteKnight = Knight.createKnight(20, Type.KNIGHT_WHITE);
        assertTrue(PieceUtils.isSameType(whitePawn, whiteKnight));
    }

    @Test
    void testIsSameType_falseWhenDifferentColor() {
        ChessPiece white = Pawn.createPawn(11, Type.PAWN_WHITE);
        ChessPiece black = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(white, black));
    }

    @Test
    void testIsSameType_handlesNulls() {
        ChessPiece p = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(null, p));
        assertFalse(PieceUtils.isSameType(p, null));
    }

    @Test
    void testIsSameBoardPosition_trueWhenSamePosition() {
        ChessPiece p1 = Pawn.createPawn(20, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(20, Type.KNIGHT_WHITE);
        assertTrue(PieceUtils.isSameBoardPosition(p1, p2));
    }

    @Test
    void testIsSameBoardPosition_falseWhenDifferentPosition() {
        ChessPiece p1 = Pawn.createPawn(20, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(21, Type.KNIGHT_WHITE);
        assertFalse(PieceUtils.isSameBoardPosition(p1, p2));
    }

    @Test
    void testIsSameBoardPosition_handlesNulls() {
        ChessPiece p = Pawn.createPawn(20, Type.PAWN_WHITE);
        assertFalse(PieceUtils.isSameBoardPosition(null, p));
        assertFalse(PieceUtils.isSameBoardPosition(p, null));
    }

    @Test
    void testIsPieceUnderAttack(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][4] = Queen.createQueen(34, Type.QUEEN_BLACK);
        board[3][3] = pawn;

        boolean result = PieceUtils.isPieceUnderAttack(pawn, board);
        assertTrue(result);
    }

    @Test
    void testIsPieceNotUnderAttack(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece pawn = Pawn.createPawn(42, Type.PAWN_WHITE);
        board[3][4] = Queen.createQueen(34, Type.QUEEN_BLACK);
        board[4][2] = pawn;

        boolean result = PieceUtils.isPieceUnderAttack(pawn, board);
        assertFalse(result);
    }

    @Test
    void testIsPathSafeFromKing(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece king = King.createKing(43, Type.KING_WHITE);
        ChessPiece queen = Queen.createQueen(13, Type.QUEEN_BLACK);
        board[1][3] = queen;
        board[4][3] = king;

        boolean result = PieceUtils.pathSafeForKing(44, king, board);
        assertTrue(result);
    }

    @Test
    void testIsPathUnsafeFromKing(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece king = King.createKing(43, Type.KING_WHITE);
        ChessPiece queen = Queen.createQueen(13, Type.QUEEN_BLACK);
        board[1][3] = queen;
        board[4][3] = king;

        boolean result = PieceUtils.pathSafeForKing(46, king, board);
        assertFalse(result);
    }


}
