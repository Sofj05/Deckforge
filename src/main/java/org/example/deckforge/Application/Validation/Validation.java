package org.example.deckforge.Application.Validation;

import org.example.deckforge.Domain.Repository.ICardRepository;
import org.example.deckforge.Domain.Repository.IDeckRepository;
import org.example.deckforge.Domain.Repository.IEventRepository;
import org.example.deckforge.Domain.Repository.IUserRepository;


public class Validation {

    private final ICardRepository cRepo;
    private final IDeckRepository dRepo;
    private final IEventRepository eRepo;
    private final IUserRepository uRepo;

    public Validation(ICardRepository cRepo, IDeckRepository dRepo, IEventRepository eRepo, IUserRepository uRepo) {
        this.cRepo = cRepo;
        this.dRepo = dRepo;
        this.eRepo = eRepo;
        this.uRepo = uRepo;
    }

    public void validateCard() throws ValidationException {}

    public void validateDeck() throws ValidationException {}

    public void validateEvent() throws ValidationException {}

    public void validateUser() throws ValidationException {}

    public void validateLogin() throws ValidationException {}

    public void validateString() throws ValidationException {}

    public void validateInt() throws ValidationException {}
}
