package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class vehiculo3 extends AppCompatActivity {
    private String [][]mat;
    private ListView lv1;
    private EditText et1;
    private String curp;
    private EditText et_numero, et_placas, et_marca, et_modelo, et_descr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo3);

        et_numero = findViewById(R.id.et_veh3_numero);
        et_placas = findViewById(R.id.et_veh3_placas);
        et_marca = findViewById(R.id.et_veh3_marca);
        et_modelo = findViewById(R.id.et_veh3_modelo);
        et_descr = findViewById(R.id.et_veh3_descripc);
        lv1 = findViewById(R.id.lv_veh3_1);
        et1 = findViewById(R.id.et_veh3_nombre);
        llenarVector();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);
                curp = mat[0][i];
                if(curp != "")
                {
                    setTxt(curp);
                }
            }
        });
    }

    public void setTxt(String id)
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        Cursor slct = dbTP.rawQuery("SELECT * FROM vehiculo WHERE numero ='" + id + "'", null);
        if(slct.moveToFirst())
        {
            et_numero.setText(slct.getString(0));
            et_placas.setText(slct.getString(1));
            et_marca.setText(slct.getString(2));
            et_modelo.setText(slct.getString(3));
            et_descr.setText(slct.getString(4));
        }
        slct.close();
        dbTP.close();
    }

    public void modificarR(View view)
    {
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

            int cantidad = dbTP.update("vehiculo", registro, "numero='" + curp + "'", null);
            dbTP.close();

            if(cantidad == 1){
                Toast.makeText(this, "Vehiculo modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El vehiculo no existe", Toast.LENGTH_SHORT).show();
            }

            dbTP.close();
        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        dbTP.close();
    }
    public void eliminarR(View view)
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        if(curp != "")
        {
            int cantidad = dbTP.delete("vehiculo", "numero='" + curp + "'", null);
            if(cantidad == 1){
                et_numero.setText("");
                et_placas.setText("");
                et_marca.setText("");
                et_modelo.setText("");
                et_descr.setText("");
                llenarVector();
                Toast.makeText(this, "vehiculo eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El vehiculo no existe", Toast.LENGTH_SHORT).show();
            }
        }

        dbTP.close();
    }

    public void llenarVector()
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        int tam = 0;
        String cad;
        Cursor tama = dbTP.rawQuery("SELECT count(numero) FROM vehiculo", null);
        if(tama.moveToFirst())
        {
            cad = tama.getString(0);
            tam = Integer.parseInt(cad);
            tama.close();
        }
        mat = new String[2][tam];
        Cursor fila = dbTP.rawQuery("SELECT numero, placas FROM vehiculo ORDER BY numero ASC", null);


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, mat[0]);
        lv1.setAdapter(adapter);
    }

    public void buscCrp(View view)
    {
        String curp = et1.getText().toString();
        if(curp != "")
        {
            SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
            SQLiteDatabase dbTP = admin.getWritableDatabase();

            Cursor conN = dbTP.rawQuery("SELECT * FROM vehiculo WHERE numero = '"+curp+"'", null);
            if(conN.moveToFirst()) {
                for (int c = 0; c < 12; c++) {
                    curp += ((conN.getString(c)) + "\n");
                }
                //tvDetl.setText(curp);
                Toast.makeText(this,"CRP [Ok]", Toast.LENGTH_SHORT).show();
                conN.close();

            }else{
                llenarVector();
            }
        }

    }
    public int bscNomb(View view)
    {
        String nom = et1.getText().toString();
        int tam = 0;

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();
        String cad;
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
            Cursor fila = dbTP.rawQuery("SELECT numero, modelo, placas FROM vehiculo WHERE numero ='"+nom+"'", null);
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
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, matN);
            lv1.setAdapter(adapter);

        }
        dbTP.close();
        //Toast.makeText(this,"Vector ERROR", Toast.LENGTH_SHORT).show();
        return 0;
    }

}