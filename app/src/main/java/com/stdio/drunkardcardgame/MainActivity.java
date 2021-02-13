package com.stdio.drunkardcardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.stdio.drunkardcardgame.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                startActivity(new Intent(this, GameActivity.class));
                break;
            case R.id.btnPrivacyPolicy:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
        }
    }
}