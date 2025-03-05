package com.example.myapplication.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.DB.CourseQueries;
import com.example.myapplication.DB.DBConnect;
import com.example.myapplication.DB.HomeQueries;
import com.example.myapplication.DB.QuestionQueries;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;

public class TheoryActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private QuestionQueries DB;
    public DBConnect databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_theory);

        DB = new QuestionQueries();
        databaseHelper = new DBConnect(TheoryActivity.this);
        Cursor res = DB.getTheory(databaseHelper, TheoryActivity.this.getIntent().getStringExtra("theme"));
        res.moveToFirst();

        PDFView pdf = findViewById(R.id.pdfview);
        //pdf.fromAsset("theme1.pdf").load();
        pdf.fromBytes(res.getBlob(0)).load();
        pdf.zoomTo(2.2F);
        pdf.setMinZoom(2);


        TextView textView = findViewById(R.id.textView23);
        textView.setText(TheoryActivity.this.getIntent().getStringExtra("theme"));
        ImageView imageView = findViewById(R.id.imageView17);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(TheoryActivity.this, CourseActivity.class);
                intent.putExtra("course", TheoryActivity.this.getIntent().getStringExtra("course"));
                intent.putExtra("parent", TheoryActivity.this.getIntent().getStringExtra("parent"));
                intent.putExtra("login", TheoryActivity.this.getIntent().getStringExtra("login"));
                startActivity(intent);
            }
        });
    }
    private byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
}