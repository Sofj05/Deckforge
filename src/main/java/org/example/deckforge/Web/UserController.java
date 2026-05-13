package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user")
public class UserController{

public UserController(UserService userService){
        this.userService = userService;
}

    private final UserService userService;

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
        return "redirect:/user/home"; // Går tilbage til registerings formen
    }

    // ---- Login af bruger ---- //

    @GetMapping("/login") // Viser login siden
    public String login() {
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
        return "redirect:/user/home";       // Send til home‑side
    }



    @GetMapping("/home")
    public String home(HttpSession session, Model model){
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/user/login";
        }
        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("Cards", cardService.getCardsByUser(loggedinUser));
        return "/home";
    }

}

