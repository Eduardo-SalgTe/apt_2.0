package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Lobby extends AppCompatActivity {

    private RadioButton r1, r2, r3, r4, r5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        int num = MainActivity.tipo;

        r1 = findViewById(R.id.lobby_rb1);
        r2 = findViewById(R.id.lobby_rb2);
        r3 = findViewById(R.id.lobby_rbt3);
        r4 = findViewById(R.id.lobby_rbt4);

        if (num == 0){
            r2.setEnabled(false);
            r3.setEnabled(false);
            r4.setEnabled(false);
        } if(num == 1){
            r1.setEnabled(true);
            r2.setEnabled(true);
            r3.setEnabled(true);
            r4.setEnabled(true);
        }
        if(num < 0){
            r1.setEnabled(false);
            r2.setEnabled(false);
            r3.setEnabled(false);
            r4.setEnabled(false);
        }
    }
    public void toFechas(View view)
    {
        Intent aFechas = new Intent (this, fechas.class);
        startActivity(aFechas);
    }
    public void toChofer(View view)
    {
        Intent aChofer = new Intent (this, menu_Chofer.class);
        startActivity(aChofer);
    }

    public void toVehiculo(View view)
    {
        Intent aVehiculo = new Intent (this, sub_Vehicu.class);
        startActivity(aVehiculo);
    }

    public void toGestion(View view)
    {
        Intent aGestion = new Intent (this, gestion.class);
        startActivity(aGestion);
    }

    public void toReturn(View view)
    {
        Intent aRetornar = new Intent (this, MainActivity.class);
        startActivity(aRetornar);
    }
}