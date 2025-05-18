package com.seios.warofkings.pieces;

import com.seios.warofkings.pieces.enums.Type;

public abstract class ChessPiece implements Movable, Positionable {
    protected int position;
    protected Type type;
    protected int n_moves;

    public ChessPiece() {
        this.n_moves = 0; // teoricamente todas as peças começam com 0 movimentos, mas como antes de fazer isso eu tenho que fazer uma verificação acho q esse construtor se torna inutil
    }

    @Override
    public int getX() {
        return (this.position / 10) % 10; // Dezena
    }

    public static int getX(int position) {
        return (position / 10) % 10;
    }

    @Override
    public int getY() {
        return this.position % 10; // Unidade
    }

    public static int getY(int position) {
        return position % 10;
    }

    @Override
    public String toString() {
        return "Tipo da peça: " + type.displayName() + ". " +
                "Na posição : (" + this.getX() +  ", " + this.getY() + ")";
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(int position) { // muda a posição
        this.position = position;
    }

    // o setPosition é pra uso interno de moveTo,
    // mas n pode ser private nem protected, somente public ai é peso.
    @Override
    public boolean moveTo(int position, int[][] board) { // faz verificações antes de mudar
        //TODO: verificações como: Se é possível o movimento de acordo com o andar da peça (essa verificação fica para as classes concretas); Se não tem uma peça do mesmo exercíto nessa posição; etc

        boolean exercito = this.type.getValor() <= 5; // if -> exercito branco; else -> exercito preto
        if (board[getX(position)][getY(position)] <= 5 && !exercito) {
            setPosition(position);
            this.n_moves++;
            return true;
        } else if (board[getX(position)][getY(position)] >= 6 && exercito) {
            setPosition(position);
            this.n_moves++;
            return true;
        }
        return false;
    }

    public int getType() {
        return this.type.getValor();
    }

    public int getN_moves() {
        return this.n_moves;
    }

    public void setN_moves(int n_moves) {
        this.n_moves = n_moves;
    }
}
