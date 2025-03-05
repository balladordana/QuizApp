package com.example.myapplication.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.LoginQueries;
import com.example.myapplication.R;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button login;
    private LoginQueries DB;
    public DBConnect databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginEdit);
        password = findViewById(R.id.passwordEdit);
        login = findViewById(R.id.button);

        DB = new LoginQueries();
        databaseHelper = new DBConnect(getApplicationContext());
        databaseHelper.copyDataBase();

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        findUser();
    }

    private void findUser(){
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boolean var = DB.checkUser(databaseHelper, username.getText().toString(), password.getText().toString());
                if (var){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("login", username.getText().toString());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(LoginActivity.this, "Логин/пароль не верны", Toast.LENGTH_SHORT).show();
            }
        });
    }
}