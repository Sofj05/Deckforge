package org.example.deckforge.Domain.Enums;

public enum Status {
    PROCESSING ("Processing"),
    ONGOING ("Ongoing"),
    COMPLETED ("Completed"),
    CANCELLED ("Cancelled");

    private final String description;

    Status(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
