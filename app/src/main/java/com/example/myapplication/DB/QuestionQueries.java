package com.example.myapplication.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public Cursor getQuestion(DBConnect databaseHelper, String theme, String login){
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select id from users where login = ?", new String[]{login});
        cursor.moveToFirst();
        String user_id = cursor.getString(0);
        userCursor = db.rawQuery("select id from sub_themes where theme = ?", new String[]{theme});
        userCursor.moveToNext();
        String theme_id = userCursor.getString(0);
        userCursor = db.rawQuery("select id from task_completion where user_id = ?", new String[]{user_id});
        if (userCursor.getCount() > 0)
            userCursor = db.rawQuery("select id, question, pic from questions where sub_theme_id = ? " +
                            "order by random() limit 8",
                    new String[]{theme_id});
        else
            userCursor = db.rawQuery("select id, question, pic from questions where sub_theme_id = ? order by random() limit 8",
                    new String[]{theme_id});
        return userCursor;
    }
    public Cursor getAnswers(DBConnect databaseHelper, String id){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from answers where question_id = ? order by random()", new String[]{id});
        return userCursor;
    }
    public boolean getScore(DBConnect databaseHelper, String login, String theme){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select task_completion.id from task_completion\n" +
                "join users on users.id = task_completion.user_id\n" +
                "join sub_themes on task_completion.sub_theme_id = sub_themes.id\n" +
                "where users.login = ? and sub_themes.theme = ?", new String[]{login, theme});
        return (userCursor.getCount() == 0);
    }
    public boolean setResult(DBConnect databaseHelper, String login, String theme, long time,
                             double coeff_mistake, double coeff_assesement, int corrects) {
        db = databaseHelper.open();
        Cursor cursor = db.rawQuery("select id from users where login = ?", new String[]{login});
        cursor.moveToFirst();
        String user_id = cursor.getString(0);
        cursor = db.rawQuery("select id from sub_themes where theme = ?", new String[]{theme});
        cursor.moveToFirst();
        SimpleDateFormat ft
                = new SimpleDateFormat("yyyyMMdd");
        String d1 = ft.format(new Date());
        String theme_id = cursor.getString(0);
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", user_id);
        contentValues.put("sub_theme_id", theme_id);
        contentValues.put("time", time);
        contentValues.put("coeff_mistake", coeff_mistake);
        contentValues.put("coeff_assesement", coeff_assesement);
        contentValues.put("date", d1);
        long result = db.insert("task_completion", null, contentValues);
        if (result == -1) {
            db.close();
            return false;
        } else {
            cursor = db.rawQuery("select tasks, score from rating where user_id = ? order by id asc",
                    new String[]{user_id});
            cursor.moveToFirst();
            int score = (Integer.parseInt(cursor.getString(1)) + (int) Math.ceil(corrects * coeff_assesement));
            int tasks = Integer.parseInt(cursor.getString(0)) + corrects;
            contentValues = new ContentValues();
            contentValues.put("score", score);
            contentValues.put("tasks", tasks);
            db.update("rating", contentValues, "user_id=? and status = \"week\"",
                    new String[]{user_id});
            cursor.moveToNext();
            score = (Integer.parseInt(cursor.getString(1)) + (int) Math.ceil(corrects * coeff_assesement));
            tasks = Integer.parseInt(cursor.getString(0)) + corrects;
            contentValues = new ContentValues();
            contentValues.put("score", score);
            contentValues.put("tasks", tasks);
            db.update("rating", contentValues, "user_id=? and status = \"month\"",
                    new String[]{user_id});
            cursor.moveToNext();
            score = (Integer.parseInt(cursor.getString(1)) + (int) Math.ceil(corrects * coeff_assesement));
            tasks = Integer.parseInt(cursor.getString(0)) + corrects;
            contentValues = new ContentValues();
            contentValues.put("score", score);
            contentValues.put("tasks", tasks);
            db.update("rating", contentValues, "user_id=? and status = \"all\"",
                    new String[]{user_id});
            db.close();
            return true;
        }
    }
    public Cursor getTheory(DBConnect databaseHelper, String theme){
        db = databaseHelper.open();
        userCursor = db.rawQuery("SELECT sub_themes.theory from sub_themes " +
                        "where theme = ?",
                new String[]{theme});
        return userCursor;
    }

}
