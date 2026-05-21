package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.EventService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.Event;
import org.example.deckforge.Domain.User;
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
        model.addAttribute("decktype", Decktype.values()); //Bruges til at sætte værdierne for enums som brugeren kan vælge
        return "event/createEventUser"; // Returnere html filen "createEventUser.html"
    }

    // Post: Opretter Event
    @PostMapping("/createEventUser") // Modtager createEvent-formularen
    public String createNewEvent(@ModelAttribute("event") Event event, Model model) {

        eventService.createEventAsUser(event); // Kalder service -> opretter eventet
        return "redirect:/event/list";
    }

    //GET: Viser en liste over de events der ventes på at blive behandles. KUN FOR ADMIN!
    @GetMapping("/processingEvents")
    public String getProcessingEvents(Model model) {
        model.addAttribute("events",eventService.getProcessingEvents());
        return "event/processingEvents";
    }

    //POST: Handling der udføres for at fortælle om eventet er accepteret eller det blive afvist
    @PostMapping("/approve/{id}")
    public String approveEvent(@PathVariable int id,
                               @RequestParam String decision,
                               HttpSession session){
        Event event = eventService.getEventById(id);
        User loggedInUser = AuthHelper.getLoggedIn(session);
        eventService.approveEvent(loggedInUser, event, decision);

        return "redirect:/event/processingEvents";
    }


    // Get: Viser Event info
    @GetMapping("/showEvent/{id}")
    public String showEvent(@PathVariable Model model, int id) {
        Event event = eventService.getEventById(id);
        model.addAttribute("event", event);
        model.addAttribute("participants", eventService.getParticipationList(event));
        return "/event/showEvent";
    }



    // Get: Viser Opdater Event Form
    @GetMapping("/updateEvent/{id}")
    public String showUpdateForm(Model model, int id) {
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

    //GET: se kommende events
    @GetMapping("/ongoingEvents")
    public String ongoingEventsList(Model model){
        model.addAttribute("events",eventService.getOngoingEvents());
        return "event/ongoingEvents";
    }



    //POST: Handling der udføres for at fortælle om man kan tilmelde sig eventet
    @PostMapping("/participate/{id}")
    public String approveEvent(@PathVariable int id, HttpSession session, Model model){
        Event event = eventService.getEventById(id);
        User loggedInUser = AuthHelper.getLoggedIn(session);
        try {
            eventService.participateEvent(event, loggedInUser);
            model.addAttribute("success", "Event tilmeldt!");
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
        }
        return "redirect:/event/ongoingEvents";
    }










}

