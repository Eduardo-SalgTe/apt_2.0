package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class vehiculos extends AppCompatActivity {
    private ListView lv_chof1;
    private EditText et1;
    private String mat[][];
    private ListView lv1;
    private TextView tvDetl;
    private TextView tvCrp;

    private String curp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculos);
        et1 = findViewById(R.id.et_veh1_nombre);
        tvDetl = findViewById(R.id.tv_veh1_2);
        lv1 = findViewById(R.id.lv_veh3_1);
        llenarVector();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);
                curp = mat[0][i];
                verDetal(curp);
            }
        });
    }
    public void verDetal(String id)
    {
        String cad = "";

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        Cursor slct = dbTP.rawQuery("SELECT * FROM vehiculo WHERE numero ='"+id+"'", null);
        if(slct.moveToFirst())
        {

            cad += "               vehiculo: "+(slct.getString(0) + "\n");
            cad += "               placas: "+(slct.getString(1) + "\n");
            cad += "              marca: "+(slct.getString(2) + "\n");
            cad += "              modelo: "+(slct.getString(3) + "\n");
            cad += "                  descripcion: "+(slct.getString(4));
        }
        tvDetl.setText(cad);
        slct.close();
        dbTP.close();
    }

    public int bscNomb(View view)
    {
        String nom = et1.getText().toString();
        int tam = 0;

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();
        String cad = "";
        Cursor taman = dbTP.rawQuery("SELECT count(numero) FROM vehiculo WHERE numero ='"+nom+"'", null);
        if(taman.moveToFirst()){
            cad = taman.getString(0);
            try{
                tam = Integer.parseInt(cad);
            }catch(NumberFormatException e){
                tam = 0;
            }
        }
        taman.close();
        mat = new String[2][tam];
        if (tam > 0)
        {
            String matN[] = new String[tam];
            Cursor fila = dbTP.rawQuery("SELECT numero, modelo, placas FROM vehiculo WHERE numero ="+nom +" ORDER BY numero ASC", null);
            String nmbA = "";
            for (int n = 0; n < tam; n++)
            {
                if (fila.moveToNext()) {
                    //String cadena = fila.getString(0);
                    nmbA += (fila.getString(0)+" ");
                    nmbA += fila.getString(1);
                    matN[n] = nmbA;
                    nmbA = "";

                    mat[0][n] = fila.getString(0);
                    mat[1][n] = fila.getString(2);
                    //et1.setText(fila.getString(0));
                    //Toast.makeText(this, "Vector OK", Toast.LENGTH_SHORT).show();
                }
            }
            fila.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, matN);
            lv1.setAdapter(adapter);

        }
        dbTP.close();
        //Toast.makeText(this,"Vector ERROR", Toast.LENGTH_SHORT).show();
        return 0;
    }

    public void llenarVector()
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        int tam = 0;
        String cad = "";
        Cursor tama = dbTP.rawQuery("SELECT count(numero) FROM vehiculo", null);
        if(tama.moveToFirst())
        {
            cad = tama.getString(0);
            tam = Integer.parseInt(cad);
            tama.close();
        }
        mat = new String[2][tam];
        Cursor fila = dbTP.rawQuery("SELECT numero, placas FROM vehiculo", null);
        //Cursor fila = dbTP.rawQuery("SELECT numero, modelo, placas FROM vehiculo ORDER BY vehiculo ASC", null);

        for (int n = 0; n < tam; n++)
        {
            if (fila.moveToNext()) {
                //String cadena = fila.getString(0);
                mat[0][n] = fila.getString(0);
                mat[1][n] = fila.getString(1);
                //et1.setText(fila.getString(0));
                //Toast.makeText(this, "Vector OK", Toast.LENGTH_SHORT).show();
            }
        }
        fila.close();
        dbTP.close();

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, mat[0]);
        lv1.setAdapter(adapter);
    }

    public void toRegistro(View view)
    {
        Intent aVehiculo2 = new Intent (this, vehiculo2.class);
        startActivity(aVehiculo2);
    }
}