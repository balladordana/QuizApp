package com.example.myapplication.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RatingQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public Cursor selectWeek(DBConnect databaseHelper){
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select users.login, users.pic, rating.score from users " +
                "join rating on users.id = rating.user_id where rating.status = \"week\" " +
                "order by rating.score desc", null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        return userCursor;
    }
    public Cursor selectMonth(DBConnect databaseHelper){
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select users.login, users.pic, rating.score from users " +
                "join rating on users.id = rating.user_id where rating.status = \"month\" " +
                "order by rating.score desc", null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        return userCursor;
    }
    public Cursor selectAll(DBConnect databaseHelper){
        // открываем подключение
        db = databaseHelper.open();
        //получаем данные из бд в виде курсора
        userCursor = db.rawQuery("select users.login, users.pic, rating.score from users " +
                "join rating on users.id = rating.user_id where rating.status = \"all\" " +
                "order by rating.score desc", null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        if (userCursor.getCount() <= 0) {
            return userCursor;
        } else {
            return userCursor;
        }
    }

}
