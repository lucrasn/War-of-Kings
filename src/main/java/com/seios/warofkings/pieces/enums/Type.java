package com.seios.warofkings.pieces.enums;

/**
 * Este Enum é responsável pela classificação dos tipos das peças de xadrez
 *
 * @author lucas
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
    KING_BLACK(11);


    private final int valor;

    Type(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return this.valor;
    }

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
