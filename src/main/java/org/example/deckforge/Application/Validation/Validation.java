package org.example.deckforge.Application.Validation;

import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Interface.ICardRepository;
import org.example.deckforge.Domain.Interface.IDeckRepository;
import org.example.deckforge.Domain.Interface.IEventRepository;
import org.example.deckforge.Domain.Interface.IUserRepository;
import org.example.deckforge.Domain.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Validation {

    private final ICardRepository cRepo;
    private final IDeckRepository dRepo;
    private final IEventRepository eRepo;
    private final IUserRepository uRepo;

    @Autowired
    public Validation(ICardRepository cRepo, IDeckRepository dRepo, IEventRepository eRepo, IUserRepository uRepo) {
        this.cRepo = cRepo;
        this.dRepo = dRepo;
        this.eRepo = eRepo;
        this.uRepo = uRepo;
    }

    public void validateNewCard(Card card) throws ValidationException {
        if (card.getName() == null || card.getName().isEmpty() ){
            throw new ValidationException("Kort skal indeholde navn");
        }
        if (card.getAbility() == null || card.getAbility().isEmpty()){
            throw new ValidationException("Kort skal have ability");
        }
        if (card.getRarity() == null){
            throw new ValidationException("Kort skal have sjælendhed");
        }
        if (card.getMana() == null){
            throw new ValidationException("Kort skal have mana");
        }
        if (card.getCardtype() == null){
            throw new ValidationException("Korttype skal nævnes");
        }
        if (card.getNameOfSet() == null || card.getNameOfSet().isEmpty()){
            throw new ValidationException("Navn på sæt skal nævnes");
        }

    }

    public void validateDeck(Deck deck) throws ValidationException {}

    public void validateEvent(Event event) throws ValidationException {
        if (event.getName() == null || event.getName().isEmpty()){
            throw new ValidationException("Venligst indtast navnet på dette event");
        }
        if (event.getOrganizer() == null){
            throw new ValidationException("Event skal have arrangør");
        }
        if (event.getDate() == null){
            throw new ValidationException("Event skal have dato");
        }
        if (event.getTime() == null){
            throw new ValidationException("Event skal have tidspunkt");
        }
        if (event.getMaxParticipants() <= 0){
            throw new ValidationException("Event skal have et gyldigt antal pladser til eventet. F.eks. 10");
        }
        if (event.getRules().isEmpty() || event.getRules() == null){
            throw new ValidationException("Regler for event skal inkluderes");
        }
    }

    public void validateUser(User user) throws ValidationException {
        if (user.getUsername() == null || user.getUsername().isEmpty()){
            throw new ValidationException("Bruger skal have brugernavn");
        }
        if (user.getEmail() ==null || user.getEmail().isEmpty()){
            throw new ValidationException("Der skal bruges email for at oprette en bruger");
        }
        if (!user.getEmail().contains("@")){
            throw new ValidationException("Email skal indeholde '@'");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()){
            throw new ValidationException("Bruger skal have adgangskode");
        }
        if (user.getPassword().length() < 6){
            throw new ValidationException("Adgangskode skal være mere end 6 karakterer");
        }

    }

    public void validateLogin(User user, String password) throws ValidationException {
        if (user == null || !BCrypt.checkpw(password, user.getPasswordHash())) {
            throw new ValidationException("Brugernavn eller adgangskode forkert");
        }
    }

    public void validateString() throws ValidationException {}

    public void validateInt(int id) throws ValidationException {}
}
