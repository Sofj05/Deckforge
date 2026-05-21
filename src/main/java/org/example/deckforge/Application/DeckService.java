package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Interface.IDeckRepository;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Service;
import java.util.List;

@Service


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

    public Deck getDecksByUser(User user) {
        return dRepo.getDeckByUser(user);
    }
    public Deck getDeckById(int id) {
        return dRepo.getDeckById(id);
    }

    public List<Card> getCardsInDeck(int deckId) {
        return dRepo.getCardsInDeck(deckId);
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
