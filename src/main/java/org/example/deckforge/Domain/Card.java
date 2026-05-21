package org.example.deckforge.Domain;

import org.example.deckforge.Domain.Enums.Cardtype;
import org.example.deckforge.Domain.Enums.Rarity;

public class Card {
    private int id;
    private String name;
    private Cardtype cardtype;
    private String mana;
    private String nameOfSet;
    private Rarity rarity;
    private String ruleText;
    private String image;
    private String ability;
    private int quantity;


    public Card(){}

    public Card(String name, Cardtype cardtype, String mana, String nameOfSet, Rarity rarity, String ruleText, String image, String ability) {
        this.name = name;
        this.cardtype = cardtype;
        this.mana = mana;
        this.nameOfSet = nameOfSet;
        this.rarity = rarity;
        this.ruleText = ruleText;
        this.image = image;
        this.ability = ability;
        this.quantity = 1;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setCardtype(Cardtype cardtype){
        this.cardtype = cardtype;
    }
    public Cardtype getCardtype(){
        return cardtype;
    }

    public void setMana(String mana){
        this.mana = mana;
    }
    public String getMana(){
        return mana;
    }

    public void setNameOfSet(String nameOfSet){
        this.nameOfSet = nameOfSet;
    }
    public String getNameOfSet(){
        return nameOfSet;
    }

    public void setRarity(Rarity rarity){
        this.rarity = rarity;
    }
    public Rarity getRarity(){
        return rarity;
    }

    public void setRuleText(String ruleText){
        this.ruleText = ruleText;
    }
    public String getRuleText(){
        return ruleText;
    }

    public void setImage(String image){
        this.image = image;
    }
    public String getImage(){
        return image;
    }

    public void setAbility(String ability) { this.ability = ability; }
    public String getAbility() { return ability; }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
