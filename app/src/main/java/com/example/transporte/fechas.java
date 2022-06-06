package com.example.transporte;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class fechas extends AppCompatActivity {

    private String [][]mat;
    private String curp;
    private int [] matPag;
    private String[] matc;
    private int year, month, day;
    private int pago;
    private float cantd;

    private ListView lv1;
    private EditText et1, etdd;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;
    private RadioGroup rg1;
    private TextView tv_top, tv_com;

    private String [] yyear = {"2021", "2022", "2023"};
    private String [] mmonth = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};

    private Spinner sp1, sp2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fechas);
        getDat();
        rb1 = findViewById(R.id.rb_fec1_pago1);
        rb2 = findViewById(R.id.rb_fec1_pago2);
        rb3 = findViewById(R.id.rb_fec1_pago3);
        rb4 = findViewById(R.id.rb_fec1_pago4);
        rb5 = findViewById(R.id.rb_fec1_pago5);
        rg1 = findViewById(R.id.rg_fec_1);
        rb1.setEnabled(false);
        rb2.setEnabled(false);
        rb3.setEnabled(false);
        rb4.setEnabled(false);
        rb5.setEnabled(false);

        lv1 = findViewById(R.id.lv_fec1_nombres);
        sp1 = findViewById(R.id.sp_fec1_yy);
        sp2 = findViewById(R.id.sp_fec_mm);
        et1 = findViewById(R.id.et_fec1_nombre);
        etdd = findViewById(R.id.etn_fec1_cantidad);
        tv_top = findViewById(R.id.tv_fec1_chofer);
        tv_com = findViewById(R.id.tv_fec1_coment);

        int pA = 0;

        for (int b = 0; b < 3; b++)
        {
            int ano = Integer.parseInt(yyear[b]);
            if(year == ano)
            {
               pA = b;
            }
        }
        //Toast.makeText(this, "yy[1]: " + yyear[1], Toast.LENGTH_SHORT).show();
        ArrayAdapter <String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yyear);
        ArrayAdapter <String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mmonth);

        sp1.setAdapter(adapter);
        sp2.setAdapter(adapter2);
        sp1.setSelection(pA);
        sp2.setSelection(month);

        llenarVector();
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //tvCrp.setText(lv1.getItemAtPosition(i) + ", " + mat[1][i]);

                curp = mat[1][i];
                tv_top.setText(curp);
                valPag();
            }
        });
    }

    public void getDat()
    {
        Calendar fecha = Calendar.getInstance();
        month = fecha.get(Calendar.MONTH);
        day = fecha.get(Calendar.DAY_OF_MONTH);
        year = fecha.get(Calendar.YEAR);
        Toast.makeText(this, month+", " + day + ", " + year, Toast.LENGTH_SHORT).show();

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_usuarios, mat[0]);
        lv1.setAdapter(adapter);
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

    public void buscCrp(View view)
    {
        String crp = et1.getText().toString();
        curp = crp;
        if(curp != "")
        {
            SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
            SQLiteDatabase dbTP = admin.getWritableDatabase();

            Cursor conN = dbTP.rawQuery("SELECT nombre, apep FROM chofer WHERE curp = '"+curp+"'", null);
            if(conN.moveToFirst()) {
                crp += ((conN.getString(0)) + " " + (conN.getString(1)));
                //tvDetl.setText(curp);
                Toast.makeText(this,"CRP [Ok]", Toast.LENGTH_SHORT).show();
                conN.close();

            }else{
                llenarVector();
            }
            dbTP.close();
        }

    }

    public void valPag()
    {
        matc = new String[6];
        String cCrp = curp +"_"+ (year+"") +"_"+ (month+"");
        //Toast.makeText(this, "cCrp: "+cCrp, Toast.LENGTH_SHORT).show();
        //matPag = new int[6];

        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase pag = admin.getWritableDatabase();

        Cursor fila = pag.rawQuery("SELECT chofer, pago1, pago2, pago3, pago4, pago5 FROM pago WHERE id = '"+cCrp+"'", null);
        if(fila.moveToFirst())
        {
            matc[0] = fila.getString(0);
            matc[1] = fila.getString(1);
            matc[2] = fila.getString(2);
            matc[3] = fila.getString(3);
            matc[4] = fila.getString(4);
            matc[5] = fila.getString(5);

            Toast.makeText(this, "existe usuario", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No existe", Toast.LENGTH_SHORT).show();
            ContentValues registro = new ContentValues();
            registro.put("id", cCrp);
            registro.put("chofer", curp);
            registro.put("comentarios", " ");
            registro.put("ano", year);
            registro.put("mes", month);
            registro.put("pago1", "0");
            registro.put("pago2", "0");
            registro.put("pago3", "0");
            registro.put("pago4", "0");
            registro.put("pago5", "0");
            pag.insert("pago", null, registro);
            pag.close();
            Toast.makeText(this, "posible registro", Toast.LENGTH_SHORT).show();
            valPag();
        }
        //Toast.makeText(this, "Pago 5: "+matriz[5], Toast.LENGTH_SHORT).show();
        fila.close();
        pag.close();
        rbBlck();
    }

    public void upPay(View view)
    {
        SQLiteOH admin = new SQLiteOH(this, "administracion", null, 1);
        SQLiteDatabase dbTP1 = admin.getWritableDatabase();
        ContentValues updat = new ContentValues();
        String cCrp = curp +"_"+ (year+"") +"_"+ (month+"");
        String tmp1 = etdd.getText().toString();
        cantd = Float.parseFloat(tmp1);
        //valPag();

        String pmat = "";
        pmat += (matc[0]) + ", ";
        pmat += (matc[1]) + ", ";
        pmat += (matc[2]) + ", ";
        pmat += (matc[3]) + ", ";
        pmat += (matc[4]) + ", ";
        pmat += (matc[5]) + ", ";
        tv_com.setText("antes: " + pmat);
        if((matc[1].equals("0")) && (pago == 0))
        {
            updat.put("ano", year);
            updat.put("mes", mmonth[month]);
            updat.put("pago1", cantd);
            rb1.setEnabled(false);
            int cantidad = dbTP1.update("pago", updat, "id='" + cCrp + "'", null);

            if(cantidad == 1){
                Toast.makeText(this, "Pago exitoso", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
            } else {
                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
            }
        }
        if((matc[2].equals("0")) && (pago == 1))
        {
            updat.put("pago2", cantd);
            rb2.setEnabled(false);
            int cantidad = dbTP1.update("pago", updat, "id='" + cCrp + "'", null);

            if(cantidad == 1){
                Toast.makeText(this, "Pago exitoso", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
            } else {
                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
            }
        }
        if((matc[3].equals("0")) && (pago == 2))
        {
            updat.put("pago3", cantd);
            rb3.setEnabled(false);
            int cantidad = dbTP1.update("pago", updat, "id='" + cCrp + "'", null);

            if(cantidad == 1){
                Toast.makeText(this, "Pago exitoso", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
            } else {
                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
            }
        }
        if((matc[4].equals("0")) && (pago == 3))
        {
            updat.put("pago4", cantd);
            rb4.setEnabled(false);
            int cantidad = dbTP1.update("pago", updat, "id='" + cCrp + "'", null);

            if(cantidad == 1){
                Toast.makeText(this, "Pago exitoso", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
            } else {
                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
            }
        }
        if((matc[5].equals("0")) && (pago == 4))
        {
            updat.put("pago5", cantd);
            rb5.setEnabled(false);
            int cantidad = dbTP1.update("pago", updat, "id='" + cCrp + "'", null);


            if(cantidad == 1){
                Toast.makeText(this, "Pago exitoso", Toast.LENGTH_SHORT).show();
                rg1.clearCheck();
            } else {
                Toast.makeText(this, "Pago fallido", Toast.LENGTH_SHORT).show();
            }
        }
        dbTP1.close();
    }

    public void rbBlck()
    {
        if(matc[1].equals("0")){rb1.setEnabled(true);}
        if(matc[2].equals("0")){rb2.setEnabled(true);}
        if(matc[3].equals("0")){rb3.setEnabled(true);}
        if(matc[4].equals("0")){rb4.setEnabled(true);}
        if(matc[5].equals("0")){rb5.setEnabled(true);}
        if(!matc[1].equals("0")){rb1.setEnabled(false);}
        if(!matc[2].equals("0")){rb2.setEnabled(false);}
        if(!matc[3].equals("0")){rb3.setEnabled(false);}
        if(!matc[4].equals("0")){rb4.setEnabled(false);}
        if(!matc[5].equals("0")){rb5.setEnabled(false);}
    }
    public void pUno(View view){pago = 0;}
    public void pDos(View view){pago = 1;}
    public void pTres(View view){pago = 2;}
    public void pCuatro(View view){pago = 3;}
    public void pCinco(View view){pago = 4;}

    public void toReturn(View view)
    {
        Intent aRetornar = new Intent (this, Lobby.class);
        startActivity(aRetornar);
    }
}