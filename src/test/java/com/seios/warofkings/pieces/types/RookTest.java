package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link Rook}, validando seus movimentos
 * em linhas retas (horizontal e vertical) e comportamento ao interagir com peças aliadas e inimigas.
 *
 * <p>Os testes abrangem:</p>
 * <ul>
 *   <li>Criação correta da torre com tipo e posição inicial;</li>
 *   <li>Movimentos válidos em um tabuleiro vazio;</li>
 *   <li>Bloqueio por peças da mesma cor;</li>
 *   <li>Capacidade de capturar peças adversárias.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class RookTest {

    /**
     * Testa o método {@link Rook#createRook(int, Type)} para verificar
     * a criação correta da torre com atributos esperados.
     */
    @Test
    void testCreateRookValid() {
        Rook queen = Rook.createRook(22, Type.ROOK_WHITE);
        assertEquals(22, queen.getPosition());
        assertEquals(Type.ROOK_WHITE, queen.getType());
    }

    /**
     * Testa os movimentos da torre em um tabuleiro vazio,
     * garantindo que ela possa se mover livremente em todas as direções ortogonais.
     */
    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;

        List<Integer> moves = rook.getPossibleMoves(board);
        assertTrue(moves.contains(23)); // cima
        assertTrue(moves.contains(43)); // baixo
        assertTrue(moves.contains(32)); // esquerda
        assertTrue(moves.contains(34)); // direita
        assertFalse(moves.contains(33)); // não pode incluir sua própria posição
    }

    /**
     * Testa se a torre respeita peças da mesma cor,
     * não permitindo movimentos que capturam ou ultrapassam aliadas.
     */
    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;
        board[3][2] = Pawn.createPawn(32, Type.PAWN_WHITE);

        List<Integer> moves = rook.getPossibleMoves(board);
        assertFalse(moves.contains(32));
    }

    /**
     * Testa se a torre pode capturar uma peça adversária
     * posicionada na linha ou coluna acessível.
     */
    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Rook rook = Rook.createRook(33, Type.ROOK_WHITE);
        board[3][3] = rook;
        board[3][2] = Pawn.createPawn(32, Type.PAWN_BLACK);

        List<Integer> moves = rook.getPossibleMoves(board);
        assertTrue(moves.contains(32));
    }
}
