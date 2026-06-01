package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.DeckService;
import org.example.deckforge.Application.EventService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.Enums.Decktype;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController{
    private final UserService userService;
    private final CardService cardService;
    private final DeckService deckService;
    private final EventService eventService;


public UserController(UserService userService, CardService cardService, DeckService deckService, EventService eventService){
    this.userService = userService;
    this.cardService = cardService;
    this.deckService = deckService;
    this.eventService = eventService;
}

    @GetMapping("/")
    public String index(Model model){
    return "index";
    }


    // ---- Register User ---- //

    @GetMapping("/register") // Viser register siden
    public String register(Model model) {
        model.addAttribute("user", new User()); // Tom bruger-objekt til visning i HTML-form
        return "/user/register"; // Returnere html filen "register.html"
    }

    // POST: Opretter bruger
    @PostMapping("/register") // Modtager register-formularen
    public String registerUser(@ModelAttribute("user") User user, Model model) {

        userService.createUser(user); // Kalder service -> opretter brugeren
        return "redirect:/user/login"; // Går tilbage til registerings formen
    }

    // ---- Login af bruger ---- //

    @GetMapping("/login") // Viser login siden
    public String login(HttpSession session) {
        if(AuthHelper.isLoggedIn(session)){
            return "redirect:/user/profile";
        }
        return "user/login"; // Returnerer html filen "login.html"
    }

    @PostMapping("/login") // Modtager login-formularen
    public String loginUser(
            @RequestParam String username, // Adgangskode fra HTML
            @RequestParam String password, // Brugernavn fra HTML
            HttpSession session, // Session til at gemme bruger i
            Model model) {


        try {

            User loggedInUser = userService.login(username, password); // Tjekker login

            if (loggedInUser == null) { // Hvis login fejler
                model.addAttribute("error", "Forkert brugernavn eller adgangskode");
                return "user/login"; // Gå tilbage til login
            }

            loggedInUser.setPassword(null);
            loggedInUser.setPasswordHash(null);

            session.setAttribute("loggedInUser", loggedInUser); // Gem bruger i session
            return "redirect:/user/profile";       // Send til profil‑side
        }catch (ValidationException e){
            model.addAttribute("error", e.getMessage());
            return "user/login";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String home(HttpSession session, Model model){
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cards", cardService.getCardsByUser(loggedInUser));
        model.addAttribute("decks", deckService.getDecksByUser(loggedInUser));
        model.addAttribute("decktypes", Decktype.values());
        model.addAttribute("joinedEvents", eventService.getUsersParticipation(loggedInUser));
        model.addAttribute("organizersEvents", eventService.getOrganizersEvents(loggedInUser));
        model.addAttribute("wins", eventService.getWinsForUser(loggedInUser));

        model.addAttribute("deck", new Deck());

        return "user/profile";
    }

    @GetMapping("/cards")
    public String userCards(HttpSession session, Model model) {

        if (!AuthHelper.isLoggedIn(session)){
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cards", cardService.getCardsByUser(loggedInUser));
        model.addAttribute("decks", deckService.getDecksByUser(loggedInUser));

        return "user/usersCards";
    }
    @GetMapping("/deck/{id}")
    public String viewDeck(@PathVariable int id,
                           HttpSession session,
                           Model model) {

        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        User loggedInUser = AuthHelper.getLoggedIn(session);
        Deck deck = deckService.getDeckById(id);
        if (deck == null) {
            return "redirect:/user/profile";
        }
        if (deck.getUserId() != null &&
                !deck.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/user/profile";
        }

        List<Card> cards = deckService.getCardsInDeck(id);

        int totalCards = cards.stream()
                .mapToInt(Card::getQuantity)
                .sum();

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCards", totalCards);

        return "deck/deck";
    }
    @PostMapping("/addCard")
    public String addCardToCollection(
            @RequestParam int cardId,
            @RequestParam int amount,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/user/login";
        }

        cardService.addCardToUserCollection(user.getId(), cardId, amount);

        return "redirect:/card/cardList";
    }

    @GetMapping("/updateUser")
    public String updateUser(HttpSession session, Model model) {
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        User loggedInUser = AuthHelper.getLoggedIn(session);
        model.addAttribute("loggedInUser", loggedInUser);
        return "user/updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam(required = false) String currentPassword,
                             @RequestParam(required = false) String newPassword,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        User user = AuthHelper.getLoggedIn(session);

        user.setUsername(username);
        user.setEmail(email);

        //Bruges til at fortælle om brugeren har ændret deres kode eller ej
        boolean updPass = newPassword != null && !newPassword.isBlank();

        try {
            userService.updateUser(user, currentPassword, newPassword, updPass);
            redirectAttributes.addFlashAttribute("success", "Profil opdateret");
            //RedirectAttributes bruges til at vise meddelelser med til næste side
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            //RedirectAttributes bruges til at vise meddelelser inden redirect da den ikke ville nå at gemme det hvis det kun var model.attribute
            return "redirect:/user/updateUser";
        }
        return "redirect:/user/profile";
    }




}