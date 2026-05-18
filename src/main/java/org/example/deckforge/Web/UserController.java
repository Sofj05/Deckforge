package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController{
    private final UserService userService;
    private final CardService cardService;


public UserController(UserService userService, CardService cardService){
    this.userService = userService;
    this.cardService = cardService;
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
            return "redirect:/user/home";
        }
        return "/user/login"; // Returnerer html filen "login.html"
    }

    @PostMapping("/login") // Modtager login-formularen
    public String loginUser(
            @RequestParam String username, // Adgangskode fra HTML
            @RequestParam String password, // Brugernavn fra HTML
            HttpSession session, // Session til at gemme bruger i
            Model model) {

        User loggedInUser = userService.login(username, password); // Tjekker login

        if (loggedInUser == null) { // Hvis login fejler
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "/user/login"; // Gå tilbage til login
        }

        session.setAttribute("loggedInUser", loggedInUser); // Gem bruger i session
        return "redirect:/user/profile";       // Send til home‑side
    }



    @GetMapping("/profil")
    public String home(HttpSession session, Model model){
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("Cards", cardService.getCardsByUser(loggedInUser));
        return "/user/profile";
    }

}