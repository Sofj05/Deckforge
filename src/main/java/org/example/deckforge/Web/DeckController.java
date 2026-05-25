package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.DeckService;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping("/create")
    public String createDeck(@ModelAttribute Deck deck, HttpSession session
    ) {

        User user = (User) session.getAttribute("loggedInUser");

        deck.setUserId(user.getId());
        deckService.createDeck(deck);

        return "redirect:/user/profile";
    }
    @PostMapping("/update")
    public String updateDeck(@ModelAttribute Deck deck, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        deck.setUserId(user.getId());
        deckService.updateDeck(deck.getId(), deck);
        return "redirect:/user/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteDeck(@ModelAttribute Deck deck, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        deckService.deleteDeck(deck.getId());

        return "redirect:/user/profile";
    }

}