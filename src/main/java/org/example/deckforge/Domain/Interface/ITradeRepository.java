package org.example.deckforge.Domain.Interface;

import org.example.deckforge.Domain.Enums.TradeStatus;
import org.example.deckforge.Domain.Trade;
import org.example.deckforge.Domain.User;

import java.util.List;

public interface ITradeRepository {
    void createTrade(Trade trade);
    void updateTradeStatus(int tradeId, TradeStatus tradestatus);
    List<Trade> getTradesForUser(User user);
    Trade getTrade(int tradeId);
    List<Trade> getIncomingTrades(int id);
    List<Trade> getOutgoingTrades(int id);
    Trade getTradeById(int id);
}
