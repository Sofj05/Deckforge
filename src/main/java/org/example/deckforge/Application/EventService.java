package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Enums.Status;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Repository.IEventRepository;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private IEventRepository eRepo;
    private Validation validation;

    public EventService(IEventRepository eRepo, Validation validation) {
        this.eRepo = eRepo;
        this.validation = validation;
    }

    public void createEventAsUser(Event event) {
        validation.validateEvent(event);
        event.setStatus(Status.PROCESSING);

        eRepo.createEvent(event);
    }

    public Event readEvent(Event event) {
        validation.validateEvent(event);
        return eRepo.readEvent(event);
    }

    public List<Event> getOngoingEvents(){
        Status status = Status.ONGOING;
        return eRepo.getOngoingEvents(status);
    }

    public void updateEvent(int id, Event event) {
        validation.validateEvent(event);
        eRepo.updateEvent(id, event);
    }
    
    public void deleteEvent(int id) {
        validation.validateInt(id);
        eRepo.deleteEvent(id);
    }

    public void approveEvent(User user, Event event, String decision){
        if (user.getRole().equals(Role.ADMIN)){
            if (decision.equalsIgnoreCase("yes")){
                deleteEvent(event.getId());
            }
            if (decision.equalsIgnoreCase("no")){
                event.setStatus(Status.ONGOING);
                eRepo.updateEvent(event.getId(), event);
            }
        } else {
            throw new ValidationException("Kun admin kan godkende events");
        }
    }

    public void completeEvent(Event event, User user){
        //Lav status om til completed og/eller giv vinder på dem der vandt + validering på at det er arrangøren der gør dette

    }

    public void participateEvent(Event event, User user){
        //Metode til at tilføje brugere i eventet
    }


}
