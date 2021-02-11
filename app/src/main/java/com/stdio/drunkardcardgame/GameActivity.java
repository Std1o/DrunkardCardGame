package com.stdio.drunkardcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toast.makeText(this, "" + new CardsListHelper().getCards().size(), Toast.LENGTH_SHORT).show();
    }
}