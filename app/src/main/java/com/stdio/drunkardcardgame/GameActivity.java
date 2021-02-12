package com.stdio.drunkardcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private List<CardModel> playerCards = new ArrayList<>();
    private List<CardModel> aiCards = new ArrayList<>();
    private ImageView ivAICard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        distributeCards(new CardsListHelper().getCards(), 18);
        for (CardModel cardModel : playerCards) {
            System.out.println(cardModel.getSuit() + " " + cardModel.getWeight());
        }
        ivAICard = findViewById(R.id.ivAICard);
        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(0).getResource()));
    }

    private void distributeCards(ArrayList<CardModel> lst, int n) {
        List<CardModel> copy = new ArrayList<>(lst);
        Collections.shuffle(copy);
        playerCards = copy.subList(0, n);
        aiCards = copy.subList(n, copy.size());
    }
}