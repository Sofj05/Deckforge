package org.example.deckforge.Domain.Enums;

public enum Decktype {
    COMMANDER ("Commander"),
    STANDARD ("Standard");

    private final String description;

    Decktype(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
