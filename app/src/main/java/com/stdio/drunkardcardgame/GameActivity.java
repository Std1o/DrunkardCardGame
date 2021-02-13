package com.stdio.drunkardcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    private List<CardModel> playerCards = new ArrayList<>();
    private List<CardModel> aiCards = new ArrayList<>();
    private ImageView ivAICard, ivPlayerCard, ivAIReverseSide, ivPlayerReverseSide;
    private TextView tvStatus, tvPlayerCardCount, tvAICardCount;
    private final String PLAYER_CARD_COUNT_FRAGMENT = "Кол-во карт\nу игрока: ";
    private final String AI_CARD_COUNT_FRAGMENT = "Кол-во карт\nу компьютера: ";
    private final String STATUS_FRAGMENT = "Статус: ";
    private final String YOU_TAKE_AWAY = "Вы забираете карту";
    private final String AI_TAKES_AWAY = "Компьютер забирает карту";
    private final String STATUS_WAITING_FOR_YOU = "ожидается ваш ход";
    private final String STATUS_CONFLICT = "возник спор";
    private int position = 0;
    Button button;

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
        if (button.getText().equals("Сыграть заново")) recreate();
        else {
            if (aiCards.size() <= position) {
                giveCardsToPlayer(1, 0);
            } else if (playerCards.size() <= position) {
                giveCardsToAI(0, 1);
            } else {
                button.setEnabled(false);
                makeAMove();
            }
        }
    }

    private void win() {
        tvStatus.setText("Вы выиграли!");
        tvStatus.setTextColor(Color.GREEN);
        button.setEnabled(true);
        button.setText("Сыграть заново");
        ivPlayerCard.setVisibility(View.INVISIBLE);
        ivAICard.setVisibility(View.INVISIBLE);
        ivAIReverseSide.setVisibility(View.INVISIBLE);
    }

    private void lose() {
        tvStatus.setText("Вы проиграли");
        tvStatus.setTextColor(Color.RED);
        button.setEnabled(true);
        button.setText("Сыграть заново");
        ivPlayerCard.setVisibility(View.INVISIBLE);
        ivAICard.setVisibility(View.INVISIBLE);
        ivPlayerReverseSide.setVisibility(View.INVISIBLE);
    }

    private void makeAMove() {
        ivPlayerCard.setVisibility(View.VISIBLE);
        ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(position).getResource()));
        ivPlayerCard.setImageDrawable(getResources().getDrawable(playerCards.get(position).getResource()));
        int playerCardWeight = playerCards.get(position).getWeight();
        int aiCardWeight = aiCards.get(position).getWeight();
        if (playerCardWeight > aiCardWeight) {
            giveCardsToPlayer(playerCardWeight, aiCardWeight);
        } else if (aiCardWeight > playerCardWeight) {
            giveCardsToAI(playerCardWeight, aiCardWeight);
        } else {
            System.out.println(STATUS_FRAGMENT + STATUS_CONFLICT + "\n" + STATUS_WAITING_FOR_YOU);
            tvStatus.setText(STATUS_FRAGMENT + STATUS_CONFLICT + "\n" + STATUS_WAITING_FOR_YOU);
            position++;
            button.setEnabled(true);
        }
    }

    private void giveCardsToPlayer(int playerCardWeight, int aiCardWeight) {
        System.out.println(STATUS_FRAGMENT + YOU_TAKE_AWAY);
        tvStatus.setText(STATUS_FRAGMENT + YOU_TAKE_AWAY);
        CardModel tmpCardModel = playerCards.get(position);
        playerCards.remove(position);
        playerCards.add(tmpCardModel);
        if (aiCards.size() > position) playerCards.add(aiCards.get(position));
        if (position > 0) {
            for (int i = 0; i < position; i++) {
                CardModel tmpCardModel1 = playerCards.get(0);
                playerCards.remove(0);
                playerCards.add(tmpCardModel1);
                playerCards.add(aiCards.get(0));
                if (aiCards.size() != 0) aiCards.remove(0);
            }
            position = 0;
        }
        if (aiCards.size() != 0) aiCards.remove(position);
        updateUI(playerCardWeight, aiCardWeight);
    }

    private void giveCardsToAI(int playerCardWeight, int aiCardWeight) {
        System.out.println(STATUS_FRAGMENT + AI_TAKES_AWAY);
        tvStatus.setText(STATUS_FRAGMENT + AI_TAKES_AWAY);
        CardModel tmpCardModel = aiCards.get(position);
        aiCards.remove(position);
        aiCards.add(tmpCardModel);
        if (playerCards.size() > position) aiCards.add(playerCards.get(position));
        if (position > 0) {
            for (int i = 0; i < position; i++) {
                CardModel tmpCardModel1 = aiCards.get(0);
                aiCards.remove(0);
                aiCards.add(tmpCardModel1);
                aiCards.add(playerCards.get(0));
                if (playerCards.size() != 0) playerCards.remove(0);
            }
            position = 0;
        }
        if (playerCards.size() != 0) playerCards.remove(position);
        updateUI(playerCardWeight, aiCardWeight);
    }

    private void updateUI(int playerCardWeight, int aiCardWeight) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                moveCard(playerCardWeight, aiCardWeight);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                hideCard(playerCardWeight, aiCardWeight);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (playerCards.size() == 0) {
                            lose();
                        } else if (aiCards.size() == 0) {
                            win();
                        } else {
                            ivAICard.setImageDrawable(getResources().getDrawable(aiCards.get(0).getResource()));
                            ivAICard.setVisibility(View.VISIBLE);
                            ivPlayerCard.setVisibility(View.INVISIBLE);
                            tvStatus.setText(STATUS_FRAGMENT + STATUS_WAITING_FOR_YOU);
                            button.setEnabled(true);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    private void moveCard(int playerCardWeight, int aiCardWeight) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPlayerCardCount.setText(PLAYER_CARD_COUNT_FRAGMENT + playerCards.size());
                tvAICardCount.setText(AI_CARD_COUNT_FRAGMENT + aiCards.size());
                if (playerCardWeight > aiCardWeight) {
                    ivAICard.setVisibility(View.INVISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Path path = new Path();
                        path.arcTo(0f, 0f, 1000f, 0f, 102f, 190f, true);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(ivPlayerCard, View.X, View.Y, path);
                        animator.setDuration(1500);
                        animator.start();
                    }
                } else {
                    ivPlayerCard.setVisibility(View.INVISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Path path = new Path();
                        path.arcTo(0f, 0f, 1000f, 0f, 102f, 190f, true);
                        ObjectAnimator animator = ObjectAnimator.ofFloat(ivAICard, View.X, View.Y, path);
                        animator.setDuration(1500);
                        animator.start();
                    } else {
                        // Create animator without using curved path
                    }
                }
            }
        });
    }

    private void hideCard(int playerCardWeight, int aiCardWeight) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvPlayerCardCount.setText(PLAYER_CARD_COUNT_FRAGMENT + playerCards.size());
                tvAICardCount.setText(AI_CARD_COUNT_FRAGMENT + aiCards.size());
                if (playerCardWeight > aiCardWeight) {
                    ivPlayerCard.setVisibility(View.INVISIBLE);
                } else {
                    ivAICard.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void initViews() {
        ivAICard = findViewById(R.id.ivAICard);
        ivPlayerCard = findViewById(R.id.ivPlayerCard);
        ivAIReverseSide = findViewById(R.id.ivAIReverseSide);
        ivPlayerReverseSide = findViewById(R.id.ivPlayerReverseSide);
        tvStatus = findViewById(R.id.tvStatus);
        tvPlayerCardCount = findViewById(R.id.tvPlayerCardCount);
        tvAICardCount = findViewById(R.id.tvAICardCount);
        button = findViewById(R.id.button);
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