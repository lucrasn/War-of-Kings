package com.seios.warofkings.board.enums;

/**
 * Representa os turnos de um jogo de xadrez, alternando entre o jogador branco (WHITE)
 * e o jogador preto (BLACK). Esta enumeração é usada para controlar a vez de jogar
 * durante a execução do jogo.
 * <p>
 * Pode ser utilizada em lógica de controle de fluxo como validação de jogadas,
 * checagem de estado de xeque, entre outros.
 * </p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-06-15
 */
public enum Turn {
    WHITE, BLACK;

    /**
     * Retorna o próximo turno. Se o turno atual for {@code WHITE}, retorna {@code BLACK}, e vice-versa.
     *
     * @return o turno oposto ao atual
     */
    public Turn next(){
        if (this == WHITE){
            return BLACK;
        } else {
            return WHITE;
        }
    }
}