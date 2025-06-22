package com.example.quizee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    TextView scoreText;
    Button retryBtn, homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        scoreText = findViewById(R.id.scoreText);
        retryBtn = findViewById(R.id.retryBtn);
        homeBtn = findViewById(R.id.homeBtn);

        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("total", 0);

        scoreText.setText("Your Score: " + score + " / " + total);

        retryBtn.setOnClickListener(view -> {
            finish(); // Just finish ResultActivity, QuizActivity will restart automatically if needed
        });

        homeBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}