package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.Enums.Status;
import org.example.deckforge.Domain.Event;

import java.util.List;

public interface IEventRepository {


    void createEvent(Event event);
    Event readEvent(Event event);
    void updateEvent(int id, Event event);
    void deleteEvent(int id);
    List<Event> getEventsByStatus(Status status);
    int getParticipationCount(Event event);
    Event getEventById(int id);
}
