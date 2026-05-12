package org.example.deckforge.Domain.Enums;

public enum Cardtype {
    CREATURE ("Creature"),
    LAND ("Land"),
    INSTANT ("Instant"),
    SORCERY ("Sorcery"),
    ARTIFACT ("Artifact"),
    ENCHANTMENT ("Enchantment"),
    PLANESWALKER ("Planeswalker");

    private final String description;

    Cardtype(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
