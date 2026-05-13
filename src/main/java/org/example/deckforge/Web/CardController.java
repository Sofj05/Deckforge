package org.example.deckforge.Web;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

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
    
}
