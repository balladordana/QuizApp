package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Adapter.IconAdapter;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.LoginQueries;
import com.example.myapplication.R;

import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {
    private EditText username, password, email;
    private Button login;
    private LoginQueries DB;
    public DBConnect databaseHelper;
    public SQLiteDatabase db;
    Spinner spinner;
    IconAdapter iconAdapter;
    String pic;
    String[] names = {"person1", "person2", "person3", "person4", "person5", "person7", "person8", "person9"};
    int[] images = {R.drawable.person1, R.drawable.person2, R.drawable.person3, R.drawable.person4, R.drawable.person5, R.drawable.person7, R.drawable.person8, R.drawable.person9};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.loginEdit);
        password = findViewById(R.id.passwordEdit);
        email = findViewById(R.id.emailEdit);
        login = findViewById(R.id.button);
        spinner = findViewById(R.id.spinner);
        iconAdapter = new IconAdapter(this, names, images);
        spinner.setAdapter(iconAdapter);
        spinner.setPopupBackgroundResource(android.R.color.transparent);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pic = names[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        DB = new LoginQueries();
        databaseHelper = new DBConnect(getApplicationContext());
//        if (databaseHelper.copyDataBase()){
//            Toast.makeText(SignUpActivity.this, "Copied", Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(SignUpActivity.this, "Not copied", Toast.LENGTH_SHORT).show();
//        }

        registerUser();
    }

    private void registerUser() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().equals("")||password.getText().toString().equals("")||email.getText().toString().equals(""))
                    Toast.makeText(SignUpActivity.this, "Нужно заполнить все поля", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkUserEmail = DB.checkEmail(databaseHelper, email.getText().toString());
                    if(checkUserEmail == false) {
                        Boolean checkUserLogin = DB.checkLogin(databaseHelper, login.getText().toString());
                        if(checkUserLogin == false) {
                            boolean var = DB.registerUser(databaseHelper, username.getText().toString(), email.getText().toString(), password.getText().toString(), pic);
                            if (var) {
                                Toast.makeText(SignUpActivity.this, "Вы зарегистрированы", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                finish();
                            } else
                                Toast.makeText(SignUpActivity.this, "Произошла ошибка, попробуйте еще раз", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Такой логин уже используется!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignUpActivity.this, "Такой email уже используется!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}