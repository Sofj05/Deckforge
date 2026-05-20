package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Application.Validation.ValidationException;
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
            return "redirect:/user/profile";
        }
        return "user/login"; // Returnerer html filen "login.html"
    }

    @PostMapping("/login") // Modtager login-formularen
    public String loginUser(
            @RequestParam String username, // Adgangskode fra HTML
            @RequestParam String password, // Brugernavn fra HTML
            HttpSession session, // Session til at gemme bruger i
            Model model) {

    try {

        User loggedInUser = userService.login(username, password); // Tjekker login

        if (loggedInUser == null) { // Hvis login fejler
            model.addAttribute("error", "Forkert brugernavn eller adgangskode");
            return "user/login"; // Gå tilbage til login
        }

        session.setAttribute("loggedInUser", loggedInUser); // Gem bruger i session
        return "redirect:/user/profile";       // Send til profil‑side
    }catch (ValidationException e){
        model.addAttribute("error", e.getMessage());
        return "user/login";
    }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String home(HttpSession session, Model model){
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cards", cardService.getCardsByUser(loggedInUser));
        return "user/profile";
    }

    @GetMapping("/cards")
    public String userCards(HttpSession session, Model model) {

        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }

        User loggedInUser = AuthHelper.getLoggedIn(session);

        model.addAttribute("loggedInUser", loggedInUser);
        model.addAttribute("cards", cardService.getCardsByUser(loggedInUser));

        return "user/usersCards";
    }

    @GetMapping("/updateUser")
    public String updateUser(HttpSession session, Model model) {
        if (!AuthHelper.isLoggedIn(session)) {
            return "redirect:/user/login";
        }
        User loggedInUser = AuthHelper.getLoggedIn(session);
        model.addAttribute("loggedInUser", loggedInUser);
        return "user/updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam(required = false) String currentPassword,
                             @RequestParam(required = false) String newPassword,
                             HttpSession session,
                             Model model){
        User user = AuthHelper.getLoggedIn(session);

        user.setUsername(username);
        user.setEmail(email);

        //Bruges til at fortælle om brugeren har ændret deres kode eller ej
        boolean updPass = newPassword != null && !newPassword.isBlank();

        try {
            userService.updateUser(user, currentPassword, newPassword, updPass);
            model.addAttribute("success", "Profil opdateret!");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/user/profile";
    }


}