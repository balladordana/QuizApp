package com.example.myapplication.DB;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;

public class DBConnect extends SQLiteAssetHelper {
    private static String DB_NAME = "EduIT.sqlite";
    private static String DB_LOCATION = "/data/data/com.example.myapplication/databases/";
    private static final int SCHEMA = 1; // версия базы данных
    private Context myContext;

    public DBConnect(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
    }
    public void copyDataBase(){
        try {
            String outFileName = DB_LOCATION + DB_NAME;
            File file = new File(outFileName);
            if (file.exists()) {
                Log.d("DBConnect", "База данных уже существует, копирование не требуется.");
                return;
            }
            InputStream myInput = myContext.getAssets().open(DB_NAME);
            OutputStream myOutput = new FileOutputStream(outFileName);

            //перемещаем байты из входящего файла в исходящий
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //закрываем потоки
            myOutput.flush();
            myOutput.close();
            myInput.close();
            Log.d("DBConnect", "База данных успешно скопирована.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public SQLiteDatabase open()throws SQLException {
        try {
            String dbPath = myContext.getDatabasePath(DB_NAME).getPath();
            return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLException e) {
            Log.e("DBConnect", "Ошибка при открытии базы данных");
            throw e;
        }
    }
}