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

public class vehiculo2 extends AppCompatActivity {
    private EditText et_numero, et_placas, et_marca, et_modelo, et_descr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo2);

        et_numero = findViewById(R.id.et_veh2_numero);
        et_placas = findViewById(R.id.et_veh2_placas);
        et_marca = findViewById(R.id.et_veh2_marca);
        et_modelo = findViewById(R.id.et_veh2_modelo);
        et_descr = findViewById(R.id.et_veh2_descrip);
    }
    public void Registrar(View view){
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        String numero = et_numero.getText().toString();
        String placas = et_placas.getText().toString();
        String marca = et_marca.getText().toString();
        String modelo = et_modelo.getText().toString();
        String descr = et_descr.getText().toString();

        if(!numero.isEmpty() && !placas.isEmpty() && !numero.isEmpty() && !modelo.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("numero", numero);
            registro.put("placas", placas);
            registro.put("marca", marca);
            registro.put("modelo", modelo);
            registro.put("descrip", descr);

            dbTP.insert("vehiculo", null, registro);
            dbTP.close();

            et_numero.setText("");
            et_placas.setText("");
            et_marca.setText("");
            et_modelo.setText("");
            et_descr.setText("");

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void toModificar(View view)
    {
        Intent aVehiculo3 = new Intent (this, vehiculo3.class);
        startActivity(aVehiculo3);
    }
}