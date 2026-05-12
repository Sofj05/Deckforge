package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.ICardRepository;

public class CardService {
    private ICardRepository cRepo;
    private Validation validation;

    public CardService(ICardRepository cRepo, Validation validation) {
        this.cRepo = cRepo;
        this.validation = validation;
    }

    public void createCard() {}

    public void readCard() {}

    public void deleteCard() {}

    public void getCardById () {}
}
