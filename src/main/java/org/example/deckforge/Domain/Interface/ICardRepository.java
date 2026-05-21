package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.User;

import java.util.List;


public interface ICardRepository {

    List<Card> getAllCards();
    List<Card> getCardsByUser(User user);
    List<Card> getFirstThreeCards();
    Card getCardById(int id);
    void addNewCard(Card card);
    Card readCard(Card card);
    void deleteCard(int id);
}