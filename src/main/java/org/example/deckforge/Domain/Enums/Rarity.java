package org.example.deckforge.Domain.Enums;

public enum Rarity {
    COMMON ("Common"),
    UNCOMMON ("Uncommon"),
    RARE ("Rare"),
    MYTHICRARE("Mythic Rare");

    private final String description;

    Rarity(String description){
        this.description = description;
    }

    public String getDescription(){
            return description;
    }


}
