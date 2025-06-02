package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.*;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PieceUtilsTest {

    @Test
    void testIsSameType_trueWhenSameColor() {
        ChessPiece p1 = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(20, Type.KNIGHT_WHITE);
        assertTrue(PieceUtils.isSameType(p1, p2));
    }

    @Test
    void testIsSameType_falseWhenDifferentColor() {
        ChessPiece p1 = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(p1, p2));
    }

    @Test
    void testIsSameType_handlesNulls() {
        ChessPiece p1 = null;
        ChessPiece p2 = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(p1, p2));
    }
}
