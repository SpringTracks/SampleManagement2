package com.lge.excel;

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

    //define table name
    private String sample = DBOpenHandler.SAMPLE_TABLE_NAME;
    private String lend = DBOpenHandler.LEND_TABLE_NAME;
    Context context;
    
    public EIOperation(Context context,DBManager dbm){
        this.context = context;
        this.dbm = dbm;
    }
    
    //initData, db to excel, Export-1
    public void writeToExcel(String tableName,String[] columnName){
        //create Excel file /SampleManagement/tableName.xls
        String filepath = JxlExcelUtils.initExcel(tableName,columnName);
        JxlExcelUtils.writeObjListToExcel(cursorToArrayList(tableName),filepath,context);
    }
    // convert cursor to ArrayList type, Export-2
    public ArrayList<ArrayList<String>> cursorToArrayList(String tableName) {
		System.out.println("zlp--tableName= "+tableName);
        ArrayList<ArrayList<String>> dbList = new ArrayList<ArrayList<String>>();
        Cursor mCursor = dbm.queryAllData(tableName);
        while (mCursor.moveToNext()) {
            ArrayList<String> rowList=new ArrayList<String>();
			System.out.println("zlp--mCursor.getString(1)="+mCursor.getString(1));
			System.out.println("zlp--mCursor.getColumnCount()="+mCursor.getColumnCount());
			for (int i=1;i<mCursor.getColumnCount();i++) {
                rowList.add(mCursor.getString(i));
				System.out.print(mCursor.getString(i)+" ");
			}
			System.out.println();
            dbList.add(rowList);
        }
        mCursor.close();
		System.out.println("dbList.length= "+dbList.size());
        return dbList;
    }
    // excel to db, Import
    public long insertToDb(String table,String[] ColumnName){
        String filePath = JxlExcelUtils.getSDPath().toString()+"/SampleManagement/"+table+".xls";
        Log.i(TAG, "zlp--read excel path = "+filePath);
        ArrayList<ArrayList<String>> excelInfo = JxlExcelUtils.readFromExcel(filePath);
        int num = ColumnName.length;
        int count;
        String key = "";
        long rowId = 0;
        for (int i=0;i<excelInfo.size();i++){
            ContentValues cvalues =new ContentValues();
            ArrayList<String> rowExcelInfo = excelInfo.get(i);
            
            for (int j=0;j<rowExcelInfo.size();j++){
                cvalues.put(ColumnName[j],rowExcelInfo.get(j).toString());
            }
            
            //determine whether the data is duplicate, if not, insert to db, or log out the key info
            if(table.equals(sample)||table.equals(lend)){
                key = cvalues.get("phone_id").toString();
                count = dbm.queryDataCount(table,"phone_id",key);
                Log.i(TAG, "zlp-- table="+table+", count(phone_id)= "+count);
            }else{
                key = cvalues.get("employee_id").toString();
                count = dbm.queryDataCount(table,"employee_id",key);
                Log.i(TAG, "zlp--EIOperation--table="+table+", count(employee_id) = "+count);
            }
            
            if(count==0){
                rowId = dbm.insertDataToDB(table, cvalues);
                Log.i(TAG, "zlp--count=0, rowID="+rowId+", key= "+key);
            }
            else{
                rowId = 10086;
                Log.i(TAG, "zlp--data already inserted in db, phone_id or employee id = "+key);
                
            }
            
            if(rowId < 0){
                toast(rowId);
                Log.i(TAG, "zlp--data insert fail, phone_id or employee id = "+key);
            }
    }
        return rowId;
    }
    public void toast(long rowId){
        if(rowId >0){
            Toast.makeText(context, R.string.import_success, Toast.LENGTH_LONG).show();
            }
        else
            Toast.makeText(context, R.string.import_Exception,Toast.LENGTH_SHORT).show();
    }
    

}
