package com.stdio.drunkardcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private List<CardModel> playerCards = new ArrayList<>();
    private List<CardModel> aiCards = new ArrayList<>();
    private ImageView ivAICard, ivPlayerCard;
    private TextView tvStatus, tvPlayerCardCount, tvAICardCount;
    private final String PLAYER_CARD_COUNT_FRAGMENT = "Кол-во карт\nу игрока: ";
    private final String AI_CARD_COUNT_FRAGMENT = "Кол-во карт\nу компьютера: ";
    private final String STATUS_FRAGMENT = "Статус: ";
    private final String YOU_TAKE_AWAY = "Вы забираете карту";
    private final String AI_TAKES_AWAY = "Компьютер забирает карту";
    private final String STATUS_WAITING_FOR_YOU = "ожидается ваш ход";
    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        try {
            distributeCards(new CardsListHelper().getCards(), 18);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        initViews();
        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(0).getResource()));
    }

    public void onClick(View v) {
        makeAMove(position);
    }

    private void makeAMove(int pos) {
        ivPlayerCard.setVisibility(View.VISIBLE);
        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(pos).getResource()));
        ivPlayerCard.setImageDrawable(getResources().getDrawable(playerCards.get(pos).getResource()));
        int playerCardWeight = playerCards.get(pos).getWeight();
        int aiCardWeight = aiCards.get(pos).getWeight();
        System.out.println("player: " + playerCardWeight + " ai: " + aiCardWeight);
        if (playerCardWeight > aiCardWeight) {
            tvStatus.setText(STATUS_FRAGMENT + YOU_TAKE_AWAY);
            CardModel tmpCardModel = playerCards.get(pos);
            playerCards.remove(pos);
            playerCards.add(tmpCardModel);
            playerCards.add(aiCards.get(pos));
            if (position == 1) {
                CardModel tmpCardModel1 = playerCards.get(0);
                playerCards.remove(0);
                playerCards.add(tmpCardModel1);
                playerCards.add(aiCards.get(0));
                aiCards.remove(0);
                position = 0;
            }
            aiCards.remove(pos);
            updateUI(playerCardWeight, aiCardWeight);
        } else if (aiCardWeight > playerCardWeight) {
            tvStatus.setText(STATUS_FRAGMENT + AI_TAKES_AWAY);
            CardModel tmpCardModel = aiCards.get(pos);
            aiCards.remove(pos);
            aiCards.add(tmpCardModel);
            aiCards.add(playerCards.get(pos));
            if (position == 1) {
                CardModel tmpCardModel1 = aiCards.get(0);
                aiCards.remove(0);
                aiCards.add(tmpCardModel1);
                aiCards.add(playerCards.get(0));
                playerCards.remove(0);
                position = 0;
            }
            playerCards.remove(pos);
            updateUI(playerCardWeight, aiCardWeight);
        } else {
            solveConflict();
            position = 1;
        }

    }

    private void solveConflict() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(0).getResource()));
                        ivAICard.setVisibility(View.VISIBLE);
                        tvStatus.setText(STATUS_FRAGMENT + STATUS_WAITING_FOR_YOU);
                    }
                });
            }
        });
        thread.start();
    }

    private void updateUI(int playerCardWeight, int aiCardWeight) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPlayerCardCount.setText(PLAYER_CARD_COUNT_FRAGMENT + playerCards.size());
                        tvAICardCount.setText(AI_CARD_COUNT_FRAGMENT + aiCards.size());
                        if (playerCardWeight > aiCardWeight) {
                            ivAICard.setVisibility(View.INVISIBLE);
                        } else {
                            ivPlayerCard.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(0).getResource()));
                        ivAICard.setVisibility(View.VISIBLE);
                        ivPlayerCard.setVisibility(View.INVISIBLE);
                        tvStatus.setText(STATUS_FRAGMENT + STATUS_WAITING_FOR_YOU);
                    }
                });
            }
        });
        thread.start();
    }

    private void initViews() {
        ivAICard = findViewById(R.id.ivAICard);
        ivPlayerCard = findViewById(R.id.ivPlayerCard);
        tvStatus = findViewById(R.id.tvStatus);
        tvPlayerCardCount = findViewById(R.id.tvPlayerCardCount);
        tvAICardCount = findViewById(R.id.tvAICardCount);
    }

    private void distributeCards(ArrayList<CardModel> lst, int n) throws CloneNotSupportedException {
        List<CardModel> copy = new ArrayList<>(lst);
        Collections.shuffle(copy);
        playerCards = cloneList(copy.subList(0, n));
        aiCards = cloneList(copy.subList(n, copy.size()));
    }

    public static List<CardModel> cloneList(List<CardModel> list) throws CloneNotSupportedException, NullPointerException {
        List<CardModel> clone = new ArrayList<>(list.size());
        for (CardModel item : list) clone.add(item.clone());
        return clone;
    }
}