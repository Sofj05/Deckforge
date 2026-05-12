package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Domain.Repository.IEventRepository;

public class EventService {
    private IEventRepository eRepo;
    private Validation validation;

    public EventService(IEventRepository eRepo, Validation validation) {
        this.eRepo = eRepo;
        this.validation = validation;
    }

    public void createEvent() {}

    public void readEvent() {}

    public void updateEvent() {}
    
    public void deleteEvent() {}
}
