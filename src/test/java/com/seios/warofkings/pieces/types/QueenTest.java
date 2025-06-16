package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link Queen}, validando sua criação
 * e os movimentos permitidos conforme as regras clássicas do xadrez.
 *
 * <p>A rainha combina os movimentos da torre e do bispo, podendo se mover em
 * linhas retas (horizontal/vertical) e diagonais até encontrar um bloqueio.</p>
 *
 * <p>Os testes incluem:</p>
 * <ul>
 *   <li>Criação correta da peça;</li>
 *   <li>Movimentos em todas as direções num tabuleiro vazio;</li>
 *   <li>Bloqueio por peças da mesma cor;</li>
 *   <li>Captura de peças adversárias.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class QueenTest {

    /**
     * Testa o método {@link Queen#createQueen(int, Type)} para garantir
     * a correta criação da instância de rainha com seus atributos.
     */
    @Test
    void testCreateQueenValid() {
        Queen queen = Queen.createQueen(22, Type.QUEEN_WHITE);
        assertEquals(22, queen.getPosition());
        assertEquals(Type.QUEEN_WHITE, queen.getType());
    }

    /**
     * Testa os movimentos da rainha em um tabuleiro vazio.
     * Verifica se todas as direções (vertical, horizontal e diagonal) estão disponíveis.
     */
    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;

        List<Integer> moves = queen.getPossibleMoves(board);
        assertTrue(moves.contains(23)); // cima
        assertTrue(moves.contains(43)); // baixo
        assertTrue(moves.contains(32)); // esquerda
        assertTrue(moves.contains(34)); // direita
        assertTrue(moves.contains(22)); // noroeste
        assertTrue(moves.contains(44)); // sudeste
        assertTrue(moves.contains(24)); // nordeste
        assertTrue(moves.contains(42)); // sudoeste
        assertFalse(moves.contains(33)); // onde já está
    }

    /**
     * Testa se a rainha respeita o bloqueio por peças da mesma cor,
     * ou seja, não deve considerar esses alvos como válidos para movimento ou captura.
     */
    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE); // diagonal
        board[3][2] = Pawn.createPawn(32, Type.PAWN_WHITE); // lateral

        List<Integer> moves = queen.getPossibleMoves(board);
        assertFalse(moves.contains(32));
        assertFalse(moves.contains(22));
    }

    /**
     * Testa se a rainha pode capturar peças adversárias posicionadas
     * em uma diagonal e em uma linha lateral.
     */
    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Queen queen = Queen.createQueen(33, Type.QUEEN_WHITE);
        board[3][3] = queen;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK); // diagonal
        board[3][2] = Pawn.createPawn(32, Type.PAWN_BLACK); // lateral

        List<Integer> moves = queen.getPossibleMoves(board);
        assertTrue(moves.contains(32));
        assertTrue(moves.contains(22));
    }
}
