package com.seios.warofkings.exceptions;

public class SamePieceException extends RuntimeException {
    public SamePieceException() {
        super("A ação não é permitida: ambas as peças pertencem ao mesmo tipo (mesmo lado).");
    }

    public SamePieceException(String message) {
        super(message);
    }
}
