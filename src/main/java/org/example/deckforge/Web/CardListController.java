package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Deck;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/card")
public class CardListController {

    private final CardService cardService;

    public CardListController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/cardList")
    public String cardList(Model model) {

        model.addAttribute("featuredCards", cardService.getFirstThreeCards());
        model.addAttribute("allCards", cardService.getAllCards());
        System.out.println(cardService.getAllCards());
        return "card/cardList"; // Viser html-filen
    }

}
