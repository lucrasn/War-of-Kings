package com.seios.warofkings.exceptions;

public class SameTypeException extends RuntimeException {
    public SameTypeException() {
        super("A ação não é permitida: ambas as peças pertencem ao mesmo tipo (mesmo lado).");
    }

    public SameTypeException(String message) {
        super(message);
    }
}
