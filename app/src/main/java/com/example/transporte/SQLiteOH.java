package com.example.transporte;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteOH extends SQLiteOpenHelper{
    public SQLiteOH(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase dbTP) {
        dbTP.execSQL("CREATE TABLE chofer (nombre VARCHAR(40), numero INT(5), apep VARCHAR(40), apem VARCHAR(40), edad INT(4), curp VARCHAR(20) primary key, localidad VARCHAR(40), direccion VARCHAR(40), telef1 INT(20), telf2 INT(20), licencia VARCHAR(40), tipLice VARCHAR(30))");
        dbTP.execSQL("CREATE TABLE vehiculo (numero int(5) primary key, placas varchar(15), marca varchar(30), modelo varchar(40), descrip varchar(50))");
        dbTP.execSQL("CREATE TABLE pago (id varchar(35) primary key, chofer varchar(20), comentarios varchar(50), ano int(9), mes int(9), pago1 DECIMAL(10,2), pago2 DECIMAL(10,2), pago3 DECIMAL(10,2), pago4 DECIMAL(10,2), pago5 DECIMAL(10,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
