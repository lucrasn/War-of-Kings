package com.seios.warofkings.pieces;

/**
 * Esta Interface é responsável pela abstração das posições do tabuleiro
 *
 * @author lucas
 * @version 1.0
 * @since 2025-05-14
 */
public interface Positionable {
    int getX();
    int getY();
    void setPosition(int position);

}
