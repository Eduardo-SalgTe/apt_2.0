package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static int tipo;
    private EditText et1;
    private int rq = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et_menu);

        verPermt();
    }

    public void verPermt()
    {
        int perW = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int perR = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (perW != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, rq);
        if (perR != PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, rq);

    }
    public void toLobby(View view)
    {
        String cad = et1.getText().toString();
        if (cad.equals("administrador")){
            tipo = 1;
        }
        if(cad.equals("auxiliar")){
            tipo = 0;
        }
        Intent aLobby = new Intent (this, Lobby.class);
        startActivity(aLobby);
    }

}