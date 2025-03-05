package com.example.myapplication.Activity;

import static java.lang.Integer.parseInt;
import static java.lang.Math.ceil;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ThemesAdapter;
import com.example.myapplication.DB.CourseQueries;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.Domain.NestedDomain;
import com.example.myapplication.Domain.ThemesDomain;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ThemesDomain> mList;
    private ThemesAdapter adapter;
    private CourseQueries DB;
    public DBConnect databaseHelper;
    private Cursor res2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        String courseText = CourseActivity.this.getIntent().getStringExtra("course");
        String parent = CourseActivity.this.getIntent().getStringExtra("parent");
        String login = CourseActivity.this.getIntent().getStringExtra("login");
        TextView course = findViewById(R.id.textView);
        course.setText(courseText);

        ConstraintLayout back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (parent.equals("courselist")){
                intent = new Intent(CourseActivity.this, CoursesListActivity.class);
                } else if (parent.equals("theory")){
                    intent = new Intent(CourseActivity.this, MainActivity.class);
                    intent.putExtra("parent", parent);
                } else {
                    intent = new Intent(CourseActivity.this, MainActivity.class);
                }
                intent.putExtra("login", login);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int score = 0;
        DB = new CourseQueries();
        databaseHelper = new DBConnect(getApplicationContext());
        Cursor res = DB.getMainThemes(databaseHelper, course.getText().toString());
        if( res != null ) {
            mList = new ArrayList<>();
            while (res.moveToNext()) {
                List<NestedDomain> nestedList = new ArrayList<>();
                res2 = DB.getSubThemes(databaseHelper, res.getString(0));
                if( res2 != null){
                    while (res2.moveToNext()) {
                        String theme = res2.getString(0);
                        score = DB.getThemeScore(databaseHelper, theme, login);
                        nestedList.add(new NestedDomain(theme, score));
                    }
                }
                mList.add(new ThemesDomain(nestedList, res.getString(1)));
            }
        }
        adapter = new ThemesAdapter(mList, courseText, parent, login);
        recyclerView.setAdapter(adapter);
    }

}