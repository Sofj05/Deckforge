package org.example.deckforge.Web;

import org.example.deckforge.Application.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

        return "card/cardList"; // Viser html-filen
    }
}
