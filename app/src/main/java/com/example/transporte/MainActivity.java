package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static int tipo;
    private EditText et1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.et_menu);


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