package com.lge.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBManager implements DataEmployee, DataSample,
						DataLend ,DataReturn{
	private final String TAG = DBManager.class.getSimpleName();
	
    private DBOpenHandler dbOpenHandler;
    SQLiteDatabase db;
    Cursor cursor;
    Context context; // 定义的临时，主要用于Toast提示，后期要删除

    public DBManager(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context);
        db = dbOpenHandler.getWritableDatabase();
        
        this.context = context;
    }
    
    @Override
	public Cursor queryAllData(String table) {
    	try {
            String sql = "select * from " + table;
            Cursor cursor = db.rawQuery(sql, null);
            return cursor;
        }catch (SQLException e) {
            toastError(e);
            return null;
        }
	}
    
    @Override
    public Cursor queryDataByKey(String table, String key, String value) {
    	try {
    		String sql = "select * from " + table + " where " + key + "='" + value + "'";
        	cursor = db.rawQuery(sql, null);
        	return cursor;
		} catch (SQLException e) {
			toastError(e);
            return null;
		}
    	
    }

    @Override
	public Cursor queryVagueDataByKey(String table, String key, String value) {
    	try {
            String sql = "select * from " + table + " where " + key + " like '%" + value + "%'";
            cursor = db.rawQuery(sql, null);
            return cursor;
        } catch (SQLException e) {
            toastError(e);
            return null;
        }
	}

	@Override
	public Cursor queryDataByBAndA(String table, String KeyA, String KeyB, String a, String b) {
		try {
			String sql = "select * from " + table + " where " + KeyA + "='" + a + "' and " + KeyB + "='" + b + "'";
			cursor = db.rawQuery(sql, null);
			return cursor;
		} catch (SQLException e) {
			toastError(e);
			return null;
		}
	}

	@Override
    public int queryDataCount(String table, String key, String value) {
    	String sql = "select count(*) from " + table +" where " + key + "='" +value + "'";
    	cursor = db.rawQuery(sql, null);
    	cursor.moveToFirst();
    	return cursor.getInt(0);
//    	return cursor.getCount();
    }
    
	public long insertDataToDB(String table, ContentValues values) {
        return db.insert(table, null, values);
    }
	
	public int deleteDataBykey(String table, String key, String value) {
		try {
            String sql = "delete from " + table + " where " + key + "='" + value + "'";
            db.execSQL(sql);
            return 1;
        } catch (SQLException e) {
            toastError(e);
            return -1;
        }
	}
	
    @Override
	public int updateDataBy_id(String table, ContentValues values, int _id) {
		return db.update(table, values, "_id=", new String[]{""+_id} );
	}

	@Override
    public Cursor queryAllDataEmployee() {
    	cursor = queryAllData(DBOpenHandler.EMPLOYEE_TABLE_NAME);
    	return cursor;
    }

    @Override
	public Cursor queryPersonByEmployeeIDIfVague(String employee_id, boolean vague) {
		if (vague == true) {
			return queryVagueDataByKey(DBOpenHandler.EMPLOYEE_TABLE_NAME, 
					DBOpenHandler.EMPLOYEE_TABLE_KEY[0], employee_id);
		} else {
			return queryDataByKey(DBOpenHandler.EMPLOYEE_TABLE_NAME, 
					DBOpenHandler.EMPLOYEE_TABLE_KEY[0], employee_id);
		}
	}

	@Override
    public int deletePersonByEmployeeID(String employee_id) {
    	return deleteDataBykey(DBOpenHandler.EMPLOYEE_TABLE_NAME, 
    				DBOpenHandler.EMPLOYEE_TABLE_KEY[0], employee_id);
    }

    @Override
	public int updatePersonBy_Id(ContentValues values, int _id) {
		return updateDataBy_id(DBOpenHandler.EMPLOYEE_TABLE_NAME, values, _id);
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
    	return insertDataToDB(DBOpenHandler.EMPLOYEE_TABLE_NAME, values);
    }

    @Override
    public Cursor queryAllDataSample() {
    	return queryAllData(DBOpenHandler.SAMPLE_TABLE_NAME);
    }

    @Override
    public Cursor querySampleByPhoneIdIfVague(String phone_id, boolean vague) {
    	if (vague == true) {
    		return queryVagueDataByKey(DBOpenHandler.SAMPLE_TABLE_NAME, 
					DBOpenHandler.SAMPLE_TABLE_KEY[0], phone_id);
		} else {
			return queryDataByKey(DBOpenHandler.SAMPLE_TABLE_NAME, 
					DBOpenHandler.SAMPLE_TABLE_KEY[0], phone_id);
		}
    }

    @Override
    public Cursor querySampleByNameIfVague(String model_name, boolean vague) {
    	if (vague == true) {
    		return queryVagueDataByKey(DBOpenHandler.SAMPLE_TABLE_NAME, 
					DBOpenHandler.SAMPLE_TABLE_KEY[1], model_name);
		} else {
			return queryDataByKey(DBOpenHandler.SAMPLE_TABLE_NAME, 
					DBOpenHandler.SAMPLE_TABLE_KEY[1], model_name);
		}
    	
    }
    
    @Override
	public Cursor querySampleByIdAndNmae(String phone_id, String model_name) {
    	return queryDataByBAndA(DBOpenHandler.SAMPLE_TABLE_NAME, DBOpenHandler.SAMPLE_TABLE_KEY[0],
    			DBOpenHandler.SAMPLE_TABLE_KEY[1], phone_id, model_name);
	}

	@Override
    public int insertDataToSample(String phone_id, String model_name) {
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
    public long insertDataToSample(ContentValues values) {
    	return insertDataToDB(DBOpenHandler.SAMPLE_TABLE_NAME, values);
    }

    @Override
    public int deleteSampleByPhoneId(String phone_id) {
    	return deleteDataBykey(DBOpenHandler.SAMPLE_TABLE_NAME, 
    						DBOpenHandler.SAMPLE_TABLE_KEY[0], phone_id);
    }

    @Override
	public int updateSampleBy_Id(ContentValues values, int _id) {
		return updateDataBy_id(DBOpenHandler.SAMPLE_TABLE_NAME, values, _id);
	}

	@Override
	public Cursor queryAllDataLend() {
		return queryAllData(DBOpenHandler.LEND_TABLE_NAME);
	}

	@Override
	public Cursor queryLendByPhoneIdIfVague(String phone_id, boolean vague) {
		if (vague == true) {
			return queryVagueDataByKey(DBOpenHandler.LEND_TABLE_NAME, 
					DBOpenHandler.LEND_TABLE_KEY[0], phone_id);
		} else {
			return queryDataByKey(DBOpenHandler.LEND_TABLE_NAME, 
					DBOpenHandler.LEND_TABLE_KEY[0], phone_id);
		}
	}
	

	@Override
	public long insertDataToLend(ContentValues values) {
		return insertDataToDB(DBOpenHandler.LEND_TABLE_NAME, values);
	}
	
	@Override
	public int updateLendBy_Id(ContentValues values, int _id) {
		return updateDataBy_id(DBOpenHandler.LEND_TABLE_NAME, values, _id);
	}

	private void toastError(SQLException e) {
        Log.e(TAG, e.getMessage());
        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    
	@Override
    public void closeDataBase() {
        dbOpenHandler.close();
    }

//	@Override
//	public int deleteLendByPhoneId(String employee_id) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
	//DataReturn 方法
	  @Override	
		public Cursor queryLendByPhoneId(String phone_id) {
				return queryDataByKey(DBOpenHandler.LEND_TABLE_NAME, 
						DBOpenHandler.LEND_TABLE_KEY[0], phone_id);
		}
		
	  @Override
	   public long insertDataToLendHistory(ContentValues values) {
		       return insertDataToDB(DBOpenHandler.LEND_HISTORY_TABLE_NAME, values);
	   }
	  
		@Override
		public int deleteLendByPhoneId(String phone_id) {
			// TODO Auto-generated method stub
			return deleteDataBykey(DBOpenHandler.LEND_TABLE_NAME, 
					DBOpenHandler.LEND_TABLE_KEY[0], phone_id);
		}
}
