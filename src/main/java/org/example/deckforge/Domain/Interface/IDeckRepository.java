package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;

public interface IDeckRepository {

    void createDeck(Deck deck);
    Deck getDeckByUser(User user);
    void updateDeck(int id, Deck deck);
    void deleteDeck(int id);
}
