package org.example.deckforge.Domain.Enums;

public enum Role {
    USER ("User"),
    ADMIN ("Admin");

    private final String description;

    Role(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
