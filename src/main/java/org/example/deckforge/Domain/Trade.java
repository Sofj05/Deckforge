package org.example.deckforge.Domain;

import org.example.deckforge.Domain.Enums.TradeStatus;

public class Trade {

    private int tradeId;
    private User fromUser;
    private User toUser;
    private Card offeredCard;
    private Card requestedCard;
    private TradeStatus status;

    public Trade() {}

    public Trade(User fromUser, User toUser, Card offeredCard, Card requestedCard, TradeStatus status) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.offeredCard = offeredCard;
        this.requestedCard = requestedCard;
        this.status = status;
    }

    // ID
    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    // From user
    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    // To user
    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    // Offered card
    public Card getOfferedCard() {
        return offeredCard;
    }

    public void setOfferedCard(Card offeredCard) {
        this.offeredCard = offeredCard;
    }

    // Requested card
    public Card getRequestedCard() {
        return requestedCard;
    }

    public void setRequestedCard(Card requestedCard) {
        this.requestedCard = requestedCard;
    }

    // Status
    public TradeStatus getStatus() {
        return status;
    }

    public void setStatus(TradeStatus status) {
        this.status = status;
    }
}
