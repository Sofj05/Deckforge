package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.Deck;

public interface IDeckRepository {

    void createDeck(Deck deck);
    Deck readDeck(Deck deck);
    void updateDeck(int id, Deck deck);
    void deleteDeck(int id);
}
