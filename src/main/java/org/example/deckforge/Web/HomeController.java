package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CardService cardService;

    @Autowired
    public HomeController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {

        // Hent bruger fra session
        User user = (User) session.getAttribute("loggedInUser");

        // Hvis ingen bruger er logget ind → send til login
        if (user == null) {
            return "redirect:/login";
        }

        // Send brugerens data til Thymeleaf
        model.addAttribute("loggedInUser", user);

        // Hent brugerens kort fra databasen
        model.addAttribute("cards", cardService.getCardsByUser(user));

        // Vis home.html
        return "home";
    }
}