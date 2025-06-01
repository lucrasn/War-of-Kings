package com.seios.warofkings.game.enums;

public enum Turn {
    WHITE, BLACK;

    public Turn next(){
        if (this == WHITE){
            return BLACK;
        } else {
            return WHITE;
        }
    }

    public String displayName(){
        if (this == WHITE){
            return "Branco";
        } else {
            return "Preto";
        }
    }
}