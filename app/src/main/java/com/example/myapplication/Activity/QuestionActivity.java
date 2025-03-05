package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AnswerAdapter;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.QuestionQueries;
import com.example.myapplication.Domain.AnswerDomain;
import com.example.myapplication.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {
    private AnswerAdapter adapterQuestion;
    private RecyclerView recyclerViewCourse;
    private QuestionQueries DB;
    public DBConnect databaseHelper;
    int correct;
    double coeff_assesement, coeff_mistake;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        TextView textView = findViewById(R.id.textView23);
        textView.setText(QuestionActivity.this.getIntent().getStringExtra("theme"));
        ImageView imageView = findViewById(R.id.imageView17);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(QuestionActivity.this, CourseActivity.class);
                intent.putExtra("course", QuestionActivity.this.getIntent().getStringExtra("course"));
                intent.putExtra("parent", QuestionActivity.this.getIntent().getStringExtra("parent"));
                intent.putExtra("login", QuestionActivity.this.getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });
        long startTime = System.currentTimeMillis();

        DB = new QuestionQueries();
        databaseHelper = new DBConnect(getApplicationContext());
        Cursor res = DB.getQuestion(databaseHelper, QuestionActivity.this.getIntent().getStringExtra("theme"), QuestionActivity.this.getIntent().getStringExtra("login"));
        res.moveToNext();
        setQuestion(res);
        ImageView right = findViewById(R.id.rightArrow);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (res.moveToNext()) {
                    setQuestion(res);
                }else {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    countCoeff(duration/1000, correct);
                    if (DB.getScore(databaseHelper,
                            QuestionActivity.this.getIntent().getStringExtra("login"),
                            QuestionActivity.this.getIntent().getStringExtra("theme")))
                        DB.setResult(databaseHelper,QuestionActivity.this.getIntent().getStringExtra("login"), QuestionActivity.this.getIntent().getStringExtra("theme"),
                                    duration/1000,coeff_mistake,coeff_assesement, correct);
                    int score = (int) Math.ceil(correct * coeff_assesement);
                    Intent intent = null;
                    intent = new Intent(QuestionActivity.this, ScoreActivity.class);
                    intent.putExtra("course", QuestionActivity.this.getIntent().getStringExtra("course"));
                    intent.putExtra("parent", QuestionActivity.this.getIntent().getStringExtra("parent"));
                    intent.putExtra("login", QuestionActivity.this.getIntent().getStringExtra("login"));
                    intent.putExtra("score", String.valueOf(score));
                    startActivity(intent);
                }
            }
        });
    }
    public void setQuestion(Cursor res){
        TextView question = findViewById(R.id.question);
        question.setText(res.getString(1));
        TextView q = findViewById(R.id.textView24);
        q.setText("Вопрос " + (res.getPosition()+1) + "/8");
        LinearProgressIndicator progress = findViewById(R.id.progress);
        progress.setProgress(res.getPosition()+1);
        ImageView imageView = findViewById(R.id.pic);
        if (res.getString(2) != null) {
            String image = res.getString(2);
            int iconResId = getResources().getIdentifier(image, "drawable",QuestionActivity.this.getPackageName());
            imageView.setImageResource(iconResId);
        } else {
            imageView.setImageResource(0);
        }
        Cursor res2 = DB.getAnswers(databaseHelper, res.getString(0));
        recyclerViewCourse = findViewById(R.id.questionList);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(QuestionActivity.this, LinearLayoutManager.VERTICAL, false));
        if (res2 != null && res2.moveToFirst()) {
            ArrayList<AnswerDomain> items = new ArrayList<>();
            items.add(new AnswerDomain(res2.getString(2), Integer.parseInt(res2.getString(3))));
            while (res2.moveToNext()) {
                items.add(new AnswerDomain(res2.getString(2), Integer.parseInt(res2.getString(3))));
            }
            adapterQuestion = new AnswerAdapter(items);
            recyclerViewCourse.setAdapter(adapterQuestion);
        }
        else{
            recyclerViewCourse.setAdapter(null);
        }
        adapterQuestion.setOnAnswerSelectedListener(new AnswerAdapter.OnAnswerSelectedListener() {
            @Override
            public void onAnswerSelected(int correctAnswers) {
                correct++;
            }
        });
    }
    public void countCoeff(long time, int corrects){
        coeff_mistake = (double) (8 - corrects) /8;
        if (time > 70){
            coeff_assesement = 1 - coeff_mistake - 0.3;
        } else if (time < 25){
            coeff_assesement = 1 - coeff_mistake + 0.3;
        }
        else {
            coeff_assesement = 1 - coeff_mistake;
        }
    }
}