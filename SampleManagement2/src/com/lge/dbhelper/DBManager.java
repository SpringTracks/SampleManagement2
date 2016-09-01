package com.lge.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBManager implements DataEmployee, DataModel{
	private final String TAG = DBManager.class.getSimpleName();
	
    private DBOpenHandler dbOpenHandler;
    SQLiteDatabase db;
    Cursor cursor;
    Context context;  // 现在测试使用弹出Toast使用，后期删除

    public DBManager(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context);
        this.context = context;
    }
    public long insertDataToDB(String table, ContentValues cv) {
        
        db = dbOpenHandler.getWritableDatabase();// Get DB operation
        
        //define a long parameter to return the insert value
        long revalue = 0;
        
        revalue = db.insert(table, null, cv);
        
        Log.i("AndrioidDBTest","insert sucessfully");
        // Close DB
        closeDataBase();
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

    @Override
    public Cursor queryAllDataEmployee() {
        try {
            String sql = "select * from " + DBOpenHandler.EMPLOYEE_TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            return cursor;
        }catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public Cursor queryPersonByEmployeeID(String employee_id) {
        try {
            // 根据邮件查询数据
            String sql = "select * from " + DBOpenHandler.EMPLOYEE_TABLE_NAME + " where " +
            		DBOpenHandler.EMPLOYEE_TABLE_KEY[0] + "='" + employee_id + "'";
            cursor = db.rawQuery(sql, null);
            return cursor;

        } catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public Cursor queryVaguePersonByEmployeeID(String employee_id) {
        try {
            String sql = "select * from " + DBOpenHandler.EMPLOYEE_TABLE_NAME + " where " +
            		DBOpenHandler.EMPLOYEE_TABLE_KEY[0] + " like '%" + employee_id + "%'";
            cursor = db.rawQuery(sql, null);
            return cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }

    }

    @Override
    public int deletePersonByEmployeeID(String employee_id) {
        try {
            String sql = "delete from " + DBOpenHandler.EMPLOYEE_TABLE_NAME + " where "
                    + DBOpenHandler.EMPLOYEE_TABLE_KEY[0] + "='" + employee_id + "'";
            db.execSQL(sql);
            return 1;
        } catch (SQLException e) {
            toastError(e);
            return -1;
        }

    }

    @Override
    public int insertDataToEmployee(String employee_id, String employee_name) {
        try {
            String sql = "insert into " + DBOpenHandler.EMPLOYEE_TABLE_NAME + " values(null, ?, ?)";
            db.execSQL(sql, DBOpenHandler.EMPLOYEE_TABLE_KEY);
            return 1;
        } catch (SQLException e) {
            toastError(e);
            return -1;
        }
    }

    @Override
    public long insertDataToEmployee(ContentValues values) {
        return db.insert(DBOpenHandler.EMPLOYEE_TABLE_NAME, null, values);
    }

    @Override
    public Cursor queryAllDataModel() {
        try {
            String sql = "select * from " + DBOpenHandler.SAMPLE_TABLE_NAME;
            cursor = db.rawQuery(sql, null);
            return cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public Cursor queryModelByPhoneId(String phone_id) {
        try {
            String sql = "select * from " + DBOpenHandler.SAMPLE_TABLE_NAME +
                    " where " + DBOpenHandler.SAMPLE_TABLE_KEY[0] + "'=" + phone_id +"'";
            cursor = db.rawQuery(sql, null);
            return  cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }

    }

    @Override
    public Cursor queryModelByName(String model_name) {
        try {
            String sql = "select * from " + DBOpenHandler.SAMPLE_TABLE_NAME +
                        " where " + DBOpenHandler.SAMPLE_TABLE_KEY[1] + "='" + model_name +"'";
            cursor = db.rawQuery(sql, null);
            return  cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public Cursor queryVagueModelByPhoneId(String phone_id) {
        try {
            String sql = "select * from " + DBOpenHandler.SAMPLE_TABLE_NAME +
                    " where " + DBOpenHandler.SAMPLE_TABLE_KEY[0] + " like '%" + phone_id +"%'";
            cursor = db.rawQuery(sql, null);
            return  cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public Cursor queryVagueModelByName(String model_name) {
        try {
            String sql = "select * from " + DBOpenHandler.SAMPLE_TABLE_NAME +
                    " where " + DBOpenHandler.SAMPLE_TABLE_KEY[1] + " like '%" + model_name +"%'";
            cursor = db.rawQuery(sql, null);
            return  cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }
    }

    @Override
    public int insertDataToModel(String phone_id, String model_name) {
        try {
            String sql = "insert into " + DBOpenHandler.SAMPLE_TABLE_NAME + " values(null, ?, ?)";
            db.execSQL(sql, DBOpenHandler.SAMPLE_TABLE_KEY);
            return 1;
        } catch (SQLException e) {
            toastError(e);
            return -1;
        }
    }

    @Override
    public long insertDataToModel(ContentValues values) {
        return db.insert(DBOpenHandler.SAMPLE_TABLE_NAME, null, values);
    }

    @Override
    public int deleteModelByPhoneId(String phone_id) {
        try {
            String sql = "delete from " + DBOpenHandler.SAMPLE_TABLE_NAME + " where " +
            		DBOpenHandler.SAMPLE_TABLE_KEY[0] + "='" + phone_id + "'";
            db.execSQL(sql);
            return 1;
        } catch (SQLException e) {
            toastError(e);
            return -1;
        }

    }

    /**
     * 访问数据库异常时，打印log，并且抛出Toast
     *
     * @param e 异常
     */
    private void toastError(SQLException e) {
        Log.e(TAG, e.getMessage());
        // TODO: 数据库操作不应该涉及到UI，后期应该删掉
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public long getCount() {//Count the record number
        cursor = db.rawQuery("select count(*) from t_users ", null);
        cursor.moveToFirst();
        closeDataBase();
        return cursor.getLong(0);
    }
    
    /**
     * 调用数据库之后要关掉数据库
     */
    public void closeDataBase() {
        dbOpenHandler.close();
    }
}
