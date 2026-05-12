package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Repository.IEventRepository;

public class EventService {
    private IEventRepository eRepo;
    private Validation validation;

    public EventService(IEventRepository eRepo, Validation validation) {
        this.eRepo = eRepo;
        this.validation = validation;
    }

    public void createEvent(Event event) {
        validation.validateEvent(event);
        eRepo.createEvent(event);
    }

    public void readEvent(Event event) {
        validation.validateEvent(event);
        eRepo.readEvent(event);
    }

    public void updateEvent(int id, Event event) {
        validation.validateEvent(event);
        eRepo.updateEvent(id, event);
    }
    
    public void deleteEvent(int id) {
        validation.validateEvent(id);
        eRepo.deleteEvent(id);
    }
}
