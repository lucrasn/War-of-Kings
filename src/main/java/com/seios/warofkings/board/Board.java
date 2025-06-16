package com.seios.warofkings.board;

import com.seios.warofkings.board.enums.Turn;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa o tabuleiro de xadrez e a lógica de inicialização das peças em suas posições iniciais.
 * Também gerencia o estado atual do jogo, incluindo o turno e as peças presentes.
 * <p>
 * Esta classe é central para a estrutura do jogo, sendo utilizada para acessar
 * e manipular o estado das casas e das peças ao longo da partida.
 * </p>
 *
 * <p><b>Nota:</b> O método de inicialização das peças foi implementado diretamente
 * no construtor. O uso de um método separado como {@code setupBoard} não é necessário.</p>
 *
 * @author Raffael
 * @version 2.0
 * @since 2025-05-14
 */
public class Board {
    /** Matriz que representa o estado atual do tabuleiro. */
    protected ChessPiece[][] pieces;

    /** Indica o turno atual do jogo. */
    protected Turn turn;

    /**
     * Construtor da classe Board. Inicializa a matriz de peças como nula e
     * posiciona todas as peças na sua posição inicial conforme o padrão do xadrez clássico.
     * O turno inicial é definido como {@code WHITE}.
     */
    public Board() {
        this.pieces = new ChessPiece[8][8];
        this.turn = Turn.WHITE;

        // Inicializa todas as casas como vazias
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                pieces[i][j] = null;
            }
        }

        // Pretas
        pieces[0][0] = Rook.createRook(0, Type.ROOK_BLACK);
        pieces[0][1] = Knight.createKnight(1, Type.KNIGHT_BLACK);
        pieces[0][2] = Bishop.createBishop(2, Type.BISHOP_BLACK);
        pieces[0][3] = Queen.createQueen(3, Type.QUEEN_BLACK);
        pieces[0][4] = King.createKing(4, Type.KING_BLACK);
        pieces[0][5] = Bishop.createBishop(5, Type.BISHOP_BLACK);
        pieces[0][6] = Knight.createKnight(6, Type.KNIGHT_BLACK);
        pieces[0][7] = Rook.createRook(7, Type.ROOK_BLACK);
        for (int j = 0; j < 8; j++) {
            pieces[1][j] = Pawn.createPawn(10 + j, Type.PAWN_BLACK);
        }

        // Brancas
        pieces[7][0] = Rook.createRook(70,Type.ROOK_WHITE);
        pieces[7][1] = Knight.createKnight(71,Type.KNIGHT_WHITE);
        pieces[7][2] = Bishop.createBishop(72, Type.BISHOP_WHITE);
        pieces[7][3] = Queen.createQueen(73, Type.QUEEN_WHITE);
        pieces[7][4] = King.createKing(74,Type.KING_WHITE);
        pieces[7][5] = Bishop.createBishop(75, Type.BISHOP_WHITE);
        pieces[7][6] = Knight.createKnight(76,Type.KNIGHT_WHITE);
        pieces[7][7] = Rook.createRook(77,Type.ROOK_WHITE);
        for (int j = 0; j < 8; j++) {
            pieces[6][j] = Pawn.createPawn(6*10 + j, Type.PAWN_WHITE);
        }
    }

    /**
     * Retorna a matriz de peças do tabuleiro.
     *
     * @return matriz 8x8 com as peças de xadrez.
     */
    public ChessPiece[][] getPieces() {
        return pieces;
    }

    /**
     * Define a matriz de peças do tabuleiro.
     *
     * @param pieces nova matriz 8x8 de peças a ser atribuída ao tabuleiro.
     */
    public void setPieces(ChessPiece[][] pieces) {
        this.pieces = pieces;
    }

    /**
     * Retorna o turno atual do jogo.
     *
     * @return {@link Turn#WHITE} ou {@link Turn#BLACK}
     */
    public Turn getTurn() {
        return turn;
    }

    /**
     * Define o turno atual do jogo.
     *
     * @param turn novo turno a ser atribuído
     */
    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
