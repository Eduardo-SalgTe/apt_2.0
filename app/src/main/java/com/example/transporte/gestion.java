package com.example.transporte;

import androidx.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class gestion extends AppCompatActivity {
    private EditText et1;
    private TextView tvDetl;
    private String[] data;
    private ListView lv1;
    private String mat[][];
    private String mat2[][];
    private ListView lv2;
    private String curp;
    String [] header = {"Pago", "Cantidad", "Detalle"};
    private String shorText = "Eduardo";
    private String longText = "Detalles de factura del mes: mayo";
    private TemplatePdf templatePdf;
    private String [] monthAS = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
    private String dateComp;

    public static String indexE = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);



        et1 = findViewById(R.id.et_est1_nombre);
        tvDetl = findViewById(R.id.tmp);
        lv1 = findViewById(R.id.lv_est1_1);
        lv2 = findViewById(R.id.lv_est1_2);
        llenarVector();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);
                curp = mat[1][i];
                //verDetal(curp);
                llenarVector2();
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);
                String busqueda = mat2[0][i];
                curp = mat2[0][i];
                //verDetal(curp);
                finaL(busqueda);
            }
        });

    }

    public void finaL(String bus)
    {
        String cad = "";

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();
        Cursor slct = dbTP.rawQuery("SELECT * FROM pago WHERE id ='" + bus + "'", null);
        data = new String[10];
        if(slct.moveToFirst()) {
            for (int f = 0; f < 10; f++)
            {
                data[f] = slct.getString(f);
            }
            indexE = data[0];
            cad += "id: "+(indexE) + "\n";
            cad += "chofer: "+(data[1] + "\n");
            cad += "comentarios: "+(data[2] + "\n");
            cad += "ano: "+(data[3] + "\n");
            cad += "mes: "+(data[4] + "\n");
            cad += "pago1: "+(data[5] + "\n");
            cad += "pago2: "+(data[6] + "\n");
            cad += "paago3: "+(data[7] + "\n");
            cad += "pago4: "+(data[8] + "\n");
            cad += "pago5: "+(data[9]);
        }
        tvDetl.setText(cad);
        slct.close();
        dbTP.close();
    }
    public void verDetal(String id)
    {
        String cad = "";

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();


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
                tvDetl.setText(curp);
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
        String cad = "";
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
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, matN);
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

    public void llenarVector()
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        int tam = 0;
        String cad = "";
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

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, mat[0]);
        lv1.setAdapter(adapter);
    }

    public void llenarVector2()
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP = admin.getWritableDatabase();

        int tam = 0;
        String cad = "";
        Cursor tama = dbTP.rawQuery("SELECT count(chofer) FROM pago WHERE chofer ='"+curp+"'", null);
        if(tama.moveToFirst())
        {
            cad = tama.getString(0);
            tam = Integer.parseInt(cad);
            tama.close();
        }
        mat2 = new String[2][tam];
        Cursor fila = dbTP.rawQuery("SELECT id, ano, mes FROM pago ORDER BY mes ASC", null);

        String cadena = "";
        for (int n = 0; n < tam; n++)
        {
            if (fila.moveToNext()) {

                mat2[0][n] = fila.getString(0);
                mat2[1][n] = (fila.getString(1)) + " " + (fila.getString(2));
                //et1.setText(fila.getString(0));
                //Toast.makeText(this, "Vector OK", Toast.LENGTH_SHORT).show();
            }
        }
        fila.close();
        dbTP.close();

        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_usuarios, mat2[1]);
        lv2.setAdapter(adapter);
    }

    public void getDat() {
        Calendar fecha = Calendar.getInstance();
        int month = 0, day = 0, year = 0;
        String monthS = "";
        month = fecha.get(Calendar.MONTH);
        day = fecha.get(Calendar.DAY_OF_MONTH);
        year = fecha.get(Calendar.YEAR);
        Toast.makeText(this, month + ", " + day + ", " + year, Toast.LENGTH_SHORT).show();
        for (int m = 0; m < monthAS.length; m++)
        {
            monthS = monthAS[month];
        }
        dateComp = monthS + ", " + day + ". " + year;
    }

    @NonNull
    private ArrayList<String[]>getClients()
    {
        ArrayList<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"1", data[5], "uno"});
        rows.add(new String[]{"2", data[6], "dos"});
        rows.add(new String[]{"3", data[7], "tres"});
        rows.add(new String[]{"4", data[8], "cuatro"});
        return rows;
    }

    public void toEstad2(View view)
    {
        if((indexE!="") && (indexE.length()>5))
        {
            getDat();

            templatePdf = new TemplatePdf(getApplicationContext());
            templatePdf.openDocument(indexE, ".pdf");
            templatePdf.addMetaData("Pago de empleados", "historial", "Eduardo Salgado");
            templatePdf.addTitles("REPORTE DE PAGOS", "EMPLEADO: "+data[1], dateComp);
            templatePdf.addParagraph(shorText);
            templatePdf.addParagraph(longText);
            templatePdf.createTable(header, getClients());
            templatePdf.closeDocument();

            //Toast.makeText(this, "Dha", Toast.LENGTH_SHORT).show();
            templatePdf.viewPDF();
            //Intent aEstad2 = new Intent (this, estadistica2.class);
            //startActivity(aEstad2);
        }
        else
            Toast.makeText(this, "Debes seleccionar una fecha", Toast.LENGTH_SHORT).show();
    }

}