package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.Toast;

public class chofer2 extends AppCompatActivity {

    private EditText et_nombre, et_apep, et_apem, et_numero, et_edad, et_curp, et_localidad, et_direccion, et_telefono1, et_telefono2, et_licencia, et_licencia_tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chofer2);

        et_nombre = (EditText)findViewById(R.id.et_cho2_nombre);
        et_apep = (EditText)findViewById(R.id.et_cho2_apellidop);
        et_apem = (EditText)findViewById(R.id.et_cho2_apellidom);
        et_numero = (EditText)findViewById(R.id.et_cho2_numero);
        et_edad = (EditText)findViewById(R.id.et_cho2_edad);
        et_curp = (EditText)findViewById(R.id.et_cho2_curp);
        et_localidad = (EditText)findViewById(R.id.et_cho2_localidad);
        et_direccion = (EditText)findViewById(R.id.et_cho2_direccion);
        et_telefono1 = (EditText)findViewById(R.id.et_cho2_telefono1);
        et_telefono2 = (EditText)findViewById(R.id.et_cho2_telefono2);
        et_licencia = (EditText)findViewById(R.id.et_cho2_licencia);
        et_licencia_tip = (EditText)findViewById(R.id.et_cho2_tipolice);

    }

    public void Registrar(View view){
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String apep = et_apep.getText().toString();
        String apem = et_apem.getText().toString();
        String numero = et_numero.getText().toString();
        String edad = et_edad.getText().toString();
        String curp = et_curp.getText().toString();
        String localidad = et_localidad.getText().toString();
        String direccion = et_direccion.getText().toString();
        String telefono1 = et_telefono1.getText().toString();
        String telefono2 = et_telefono2.getText().toString();
        String licencia = et_licencia.getText().toString();
        String licencia_tip = et_licencia_tip.getText().toString();

        if(!nombre.isEmpty() && !apep.isEmpty() && !numero.isEmpty() && !curp.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("numero", numero);
            registro.put("apep", apep);
            registro.put("apem", apem);
            registro.put("edad", edad);
            registro.put("curp", curp);
            registro.put("localidad", localidad);
            registro.put("direccion", direccion);
            registro.put("telef1", telefono1);
            registro.put("telf2", telefono2);
            registro.put("licencia", licencia);
            registro.put("tipLice", licencia_tip);

            dbTP.insert("chofer", null, registro);
            dbTP.close();

            et_nombre.setText("");
            et_apep.setText("");
            et_apem.setText("");
            et_numero.setText("");
            et_edad.setText("");
            et_curp.setText("");
            et_localidad.setText("");
            et_direccion.setText("");
            et_telefono1.setText("");
            et_telefono2.setText("");
            et_licencia.setText("");
            et_licencia_tip.setText("");

            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void toModificar(View view)
    {
        Intent aChofer3 = new Intent (this, chofer3.class);
        startActivity(aChofer3);
    }
}