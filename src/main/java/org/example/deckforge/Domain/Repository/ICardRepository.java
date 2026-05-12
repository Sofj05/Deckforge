package org.example.deckforge.Domain.Repository;

public interface ICardRepository {
    
    void createCard(Card card);
    void readCard(Card card);
    void deleteCard(int id);
}
