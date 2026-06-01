package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Interface.ICardRepository;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {
    private ICardRepository cRepo;
    private Validation validation;

    @Autowired
    public CardService(ICardRepository cRepo, Validation validation) {
        this.cRepo = cRepo;
        this.validation = validation;
    }

    public void addNewCardAsAdmin(Card card, User user) {
        if (user.getRole().equals(Role.ADMIN)) {
            validation.validateNewCard(card);
            cRepo.addNewCard(card);
        }
        if (user.getRole().equals(Role.USER)) {
            throw new ValidationException("Kun admin kan tilføje nye kort!");
        }
    }


    public List<Card> getAllCards() {
        return cRepo.getAllCards();
    }

    public List<Card> getCardsByUser(User user) {
        return cRepo.getCardsByUser(user);
    }

    public void addCardToUserCollection(int userId, int cardId, int quantity) {


        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity skal være mindst 1");
        }

        cRepo.addCardToUserCollection(userId, cardId, quantity);
    }

    public void createCard(String cardName, Cardtype cardType, String mana, String nameOfSet, Rarity rarity, String ruleText, String ability, String image) {

    }

    public List<Card> getFirstThreeCards() {
        return cRepo.getFirstThreeCards();
    }

    public Card getCardById(int offeredCardId) {
    }
}
