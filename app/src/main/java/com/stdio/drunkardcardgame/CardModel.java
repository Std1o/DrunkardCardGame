package com.stdio.drunkardcardgame;

import androidx.annotation.NonNull;

public class CardModel implements Cloneable{

    @NonNull
    @Override
    public CardModel clone() throws CloneNotSupportedException {
        return (CardModel) super.clone();
    }

    private int resource, weight;
    String suit;

    public CardModel(int resource, int weight, String suit) {
        this.resource = resource;
        this.weight = weight;
        this.suit = suit;
    }

    public int getResource() {
        return resource;
    }

    public int getWeight() {
        return weight;
    }

    public String getSuit() {
        return suit;
    }
}
