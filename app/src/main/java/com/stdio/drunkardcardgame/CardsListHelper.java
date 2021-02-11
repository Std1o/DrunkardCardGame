package com.stdio.drunkardcardgame;

import java.util.ArrayList;

public class CardsListHelper {

    public static final String DIAMOND = "diamond";
    public static final String CLUB = "club";
    public static final String HEART = "heart";
    public static final String SPADE = "spade";

    public ArrayList<CardModel> getCards() {
        ArrayList<CardModel> cards = new ArrayList<>();
        cards.add(new CardModel(R.drawable.six_diamond, 1, DIAMOND));
        cards.add(new CardModel(R.drawable.six_club, 1, CLUB));
        cards.add(new CardModel(R.drawable.six_heart, 1, HEART));
        cards.add(new CardModel(R.drawable.six_spade, 1, SPADE));

        cards.add(new CardModel(R.drawable.seven_diamond, 2, DIAMOND));
        cards.add(new CardModel(R.drawable.seven_club, 2, CLUB));
        cards.add(new CardModel(R.drawable.seven_heart, 2, HEART));
        cards.add(new CardModel(R.drawable.seven_spade, 2, SPADE));

        cards.add(new CardModel(R.drawable.eight_diamond, 3, DIAMOND));
        cards.add(new CardModel(R.drawable.eight_club, 3, CLUB));
        cards.add(new CardModel(R.drawable.eight_heart, 3, HEART));
        cards.add(new CardModel(R.drawable.eight_spade, 3, SPADE));

        cards.add(new CardModel(R.drawable.nine_diamond, 4, DIAMOND));
        cards.add(new CardModel(R.drawable.nine_club, 4, CLUB));
        cards.add(new CardModel(R.drawable.nine_heart, 4, HEART));
        cards.add(new CardModel(R.drawable.nine_spade, 4, SPADE));

        cards.add(new CardModel(R.drawable.ten_diamond, 5, DIAMOND));
        cards.add(new CardModel(R.drawable.ten_club, 5, CLUB));
        cards.add(new CardModel(R.drawable.ten_heart, 5, HEART));
        cards.add(new CardModel(R.drawable.ten_spade, 5, SPADE));

        cards.add(new CardModel(R.drawable.jack_diamond, 6, DIAMOND));
        cards.add(new CardModel(R.drawable.jack_club, 6, CLUB));
        cards.add(new CardModel(R.drawable.jack_heart, 6, HEART));
        cards.add(new CardModel(R.drawable.jack_spade, 6, SPADE));

        cards.add(new CardModel(R.drawable.queen_diamond, 7, DIAMOND));
        cards.add(new CardModel(R.drawable.queen_club, 7, CLUB));
        cards.add(new CardModel(R.drawable.queen_heart, 7, HEART));
        cards.add(new CardModel(R.drawable.queen_spade, 7, SPADE));

        cards.add(new CardModel(R.drawable.king_diamond, 8, DIAMOND));
        cards.add(new CardModel(R.drawable.king_club, 8, CLUB));
        cards.add(new CardModel(R.drawable.king_heart, 8, HEART));
        cards.add(new CardModel(R.drawable.king_spade, 8, SPADE));

        cards.add(new CardModel(R.drawable.ace_diamond, 9, DIAMOND));
        cards.add(new CardModel(R.drawable.ace_club, 9, CLUB));
        cards.add(new CardModel(R.drawable.ace_heart, 9, HEART));
        cards.add(new CardModel(R.drawable.ace_spade, 9, SPADE));
        return cards;
    }
}
