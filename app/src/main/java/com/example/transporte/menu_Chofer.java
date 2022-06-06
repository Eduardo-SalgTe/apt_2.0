package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class menu_Chofer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chofer);
    }

    public void toConsulta(View view)
    {
        Intent aConsultar = new Intent (this, choferes.class);
        startActivity(aConsultar);
    }
    public void toRegistrar(View view)
    {
        Intent aRegistrar = new Intent (this, chofer2.class);
        startActivity(aRegistrar);
    }
    public void toModificar(View view)
    {
        Intent aModificar = new Intent (this, chofer3.class);
        startActivity(aModificar);
    }
}