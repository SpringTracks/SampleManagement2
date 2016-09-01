package com.lge.dbhelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBManager {
    private DBOpenHandler dbOpenHandler;

    public DBManager(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context);
    }
    public long InsertRecordToDB(String table, ContentValues cv) {
        
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();// Get DB operation
        
        //define a long parameter to return the insert value
        long revalue = 0;
        
        revalue = db.insert(table, null, cv);
        
        Log.i("AndrioidDBTest","insert sucessfully");
        // Close DB
        db.close();
        return revalue;
    }

    public void delete(Integer id) {// Delete Item
        //SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        //db.execSQL("delete from t_users where id=?", new Object[] { id.toString() });
        //db.close();
    }

    public void update() {// Update Item
        //SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        //db.execSQL("update t_users set username=?,pass=? where" + " id=?", new Object[] { tusers.getUsername(), tusers.getPass(), tusers.getId() });
        //db.close();
    }
/*
    public TUsers find(Integer id) {// Search Record accorint to ID
        //TUsers tusers = null;
       //SQLiteDatabase db = dbOpenHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from t_users where id=?", new String[] { id.toString() });
        //if (cursor.moveToFirst()) {
        //tusers = new TUsers();
        //tusers.setId(cursor.getInt(cursor.getColumnIndex("id")));
       //tusers.setUsername(cursor.getString(cursor.getColumnIndex("username")));
       //tusers.setPass(cursor.getString(cursor.getColumnIndex("pass")));

    }
        //db.close();
        //return tusers;
    }

    public List<TUsers> findAll() {// Search all records
        List<TUsers> lists = new ArrayList<TUsers>();
        TUsers tusers = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
        // String[]{offset.toString(),maxLength.toString()});


        Cursor cursor = db.rawQuery("select * from t_users ", null);
        while (cursor.moveToNext()) {
            tusers = new TUsers();
            tusers.setId(cursor.getInt(cursor.getColumnIndex("id")));
            tusers.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            tusers.setPass(cursor.getString(cursor.getColumnIndex("pass")));
            lists.add(tusers);
        }
        db.close();
        return lists;
    }
*/
    public long getCount() {//Count the record number
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from t_users ", null);
        cursor.moveToFirst();
        db.close();
        return cursor.getLong(0);
    }
}
