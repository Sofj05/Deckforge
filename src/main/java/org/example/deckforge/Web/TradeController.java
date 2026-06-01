package org.example.deckforge.Web;

import jakarta.servlet.http.HttpSession;
import org.example.deckforge.Application.CardService;
import org.example.deckforge.Application.TradeService;
import org.example.deckforge.Application.UserService;
import org.example.deckforge.Application.Validation.AuthHelper;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CardService cardService;


    // Viser alle trades for den aktuelle bruger
    @GetMapping("/myTrades")
    public String myTrades(Model model, HttpSession session) {

        User loggedIn = AuthHelper.getLoggedIn(session);

        model.addAttribute("incomingTrades", tradeService.getIncomingTrades(loggedIn.getId()));
        model.addAttribute("outgoingTrades", tradeService.getOutgoingTrades(loggedIn.getId()));

        return "trade/myTrades";
    }


    // Formular til at foreslå en trade
    @GetMapping("/propose")
    public String proposeTradeForm(@RequestParam int toUserId, Model model, HttpSession session) {

        User loggedIn = AuthHelper.getLoggedIn(session);
        User otherUser = userService.getUserById(toUserId);

        model.addAttribute("myCards", cardService.getCardsByUser(loggedIn));
        model.addAttribute("theirCards", cardService.getCardsByUser(otherUser));
        model.addAttribute("otherUser", otherUser);

        return "trade/proposeTrade";
    }


    // POST: opret trade
    @PostMapping("/propose")
    public String proposeTrade(
            @RequestParam int toUserId,
            @RequestParam int offeredCardId,
            @RequestParam int requestedCardId,
            HttpSession session) {

        User fromUser = AuthHelper.getLoggedIn(session);
        User toUser = userService.getUserById(toUserId);

        Card offered = cardService.getCardById(offeredCardId);
        Card requested = cardService.getCardById(requestedCardId);

        tradeService.proposeTrade(fromUser, toUser, offered, requested);

        return "redirect:/trade/myTrades";
    }


    // Accepter trade
    @PostMapping("/accept")
    public String acceptTrade(@RequestParam int tradeId) {
        tradeService.acceptTrade(tradeId);
        return "redirect:/trade/myTrades";
    }


    // Afvis trade
    @PostMapping("/decline")
    public String declineTrade(@RequestParam int tradeId) {
        tradeService.declineTrade(tradeId);
        return "redirect:/trade/myTrades";
    }
}