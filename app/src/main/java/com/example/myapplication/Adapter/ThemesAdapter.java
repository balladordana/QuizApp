package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activity.QuestionActivity;
import com.example.myapplication.Domain.NestedDomain;
import com.example.myapplication.Domain.ThemesDomain;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ItemViewHolder> {

    private List<ThemesDomain> mList;
    private List<NestedDomain> list = new ArrayList<>();
    private String course, parent, login;

    public ThemesAdapter(List<ThemesDomain> mList, String course, String parent, String login) {
        this.mList = mList;
        this.course = course;
        this.parent = parent;
        this.login = login;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.themes_layout, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ThemesDomain domain = mList.get(position);
        holder.textView.setText(domain.getItemText());
        holder.textView.setTextSize(16);

        boolean isExpandable = domain.isExpandable();
        holder.relativeLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if (isExpandable){
            holder.imageView.setImageResource(R.drawable.baseline_keyboard_arrow_right_24);
        }else{
            holder.imageView.setImageResource(R.drawable.baseline_keyboard_arrow_down_24);
        }
        NestedAdapter adapter = new NestedAdapter(list, course, parent, login);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setAdapter(adapter);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                domain.setExpandable(!domain.isExpandable());
                list = domain.getItemList();
                if (list.isEmpty())
                    Toast.makeText(v.getContext(), "Темы скоро появятся!", Toast.LENGTH_SHORT).show();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private RelativeLayout relativeLayout;
        private TextView textView;
        private ImageView imageView;
        private RecyclerView recyclerView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
            recyclerView = itemView.findViewById(R.id.recyclerView);
        }
    }
}
