package org.example.deckforge.Domain;

import org.example.deckforge.Domain.Enums.Decktype;

import java.util.List;

public class Deck {
    private int id;
    private String name;
    private Decktype format;
    private List<Card> cards;
    private Integer userId;

    public Deck(){}

    public Deck(String name, Decktype format, List<Card> cards){
        this.name = name;
        this.format = format;
        this.cards = cards;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){return id;}

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setFormat(Decktype format){
        this.format = format;
    }
    public Decktype getFormat(){
        return format;
    }

    public void setCards(List<Card> cards){
        this.cards = cards;
    }
    public List<Card> getCards(){
        return cards;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }
    public Integer getUserId(){
        return userId;
    }

}
