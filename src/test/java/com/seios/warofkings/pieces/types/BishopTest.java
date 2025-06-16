package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link Bishop}, responsável por validar sua criação
 * e a geração correta de movimentos válidos conforme as regras do xadrez.
 *
 * <p>Os testes abordam os seguintes cenários:</p>
 * <ul>
 *   <li>Criação correta de um bispo com tipo e posição;</li>
 *   <li>Movimentos em diagonais livres;</li>
 *   <li>Bloqueio por peças da mesma cor;</li>
 *   <li>Possibilidade de captura de peças adversárias.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class BishopTest {

    /**
     * Testa o método {@link Bishop#createBishop(int, Type)} para garantir
     * a correta criação de uma instância de bispo com os atributos esperados.
     */
    @Test
    void testCreateBishopValid() {
        Bishop bishop = Bishop.createBishop(22, Type.BISHOP_WHITE);
        assertEquals(22, bishop.getPosition());
        assertEquals(Type.BISHOP_WHITE, bishop.getType());
    }

    /**
     * Testa o método {@link Bishop#getPossibleMoves(ChessPiece[][])} em um tabuleiro vazio,
     * garantindo que o bispo consiga se mover em todas as quatro diagonais enquanto estiver livre.
     */
    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertTrue(moves.contains(22)); // noroeste
        assertTrue(moves.contains(44)); // sudeste
        assertTrue(moves.contains(24)); // nordeste
        assertTrue(moves.contains(42)); // sudoeste
        assertFalse(moves.contains(33)); // onde ele já está
    }

    /**
     * Testa se o bispo não considera como movimento válido uma casa ocupada por peça da mesma cor.
     */
    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE); // mesma cor

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertFalse(moves.contains(22)); // não pode capturar peça própria
    }

    /**
     * Testa se o bispo pode capturar uma peça adversária ao encontrá-la em uma de suas diagonais.
     */
    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Bishop bishop = Bishop.createBishop(33, Type.BISHOP_WHITE);
        board[3][3] = bishop;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK); // inimigo

        List<Integer> moves = bishop.getPossibleMoves(board);
        assertTrue(moves.contains(22)); // pode capturar inimigo
    }
}
