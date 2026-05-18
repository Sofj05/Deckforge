package org.example.deckforge.Web;

import org.example.deckforge.Application.EventService;
import org.example.deckforge.Domain.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    // ----- Lav Event ----- //
    @GetMapping("/createEvent") // Viser createEvent siden
    public String createEvent(Model model) {
        model.addAttribute("event", new Event()); // Tom bruger-objekt til visning i HTML-form
        return "/event/createEvent"; // Returnere html filen "createEvent.html"
    }

    // Post: Opretter Event
    @PostMapping("/createEvent") // Modtager createEvent-formularen
    public String createNewEvent(@ModelAttribute("event") Event event, Model model) {

        eventService.createEventAsUser(event); // Kalder service -> opretter eventet
        return "redirect:/event/list";
    }

}
