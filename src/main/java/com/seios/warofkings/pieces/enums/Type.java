package com.seios.warofkings.pieces.enums;

import com.seios.warofkings.board.enums.Turn;

/**
 * Enumeração que representa todos os tipos possíveis de peças de xadrez no jogo,
 * distinguindo-as por cor (branca ou preta).
 * <p>
 * Cada tipo é associado a um valor inteiro único que pode ser utilizado para identificação,
 * persistência ou lógica de exibição.
 * </p>
 *
 * <p>Exemplos de uso incluem identificação do tipo da peça, renderização em interface gráfica
 * e lógica de movimentação e captura.</p>
 *
 * @author Lucas
 * @version 1.0
 * @since 2025-05-14
 */
public enum Type {

    PAWN_WHITE(0),
    ROOK_WHITE(1),
    KNIGHT_WHITE(2),
    BISHOP_WHITE(3),
    QUEEN_WHITE(4),
    KING_WHITE(5),

    PAWN_BLACK(6),
    ROOK_BLACK(7),
    KNIGHT_BLACK(8),
    BISHOP_BLACK(9),
    QUEEN_BLACK(10),
    KING_BLACK(11),



    /** Valor numérico associado ao tipo da peça. */
    private final int valor;

    /**
     * Construtor da enumeração {@code Type}.
     *
     * @param valor valor numérico único representando o tipo da peça.
     */
    Type(int valor) {
        this.valor = valor;
    }

    /**
     * Retorna o valor numérico associado ao tipo da peça.
     *
     * @return valor inteiro que representa o tipo.
     */
    public int getValor() {
        return this.valor;
    }

    /**
     * Retorna a cor da peça associada ao tipo.
     *
     * @return {@link Turn#WHITE} se for peça branca, {@link Turn#BLACK} se for preta.
     */
    public Turn getColor() {

        if(name().endsWith("WHITE")){
            return Turn.WHITE;
        }
        else {
            return Turn.BLACK;
        }

    }

    /**
     * Retorna uma string legível correspondente ao tipo da peça,
     * incluindo sua cor (branca ou preta).
     *
     * @return nome amigável da peça para exibição.
     */
    public String displayName() {
        return switch (this.getValor()) {
            case 0 -> "Peão branco";
            case 1 -> "Torre branca";
            case 2 -> "Cavalo branco";
            case 3 -> "Bispo branco";
            case 4 -> "Rainha branca";
            case 5 -> "Rei branco";
            case 6 -> "Peão preto";
            case 7 -> "Torre preta";
            case 8 -> "Cavalo preto";
            case 9 -> "Bispo preto";
            case 10 -> "Rainha preta";
            case 11 -> "Rei preto";
            default -> "Tipo de peça desconhecido";
        };


    }
}
