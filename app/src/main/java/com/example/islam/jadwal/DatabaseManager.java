package com.example.islam.jadwal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Islam on 14/02/2016.
 */
public class DatabaseManager {
    private static final String ROW_ID = "id";
    private static final String ROW_JAM = "jam";
    private static final String ROW_MAPEL = "mapel";
    private static final String ROW_GURU = "guru";
    private static final String NAMA_DB = "jadwal";
    private static final String NAMA_TABEL1 = "senin";
    private static final String NAMA_TABEL2 = "selasa";
    private static final String NAMA_TABEL3 = "rabu";
    private static final String NAMA_TABEL4 = "kamis";
    private static final String NAMA_TABEL5 = "jumat";
    private static final int DB_VERSION = 1;

    private static final String CREATE_TABLE1 = "create table "+NAMA_TABEL1+" ("+ROW_ID+" integer, "+ROW_JAM+" text, "+ROW_MAPEL+" text,"+ROW_GURU+" text)";
    private static final String CREATE_TABLE2 = "create table "+NAMA_TABEL2+" ("+ROW_ID+" integer, "+ROW_JAM+" text, "+ROW_MAPEL+" text,"+ROW_GURU+" text)";
    private static final String CREATE_TABLE3 = "create table "+NAMA_TABEL3+" ("+ROW_ID+" integer, "+ROW_JAM+" text, "+ROW_MAPEL+" text,"+ROW_GURU+" text)";
    private static final String CREATE_TABLE4 = "create table "+NAMA_TABEL4+" ("+ROW_ID+" integer, "+ROW_JAM+" text, "+ROW_MAPEL+" text,"+ROW_GURU+" text)";
    private static final String CREATE_TABLE5 = "create table "+NAMA_TABEL5+" ("+ROW_ID+" integer, "+ROW_JAM+" text, "+ROW_MAPEL+" text,"+ROW_GURU+" text)";

    private final Context context;
    private SQLiteOpenHelper dbHelper;
    private SQLiteDatabase db;

    Cursor c;
    public DatabaseManager(Context ctx){
        this.context = ctx;
        dbHelper = new DatabaseOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    private static class DatabaseOpenHelper extends SQLiteOpenHelper{
        public DatabaseOpenHelper(Context context){
            super(context,NAMA_DB,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);
            db.execSQL(CREATE_TABLE3);
            db.execSQL(CREATE_TABLE4);
            db.execSQL(CREATE_TABLE5);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
            db.execSQL("DROP TABLE IF EXISTS"+NAMA_DB);
            onCreate(db);
        }
    }

    public void close(){
        dbHelper.close();
    }

    public void tambahData(Integer id,String jam,String mapel,String guru, String hari){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ROW_ID,id);
        contentValues.put(ROW_JAM,jam);
        contentValues.put(ROW_MAPEL,mapel);
        contentValues.put(ROW_GURU, guru);
        try{
            switch (hari){
                case "senin" : db.insert(NAMA_TABEL1,null,contentValues);break;
                case "selasa" : db.insert(NAMA_TABEL2,null,contentValues);break;
                case "rabu" : db.insert(NAMA_TABEL3,null,contentValues);break;
                case "kamis" : db.insert(NAMA_TABEL4,null,contentValues);break;
                case "jumat" : db.insert(NAMA_TABEL5,null,contentValues);break;
            }
        }catch (Exception e){
            Log.e("DB ERROR",e.toString());
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Object>> ambilData(String hari){
        ArrayList<ArrayList<Object>> jadwal = new ArrayList<ArrayList<Object>>();
        try{
            switch (hari){
                case "senin" : c = db.query(NAMA_TABEL1,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},null,null,null,null,null);break;
                case "selasa" : c = db.query(NAMA_TABEL2,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},null,null,null,null,null);break;
                case "rabu" : c = db.query(NAMA_TABEL3,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},null,null,null,null,null);break;
                case "kamis" : c = db.query(NAMA_TABEL4,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},null,null,null,null,null);break;
                case "jumat" : c = db.query(NAMA_TABEL5,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},null,null,null,null,null);break;
            }
            c.moveToFirst();
            if(!c.isAfterLast()){
                do{
                    ArrayList<Object> dataList = new ArrayList<Object>();
                    dataList.add(c.getLong(0));
                    dataList.add(c.getString(1));
                    dataList.add(c.getString(2));
                    dataList.add(c.getString(3));
                    jadwal.add(dataList);
                }while(c.moveToNext());
            }
        }catch (Exception e){

        }
        return jadwal;
    }

    public ArrayList<Object> ambilBaris(Long id,String hari){
        ArrayList<Object> arrBaris = new ArrayList<Object>();
        try{
            switch (hari){
                case "senin" : c = db.query(NAMA_TABEL1,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},ROW_ID + "=" +id,null,null,null,null,null);break;
                case "selasa" : c = db.query(NAMA_TABEL2,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},ROW_ID + "=" +id,null,null,null,null,null);break;
                case "rabu" : c = db.query(NAMA_TABEL3,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},ROW_ID + "=" +id,null,null,null,null,null);break;
                case "kamis" : c = db.query(NAMA_TABEL4,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},ROW_ID + "=" +id,null,null,null,null,null);break;
                case "jumat" : c = db.query(NAMA_TABEL5,new String[]{ROW_ID,ROW_JAM,ROW_MAPEL,ROW_GURU},ROW_ID + "=" +id,null,null,null,null,null);break;
            }
            c.moveToFirst();
            if(!c.isAfterLast()){
                do{
                    arrBaris.add(c.getLong(0));
                    arrBaris.add(c.getString(1));
                    arrBaris.add(c.getString(2));
                    arrBaris.add(c.getString(3));
                }while(c.moveToNext());
            }
            c.close();
        }catch (Exception e){

        }
        return arrBaris;
    }

    public void update(Long id,String jam,String mapel,String guru, String hari){
        ContentValues cv= new ContentValues();
        cv.put(ROW_JAM, jam);
        cv.put(ROW_MAPEL, mapel);
        cv.put(ROW_GURU, guru);

        try{
            switch (hari){
                case "senin" : db.update(NAMA_TABEL1,cv,ROW_ID + "=" + id,null);break;
                case "selasa" : db.update(NAMA_TABEL2,cv,ROW_ID + "=" + id,null);break;
                case "rabu" : db.update(NAMA_TABEL3,cv,ROW_ID + "=" + id,null);break;
                case "kamis" : db.update(NAMA_TABEL4,cv,ROW_ID + "=" + id,null);break;
                case "jumat" : db.update(NAMA_TABEL5,cv,ROW_ID + "=" + id,null);break;
            }
        }catch (Exception e){

        }
    }

    public void delete(Long id,String hari){
        try{
            switch (hari){
                case "senin" : db.delete(NAMA_TABEL1,ROW_ID + "=" + id,null);break;
                case "selasa" : db.delete(NAMA_TABEL2,ROW_ID + "=" + id,null);break;
                case "rabu" : db.delete(NAMA_TABEL3,ROW_ID + "=" + id,null);break;
                case "kamis" : db.delete(NAMA_TABEL4,ROW_ID + "=" + id,null);break;
                case "jumat" : db.delete(NAMA_TABEL5,ROW_ID + "=" + id,null);break;
            }
        }catch (Exception e){

        }
    }
}
