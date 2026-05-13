package org.example.deckforge.Web;

import org.example.deckforge.Application.EventService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    // ----- Lav Event ----- //


    @GetMapping("/createEvent")
    public String createEvent() {
        model.attribute
        return "/event/createEvent";
    }

}
