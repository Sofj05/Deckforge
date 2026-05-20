package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Repository.ICardRepository;
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
        return null;
    }

    public List<Card> getCardsByUser(User user) {
        return cRepo.getCardsByUser(user);
    }
    public Card getCardByUser(int id){
        return cRepo.getCardById(id);
    }


    public void createCard(String cardName, Cardtype cardType, String mana, String nameOfSet, Rarity rarity, String ruleText, String ability, String image) {
        // Lav nyt Card-objekt
        Card card = new Card();
        card.setName(cardName);
        card.setCardtype(cardType);
        card.setMana(mana);
        card.setNameOfSet(nameOfSet);
        card.setRarity(rarity);
        card.setRuleText(ruleText);
        card.setImage(image);
        card.setAbility(ability);

        // Valider kortet
        validation.validateNewCard(card);

        // Gem i database
        cRepo.addNewCard(card);
    }
}
