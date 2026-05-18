package org.example.deckforge.Web;

import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Mana;
import org.example.deckforge.Domain.Enums.Rarity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.UserService;

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
            @RequestParam Mana mana,
            @RequestParam String nameOfSet,
            @RequestParam Rarity rarity,
            @RequestParam String ruleText,
            @RequestParam String ability,
            @RequestParam("image") MultipartFile imageFile
    ) throws Exception {

        // Gem billede i /images/
        String fileName = imageFile.getOriginalFilename();
        Path imagePath = Paths.get("src/main/resources/static/images/" + fileName);
        Files.write(imagePath, imageFile.getBytes());

        // Gem i database
        cardService.createCard(
                card_name,
                cardType,
                mana,
                nameOfSet,
                rarity,
                ruleText,
                ability,
                fileName
        );

        return "redirect:/cards"; // tilbage til kortoversigten
    }
}
