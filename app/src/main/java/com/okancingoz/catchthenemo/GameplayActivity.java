package com.okancingoz.catchthenemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameplayActivity extends AppCompatActivity {
    TextView txtDuration, txtScore, txtLastScore, txtNickname;
    String nickname;
    ImageView[] imgs;
    int score, duration, imgDisplaySpeed;
    Runnable runnable;
    Handler handler;
    Intent intent;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgs = new ImageView[9];
        for (int i = 0; i < 9; i++) {
            int resId = getResources().getIdentifier("img" + (i + 1), "id", getPackageName());
            imgs[i] = findViewById(resId);
        }
        txtDuration = findViewById(R.id.txtDuration);
        txtScore = findViewById(R.id.txtScore);
        txtLastScore = findViewById(R.id.txtLastScore);
        txtNickname = findViewById(R.id.txtNickname);

        intent = getIntent();
        nickname = intent.getStringExtra("nickname");
        imgDisplaySpeed = intent.getIntExtra("imgDisplaySpeed",500);
        txtNickname.setText(nickname);

        sharedPreferences = this.getSharedPreferences("com.okancingoz.catchthenemo", Context.MODE_PRIVATE);
        int lastScore = sharedPreferences.getInt("lastScore",0);
        txtLastScore.setText("Last Score:"+lastScore);
        score = 0;
        duration = 15;
        timer();

    }

    public void timer() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                showImg(imgDisplaySpeed);
                txtDuration.setText("Time:" + duration);
                duration--;
                if (duration <= 5) {
                    txtDuration.setTextColor(Color.parseColor("#ff0000"));
                }
                txtDuration.setText("Time:" + duration);
                handler.postDelayed(runnable, 1000);
                if (duration == 0) {
                    handler.removeCallbacks(runnable);
                    txtDuration.setTextColor(Color.parseColor("#3f51b5"));
                    sharedPreferences.edit().putInt("lastScore",score).apply();
                    score = 0;
                    duration = 15;
                    alert();
                }
            }
        };
        handler.post(runnable);
    }

    public void alert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(GameplayActivity.this);
        alert.setMessage("Restart the game?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timer();
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(GameplayActivity.this,GameSetupActivity.class);
                startActivity(intent);
            }
        });
        alert.show();
    }

    public void showImg(int imgDisplaySpeed) {
        new CountDownTimer(imgDisplaySpeed, imgDisplaySpeed) {
            int randomImgNum = new Random().nextInt(9);

            @Override
            public void onTick(long millisUntilFinished) {
                imgs[randomImgNum].setVisibility(View.VISIBLE);
                imgs[randomImgNum].setClickable(true);
            }

            @Override
            public void onFinish() {
                imgs[randomImgNum].setVisibility(View.INVISIBLE);
                imgs[randomImgNum].setClickable(false);
            }
        }.start();
    }

    public void increaseScore(View view) {
        score += 10;
        txtScore.setText("Score:" + score);
    }
}