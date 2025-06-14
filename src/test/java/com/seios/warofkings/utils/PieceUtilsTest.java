package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.Knight;
import com.seios.warofkings.pieces.types.Pawn;
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
}
