package com.seios.warofkings.pieces;

/**
 * Interface que define o comportamento de posicionamento das peças de xadrez no tabuleiro.
 * <p>
 * Fornece métodos para obter as coordenadas (linha e coluna) de uma peça,
 * além de permitir alterar sua posição com base no sistema posicional usado (XY → XY = 10 * linha + coluna).
 * </p>
 *
 * <p>Geralmente implementada junto à interface {@link Movable} por classes concretas de peças.</p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-05-14
 */
public interface Positionable {
    /**
     * Retorna a coordenada X (linha) da peça no tabuleiro.
     *
     * @return valor da linha (0 a 7).
     */
    int getX();

    /**
     * Retorna a coordenada Y (coluna) da peça no tabuleiro.
     *
     * @return valor da coluna (0 a 7).
     */
    int getY();

    /**
     * Atualiza a posição da peça no formato inteiro (XY),
     * onde a dezena representa a linha e a unidade a coluna.
     *
     * @param position nova posição da peça.
     */
    void setPosition(int position);
}
