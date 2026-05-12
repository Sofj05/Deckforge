package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.IDeckRepository;

public class DeckService {
    private IDeckRepository dRepo;
    private Validation validation;

    public DeckService (IDeckRepository dRepo, Validation validation) {
        this.dRepo = dRepo;
        this.validation = validation;
    }

    public void createDeck() {}

    public void readDeck() {}

    public void updateDeck() {}

    public void deleteDeck() {}
}
