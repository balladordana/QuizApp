package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.Domain.RatingDomain;
import com.example.myapplication.R;

import java.util.ArrayList;

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.Viewholder> {
    ArrayList<RatingDomain> items;
    Context context;
    public RatingAdapter(ArrayList<RatingDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RatingAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_leaders,parent,false);
        context = parent.getContext();
        return new Viewholder(inflator);
    }
    @Override
    public void onBindViewHolder(@NonNull RatingAdapter.Viewholder holder, int position) {
        holder.place.setText(String.valueOf(items.get(position).getPlace()));
        holder.login.setText(String.valueOf(items.get(position).getLogin()));
        holder.score.setText(String.valueOf(items.get(position).getScore()));
        int drawableResourceId=holder.itemView.getResources()
                .getIdentifier(items.get(position).getPic(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResourceId).into(holder.pic);

        if (position == 0) {
            holder.layout.setBackgroundResource(R.drawable.list_background);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        TextView place, login, score;
        ImageView pic;
        LinearLayout layout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.textView8);
            login = itemView.findViewById(R.id.textView20);
            score = itemView.findViewById(R.id.textView21);
            pic = itemView.findViewById(R.id.imageView3);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
