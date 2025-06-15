package com.seios.warofkings.pieces;

import com.seios.warofkings.board.Board;

import java.util.List;

/**
 * Interface que define o comportamento de movimentação das peças de xadrez.
 * <p>
 * Implementada por todas as classes concretas de peças para garantir que cada uma
 * saiba calcular seus próprios movimentos válidos e realizar movimentações,
 * respeitando as regras específicas de cada tipo de peça.
 * </p>
 *
 * <p>Deve ser usada em conjunto com {@link Positionable} e {@link ChessPiece}.</p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-05-14
 */
public interface Movable {
    /**
     * Realiza o movimento da peça para a posição especificada, se estiver contida
     * na lista de movimentos válidos e se o movimento for permitido segundo as regras do jogo.
     *
     * @param place      posição de destino.
     * @param listMoves  lista de posições válidas previamente calculadas.
     * @param board      instância atual do tabuleiro.
     * @return true se o movimento foi realizado com sucesso, false caso contrário.
     */
    boolean moveTo(int place, List<Integer> listMoves, Board board);

    /**
     * Retorna uma lista de posições inteiras para onde a peça pode se mover
     * de acordo com as regras do tipo de peça e o estado atual do tabuleiro.
     *
     * @param board matriz representando o estado atual do tabuleiro.
     * @return lista de movimentos válidos.
     */
    List<Integer> getPossibleMoves(ChessPiece[][] board);
}
