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
    private final UserService userService;

    public EventService(IEventRepository eRepo, Validation validation, UserService userService) {
        this.eRepo = eRepo;
        this.validation = validation;
        this.userService = userService;
    }

    public Event getEventById(int id) {
        validation.validateInt(id);
        return eRepo.getEventById(id);
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
        return eRepo.getEventsByStatus(status);
    }

    public List<Event> getProcessingEvents(){
        Status status = Status.PROCESSING;

        List<Event> events = eRepo.getEventsByStatus(status);
        for (Event event : events){
            User organizer = userService.getUserById(event.getOrganizer().getId());
            if (organizer != null) {
                event.setOrganizer(organizer);
            }
        }
        return eRepo.getEventsByStatus(status);
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
