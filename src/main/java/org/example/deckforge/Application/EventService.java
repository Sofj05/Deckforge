package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.Enums.Status;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.Interface.IEventRepository;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<Event> events = eRepo.getEventsByStatus(status);
        setOrganizer(events);
        return events;
    }

    public List<Event> getProcessingEvents(){
        Status status = Status.PROCESSING;

        List<Event> events = eRepo.getEventsByStatus(status);
        setOrganizer(events);
        return events;
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
                event.setStatus(Status.ONGOING);
                eRepo.updateEvent(event.getId(), event);
            }
            if (decision.equalsIgnoreCase("no")){
                deleteEvent(event.getId());
            }
        } else {
            throw new ValidationException("Kun admin kan godkende events");
        }
    }

    public void completeEvent(Event event, User user){
        //Lav status om til completed og evt. giv vinder på dem der vandt + validering på at det er arrangøren der gør dette

    }

    public void participateEvent(Event event, User user){
        //Sikrer at man ikke overskrider grænsen for deltagere
        int now = eRepo.getParticipationCount(event);
        if (now >= event.getMaxParticipants()){
            throw new ValidationException("Ikke flere pladser");
        }

        //Sikrer at brugeren ikke tilmeldes to gange
        List<Integer> participationList = eRepo.getParticipantsForEvent(event);
        for (Integer participation : participationList){
            if (participation == user.getId()){
                throw new ValidationException("Du er allerede tilmeldt");
            }
        }
        eRepo.addParticipant(event, user);
    }
    
    public List<User> getParticipationList(Event event){
        List<Integer> participantionList = eRepo.getParticipantsForEvent(event);
        List<User> participants = new ArrayList<>();

        //Da kun deltagernes id bliver hentet, skal man lige sætte brugerne på
        for (Integer id : participantionList){
            participants.add(userService.getUserById(id));
        }
        return participants;
    }

    //Lille privat metode til at sette arrangøren da den kun henter id fra db i EventRepository
    private void setOrganizer(List<Event> events){
        for (Event event : events){
            User organizer = userService.getUserById(event.getOrganizer().getId());
            if (organizer != null) {
                event.setOrganizer(organizer);
            }
        }
    }


}
