package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Repository.IDeckRepository;

public class DeckService {
    private IDeckRepository dRepo;
    private Validation validation;

    public DeckService (IDeckRepository dRepo, Validation validation) {
        this.dRepo = dRepo;
        this.validation = validation;
    }

    public void createDeck(Deck deck) {
        validation.validateDeck(deck);
        dRepo.createDeck(deck);
    }

    public void readDeck(Deck deck) {
        validation.validateDeck(deck);
        dRepo.readDeck(deck);
    }

    public void updateDeck(int id, Deck deck) {
        validation.validateDeck(deck);
        dRepo.updateDeck(id, deck);
    }

    public void deleteDeck(int id) {
        validation.validateInt(id);
        dRepo.deleteDeck(id);
    }
}
