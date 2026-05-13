package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Repository.ICardRepository;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.User;

import java.util.List;

public class CardService {
    private ICardRepository cRepo;
    private Validation validation;

    public CardService(ICardRepository cRepo, Validation validation) {
        this.cRepo = cRepo;
        this.validation = validation;
    }

    public void addNewCardAsAdmin(Card card, User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            validation.validateNewCard(card);
            cRepo.addNewCard(card);
        }
        if (user.getRole().equals(Role.USER)){
            throw new ValidationException("Kun admin kan tilføje nye kort!");
        }
    }

    public void readCard(Card card) {
        validation.validateNewCard(card);
        cRepo.readCard(card);
    }

    public void deleteCard(int id) {
        validation.validateInt(id);
        cRepo.deleteCard(id);
    }

    public List<Card> getAllCards() {
        return cRepo.getAllCards();
    }

    public List<Card> getCardByUser(User user) {
        return cRepo.getCardsByUser(user);
    }
    public Card getCardByUser(int id){
        return cRepo.getCardById(id);
    }


}
