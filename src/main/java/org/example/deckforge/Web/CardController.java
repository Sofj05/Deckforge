package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Rarity;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;
    private final UserService userService;

    @Autowired
    public CardController(CardService cardService, UserService userService){
        this.cardService = cardService;
        this.userService = userService;
    }

    // Viser formularen til at oprette et nyt kort
    @GetMapping("/new")
    public String showCreateCardForm() {
        return "createNewCard"; // HTML-filen
    }

    // Modtager POST og opretter kortet
    @PostMapping("/create")
    public String createCard(
            @RequestParam String card_name,
            @RequestParam Cardtype cardType,
            @RequestParam String mana,
            @RequestParam String nameOfSet,
            @RequestParam Rarity rarity,
            @RequestParam String ruleText,
            @RequestParam String ability,
            @RequestParam("image") MultipartFile imageFile,
            HttpSession session
    ) throws Exception {
        if (!AuthHelper.isLoggedIn(session)){
            return "redirect:/user/login";
        }
        User loggedUser = (User) session.getAttribute("loggedUser");
        Card newCard = new Card();
        newCard.setName(card_name);
        newCard.setCardtype(cardType);
        newCard.setMana(mana);
        newCard.setNameOfSet(nameOfSet);
        newCard.setRarity(rarity);
        newCard.setRuleText(ruleText);
        newCard.setAbility(ability);

        // Gem billede i /images/
        String fileName = imageFile.getOriginalFilename();
        Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);
        Files.write(imagePath, imageFile.getBytes());

        // Gem i database
        cardService.addNewCardAsAdmin(newCard, loggedUser);


        return "redirect:/card/cardList"; // tilbage til kortoversigten
    }
}

