package com.seios.warofkings.utils;

import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;

import java.util.ArrayList;
import java.util.List;

import static com.seios.warofkings.utils.PieceUtils.isSameColor;
import static com.seios.warofkings.utils.PieceUtils.isSamePiece;

/**
 * Classe utilitária para operações relacionadas ao tabuleiro de xadrez.
 * <p>
 * Contém métodos auxiliares para verificar limites, ocupações, caminhos livres
 * e recuperar ou copiar o estado do tabuleiro.
 * </p>
 *
 * <p>Usada amplamente por peças, controladores e lógica de regras do jogo.</p>
 *
 * @author Raffael
 * @version 1.1
 * @since 2025-05-14
 */
public class BoardUtils {
    /**
     * Retorna uma lista de todas as peças do tipo especificado no tabuleiro.
     *
     * @param pieces matriz 8x8 representando o tabuleiro.
     * @param type   tipo da peça a buscar.
     * @return lista de peças encontradas.
     */
    public static List<ChessPiece> findPieces(ChessPiece[][] pieces, Type type) {
        List<ChessPiece> positions = new ArrayList<>();

        for (ChessPiece[] chessPieces : pieces) {
            for (ChessPiece piece : chessPieces) {
                if (piece != null && piece.getType() == type) {
                    positions.add(piece);
                }
            }
        }
        return positions;
    }

    /**
     * Retorna uma lista de peças aliadas à peça de referência no tabuleiro.
     * <p>
     * Este método percorre todas as casas do tabuleiro e seleciona as peças que:
     * <ul>
     *   <li>Não são nulas;</li>
     *   <li>Possuem a mesma cor da peça de referência;</li>
     *   <li>São diferentes da própria peça de referência.</li>
     * </ul>
     *
     * @param referencia A peça de referência para comparar a cor.
     * @param board Matriz representando o tabuleiro de xadrez, contendo as peças em suas posições atuais.
     * @return Uma lista contendo todas as peças aliadas à peça de referência.
     *
     * @see PieceUtils#isSameColor(ChessPiece, ChessPiece)
     * @see PieceUtils#isSamePiece(ChessPiece, ChessPiece)
     */

    public static List<ChessPiece> getAliadas(ChessPiece referencia, ChessPiece[][] board) {
        List<ChessPiece> aliadas = new ArrayList<ChessPiece>();
        for (ChessPiece[] row : board) {
            for (ChessPiece p : row) {
                if (isSameColor(p, referencia) && !isSamePiece(p, referencia)) {
                    aliadas.add(p);
                }
            }
        }
        return aliadas;
    }

    /**
     * Verifica se a posição está ocupada por uma peça no tabuleiro.
     *
     * @param pieces   matriz 8x8 representando o tabuleiro.
     * @param position posição no formato inteiro (ex: 42).
     * @return {@code true} se houver peça na posição, {@code false} caso contrário.
     */
    public static boolean isPositionOccupied(ChessPiece[][] pieces, int position) {
        int x = position / 10;
        int y = position % 10;
        return BoardUtils.isWithinBounds(position) && pieces[x][y] != null;
    }

    /**
     * Retorna a peça na posição informada, se existir e se a posição for válida.
     *
     * @param pieces   matriz 8x8 representando o tabuleiro.
     * @param position posição desejada (formato XY).
     * @return a peça na posição ou {@code null} se vazia/inválida.
     */
    public static ChessPiece getPieceAt(ChessPiece[][] pieces, int position) {
        int x = position / 10;
        int y = position % 10;
        if (!BoardUtils.isWithinBounds(position)) return null;
        return pieces[x][y];
    }

    /**
     * Verifica se a posição está dentro dos limites do tabuleiro.
     *
     * @param position valor no formato XY (ex: 74, 03...).
     * @return {@code true} se estiver entre 00 e 77, {@code false} caso contrário.
     */
    public static boolean isWithinBounds(int position) {
        return PieceUtils.getX(position) >= 0 && PieceUtils.getX(position) < 8 && PieceUtils.getY(position) >= 0 && PieceUtils.getY(position) < 8;
    }

    /**
     * Retorna uma cópia superficial da matriz de peças.
     * Útil para simular jogadas sem alterar o tabuleiro original.
     *
     * @param original matriz original a ser copiada.
     * @return nova matriz com as mesmas referências de peças.
     */
    public static ChessPiece[][] copyBoard(ChessPiece[][] original) {
        ChessPiece[][] copy = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                copy[i][j] = original[i][j]; // apenas copia a referência
            }
        }
        return copy;
    }

    /**
     * Verifica se todas as casas horizontais entre duas posições estão livres (sem peças).
     * Utilizado principalmente para validar o roque entre rei e torre.
     *
     * @param from   posição de origem (ex: do rei).
     * @param to     posição da torre.
     * @param board  matriz do tabuleiro.
     * @return {@code true} se todas as casas entre from e to estiverem vazias.
     */
    public static boolean isPathClearHorizontally(int from, int to, ChessPiece[][] board) { // from = 74 (rei) e to = 77 (torre)
        int step = (from > to) ? -1 : 1; // step = 1 pois 74 > 77 = false
        for (int i = from + step; i != to; i += step) { // i = 74 + 1 = 75; i != 77; i += 1
            if (board[PieceUtils.getX(i)][PieceUtils.getY(i)] != null) {
                return false;
            }
        }
        return true;
    }
}
