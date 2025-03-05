package com.example.myapplication.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ProfileQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public boolean checkUser(DBConnect databaseHelper,String login, String password){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from users where login = ? and password = ?", new String[]{login, password});
        if(userCursor.getCount() > 0) {
            db.close();
            return true;
        }else {
            db.close();
            return false;
        }
    }
    public Cursor selectImage(DBConnect databaseHelper, String login){
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select pic from users where login = ?", new String[]{login});
        return cursor;
    }
    public Cursor selectEmail(DBConnect databaseHelper, String login){
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select email from users where login = ?", new String[]{login});
        return cursor;
    }
    public boolean changePass(DBConnect databaseHelper, String login, String password){
        db = databaseHelper.open();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = db.update("users", contentValues, "login=?", new String[]{login});
        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }
    public Cursor getRating(DBConnect databaseHelper, String login){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select rating.score from users " +
                "join rating on users.id = rating.user_id where rating.status = \"all\" and login = ?", new String[]{login});
        return userCursor;
    }
    public Cursor getUpdate(DBConnect databaseHelper, String login){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select date from users join task_completion on users.id = task_completion.user_id \n" +
                "where login = ? order by date desc", new String[]{login});
        return userCursor;
    }
}
