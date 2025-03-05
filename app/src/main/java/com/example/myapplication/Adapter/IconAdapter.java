package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class IconAdapter extends ArrayAdapter<String> {
    Context c;
    String[] name;
    int[] images;

    public IconAdapter(Context c, String[] names, int[] images){
        super(c, R.layout.iconholder, names);
        this.c = c;
        this.name = names;
        this.images = images;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.iconholder, null);
        ImageView i1 = (ImageView) row.findViewById(R.id.imageView16);
        i1.setImageResource(images[position]);
        return row;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.iconholder, null);
        ImageView i1 = (ImageView) row.findViewById(R.id.imageView16);
        i1.setImageResource(images[position]);
        return row;
    }
}
