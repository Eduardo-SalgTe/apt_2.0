package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class estadistica2 extends AppCompatActivity {
    private String indexE = gestion.indexE;
    private PDFView pdfView;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadistica2);
        pdfView = (PDFView) findViewById(R.id.pdfView);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
            file = new File(bundle.getString("path", ""));

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }

}