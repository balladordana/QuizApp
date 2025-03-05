package com.example.myapplication.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CourseListQueries {
    SQLiteDatabase db;
    Cursor userCursor;
    public Cursor checkCourses(DBConnect databaseHelper){
        db = databaseHelper.open();
        userCursor = db.rawQuery("select * from courses", null);
        return userCursor;
    }
    public int getPercent(DBConnect databaseHelper, String course, String login){
        db = databaseHelper.open();
        userCursor = db.rawQuery("WITH themes AS (\n" +
                        "    SELECT COUNT(*) AS count_1\n" +
                        "    FROM main_themes\n" +
                        "    JOIN sub_themes ON main_themes.id = sub_themes.main_theme_id\n" +
                        "    JOIN courses ON main_themes.course_id = courses.id\n" +
                        "    WHERE courses.name = ?\n" +
                        "),\n" +
                        "completed AS (\n" +
                        "    SELECT COUNT(*) AS count_2 FROM\n" +
                        "    (SELECT distinct task_completion.sub_theme_id\n" +
                        "    FROM task_completion\n" +
                        "    JOIN users ON users.id = task_completion.user_id\n" +
                        "    JOIN sub_themes ON task_completion.sub_theme_id = sub_themes.id\n" +
                        "    JOIN main_themes ON sub_themes.main_theme_id = main_themes.id\n" +
                        "    JOIN courses ON main_themes.course_id = courses.id\n" +
                        "    WHERE courses.name = ? AND users.login = ?)) " +
                        "SELECT (CAST(completed.count_2 AS REAL) / themes.count_1) * 100 AS percentage\n" +
                        "FROM themes, completed",
                new String[]{course, course, login});
        userCursor.moveToNext();
        return userCursor.getInt(0);
    }

}
