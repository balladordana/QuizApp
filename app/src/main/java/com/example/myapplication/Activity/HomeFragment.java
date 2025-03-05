package com.example.myapplication.Activity;

import static androidx.browser.customtabs.CustomTabsClient.getPackageName;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.CourseAdapter;
import com.example.myapplication.DB.HomeQueries;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView.Adapter adapterCourse;
    private RecyclerView recyclerViewCourse;
    private HomeQueries DB;
    public DBConnect databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TextView textView = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        textView.setText(requireActivity().getIntent().getStringExtra("login"));

        DB = new HomeQueries();
        databaseHelper = new DBConnect(requireActivity());

        Cursor res = DB.selectImage(databaseHelper, textView.getText().toString());
        res.moveToNext();
        String image = res.getString(0);
        int iconResId = getResources().getIdentifier(image, "drawable",view.getContext().getPackageName());
        res.close();

        Cursor res2 = DB.getRating(databaseHelper, textView.getText().toString());
        res2.moveToNext();
        textView2.setText(res2.getString(0));
        res2.close();

        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(iconResId);

        ConstraintLayout seeAll = view.findViewById(R.id.seeAll);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), CoursesListActivity.class);
                intent.putExtra("login", requireActivity().getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });
        Cursor res3 = DB.checkCoursesLimit(databaseHelper);
        ArrayList<CourseDomain> items = new ArrayList<>();
        if(res3.getCount() == 0) {
            return view;
        }
        while (res3.moveToNext()){
            items.add(new CourseDomain(res3.getString(1),
                    DB.getPercent(databaseHelper, res3.getString(1), requireActivity().getIntent().getStringExtra("login")),
                    res3.getString(3)));
        }
        res3.close();

        recyclerViewCourse = view.findViewById(R.id.viewCourse);
        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        adapterCourse = new CourseAdapter((items));
        recyclerViewCourse.setAdapter(adapterCourse);
        ((CourseAdapter) adapterCourse).setOnClickListener(new CourseAdapter.OnClickListener(){
            @Override
            public void onClick(int position, CourseDomain model) {
                Intent intent;
                intent = new Intent(requireActivity(), CourseActivity.class);
                intent.putExtra("course", model.getTitle());
                intent.putExtra("parent", "home");
                intent.putExtra("login", requireActivity().getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });

        return view;
    }

}