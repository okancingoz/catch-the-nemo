package com.okancingoz.catchthenemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GameSetupActivity extends AppCompatActivity {
    EditText txtNickname;
    String nickname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        txtNickname = findViewById(R.id.txtNickname);
        nickname = "";
    }

    public void switchToGame(int imgDisplaySpeed) {
        nickname = txtNickname.getText().toString();
        if (!nickname.matches("")) {
            Intent intent = new Intent(GameSetupActivity.this, GameplayActivity.class);
            intent.putExtra("nickname", nickname);
            intent.putExtra("imgDisplaySpeed", imgDisplaySpeed);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please enter your nickname!", Toast.LENGTH_SHORT).show();
        }
    }

    public void playEasyMode(View view) {
        switchToGame(650);
    }

    public void playMediumMode(View view) {
        switchToGame(500);
    }

    public void playHardMode(View view) {
        switchToGame(400);
    }
}