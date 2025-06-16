package com.seios.warofkings.pieces.types;

import com.seios.warofkings.board.Board;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de testes unitários para a peça {@link King}, validando seu comportamento específico no tabuleiro,
 * incluindo movimentação padrão, roque e detecção de xeque-mate.
 *
 * <p>Os testes abordam os seguintes aspectos:</p>
 * <ul>
 *   <li>Criação e identificação da cor do rei;</li>
 *   <li>Movimentos possíveis em diferentes cenários;</li>
 *   <li>Execução de roque (castling) em condições válidas e inválidas;</li>
 *   <li>Verificação do estado de xeque-mate.</li>
 * </ul>
 *
 * @author Allan
 * @version 1.0
 * @since 2025-06-16
 */
class KingTest {

    /**
     * Testa se o método {@link ChessPiece#isWhite()} retorna {@code true}
     * para uma peça do tipo branco.
     */
    @Test
    void testIsWhiteReturnsTrueForWhitePiece() {
        ChessPiece whiteKing = King.createKing(74, Type.KING_WHITE);
        assertTrue(whiteKing.isWhite());
    }

    /**
     * Testa se o método {@link ChessPiece#isWhite()} retorna {@code false}
     * para uma peça do tipo preto.
     */
    @Test
    void testIsWhiteReturnsFalseForBlackPiece() {
        ChessPiece blackKing = King.createKing(4, Type.KING_BLACK);
        assertFalse(blackKing.isWhite());
    }

    /**
     * Testa a criação correta de um rei utilizando {@link King#createKing(int, Type)}.
     */
    @Test
    void testCreateKingValid() {
        King king = King.createKing(22, Type.KING_WHITE);
        assertEquals(22, king.getPosition());
        assertEquals(Type.KING_WHITE, king.getType());
    }

    /**
     * Testa os movimentos válidos do rei em um tabuleiro vazio,
     * garantindo que ele possa se mover uma casa em todas as direções.
     */
    @Test
    void testPossibleMovesEmptyBoard() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;

        List<Integer> moves = king.getPossibleMoves(board);
        assertTrue(moves.contains(23));
        assertTrue(moves.contains(43));
        assertTrue(moves.contains(32));
        assertTrue(moves.contains(34));
        assertTrue(moves.contains(22));
        assertTrue(moves.contains(44));
        assertTrue(moves.contains(24));
        assertTrue(moves.contains(42));
        assertFalse(moves.contains(33));
    }

    /**
     * Testa se o rei evita casas ocupadas por peças da mesma cor.
     */
    @Test
    void testPossibleMovesInvalidSameColor() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_WHITE);
        board[3][2] = Pawn.createPawn(32, Type.PAWN_WHITE);

        List<Integer> moves = king.getPossibleMoves(board);
        assertFalse(moves.contains(32));
        assertFalse(moves.contains(22));
    }

    /**
     * Testa se o rei pode capturar peças adversárias em casas adjacentes.
     */
    @Test
    void testPossibleMovesCanCaptureOpponent() {
        ChessPiece[][] board = new ChessPiece[8][8];
        King king = King.createKing(33, Type.KING_WHITE);
        board[3][3] = king;
        board[2][2] = Pawn.createPawn(22, Type.PAWN_BLACK);

        List<Integer> moves = king.getPossibleMoves(board);
        assertTrue(moves.contains(32));
        assertTrue(moves.contains(22));
    }

    /**
     * Testa o movimento de roque (castling) do rei em situação válida.
     */
    @Test
    void testPossibleMovesWithCastling() {
        ChessPiece[][] board = new ChessPiece[8][8];

        King king = King.createKing(74, Type.KING_WHITE);
        Rook rook = Rook.createRook(70, Type.ROOK_WHITE);
        board[7][4] = king;
        board[7][0] = rook;

        List<Integer> moves = king.getPossibleMoves(board);
        assertTrue(moves.contains(72));
    }

    /**
     * Testa se o roque é inválido quando o rei já se moveu anteriormente.
     */
    @Test
    void testPossibleMovesNoCastlingKingMoved() {
        ChessPiece[][] board = new ChessPiece[8][8];

        King king = new King(74, Type.KING_WHITE, 1); // moveCount = 1
        Rook rook = Rook.createRook(77, Type.ROOK_WHITE);
        board[7][4] = king;
        board[7][7] = rook;

        List<Integer> moves = king.getPossibleMoves(board);
        assertFalse(moves.contains(76));
    }

    /**
     * Testa se o rei realiza corretamente um movimento normal (não roque).
     */
    @Test
    void testMoveToNormalMove() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();

        King king = King.createKing(74, Type.KING_WHITE);
        pieces[7][4] = king;
        board.setPieces(pieces);

        List<Integer> possibleMoves = List.of(75);

        boolean result = king.moveTo(75, possibleMoves, board);

        assertTrue(result);
        assertEquals(75, king.getPosition());
    }

    /**
     * Testa a execução de um roque válido e verifica a nova posição da torre após o movimento.
     */
    @Test
    void testMoveToValidCastling() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();
        board.setPieces(new ChessPiece[8][8]);

        King king = King.createKing(34, Type.KING_WHITE);
        Rook rook = Rook.createRook(30, Type.ROOK_WHITE);

        pieces[3][4] = king;
        pieces[3][0] = rook;
        board.setPieces(pieces);

        List<Integer> moves = king.getPossibleMoves(pieces);
        boolean result = king.moveTo(32, moves, board);

        assertTrue(result);
        assertEquals(32, king.getPosition());
        ChessPiece rookAfterMove = board.getPieces()[3][3];
        assertNotNull(rookAfterMove);
        assertEquals(rook, rookAfterMove);
    }

    /**
     * Testa o cenário em que o roque é inválido por haver uma peça bloqueando o caminho entre rei e torre.
     */
    @Test
    void testMoveToInvalidCastling() {
        Board board = new Board();
        ChessPiece[][] pieces = board.getPieces();

        King king = King.createKing(34, Type.KING_WHITE);
        Rook rook = Rook.createRook(37, Type.ROOK_WHITE);
        Pawn pawn = Pawn.createPawn(35, Type.PAWN_WHITE);

        pieces[3][4] = king;
        pieces[3][7] = rook;
        pieces[3][5] = pawn;
        board.setPieces(pieces);

        List<Integer> moves = king.getPossibleMoves(pieces);
        boolean result = king.moveTo(36, moves, board);

        assertFalse(result);
        assertEquals(34, king.getPosition());
    }

    /**
     * Testa o método {@link King#xequeMate(Board)} para verificar se o rei está em xeque-mate,
     * ou seja, sem qualquer movimento possível que remova a ameaça.
     */
    @Test
    void testXequeMate() {
        Board board = new Board();
        ChessPiece[][] empty = new ChessPiece[8][8];
        board.setPieces(empty);

        King king = King.createKing(74, Type.KING_WHITE);
        ChessPiece[][] pieces = board.getPieces();
        pieces[7][4] = king;

        Rook rook1 = Rook.createRook(70, Type.ROOK_BLACK);
        Rook rook2 = Rook.createRook(67, Type.ROOK_BLACK);
        pieces[7][0] = rook1;
        pieces[6][7] = rook2;

        board.setPieces(pieces);

        boolean result = king.xequeMate(board);
        assertTrue(result);
    }
}
