package com.example.quizee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnScience, btnMath, btnHistory, btnGK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScience = findViewById(R.id.btnScience);
        btnMath = findViewById(R.id.btnMath);
        btnHistory = findViewById(R.id.btnHistory);
        btnGK = findViewById(R.id.btnGK);

        btnScience.setOnClickListener(view -> startQuiz("science"));
        btnMath.setOnClickListener(view -> startQuiz("math"));
        btnHistory.setOnClickListener(view -> startQuiz("history"));
        btnGK.setOnClickListener(view -> startQuiz("gk"));
    }

    private void startQuiz(String subject) {
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        intent.putExtra("subject", subject);
        startActivity(intent);
    }
}