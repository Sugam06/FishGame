package com.easytechh.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel=findViewById(R.id.scoreLabel);
        TextView highScoreLabel=findViewById(R.id.highScoreLabel);

        int score=getIntent().getIntExtra("Score",0);
        scoreLabel.setText(score + "");

        SharedPreferences settings= getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE",0);

        if (score > highScore) {
            highScoreLabel.setText("High Score : " + score);

            // Update High Score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE ", score);
            editor.commit();

        } else {
            highScoreLabel.setText("High Score : " + highScore);

        }
    }

    public void startGameAgain(View view) {

        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }


}
