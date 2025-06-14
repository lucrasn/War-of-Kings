package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardUtilsTest {

    @Test
    void testFindPieces_withMatchingType() { // busca por peoes brancos, cavalo n eh adicionado
        ChessPiece[][] pieces = new ChessPiece[8][8];
        pieces[0][0] = Pawn.createPawn(0, Type.PAWN_WHITE);
        pieces[0][1] = Pawn.createPawn(1, Type.PAWN_WHITE);
        pieces[1][0] = Knight.createKnight(10, Type.KNIGHT_BLACK);

        List<ChessPiece> result = BoardUtils.findPieces(pieces, Type.PAWN_WHITE);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(p -> p.getType() == Type.PAWN_WHITE));
    }

    @Test
    void testFindPieces_withNoMatch() { // busca por peao mas n tem
        ChessPiece[][] pieces = new ChessPiece[8][8];
        pieces[0][0] = Knight.createKnight(0, Type.KNIGHT_BLACK);

        List<ChessPiece> result = BoardUtils.findPieces(pieces, Type.PAWN_WHITE);
        assertTrue(result.isEmpty());
    }

    @Test
    void testIsPositionOccupied_trueWhenOccupied() { // lugar ocupado
        ChessPiece[][] pieces = new ChessPiece[8][8];
        pieces[2][3] = Pawn.createPawn(23, Type.PAWN_WHITE);
        assertTrue(BoardUtils.isPositionOccupied(pieces, 23));
    }

    @Test
    void testIsPositionOccupied_falseWhenEmpty() { // vazio
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertFalse(BoardUtils.isPositionOccupied(pieces, 23));
    }

    @Test
    void testIsPositionOccupied_falseWhenOutOfBounds() { // fora do tabuleiro
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertFalse(BoardUtils.isPositionOccupied(pieces, 88));
    }

    @Test
    void testGetPieceAt_validPositionWithPiece() { // posicao com peca
        ChessPiece[][] pieces = new ChessPiece[8][8];
        ChessPiece knight = Knight.createKnight(31, Type.KNIGHT_WHITE);
        pieces[3][1] = knight;
        ChessPiece result = BoardUtils.getPieceAt(pieces, 31);
        assertEquals(knight, result);
    }

    @Test
    void testGetPieceAt_validPositionWithoutPiece() { //posicao sem peca
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertNull(BoardUtils.getPieceAt(pieces, 44));
    }

    @Test
    void testIsWithinBounds_true() { // no tabuleiro
        assertTrue(BoardUtils.isWithinBounds(77));
        assertTrue(BoardUtils.isWithinBounds(0));
    }

    @Test
    void testIsWithinBounds_false() { // fora do tabuleiro
        assertFalse(BoardUtils.isWithinBounds(-10));
        assertFalse(BoardUtils.isWithinBounds(88));
    }
}
