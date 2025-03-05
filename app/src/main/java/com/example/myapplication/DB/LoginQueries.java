package com.example.myapplication.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.myapplication.Activity.SignUpActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public boolean checkUser(DBConnect databaseHelper,String login, String password){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from users where login = ? and password = ?", new String[]{login, password});
        if (userCursor.getCount() <= 0) {
            return false;
        } else {
            return true;
        }
    }
    public Boolean checkEmail(DBConnect databaseHelper, String email){
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select * from users where email = ?", new String[]{email});
        if(cursor.getCount() > 0) {
            db.close();
            return true;
        }else {
            db.close();
            return false;
        }
    }
    public Boolean checkLogin(DBConnect databaseHelper, String login){
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select * from users where login = ?", new String[]{login});
        if(cursor.getCount() > 0) {
            db.close();
            return true;
        }else {
            db.close();
            return false;
        }
    }
    public boolean registerUser(DBConnect databaseHelper, String login, String email, String password, String pic){
        db = databaseHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("login", login);
        contentValues.put("password", password);
        contentValues.put("pic", pic);
        long result = db.insert("users", null, contentValues);
        if (result == -1) {
            db.close();
            return false;
        } else {
            Cursor cursor = db.rawQuery("select id from users where email = ? and login = ?", new String[]{email, login});
            cursor.moveToNext();
            String id = cursor.getString(0);
            contentValues = new ContentValues();
            contentValues.put("user_id",Integer.parseInt(id));
            contentValues.put("tasks", 0);
            contentValues.put("score", 0);
            contentValues.put("status", "month");
            result = db.insert("rating", null, contentValues);
            if (result == -1) {
                db.close();
                return false;
            }
            contentValues.put("status", "week");
            db.insert("rating", null, contentValues);
            contentValues.put("status", "all");
            db.insert("rating", null, contentValues);
            db.close();
            return true;
        }
    }
}
