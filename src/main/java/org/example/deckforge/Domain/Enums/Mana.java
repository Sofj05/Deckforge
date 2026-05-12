package org.example.deckforge.Domain.Enums;

public enum Mana {
    WHITE ("White"),
    BLUE ("Blue"),
    BLACK ("Black"),
    RED ("Red"),
    GREEN ("Green"),
    NEUTRAL ("Neutral");

    private final String description;

    Mana(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
