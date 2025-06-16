package com.seios.warofkings.pieces.types;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link Pawn}, validando seu comportamento padrão,
 * movimentos condicionais e regras especiais como promoção.
 *
 * <p>Os testes abrangem os seguintes cenários:</p>
 * <ul>
 *   <li>Criação correta do peão com tipo e posição;</li>
 *   <li>Movimentação em tabuleiro vazio para brancos e pretos;</li>
 *   <li>Captura de peças adversárias em diagonais;</li>
 *   <li>Bloqueio por peças aliadas;</li>
 *   <li>Promoção de peões ao atingirem a linha final do tabuleiro;</li>
 *   <li>Exceções em tentativas de promoção com tipos inválidos.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class PawnTest {

    /**
     * Testa o método {@link Pawn#createPawn(int, Type)} para garantir
     * a correta criação de um peão com seus atributos esperados.
     */
    @Test
    void testCreatePawnValid() {
        Pawn pawn = Pawn.createPawn(22, Type.PAWN_WHITE);
        assertEquals(22, pawn.getPosition());
        assertEquals(Type.PAWN_WHITE, pawn.getType());
    }

    /**
     * Testa os movimentos possíveis de um peão branco em um tabuleiro vazio.
     * Deve permitir avanço de 1 ou 2 casas para frente (posição inicial).
     */
    @Test
    void testPossibleMovesWhiteEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(23));
        assertTrue(moves.contains(13));
        assertFalse(moves.contains(33));
    }

    /**
     * Testa os movimentos possíveis de um peão preto em um tabuleiro vazio.
     * Deve permitir avanço de 1 ou 2 casas para frente (posição inicial).
     */
    @Test
    void testPossibleMovesBlackEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(43));
        assertTrue(moves.contains(53));
        assertFalse(moves.contains(33));
    }

    /**
     * Verifica se o peão branco não pode capturar uma peça da mesma cor em diagonal.
     */
    @Test
    void testPossibleWhiteInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertFalse(moves.contains(22));
    }

    /**
     * Verifica se o peão preto não pode capturar uma peça da mesma cor em diagonal.
     */
    @Test
    void testPossibleBlackInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;
        board[4][4] = Pawn.createPawn(44, Type.PAWN_BLACK);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertFalse(moves.contains(44));
    }

    /**
     * Verifica se o peão branco pode capturar uma peça adversária posicionada em diagonal.
     */
    @Test
    void testPossibleWhiteMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_WHITE);
        board[3][3] = pawn;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(22));
    }

    /**
     * Verifica se o peão preto pode capturar uma peça adversária posicionada em diagonal.
     */
    @Test
    void testPossibleBlackMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        Pawn pawn = Pawn.createPawn(33, Type.PAWN_BLACK);
        board[3][3] = pawn;
        board[4][4] = Pawn.createPawn(44, Type.PAWN_WHITE);

        List<Integer> moves = pawn.getPossibleMoves(board);
        assertTrue(moves.contains(44));
    }

    /**
     * Testa se um peão branco é promovido corretamente para rainha ao alcançar a primeira linha.
     */
    @Test
    void testPawnPromotionToQueen() {
        Pawn pawn = Pawn.createPawn(4, Type.PAWN_WHITE);
        ChessPiece promoted = pawn.promoteIfEligible(Type.QUEEN_WHITE);
        assertTrue(promoted instanceof Queen);
        assertEquals(Type.QUEEN_WHITE, promoted.getType());
    }

    /**
     * Testa se um peão preto é promovido corretamente para cavalo ao alcançar a última linha.
     */
    @Test
    void testBlackPawnPromotesToKnight() {
        Pawn pawn = Pawn.createPawn(72, Type.PAWN_BLACK);
        ChessPiece promoted = pawn.promoteIfEligible(Type.KNIGHT_BLACK);

        assertTrue(promoted instanceof Knight);
        assertEquals(Type.KNIGHT_BLACK, promoted.getType());
    }

    /**
     * Verifica que um peão branco não é promovido se ainda não estiver na linha final do tabuleiro.
     */
    @Test
    void testWhitePawnNotPromotedIfNotOnLastRow() {
        Pawn pawn = Pawn.createPawn(64, Type.PAWN_WHITE);
        ChessPiece result = pawn.promoteIfEligible(Type.QUEEN_WHITE);

        assertNotEquals(Type.QUEEN_WHITE, result.getType());
    }

    /**
     * Testa se o método {@link Pawn#promoteIfEligible(Type)} lança uma exceção
     * ao tentar promover o peão para um tipo inválido (como rei).
     */
    @Test
    void testInvalidPromotionThrowsException() {
        Pawn pawn = Pawn.createPawn(4, Type.PAWN_WHITE);

        assertThrows(IllegalArgumentException.class, () -> {
            pawn.promoteIfEligible(Type.KING_WHITE);
        });
    }
}
