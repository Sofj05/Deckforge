package org.example.deckforge.Application.Validation;
import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Domain.Enums.Role;
import org.example.deckforge.Domain.User;

/**
 * Statisk hjælpeklasse til session-baseret adgangskontrol.
 * Bruges af controllers til at tjekke om en bruger er logget ind,
 * har admin-rolle, eller forsøger at tilgå sine egne data.
 */
public class AuthHelper {

    private static final String LOGGED_IN_USER = "loggedInUser";

    public static User getLoggedIn(HttpSession session) {
        return (User) session.getAttribute(LOGGED_IN_USER);
    }

    public static boolean isLoggedIn(HttpSession session) {
        return getLoggedIn(session) != null;
    }

    public static boolean isAdmin(HttpSession session) {
        User user = getLoggedIn(session);
        return user != null && user.getRole() == Role.ADMIN;
    }

    public static boolean isSelf(HttpSession session, int id) {
        User user = getLoggedIn(session);
        return user != null && user.getId() == id;
    }

    public static boolean isAdminOrSelf(HttpSession session, int id) {
        return isAdmin(session) || isSelf(session, id);
    }
}