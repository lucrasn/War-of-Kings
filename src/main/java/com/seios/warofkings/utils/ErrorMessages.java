package com.seios.warofkings.utils;

public class ErrorMessages {

    public static final String INVALID_MOVE = "Movimento inválido para esta peça.";
    public static final String POSITION_OCCUPIED = "A posição já está ocupada.";
    public static final String PIECE_NOT_FOUND = "Nenhuma peça encontrada na posição especificada.";

    private ErrorMessages() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Classe utilitária - não deve ser instanciada.");
    }
}
