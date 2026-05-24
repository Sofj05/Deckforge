package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.DeckService;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping("/decks/create")
    public String createDeck(@ModelAttribute Deck deck, HttpSession session
    ) {

        User user = (User) session.getAttribute("loggedInUser");

        deck.setUserId(user.getId());
        deckService.createDeck(deck);

        return "redirect:/user/profile";
    }
}