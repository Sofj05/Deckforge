package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.User;

import java.util.List;


public interface ICardRepository {

    List<Card> getAllCards();
    List<Card> getCardsByUser(User user);
    Card getCardById(int id);
    void addNewCard(Card card);
    void readCard(Card card);
    void deleteCard(int id);
}