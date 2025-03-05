package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.R;

public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_score);
        TextView textView = findViewById(R.id.textView26);
        textView.setText(ScoreActivity.this.getIntent().getStringExtra("score"));
        AppCompatButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(ScoreActivity.this, CourseActivity.class);
                intent.putExtra("course", ScoreActivity.this.getIntent().getStringExtra("course"));
                intent.putExtra("parent", ScoreActivity.this.getIntent().getStringExtra("parent"));
                intent.putExtra("login", ScoreActivity.this.getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });
    }
}