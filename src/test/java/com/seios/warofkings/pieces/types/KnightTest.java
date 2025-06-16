package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link Knight}, validando sua criação
 * e os movimentos característicos em formato de "L", conforme as regras do xadrez.
 *
 * <p>Os testes cobrem os seguintes aspectos:</p>
 * <ul>
 *   <li>Instanciação correta do cavalo com posição e tipo;</li>
 *   <li>Movimentos possíveis em um tabuleiro vazio;</li>
 *   <li>Impossibilidade de capturar peças da mesma cor;</li>
 *   <li>Capacidade de capturar peças adversárias.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class KnightTest {

    /**
     * Testa o método {@link Knight#createKnight(int, Type)} para garantir
     * a correta criação de um cavalo com os atributos esperados.
     */
    @Test
    void testCreateKnightValid() {
        Knight knight = Knight.createKnight(22, Type.KNIGHT_WHITE);
        assertEquals(22, knight.getPosition());
        assertEquals(Type.KNIGHT_WHITE, knight.getType());
    }

    /**
     * Testa os movimentos possíveis do cavalo em um tabuleiro vazio,
     * assegurando que todas as 8 direções em "L" sejam consideradas válidas.
     */
    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;

        List<Integer> moves = knight.getPossibleMoves(board);
        assertTrue(moves.contains(21));
        assertTrue(moves.contains(25));
        assertTrue(moves.contains(12));
        assertTrue(moves.contains(14));
        assertTrue(moves.contains(41));
        assertTrue(moves.contains(52));
        assertTrue(moves.contains(54));
        assertTrue(moves.contains(45));
        assertFalse(moves.contains(33)); // posição atual não é válida como movimento
    }

    /**
     * Testa se o cavalo evita casas ocupadas por peças da mesma cor,
     * garantindo que essas posições não sejam consideradas movimentos válidos.
     */
    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;
        board[2][1] = Pawn.createPawn(21, Type.PAWN_WHITE); // mesma cor

        List<Integer> moves = knight.getPossibleMoves(board);
        assertFalse(moves.contains(21));
    }

    /**
     * Testa se o cavalo pode capturar uma peça adversária posicionada
     * em uma das casas de movimento em "L".
     */
    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Knight knight = Knight.createKnight(33, Type.KNIGHT_WHITE);
        board[3][3] = knight;
        board[2][1] = Pawn.createPawn(21, Type.PAWN_BLACK); // inimigo

        List<Integer> moves = knight.getPossibleMoves(board);
        assertTrue(moves.contains(21));
    }
}
