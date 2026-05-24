package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;

import java.util.List;

public interface IDeckRepository {

    void createDeck(Deck deck);
    List<Deck> getDecksByUser(User user);
    Deck getDeckById(int id);
    List<Card> getCardsInDeck(int deckId);
    void updateDeck(int id, Deck deck);
    void deleteDeck(int id);
}
