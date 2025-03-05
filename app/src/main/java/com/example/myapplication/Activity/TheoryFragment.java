package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myapplication.Adapter.CourseAdapter;
import com.example.myapplication.DB.CourseListQueries;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;


import java.util.ArrayList;

public class TheoryFragment extends Fragment {


    private RecyclerView.Adapter adapterCourse;
    private RecyclerView recyclerViewCourse;
    private CourseListQueries DB;
    public DBConnect databaseHelper;
    public SQLiteDatabase db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theory, container, false);
        initRecyclerView(view);
        return view;
    }
    private void initRecyclerView(View view){

        DB = new CourseListQueries();
        databaseHelper = new DBConnect(requireActivity());
        Cursor res = DB.checkCourses(databaseHelper);
        ArrayList<CourseDomain> items = new ArrayList<>();
        if(res.getCount() == 0) {
            return;
        }
        while (res.moveToNext()){
            if (res.getString(2).equals("в процессе")){
                items.add(new CourseDomain(res.getString(1), -1, "ic_5"));}
            else
                items.add(new CourseDomain(res.getString(1), -2, res.getString(3)));
        }

        recyclerViewCourse = view.findViewById(R.id.view);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        adapterCourse = new CourseAdapter((items));
        recyclerViewCourse.setAdapter(adapterCourse);
        ((CourseAdapter) adapterCourse).setOnClickListener(new CourseAdapter.OnClickListener(){
            @Override
            public void onClick(int position, CourseDomain model) {
                Intent intent = null;
                if (model.getPercent() != -1) {
                    intent = new Intent(requireActivity(), CourseActivity.class);
                    intent.putExtra("course", model.getTitle());
                    intent.putExtra("parent", "theory");
                    intent.putExtra("login", requireActivity().getIntent().getStringExtra("login"));
                    startActivity(intent);
                }
                else {
                    Toast.makeText(requireActivity(), "Курс скоро появится!", Toast.LENGTH_SHORT).show(); }

            }
        });
    }
}