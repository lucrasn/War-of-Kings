package com.seios.warofkings.board;

import com.seios.warofkings.game.enums.Turn;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.Pawn;

public class Board implements Boardable {
    protected int[][] board = new int[8][8];
    protected ChessPiece[][] pieces = new ChessPiece[8][8];
    protected Turn turn;

    public Board() {
        this.setupBoard();
    }

    public void printBoard() {
        System.out.println("Tabuleiro:");

        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j) {
                System.out.printf("%3d", this.board[i][j]);
            }

            System.out.println();
        }

    }

    public void setupBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = -1; // -1 casas vazias
                pieces[i][j] = null;
            }
        }
        // Pretas
        board[0][0] = 7;  //pieces[0][0] = new Rook(0, Type.ROOK_BLACK);// Torre preta
        board[0][1] = 8;  //pieces[0][1] = new Knight(1, Type.KNIGHT_BLACK);//Cavalo preto
        board[0][2] = 9;  //pieces[0][2] = new Bishop(2, Type.BISHOP_BLACK);//Bispo preto
        board[0][3] = 10; //pieces[0][3] = new Queen(3, Type.QUEEN_BLACK);//Rainha preta
        board[0][4] = 11; //pieces[0][4] = new King(4, Type.KING_BLACK);//Rei preto
        board[0][5] = 9;  //pieces[0][5] = new Bishop(5, Type.BISHOP_BLACK);//Bispo preto
        board[0][6] = 8;  //pieces[0][6] = new Knight(6, Type.KNIGHT_BLACK);//Cavalo preto
        board[0][7] = 7;  //pieces[0][7] = new Rook(7, Type.ROOK_BLACK);// Torre preta

        for (int j = 0; j < 8; j++) {
            board[1][j] = 6;
            pieces[1][j] = new Pawn(10 + j, Type.PAWN_BLACK);//Peão preto
        }

        // Brancas
        for (int j = 0; j < 8; j++) {
            board[6][j] = 0;
            pieces[6][j] = new Pawn(60 + j, Type.PAWN_WHITE);//Peão branco
        }

        board[7][0] = 1;  //pieces[7][0] = new Rook(/*70,Type.ROOK_WHITE*/);// Torre branca
        board[7][1] = 2;  //pieces[7][1] = new Knight(/*71,Type.KNIGHT_WHITE*/);// Cavalo branco
        board[7][2] = 3;  //pieces[7][2] = new Bishop(/*72,Type.BISHOP_WHITE*/);// Bispo branco
        board[7][3] = 4;  //pieces[7][3] = new Queen(/*73,Type.QUEEN_WHITE*/);// Rainha branca
        board[7][4] = 5;  //pieces[7][4] = new King(/*74,Type.KING_WHITE*/);// Rei branco
        board[7][5] = 3;  //pieces[7][5] = new Bishop(/*75,Type.BISHOP_WHITE*/);// Bispo branco
        board[7][6] = 2;  //pieces[7][6] = new Knight(/*76,Type.KNIGHT_WHITE*/);// Cavalo branco
        board[7][7] = 1;  //pieces[7][7] = new Rook(/*77,Type.ROOK_WHITE*/);// Torre branca
    }
    /**
     * Método para verificar se a posição especificada do tabuleiro está ocupada por uma peça.
     * @author Raffael Wagner
     * @param x int - A linha da posição a ser analisada está (0 a 7).
     * @param y int - A coluna da posição a ser analisada está (0 a 7).
     * @return {@code true} se houver uma peça na posição especificada, {@code false} caso contrário.*/
    public boolean isPositionOccupied(int x, int y) {
        if (!isValidPosition(x, y)) return false;
        return this.pieces[x][y] != null;
    }
    /**
     * Caso tenha, retorna a peça na posição informada do tabuleiro.
     * @author Raffael Wagner
     * @param x int - A linha da posição a ser analisada (0 a 7).
     * @param y int - A coluna da posição a ser analisada (0 a 7).
     * @return a peça na posição especificada ou {@code false} caso a posição seja inválida ou não tenha peça.*/
    public ChessPiece getPieceAt(int x, int y) {
        if (!isValidPosition(x, y)) return null;
        return pieces[x][y];
    }
    /**
     * Método para verificar se a posição está dentro dos limites do tabuleiro.
     * @author Raffael Wagner
     * @param x int - A linha da posição a ser analisada (0 a 7).
     * @param y int - A coluna da posição a ser analisada (0 a 7).
     * @return {@code true} se a posição for válida, {@code false} caso contrário.*/
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}