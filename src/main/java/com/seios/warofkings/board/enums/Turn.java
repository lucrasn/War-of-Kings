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
    WHITE, BLACK, END;

    /**
     * Alterna o turno atual para o próximo jogador.
     * <p>
     * Se o turno atual for {@code WHITE}, retorna {@code BLACK}, e vice-versa.
     * Esse método é usado para controlar a sequência de jogadas entre os dois jogadores.
     *
     * @return O próximo {@code Turn} a jogar.
     */
    public Turn next(){
        if (this == WHITE){
            return BLACK;
        } else {
            return WHITE;
        }
    }

    /**
     * Finaliza o jogo ao sinalizar que ocorreu xeque-mate.
     * <p>
     * Este método deve ser chamado quando detectado que um jogador está em xeque-mate,
     * indicando que a partida deve ser encerrada.
     *
     * @return {@code Turn.END} - uma constante que representa o estado de fim de jogo.
     */
    public Turn endGame(){
            return  END;
    }
}
