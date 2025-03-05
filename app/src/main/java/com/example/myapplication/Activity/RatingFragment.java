package com.example.myapplication.Activity;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Adapter.CourseAdapter;
import com.example.myapplication.Adapter.RatingAdapter;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.RatingQueries;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.Domain.RatingDomain;
import com.example.myapplication.R;
import com.example.myapplication.SquareImageView;

import java.util.ArrayList;

public class RatingFragment extends Fragment {
    private RatingQueries DB;
    public DBConnect databaseHelper;
    private RecyclerView recyclerViewRating;
    private RecyclerView.Adapter adapterRating;
    TextView login1, score1;
    ImageView pic1;
    TextView login2, score2;
    ImageView pic2;
    TextView login3, score3;
    ImageView pic3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        DB = new RatingQueries();
        databaseHelper = new DBConnect(requireActivity());

        Button buttonWeek = view.findViewById(R.id.button6);
        Button buttonMonth = view.findViewById(R.id.button5);
        Button buttonAll = view.findViewById(R.id.button4);
        recyclerViewRating = view.findViewById(R.id.recyclerView);
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        login1 = view.findViewById(R.id.textView12);
        score1 = view.findViewById(R.id.textView18);
        pic1 = view.findViewById(R.id.imageView4);
        login2 = view.findViewById(R.id.textView14);
        score2 = view.findViewById(R.id.textView19);
        pic2 = view.findViewById(R.id.imageView5);
        login3 = view.findViewById(R.id.textView10);
        score3 = view.findViewById(R.id.textView17);
        pic3 = view.findViewById(R.id.imageView3);


        buttonWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWeek.setBackgroundResource(R.drawable.purple_background);
                buttonMonth.setBackgroundResource(R.drawable.white_background);
                buttonAll.setBackgroundResource(R.drawable.white_background);
                getRating(v, 0, databaseHelper);
            }
        });
        buttonMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWeek.setBackgroundResource (R.drawable.white_background);
                buttonMonth.setBackgroundResource (R.drawable.purple_background);
                buttonAll.setBackgroundResource (R.drawable.white_background);
                getRating(v, 1, databaseHelper);
            }
        });
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonWeek.setBackgroundResource (R.drawable.white_background);
                buttonMonth.setBackgroundResource (R.drawable.white_background);
                buttonAll.setBackgroundResource (R.drawable.purple_background);
                getRating(v, 2, databaseHelper);
            }
        });
        getRating(view, 0, databaseHelper);
        return view;
    }

    private void getRating(View view, int status, DBConnect databaseHelper){
        Cursor res;
        if (status == 0) res = DB.selectWeek(databaseHelper);
        else if (status == 1) res = DB.selectMonth(databaseHelper);
        else res = DB.selectAll(databaseHelper);
        ArrayList<RatingDomain> items = new ArrayList<>();
        setTop(view, res);
        while (res.moveToNext()){
            items.add(new RatingDomain(res.getPosition()+1, res.getString(1), res.getString(0), res.getString(2)));
        }
        adapterRating = new RatingAdapter((items));
        recyclerViewRating.setAdapter(adapterRating);
    }
    private void setTop(View view, Cursor res){
        int iconResId;
        if (res.moveToNext())
        {//top1
            login1.setText(res.getString(0));
            score1.setText(res.getString(2));
            iconResId = getResources().getIdentifier(res.getString(1), "drawable", view.getContext().getPackageName());
            pic1.setImageResource(iconResId);
        }
        if (res.moveToNext()) {
            //top2
            login2.setText(res.getString(0));
            score2.setText(res.getString(2));
            iconResId = getResources().getIdentifier(res.getString(1), "drawable", view.getContext().getPackageName());
            pic2.setImageResource(iconResId);
        }
        if (res.moveToNext()) {
            //top3
            login3.setText(res.getString(0));
            score3.setText(res.getString(2));
            iconResId = getResources().getIdentifier(res.getString(1), "drawable", view.getContext().getPackageName());
            pic3.setImageResource(iconResId);
        }
    }
}