package org.example.deckforge.Web;

import org.example.deckforge.Application.EventService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Domain.Event;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    // ----- Lav Event ----- //
    @GetMapping("/createEventUser") // Viser createEvent siden
    public String createEvent(Model model) {
        model.addAttribute("event", new Event()); // Tom bruger-objekt til visning i HTML-form
        return "event/createEventUser"; // Returnere html filen "createEventUser.html"
    }

    // Post: Opretter Event
    @PostMapping("/createEventUser") // Modtager createEvent-formularen
    public String createNewEvent(@ModelAttribute("event") Event event, Model model) {

        eventService.createEventAsUser(event); // Kalder service -> opretter eventet
        return "redirect:/event/list";
    }

    @GetMapping("/processingEvents")
    public String getProcessingEvents(Model model) {
        model.addAttribute("events",eventService.getProcessingEvents());

        return "event/processingEvents";
    }


    // Get: Viser Event
    @GetMapping("/showEvent/{id}")
    public String showEvent(@PathVariable Model model, int id) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "/event/showEvent";
    }



    // Get: Viser Opdater Event Form
    @GetMapping("/updateEvent/{id}")
    public String showUpdateForm(@PathVariable Model model, int id) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        return "/event/updateEvent";
    }

    // Post: Opdater Event
    @PostMapping("/updateEvent")
    public String updateEvent(@ModelAttribute("event") Event event) {
    Event existingEvent = eventService.getEventById(event.getId());
        return null;
    }

    // Get: Confirm Delete Event
    @GetMapping("/deleteConfirmation/{id}")
    public String deleteEvent(@PathVariable int id, Model model) {
    Event event = eventService.getEventById(id);
    model.addAttribute("event", event);
    return "event/deleteConfirmation";
    }

    // Post: Delete Event
    @PostMapping("/deleteConfirmation/{id}")
    public String deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return "redirect:home";

    }





}
