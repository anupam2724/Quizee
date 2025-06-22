package com.example.quizee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    TextView questionText;
    RadioGroup optionsGroup;
    RadioButton option1, option2, option3, option4;
    Button submitBtn;

    List<QuestionModel> questionList;
    int currentQuestionIndex = 0;
    int score = 0;
    String apiUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.questionText);
        optionsGroup = findViewById(R.id.optionsGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        submitBtn = findViewById(R.id.submitBtn);

        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");

        if (subject != null) {
            if (subject.equals("science")) {
                apiUrl = "https://opentdb.com/api.php?amount=10&category=17&type=multiple";
            } else if (subject.equals("math")) {
                apiUrl = "https://opentdb.com/api.php?amount=10&category=19&type=multiple";
            } else if (subject.equals("history")) {
                apiUrl = "https://opentdb.com/api.php?amount=10&category=23&type=multiple";
            } else if (subject.equals("gk")) {
                apiUrl = "https://opentdb.com/api.php?amount=10&category=9&type=multiple";
            } else {
                apiUrl = "https://opentdb.com/api.php?amount=10&type=multiple";
            }
        } else {
            apiUrl = "https://opentdb.com/api.php?amount=10&type=multiple";
        }

        questionList = new ArrayList<>();
        fetchQuestions();

        submitBtn.setOnClickListener(view -> {
            int selectedId = optionsGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedOption = findViewById(selectedId);
            String answer = selectedOption.getText().toString();
            String correctAnswer = questionList.get(currentQuestionIndex).getCorrectAnswer();

            if (answer.equals(correctAnswer)) {
                score++;
            }

            currentQuestionIndex++;

            if (currentQuestionIndex < questionList.size()) {
                loadQuestion();
            } else {
                Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
                resultIntent.putExtra("score", score);
                resultIntent.putExtra("total", questionList.size());
                startActivity(resultIntent);
                finish();
            }
        });
    }

    private void fetchQuestions() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");

                        for (int i = 0; i < results.length(); i++) {
                            JSONObject item = results.getJSONObject(i);
                            String question = android.text.Html.fromHtml(item.getString("question")).toString();
                            String correct = android.text.Html.fromHtml(item.getString("correct_answer")).toString();
                            JSONArray incorrect = item.getJSONArray("incorrect_answers");

                            List<String> options = new ArrayList<>();
                            options.add(correct);
                            for (int j = 0; j < incorrect.length(); j++) {
                                options.add(android.text.Html.fromHtml(incorrect.getString(j)).toString());
                            }

                            Collections.shuffle(options);

                            questionList.add(new QuestionModel(
                                    question,
                                    options.get(0),
                                    options.get(1),
                                    options.get(2),
                                    options.get(3),
                                    correct
                            ));
                        }

                        loadQuestion();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        questionText.setText("Failed to load questions.");
                    }
                },
                error -> {
                    error.printStackTrace();
                    questionText.setText("Error fetching questions.");
                });

        queue.add(request);
    }

    private void loadQuestion() {
        QuestionModel current = questionList.get(currentQuestionIndex);
        questionText.setText(current.getQuestion());
        option1.setText(current.getOption1());
        option2.setText(current.getOption2());
        option3.setText(current.getOption3());
        option4.setText(current.getOption4());
        optionsGroup.clearCheck();
    }
}