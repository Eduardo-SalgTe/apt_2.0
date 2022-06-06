package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class sub_Vehicu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_vehicu);
    }
    public void toConsulta(View view)
    {
        Intent aConsultar = new Intent (this, vehiculos.class);
        startActivity(aConsultar);
    }
    public void toRegistrar(View view)
    {
        Intent aRegistrar = new Intent (this, vehiculo2.class);
        startActivity(aRegistrar);
    }
    public void toModificar(View view)
    {
        Intent aModificar = new Intent (this, vehiculo3.class);
        startActivity(aModificar);
    }
}