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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Domain.AnswerDomain;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;

import java.util.ArrayList;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.Viewholder>{
    ArrayList<AnswerDomain> items;
    Context context;
    boolean isAnswered;
    int correct;
    private OnAnswerSelectedListener listener;

    public void setOnAnswerSelectedListener(OnAnswerSelectedListener listener) {
        this.listener = listener;
    }

    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int correctAnswers);
    }


    private OnClickListener onClickListener;

    public interface OnClickListener {
        void onClick(int position, CourseDomain model);
    }
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public AnswerAdapter(ArrayList<AnswerDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public AnswerAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflator = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_question,parent,false);
        context = parent.getContext();
        return new Viewholder(inflator);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.Viewholder holder, int position) {
        isAnswered = false;
        holder.answer.setText(String.valueOf(items.get(position).getAnswer()));
        holder.answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnswered) {
                    isAnswered = true;
                    if (items.get(position).getCorrect() == 1) {
                        holder.answer.setBackground(ContextCompat.getDrawable(v.getContext(),
                                R.drawable.green_background));
                        correct++;
                        if (listener != null) {
                            listener.onAnswerSelected(correct);
                        }
                    } else {
                        holder.answer.setBackground(ContextCompat.getDrawable(v.getContext(),
                                R.drawable.red_background));
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class Viewholder extends RecyclerView.ViewHolder{
        TextView answer;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            answer = itemView.findViewById(R.id.answerTxt);
        }
    }
}
