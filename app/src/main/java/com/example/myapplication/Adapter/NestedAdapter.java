package com.example.myapplication.Adapter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.QuestionActivity;
import com.example.myapplication.Activity.TheoryActivity;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.QuestionQueries;
import com.example.myapplication.Domain.NestedDomain;
import com.example.myapplication.R;

import java.util.List;

public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.NestedViewHolder> {
    private List<NestedDomain> mList;
    String course, parent, login;
    private QuestionQueries DB;
    public DBConnect databaseHelper;
    public NestedAdapter(List<NestedDomain> mList, String course, String parent, String login){
        this.mList = mList;
        this.course = course;
        this.parent = parent;
        this.login = login;
    }
    @NonNull
    @Override
    public NestedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nested_item, parent, false);
        return new NestedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedViewHolder holder, int position) {
        holder.textView.setText(mList.get(position).getTheme());
        if (parent.equals("theory")) {
            holder.score.setVisibility(View.INVISIBLE);
        }
        else {
            if (mList.get(position).getScore() != -1) {
                holder.cardView.setBackgroundResource(R.drawable.dark_grey_background);
                holder.score.setText(mList.get(position).getScore() + "/10");
            }
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB = new QuestionQueries();
                if (parent.equals("theory")) {
                    databaseHelper = new DBConnect(v.getContext());
                    Cursor res = DB.getTheory(databaseHelper, holder.textView.getText().toString());
                    res.moveToFirst();
                    if (res.getBlob(0) == null) {
                        Toast.makeText(v.getContext(), "Курс еще в разработке ", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(v.getContext(), TheoryActivity.class);
                        intent.putExtra("course", course);
                        intent.putExtra("parent", parent);
                        intent.putExtra("login", login);
                        intent.putExtra("theme", holder.textView.getText().toString());
                        v.getContext().startActivity(intent);
                    }
                }
                else {
                    databaseHelper = new DBConnect(v.getContext());
                    Cursor res = DB.getQuestion(databaseHelper, holder.textView.getText().toString(), login);
                    if (res.getCount() > 0) {
                        Intent intent = new Intent(v.getContext(), QuestionActivity.class);
                        intent.putExtra("course", course);
                        intent.putExtra("parent", parent);
                        intent.putExtra("login", login);
                        intent.putExtra("theme", holder.textView.getText().toString());
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "Курс еще в разработке ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class NestedViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        private CardView cardView;
        private TextView score;
        public NestedViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            cardView = itemView.findViewById(R.id.card);
            score = itemView.findViewById(R.id.textView28);
        }
    }
}
