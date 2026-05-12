package org.example.deckforge.Domain.Repository;

import org.example.deckforge.Domain.Event;

public interface IEventRepository {

    void createEvent(Event event);
    void readEvent(Event event);
    void updateEvent(int id, Event event);
    void deleteEvent(int id);
}
