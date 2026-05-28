package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.DeckService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/decks")
public class DeckController {

    @Autowired
    private CardService cardService;
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

    @GetMapping("/{deckId}/addCards")
    public String addCardsToDeck(
            @PathVariable int deckId,
            HttpSession session,
            Model model) {
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        User loggedInUser = AuthHelper.getLoggedIn(session);
        Deck deck = deckService.getDeckById(deckId);
        if (deck == null || !deck.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/user/profile";
        }
        List<Card> userCards = cardService.getCardsByUser(loggedInUser);

        model.addAttribute("deck", deck);
        model.addAttribute("userCards", userCards);

        return "deck/addCardsToDeck";
    }

    @PostMapping("/{deckId}/addCards")
    public String addCardToDeck(
            @PathVariable int deckId,
            @ModelAttribute Card card,
            HttpSession session) {

        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        Deck deck = deckService.getDeckById(deckId);
        if (deck == null || !deck.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/user/profile";
        }

        deckService.addCardToDeck(deckId, card.getId(), card.getQuantity());
        return "redirect:/decks/" + deckId + "/addCards";
    }


    @GetMapping("/{deckId}")
    public String viewDeck(@PathVariable int deckId, HttpSession session, Model model) {
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        User loggedInUser = AuthHelper.getLoggedIn(session);
        Deck deck = deckService.getDeckById(deckId);
        if (deck == null || !deck.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/user/profile";
        }
        List<Card> cards = deckService.getCardsInDeck(deckId);
        int totalCards = cards.stream().mapToInt(Card::getQuantity).sum();

        model.addAttribute("deck", deck);
        model.addAttribute("cards", cards);
        model.addAttribute("totalCards", totalCards);

        return "deck/deck";
    }

    @PostMapping("/{deckId}/removeCard")
    public String removeCardFromDeck(
            @PathVariable int deckId,
            @ModelAttribute Card card,
            HttpSession session) {

        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        Deck deck = deckService.getDeckById(deckId);
        if (deck == null || !deck.getUserId().equals(loggedInUser.getId())) {
            return "redirect:/user/profile";
        }

        deckService.removeCardFromDeck(deckId, card.getId(), card.getQuantity());
        return "redirect:/decks/" + deckId;
    }

    @PostMapping("/update")
    public String updateDeck(@ModelAttribute Deck deck, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        deck.setUserId(user.getId());
        deckService.updateDeck(deck.getId(), deck);
        return "redirect:/user/profile";
    }

    @PostMapping("/delete/{id}")
    public String deleteDeck(@PathVariable int id) {
        deckService.deleteDeck(id);

        return "redirect:/user/profile";
    }

}