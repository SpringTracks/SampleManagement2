package com.lge.excel;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import com.lge.samplemanagement2.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

public class EIOperation {
	private String TAG = "EIOperation";
	private DBManager dbm;

	// define table name
	private String sample = DBOpenHandler.SAMPLE_TABLE_NAME;
	private String lend = DBOpenHandler.LEND_TABLE_NAME;
	Context context;

	public EIOperation(Context context, DBManager dbm) {
		this.context = context;
		this.dbm = dbm;
	}

	// initData, db to excel, Export-1
	public void writeToExcel(Cursor c, String tableName, String[] columnName) {
		// create Excel file /SampleManagement/tableName.xls
		String filepath = JxlExcelUtils.initExcel(tableName, columnName);
		JxlExcelUtils.writeObjListToExcel(cursorToArrayList(c), filepath, context);
	}

	// convert cursor to ArrayList type, Export-2
	public ArrayList<ArrayList<String>> cursorToArrayList(Cursor mCursor) {
		ArrayList<ArrayList<String>> dbList = new ArrayList<ArrayList<String>>();
		while (mCursor.moveToNext()) {

			ArrayList<String> rowList = new ArrayList<String>();

			for (int i = 1; i < mCursor.getColumnCount(); i++) {
				if (i == 8) {
					byte[] signByte = mCursor.getBlob(i);
					try {
						String signStr = new String(signByte, "ISO-8859-1");
						rowList.add(signStr);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					rowList.add(mCursor.getString(i));
					System.out.print(mCursor.getString(i) + " ");
				}
			}
			System.out.println("--zlp-" + TAG);
			dbList.add(rowList);
		}
		System.out.println("dbList.length= " + dbList.size());
		return dbList;
	}

	// excel to db, Import
	public long insertToDb(String table, String[] ColumnName, String filePath) {
		// String filePath =
		// JxlExcelUtils.getSDPath().toString()+"/SampleManagement/"+table+".xls";
		Log.i(TAG, "zlp--read excel path = " + filePath);
		ArrayList<ArrayList<String>> excelInfo = JxlExcelUtils.readFromExcel(filePath);
		String key = "";
		long rowId = 0;
		for (int i = 0; i < excelInfo.size(); i++) {
			ContentValues cvalues = new ContentValues();
			ArrayList<String> rowExcelInfo = excelInfo.get(i);
			for (int j = 0; j < rowExcelInfo.size(); j++) {
				String sign = rowExcelInfo.get(j).toString();

				// if insert data to LendTable£¬translate signature from string
				// to byte then insert
				if (table.equals(DBOpenHandler.LEND_TABLE_NAME) && j == 8) {
					try {
						byte[] signByte = sign.getBytes("ISO-8859-1");
						cvalues.put(ColumnName[j], signByte);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				} else {
					cvalues.put(ColumnName[j], sign);
				}
			}

			rowId = dbm.insertDataToDBIfNoRepeatValue(table, cvalues);

			if (rowId < 0) {
				toast(rowId);
				Log.i(TAG, "zlp--data insert fail, phone_id or employee id = " + key);
			}
		}
		return rowId;
	}

	public void toast(long rowId) {
		if (rowId > 0) {
			Toast.makeText(context, R.string.import_success, Toast.LENGTH_LONG).show();
		} else
			Toast.makeText(context, R.string.import_Exception, Toast.LENGTH_SHORT).show();
	}

}
