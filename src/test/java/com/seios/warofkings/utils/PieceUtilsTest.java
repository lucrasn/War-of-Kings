package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para os métodos utilitários definidos em {@link PieceUtils},
 * que oferecem validações sobre identidade, tipo, posição e segurança de peças no tabuleiro de xadrez.
 *
 * <p>Os testes abrangem operações como:</p>
 * <ul>
 *   <li>Verificação de identidade de instâncias e tipos de peça;</li>
 *   <li>Comparação de posição entre peças;</li>
 *   <li>Detecção de ataques sobre uma peça;</li>
 *   <li>Validação de caminhos seguros para o Rei.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class PieceUtilsTest {
    /**
     * Verifica se {@link PieceUtils#isSamePiece(ChessPiece, ChessPiece)} retorna {@code true}
     * para a mesma instância de peça.
     */
    @Test
    void testIsSamePiece_trueForSameInstance() {
        ChessPiece p = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertTrue(PieceUtils.isSamePiece(p, p));
    }

    /**
     * Verifica se {@link PieceUtils#isSamePiece(ChessPiece, ChessPiece)} retorna {@code false}
     * para instâncias diferentes, mesmo com mesmos atributos.
     */
    @Test
    void testIsSamePiece_falseForDifferentInstances() {
        ChessPiece p1 = Pawn.createPawn(10, Type.PAWN_WHITE);
        ChessPiece p2 = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertFalse(PieceUtils.isSamePiece(p1, p2));
    }

    /**
     * Testa {@link PieceUtils#isType(ChessPiece, Type)} com uma peça válida.
     */
    @Test
    void testIsType_withValidPiece() {
        ChessPiece pawn = Pawn.createPawn(10, Type.PAWN_WHITE);
        assertTrue(PieceUtils.isType(pawn, Type.PAWN_WHITE));
        assertFalse(PieceUtils.isType(pawn, Type.KNIGHT_WHITE));
    }

    /**
     * Testa {@link PieceUtils#isType(ChessPiece, Type)} quando a peça é nula.
     */
    @Test
    void testIsType_withNullPiece() {
        assertFalse(PieceUtils.isType(null, Type.PAWN_WHITE));
    }

    /**
     * Testa {@link PieceUtils#isSameType(ChessPiece, ChessPiece)} com peças da mesma cor.
     */
    @Test
    void testIsSameType_trueWhenSameColor() {
        ChessPiece whitePawn  = Pawn.createPawn(11, Type.PAWN_WHITE);
        ChessPiece whiteKnight = Knight.createKnight(20, Type.KNIGHT_WHITE);
        assertTrue(PieceUtils.isSameType(whitePawn, whiteKnight));
    }

    /**
     * Testa {@link PieceUtils#isSameType(ChessPiece, ChessPiece)} com peças de cores diferentes.
     */
    @Test
    void testIsSameType_falseWhenDifferentColor() {
        ChessPiece white = Pawn.createPawn(11, Type.PAWN_WHITE);
        ChessPiece black = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(white, black));
    }

    /**
     * Testa {@link PieceUtils#isSameType(ChessPiece, ChessPiece)} quando uma ou ambas as peças são nulas.
     */
    @Test
    void testIsSameType_handlesNulls() {
        ChessPiece p = Knight.createKnight(20, Type.KNIGHT_BLACK);
        assertFalse(PieceUtils.isSameType(null, p));
        assertFalse(PieceUtils.isSameType(p, null));
    }

    /**
     * Testa {@link PieceUtils#isSameBoardPosition(ChessPiece, ChessPiece)} com peças na mesma posição.
     */
    @Test
    void testIsSameBoardPosition_trueWhenSamePosition() {
        ChessPiece p1 = Pawn.createPawn(20, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(20, Type.KNIGHT_WHITE);
        assertTrue(PieceUtils.isSameBoardPosition(p1, p2));
    }

    /**
     * Testa {@link PieceUtils#isSameBoardPosition(ChessPiece, ChessPiece)} com peças em posições diferentes.
     */
    @Test
    void testIsSameBoardPosition_falseWhenDifferentPosition() {
        ChessPiece p1 = Pawn.createPawn(20, Type.PAWN_WHITE);
        ChessPiece p2 = Knight.createKnight(21, Type.KNIGHT_WHITE);
        assertFalse(PieceUtils.isSameBoardPosition(p1, p2));
    }

    /**
     * Testa {@link PieceUtils#isSameBoardPosition(ChessPiece, ChessPiece)} quando uma das peças é nula.
     */
    @Test
    void testIsSameBoardPosition_handlesNulls() {
        ChessPiece p = Pawn.createPawn(20, Type.PAWN_WHITE);
        assertFalse(PieceUtils.isSameBoardPosition(null, p));
        assertFalse(PieceUtils.isSameBoardPosition(p, null));
    }

    /**
     * Testa {@link PieceUtils#isPieceUnderAttack(ChessPiece, ChessPiece[][])}
     * para detectar se uma peça está sob ataque direto.
     */
    @Test
    void testIsPieceUnderAttack(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][4] = Queen.createQueen(34, Type.QUEEN_BLACK);
        board[3][3] = pawn;

        boolean result = PieceUtils.isPieceUnderAttack(pawn, board);
        assertTrue(result);
    }

    /**
     * Testa {@link PieceUtils#isPieceUnderAttack(ChessPiece, ChessPiece[][])} quando a peça está segura.
     */
    @Test
    void testIsPieceNotUnderAttack(){
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece pawn = Pawn.createPawn(42, Type.PAWN_WHITE);
        board[3][4] = Queen.createQueen(34, Type.QUEEN_BLACK);
        board[4][2] = pawn;

        boolean result = PieceUtils.isPieceUnderAttack(pawn, board);
        assertFalse(result);
    }

    /**
     * Testa {@link PieceUtils#pathSafeForKing(int, ChessPiece, ChessPiece[][])}
     * para uma posição segura no caminho do rei.
     */
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

    /**
     * Testa {@link PieceUtils#pathSafeForKing(int, ChessPiece, ChessPiece[][])}
     * para uma posição insegura no caminho do rei.
     */
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
