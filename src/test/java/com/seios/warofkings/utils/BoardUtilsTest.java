package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para os métodos utilitários definidos em {@link BoardUtils},
 * responsáveis por operações fundamentais sobre o estado do tabuleiro de xadrez.
 *
 * <p>Os testes validam funcionalidades como:</p>
 * <ul>
 *     <li>Busca por peças de determinado tipo;</li>
 *     <li>Verificação de ocupação de posições;</li>
 *     <li>Recuperação de peças por posição;</li>
 *     <li>Limites válidos do tabuleiro;</li>
 *     <li>Cópia da matriz de peças;</li>
 *     <li>Verificação de caminhos livres em linha reta;</li>
 *     <li>Identificação de peças aliadas.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class BoardUtilsTest {
    /**
     * Testa {@link BoardUtils#findPieces(ChessPiece[][], Type)} com peças compatíveis no tabuleiro.
     */
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

    /**
     * Testa {@link BoardUtils#findPieces(ChessPiece[][], Type)} sem peças do tipo buscado.
     */
    @Test
    void testFindPieces_withNoMatch() { // busca por peao mas n tem
        ChessPiece[][] pieces = new ChessPiece[8][8];
        pieces[0][0] = Knight.createKnight(0, Type.KNIGHT_BLACK);

        List<ChessPiece> result = BoardUtils.findPieces(pieces, Type.PAWN_WHITE);
        assertTrue(result.isEmpty());
    }

    /**
     * Verifica {@link BoardUtils#isPositionOccupied(ChessPiece[][], int)} em posição ocupada.
     */
    @Test
    void testIsPositionOccupied_trueWhenOccupied() { // lugar ocupado
        ChessPiece[][] pieces = new ChessPiece[8][8];
        pieces[2][3] = Pawn.createPawn(23, Type.PAWN_WHITE);
        assertTrue(BoardUtils.isPositionOccupied(pieces, 23));
    }

    /**
     * Verifica {@link BoardUtils#isPositionOccupied(ChessPiece[][], int)} em posição vazia.
     */
    @Test
    void testIsPositionOccupied_falseWhenEmpty() { // vazio
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertFalse(BoardUtils.isPositionOccupied(pieces, 23));
    }

    /**
     * Verifica {@link BoardUtils#isPositionOccupied(ChessPiece[][], int)} em posição inválida.
     */
    @Test
    void testIsPositionOccupied_falseWhenOutOfBounds() { // fora do tabuleiro
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertFalse(BoardUtils.isPositionOccupied(pieces, 88));
    }

    /**
     * Testa {@link BoardUtils#getPieceAt(ChessPiece[][], int)} com peça existente na posição.
     */
    @Test
    void testGetPieceAt_validPositionWithPiece() { // posicao com peca
        ChessPiece[][] pieces = new ChessPiece[8][8];
        ChessPiece knight = Knight.createKnight(31, Type.KNIGHT_WHITE);
        pieces[3][1] = knight;
        ChessPiece result = BoardUtils.getPieceAt(pieces, 31);
        assertEquals(knight, result);
    }

    /**
     * Testa {@link BoardUtils#getPieceAt(ChessPiece[][], int)} com posição vazia.
     */
    @Test
    void testGetPieceAt_validPositionWithoutPiece() { //posicao sem peca
        ChessPiece[][] pieces = new ChessPiece[8][8];
        assertNull(BoardUtils.getPieceAt(pieces, 44));
    }

    /**
     * Testa {@link BoardUtils#isWithinBounds(int)} com posições válidas no tabuleiro.
     */
    @Test
    void testIsWithinBounds_true() { // no tabuleiro
        assertTrue(BoardUtils.isWithinBounds(77));
        assertTrue(BoardUtils.isWithinBounds(0));
    }

    /**
     * Testa {@link BoardUtils#isWithinBounds(int)} com posições inválidas.
     */
    @Test
    void testIsWithinBounds_false() { // fora do tabuleiro
        assertFalse(BoardUtils.isWithinBounds(-10));
        assertFalse(BoardUtils.isWithinBounds(88));
    }

    /**
     * Testa {@link BoardUtils#copyBoard(ChessPiece[][])} para garantir que a cópia
     * mantém as referências às mesmas peças (shallow copy).
     */
    @Test
    void testCopyBoardCreatesReferenceCopy() {
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece rook = Rook.createRook(0, Type.ROOK_WHITE);
        board[0][0] = rook;

        ChessPiece[][] copiedBoard = BoardUtils.copyBoard(board);

        assertSame(board[0][0], copiedBoard[0][0]);
        assertNull(copiedBoard[1][1]);
    }

    /**
     * Testa {@link BoardUtils#isPathClearHorizontally(int, int, ChessPiece[][])} quando o caminho está livre.
     */
    @Test
    void testIsPathClearHorizontally() {
        ChessPiece[][] board = new ChessPiece[8][8];
        int from = 40;
        int to = 47;

        boolean result = BoardUtils.isPathClearHorizontally(from, to, board);
        assertTrue(result);
    }

    /**
     * Testa {@link BoardUtils#isPathClearHorizontally(int, int, ChessPiece[][])} quando há obstrução.
     */
    @Test
    void testIsPathNotClearHorizontally() {
        ChessPiece[][] board = new ChessPiece[8][8];
        board[3][3] = Pawn.createPawn(33, Type.PAWN_WHITE);

        int from = 30;
        int to = 37;

        boolean result = BoardUtils.isPathClearHorizontally(from, to, board);
        assertFalse(result);
    }

    /**
     * Testa {@link BoardUtils#getAliadas(ChessPiece, ChessPiece[][])} para garantir
     * que apenas peças aliadas (mesmo tipo) sejam retornadas.
     */
    @Test
    void testGetAliadas() {
        ChessPiece[][] board = new ChessPiece[8][8];
        ChessPiece king = Knight.createKnight(44, Type.KNIGHT_WHITE);
        board[4][4] = king;

        ChessPiece pawn = Pawn.createPawn(45, Type.PAWN_WHITE);
        ChessPiece bishop = Bishop.createBishop(50, Type.BISHOP_WHITE);
        board[4][5] = pawn;
        board[5][0] = bishop;

        ChessPiece blackPawn = Pawn.createPawn(46, Type.PAWN_BLACK);//botando preta
        board[4][6] = blackPawn;

        List<ChessPiece> aliadas = BoardUtils.getAliadas(king, board);

        assertEquals(2, aliadas.size());
        assertTrue(aliadas.contains(pawn));
        assertTrue(aliadas.contains(bishop));
        assertFalse(aliadas.contains(blackPawn));
    }


}
