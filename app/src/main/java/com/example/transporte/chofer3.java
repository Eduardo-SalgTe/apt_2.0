package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class chofer3 extends AppCompatActivity {

    private String [][]mat;
    private ListView lv1;
    private EditText et1;
    private String curp;
    private TextView tvCrp;

    private EditText et_nombre, et_apep, et_apem, et_numV, et_edad, et_curp, et_local, et_dire, et_tel1, et_tel2, et_licC, et_licT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chofer3);
        lv1 = findViewById(R.id.lv_cho3_seat);
        et1 = findViewById(R.id.et_cho3_nombre);
        tvCrp = findViewById(R.id.tv_cho3_titulo);

        et_nombre = findViewById(R.id.et_cho3_dbnomb);
        et_apep = findViewById(R.id.et_cho3_apellidop);
        et_apem = findViewById(R.id.et_cho3_apellidom);
        et_numV = findViewById(R.id.et_cho3_numero);
        et_edad = findViewById(R.id.et_cho3_edad);
        et_curp = findViewById(R.id.et_cho3_curp);
        et_local = findViewById(R.id.et_cho3_localidad);
        et_dire = findViewById(R.id.et_cho3_direccion);
        et_tel1 = findViewById(R.id.et_cho3_telefono1);
        et_tel2 = findViewById(R.id.et_cho3_telefono2);
        et_licC = findViewById(R.id.et_cho3_licencia);
        et_licT = findViewById(R.id.et_cho3_tipolic);

        llenarVector();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);
                curp = mat[1][i];
                tvCrp.setText(", " + curp);
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

        Cursor slct = dbTP.rawQuery("SELECT * FROM chofer WHERE curp ='" + id + "'", null);
        if(slct.moveToFirst())
        {
            et_nombre.setText(slct.getString(0));
            et_apep.setText(slct.getString(2));
            et_apem.setText(slct.getString(3));
            et_numV.setText(slct.getString(1));
            et_edad.setText(slct.getString(4));
            et_curp.setText(slct.getString(5));
            et_local.setText(slct.getString(6));
            et_dire.setText(slct.getString(7));
            et_tel1.setText(slct.getString(8));
            et_tel2.setText(slct.getString(9));
            et_licC.setText(slct.getString(10));
            et_licT.setText(slct.getString(11));
        }
        slct.close();
        dbTP.close();
    }

    public void modificarR(View view)
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String apep = et_apep.getText().toString();
        String apem = et_apem.getText().toString();
        String numero = et_numV.getText().toString();
        String edad = et_edad.getText().toString();
        String crp = et_curp.getText().toString();
        String localidad = et_local.getText().toString();
        String direccion = et_dire.getText().toString();
        String telefono1 = et_tel1.getText().toString();
        String telefono2 = et_tel2.getText().toString();
        String licencia = et_licC.getText().toString();
        String licencia_tip = et_licT.getText().toString();

        if(!nombre.isEmpty() && !apep.isEmpty() && !numero.isEmpty() && !crp.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("nombre", nombre);
            registro.put("numero", numero);
            registro.put("apep", apep);
            registro.put("apem", apem);
            registro.put("edad", edad);
            registro.put("curp", crp);
            registro.put("localidad", localidad);
            registro.put("direccion", direccion);
            registro.put("telef1", telefono1);
            registro.put("telf2", telefono2);
            registro.put("licencia", licencia);
            registro.put("tipLice", licencia_tip);

            int cantidad = dbTP.update("chofer", registro, "curp='" + curp + "'", null);
            dbTP.close();

            if(cantidad == 1){
                Toast.makeText(this, "Usuario modificado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
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
            int cantidad = dbTP.delete("chofer", "curp='" + curp + "'", null);
            if(cantidad == 1){
                et_nombre.setText("");
                et_apep.setText("");
                et_apem.setText("");
                et_numV.setText("");
                et_edad.setText("");
                et_curp.setText("");
                et_local.setText("");
                et_dire.setText("");
                et_tel1.setText("");
                et_tel2.setText("");
                et_licC.setText("");
                et_licT.setText("");
                llenarVector();
                Toast.makeText(this, "Usuario eliminado exitosamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El Usuario no existe", Toast.LENGTH_SHORT).show();
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
        Cursor tama = dbTP.rawQuery("SELECT count(nombre) FROM chofer", null);
        if(tama.moveToFirst())
        {
            cad = tama.getString(0);
            tam = Integer.parseInt(cad);
            tama.close();
        }
        mat = new String[2][tam];
        Cursor fila = dbTP.rawQuery("SELECT nombre, curp FROM chofer ORDER BY nombre ASC", null);


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

            Cursor conN = dbTP.rawQuery("SELECT * FROM chofer WHERE curp = '"+curp+"'", null);
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
        Cursor taman = dbTP.rawQuery("SELECT count(nombre) FROM chofer WHERE nombre ='"+nom+"'", null);
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
            Cursor fila = dbTP.rawQuery("SELECT nombre, apep, curp FROM chofer WHERE nombre ='"+nom+"' ORDER BY apep ASC", null);
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
        if(tam == 0)
        {
            buscCrp(view);
        }
        dbTP.close();
        //Toast.makeText(this,"Vector ERROR", Toast.LENGTH_SHORT).show();
        return 0;
    }
}