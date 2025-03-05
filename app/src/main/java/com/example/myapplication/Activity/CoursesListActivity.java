package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.CourseAdapter;
import com.example.myapplication.DB.CourseListQueries;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;
import com.example.myapplication.databinding.*;

import java.util.ArrayList;

public class CoursesListActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterCourse;
    private RecyclerView recyclerViewCourse;
    private CourseListQueries DB;
    public DBConnect databaseHelper;
    public SQLiteDatabase db;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_courses_list);
        initRecyclerView();
        ConstraintLayout back = findViewById(R.id.backList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoursesListActivity.this, MainActivity.class);
                intent.putExtra("login", CoursesListActivity.this.getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });
    }
    private void initRecyclerView(){

        DB = new CourseListQueries();
        databaseHelper = new DBConnect(getApplicationContext());
        Cursor res = DB.checkCourses(databaseHelper);
        ArrayList<CourseDomain> items = new ArrayList<>();
        if(res.getCount() == 0) {
            return;
        }
        while (res.moveToNext()){
            if (res.getString(2).equals("в процессе")){
                items.add(new CourseDomain(res.getString(1), -1, "ic_5"));}
            else
                items.add(new CourseDomain(res.getString(1),
                        DB.getPercent(databaseHelper, res.getString(1), CoursesListActivity.this.getIntent().getStringExtra("login")),
                        res.getString(3)));
        }

        recyclerViewCourse = findViewById(R.id.view);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        adapterCourse = new CourseAdapter((items));
        recyclerViewCourse.setAdapter(adapterCourse);
        ((CourseAdapter) adapterCourse).setOnClickListener(new CourseAdapter.OnClickListener(){
            @Override
            public void onClick(int position, CourseDomain model) {
                Intent intent = null;
                if (model.getPercent() != -1) {
                    intent = new Intent(CoursesListActivity.this, CourseActivity.class);
                    intent.putExtra("course", model.getTitle());
                    intent.putExtra("parent", "courselist");
                    intent.putExtra("login", CoursesListActivity.this.getIntent().getStringExtra("login"));
                    startActivity(intent);
                }
                else {Toast.makeText(CoursesListActivity.this, "Курс скоро появится!", Toast.LENGTH_SHORT).show(); }

            }
        });
    }
}