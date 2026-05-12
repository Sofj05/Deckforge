package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.Card;

import java.util.List;

public interface ICardRepository {

    List<Card> getAllCards();
    List<Card> getCardByUser();
    void createCard(Card card);
    void readCard(Card card);
    void deleteCard(int id);
}
