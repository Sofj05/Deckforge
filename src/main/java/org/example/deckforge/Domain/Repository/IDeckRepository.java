package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.Deck;

public interface IDeckRepository {

    void createDeck(Deck deck);
    void readDeck(Deck deck);
    void updateDeck(int id, Deck deck);
    void deleteDeck(int id);
}
