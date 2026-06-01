package org.example.deckforge.Application;

import org.example.deckforge.Application.Validation.Validation;
import org.example.deckforge.Application.Validation.ValidationException;
import org.example.deckforge.Domain.Card;
import org.example.deckforge.Domain.Enums.TradeStatus;
import org.example.deckforge.Domain.Interface.ITradeRepository;
import org.example.deckforge.Domain.Trade;
import org.example.deckforge.Domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private ITradeRepository tradeRepo;
    private Validation validation;

    @Autowired
    public TradeService(ITradeRepository tradeRepo, Validation validation) {
        this.tradeRepo = tradeRepo;
        this.validation = validation;
    }

    public void proposeTrade(User fromUser, User toUser, Card offered, Card requested) {
        validation.validateUser(fromUser);
        validation.validateUser(toUser);
        validation.validateCard(offered);
        validation.validateCard(requested);

        Trade trade = new Trade();
        trade.setFromUser(fromUser);
        trade.setToUser(toUser);
        trade.setOfferedCard(offered);
        trade.setRequestedCard(requested);
        trade.setStatus(TradeStatus.PENDING);

        tradeRepo.createTrade(trade);
    }

    public void acceptTrade(int tradeId) {
        tradeRepo.updateTradeStatus(tradeId, TradeStatus.ACCEPTED); // Her skal der tilføjes logik til at bytte kort i databasen
    }

    public void declineTrade(int tradeId) {
        tradeRepo.updateTradeStatus(tradeId, TradeStatus.DECLINED);
    }

    public List<Trade> getIncomingTrades(int Id) {
        return tradeRepo.getIncomingTrades(Id);
    }

    public List<Trade> getOutgoingTrades(int Id) {
        return tradeRepo.getOutgoingTrades(Id);
    }
}
