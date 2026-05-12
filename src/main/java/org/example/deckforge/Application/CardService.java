package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.ICardRepository;
import org.example.deckforge.Domain.Card;

public class CardService {
    private ICardRepository cRepo;
    private Validation validation;

    public CardService(ICardRepository cRepo, Validation validation) {
        this.cRepo = cRepo;
        this.validation = validation;
    }

    public void createCard(Card card) {
        validation.validateCard(card);
        cRepo.createCard(card);
    }

    public void readCard(Card card) {
        validation.validateCard(card);
        cRepo.readCard(card);
    }

    public void deleteCard(int id) {
        validation.validateCard(id);
        cRepo.deleteCard(id);
    }

    public void getCardById () {}
}
