package com.example.myapplication.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public Cursor getMainThemes(DBConnect databaseHelper, String course){
        db = databaseHelper.open();
        userCursor = db.rawQuery("SELECT main_themes.id, theme from main_themes join courses on course_id = courses.id where courses.name = ?",
                new String[]{course});
        return userCursor;
    }
    public Cursor getSubThemes(DBConnect databaseHelper, String theme){
        db = databaseHelper.open();
        userCursor = db.rawQuery("SELECT sub_themes.theme from sub_themes join main_themes on main_theme_id = main_themes.id where main_theme_id = ?",
                new String[]{theme});
        return userCursor;
    }
    public int getThemeScore(DBConnect databaseHelper, String course, String login){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select max(coeff_assesement) from task_completion\n" +
                "join users  on task_completion.user_id = users.id\n" +
                "join sub_themes on task_completion.sub_theme_id = sub_themes.id\n" +
                "where users.login = ? and sub_themes.theme = ?", new String[]{login,  course});
        userCursor.moveToFirst();
        if (!userCursor.isNull(0)) {
            return (int) Math.ceil(userCursor.getDouble(0));
        } else return -1;
    }

}
