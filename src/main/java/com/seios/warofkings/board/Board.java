package com.seios.warofkings.board;

import com.seios.warofkings.board.enums.Turn;
import com.seios.warofkings.pieces.ChessPiece;
import com.seios.warofkings.pieces.enums.Type;
import com.seios.warofkings.pieces.types.*;

//para a documentação tem que ter um comentario no inicio da classe parecido como o em ChessPiece que dia o altor, data de ciração, ... (tais ligado ja)
public class Board implements Boardable {
    protected int[][] board = new int[8][8];
    protected ChessPiece[][] pieces = new ChessPiece[8][8];
    protected Turn turn;

    // vc pode fzr a implementação de setupBoard dentro do construtor, a existencia de setupBoard n é necessaria
    // é preciso tbm que dentro do construtor alem de contruir o board contruir o pieces além de ser atribuido o valor de turn como sendo WHITE
    public Board() {
        this.setupBoard();
    }

    // isso é basicamente o toString
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
        board[0][0] = 7;  // Torre preta
        pieces[0][0] = Rook.createRook(0, Type.ROOK_BLACK);

        board[0][1] = 8;  //Cavalo preto
        pieces[0][1] = Knight.createKnight(1, Type.KNIGHT_BLACK);

        board[0][2] = 9;  //Bispo preto
        pieces[0][2] = Bishop.createBishop(2, Type.BISHOP_BLACK);

        board[0][3] = 10; //Rainha preta
        pieces[0][3] = Queen.createQueen(3, Type.QUEEN_BLACK);

        board[0][4] = 11; // Rei preto
        //pieces[0][4] = King.createKing(4, Type.KING_BLACK);

        board[0][5] = 9;  //Bispo preto
        pieces[0][5] = Bishop.createBishop(5, Type.BISHOP_BLACK);

        board[0][6] = 8;  //Cavalo preto
        pieces[0][6] = Knight.createKnight(6, Type.KNIGHT_BLACK);

        board[0][7] = 7;  // Torre preta
        pieces[0][7] = Rook.createRook(7, Type.ROOK_BLACK);


        for (int j = 0; j < 8; j++) {
            board[1][j] = 6;
            pieces[1][j] = Pawn.createPawn(10 + j, Type.PAWN_BLACK);//Peão preto
        }

        // Brancas
        board[7][0] = 1;  // Torre branca
        pieces[7][0] = Rook.createRook(70,Type.ROOK_WHITE);

        board[7][1] = 2;  // Cavalo branco
        pieces[7][1] = Knight.createKnight(71,Type.KNIGHT_WHITE);

        board[7][2] = 3;  // Bispo branco
        pieces[7][2] = Bishop.createBishop(72, Type.BISHOP_WHITE);

        board[7][3] = 4;  // Rainha branca
        pieces[7][3] = Queen.createQueen(73, Type.QUEEN_WHITE);

        board[7][4] = 5;  // Rei branco
        //pieces[7][4] = King.createKing(74,Type.KING_WHITE);

        board[7][5] = 3;  // Bispo branco
        pieces[7][5] = Bishop.createBishop(75, Type.BISHOP_WHITE);

        board[7][6] = 2;  // Cavalo branco
        pieces[7][6] = Knight.createKnight(76,Type.KNIGHT_WHITE);

        board[7][7] = 1;  // Torre branca
        pieces[7][7] = Rook.createRook(77,Type.ROOK_WHITE);

        for (int j = 0; j < 8; j++) {
            board[6][j] = 0;
            pieces[6][j] = Pawn.createPawn(6*10 + j, Type.PAWN_WHITE);//Peão branco
        }
    }

    // static
    // talvez seja mais protivo se passae somente um parametro nesse getPieceAt e que fosse (int position)
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

    // static
    // talvez seja mais proativo se passasse somente um parametro nesse getPieceAt e que fosse (int position)
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

    // Talvez n seja necessario mais o uso desse metodo isValidPosition devido a existencia de isWithinBounds
    // que inclusive faz mais sentido esta em BoardUtils do que PieceUtils, se puder trocar :)
    /**
     * Método para verificar se a posição está dentro dos limites do tabuleiro.
     * @author Raffael Wagner
     * @param x int - A linha da posição a ser analisada (0 a 7).
     * @param y int - A coluna da posição a ser analisada (0 a 7).
     * @return {@code true} se a posição for válida, {@code false} caso contrário.*/
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    // está faltando os gets, sets e toString*:
        // *: o toString meio que ja existe seria o printBoard
    public ChessPiece[][] getPieces() {
        return this.pieces;
    }
}