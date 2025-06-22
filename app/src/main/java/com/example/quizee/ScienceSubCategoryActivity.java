package com.example.quizee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ScienceSubCategoryActivity extends AppCompatActivity {

    Button btnChemistry, btnPhysics, btnBiology;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_sub_category);

        btnChemistry = findViewById(R.id.btnChemistry);
        btnPhysics = findViewById(R.id.btnPhysics);
        btnBiology = findViewById(R.id.btnBiology);

        btnChemistry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuiz("Chemistry");
            }
        });

        btnPhysics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuiz("Physics");
            }
        });

        btnBiology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openQuiz("Biology");
            }
        });
    }

    private void openQuiz(String topic) {
        Intent intent = new Intent(ScienceSubCategoryActivity.this, QuizActivity.class);
        intent.putExtra("topic", topic);
        startActivity(intent);
    }
}