package com.lge.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHandler extends SQLiteOpenHelper {
    private static final String TAG = "DBOpenHandler";
    
    public static final String DATABASE_NAME = "samplemanagement.db";
    
    public static final int DATABASE_VERSION = 1;
    
    public static final String SAMPLE_TABLE_NAME = "SampleTable";
    
    public static final String LEND_TABLE_NAME = "LendTable";
    
    public static final String LEND_HISTORY_TABLE_NAME = "LendTableHistory";
    
    public static final String EMPLOYEE_TABLE_NAME = "EmployeeTable";
    
    public static final String[] SAMPLE_TABLE_KEY = {"phone_id","model_name"};
    
    public static final String[] LEND_TABLE_KEY = {"phone_id","model_name","employee_id",
        "employee_name","lend_date","expected_date","memo","sign"};
    
    public static final String[] LEND_HISTORY_TABLE_KEY = {"phone_id","model_name","employee_id",
        "employee_name","lend_date","return_date"};
    
    public static final String[] EMPLOYEE_TABLE_KEY = {"employee_id","employee_name"};
    
    /**
     * 
     * @param context
     *
     * @param name
     * 
     * @param factory
     * 
     * @param version
     * DataBase Version
     */
    public DBOpenHandler(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
   }
    public DBOpenHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // DB was create when access getWritableDatabase() or getReadableDatabase()
        Log.d(TAG, "DatabaseHelper Constructor");
       
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Lendout Table
        StringBuffer sLendBuffer = new StringBuffer();
        sLendBuffer.append("CREATE TABLE IF NOT EXISTS [" + LEND_TABLE_NAME + "] (");
        sLendBuffer.append("[_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sLendBuffer.append("[phone_id] varchar(30),");
        sLendBuffer.append("[model_name] varchar(20),");
        sLendBuffer.append("[employee_id] varchar(30),");
        sLendBuffer.append("[employee_name] varchar(30),");
        sLendBuffer.append("[lend_date] varchar(20),");
        sLendBuffer.append("[expected_date] varchar(20),");
        sLendBuffer.append("[memo] varchar(30),");
        sLendBuffer.append("[sign] BLOB)");
        db.execSQL(sLendBuffer.toString());
        //Create Sample Table
        db.execSQL("CREATE TABLE IF NOT EXISTS SAMPLE_TABLE_NAME([_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[phone_id] varchar(30),[model_name] varchar(20))");
        //Create Employee Table
        db.execSQL("CREATE TABLE IF NOT EXISTS EMPLOYEE_TABLE_NAME([_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,[employee_id] varchar(30),[employee_name] varchar(30))");
        
        //Create a lend history
        StringBuffer sLendHistoryBuffer = new StringBuffer();
        sLendHistoryBuffer.append("CREATE TABLE IF NOT EXISTS [" + LEND_HISTORY_TABLE_NAME + "] (");
        sLendHistoryBuffer.append("[_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sLendHistoryBuffer.append("[phone_id] varchar(30),");
        sLendHistoryBuffer.append("[model_name] varchar(20),");
        sLendHistoryBuffer.append("[employee_id] varchar(30),");
        sLendHistoryBuffer.append("[employee_name] varchar(30),");
        sLendHistoryBuffer.append("[lend_date] varchar(20),");
        sLendHistoryBuffer.append("[return_date] varchar(20))");
        db.execSQL(sLendHistoryBuffer.toString());
        Log.i("AndrioidDBTest","create table sucessfully");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // TODO Auto-generated method stub

    }

}
