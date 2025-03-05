package com.example.myapplication.Activity;

import static java.lang.Integer.parseInt;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.LoginQueries;
import com.example.myapplication.DB.ProfileQueries;
import com.example.myapplication.Domain.CourseDomain;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProfileFragment extends Fragment {

    private ProfileQueries DB;
    public DBConnect databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        DB = new ProfileQueries();
        databaseHelper = new DBConnect(requireActivity());

        TextView textView = view.findViewById(R.id.login);
        textView.setText(requireActivity().getIntent().getStringExtra("login"));
        TextView textView2 = view.findViewById(R.id.email);
        Cursor res = DB.selectEmail(databaseHelper, textView.getText().toString());
        res.moveToNext();
        textView2.setText(res.getString(0));
        res = DB.selectImage(databaseHelper, textView.getText().toString());
        res.moveToNext();
        String image = res.getString(0);
        int iconResId = getResources().getIdentifier(image, "drawable",view.getContext().getPackageName());
        ImageView imageView = view.findViewById(R.id.imageView10);
        imageView.setImageResource(iconResId);
        TextView rating = view.findViewById(R.id.textView15);
        res = DB.getRating(databaseHelper, textView.getText().toString());
        res.moveToNext();
        rating.setText(res.getString(0));
        ConstraintLayout logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        ConstraintLayout change = view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("login", textView.getText().toString());
                showDialog(v, args);
            }
        });
        TextView textView14 = view.findViewById(R.id.textView14);
        res = DB.getUpdate(databaseHelper, textView.getText().toString());
        if (res.getCount() > 0){
            res.moveToFirst();
            int prev = res.getInt(0);
            int tek = 0;
            int streak = 0;
            SimpleDateFormat ft
                    = new SimpleDateFormat("yyyyMMdd");
            int d1 = parseInt(ft.format(new Date()));
            if (prev == d1 || prev == d1 -1) streak = 1;
            while (res.moveToNext()){
                tek = res.getInt(0);
                if (tek == prev -1){
                    streak++;
                    prev = res.getInt(0);
                } else break;
            }
            if (streak == 0 && (prev == d1 || prev == d1 -1)) {
                textView14.setText("1 день");
            } else {
                if (streak == 1) textView14.setText("1 день");
                else if (streak == 2 || streak == 3 || streak == 4) textView14.setText(streak + " дня");
                else textView14.setText(streak + " дней");
            }
        }
        else {
            textView14.setText("0 дней");
        }

        return view;
    }
    private void showDialog(View v, Bundle args){
        ConstraintLayout layout = v.findViewById(R.id.constraint);
        View dialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.change_password, layout);
        EditText old = dialogView.findViewById(R.id.edit_text);
        EditText new1 = dialogView.findViewById(R.id.edit_text2);
        EditText new2 = dialogView.findViewById(R.id.edit_text3);
        Button change = dialogView.findViewById(R.id.button2);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        String login = args.getString("login");

        dialogView.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DB.checkUser(databaseHelper, login, old.getText().toString())){
                    if (new1.getText().toString().equals(new2.getText().toString()) && !new1.getText().toString().isEmpty() && !new2.getText().toString().isEmpty()){
                        DB.changePass(databaseHelper, login, new1.getText().toString());
                        Toast.makeText(requireActivity(), "Пароль успешно изменен!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    } else {
                        Toast.makeText(requireActivity(), "Пароли не совпадают!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "Старый пароль неправильный!", Toast.LENGTH_SHORT).show();
                }
            }
        });;
        dialogView.findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });;
        dialog.show();
    }
}