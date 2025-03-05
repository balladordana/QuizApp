package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.Viewholder> {
    ArrayList<CourseDomain> items;
    Context context;
    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(int position, CourseDomain model);
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public CourseAdapter(ArrayList<CourseDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CourseAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder,parent,false);
        context = parent.getContext();
        return new Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.Viewholder holder, int position) {
        holder.title.setText(String.valueOf(items.get(position).getTitle()));
        if (items.get(position).getPercent() == -1) {
            holder.progressTxt.setText("В разработке");
            holder.percent.setText(null);
            holder.progressBar.setVisibility(View.INVISIBLE);
        } else if (items.get(position).getPercent() == -2){
            holder.percent.setVisibility(View.INVISIBLE);
            holder.progressBar.setVisibility(View.INVISIBLE);
            holder.progressTxt.setText("Открыть темы");
        }
        else
            holder.percent.setText(String.valueOf(items.get(position).getPercent()) + "%");

        int drawableResourceId=holder.itemView.getResources()
                        .getIdentifier(items.get(position).getPicPath(),"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(context).load(drawableResourceId).into(holder.pic);

        holder.progressBar.setProgress(items.get(position).getPercent());

        if (position == 0) {
            holder.layout.setBackgroundResource(R.drawable.list_background);
        }
        CourseDomain item = items.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position, item);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView title, percent, progressTxt;
        ImageView pic;
        ConstraintLayout layout;
        ProgressBar progressBar;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            percent = itemView.findViewById(R.id.percentTxt);
            progressTxt = itemView.findViewById(R.id.progressTxt);
            progressBar = itemView.findViewById(R.id.progressBar);
            pic = itemView.findViewById(R.id.pic);
            layout = itemView.findViewById(R.id.layout);
        }
    }
}
