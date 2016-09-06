package com.lge.samplemanagement2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import com.lge.excel.EIOperation;
import com.lge.samplemanagement2.activity.LendOutActivity;
import com.lge.samplemanagement2.activity.ReturnActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	private SQLiteDatabase db  = null;
	private String TAG = "MainActivity";
	
	private GridView gview;
	private List<Map<String,Object>> data_list;
	//图片封装为一个数组
	private SimpleAdapter sim_adapter;
	
	private int[] icon = { R.drawable.lendout, R.drawable.returnback , R.drawable.searchinfo };
	
	private String[] iconName = { "借出","返还", "查询" };
	
	//define table name
	private String sample = DBOpenHandler.SAMPLE_TABLE_NAME;
	private String lend = DBOpenHandler.LEND_TABLE_NAME;
	private String lend_history = DBOpenHandler.LEND_HISTORY_TABLE_NAME;
	private String employee = DBOpenHandler.EMPLOYEE_TABLE_NAME;
	
	//define table column name
	private String[] key_sample = DBOpenHandler.SAMPLE_TABLE_KEY;
	private String[] key_lend = DBOpenHandler.LEND_TABLE_KEY;
	private String[] key_lend_history =DBOpenHandler.LEND_HISTORY_TABLE_KEY;
	private String[] key_employee = DBOpenHandler.EMPLOYEE_TABLE_KEY;
	private EIOperation operation;
	private DBManager dbm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createDB();

		dbm = new DBManager(MainActivity.this);
		operation = new EIOperation(MainActivity.this,dbm);
		
		
		gview = (GridView) findViewById(R.id.gview);
		//新建List
		data_list = new ArrayList<Map<String,Object>>();
		//获取数据
		getData();
		//新建适配器
		String [] from ={"image","text"};
		
		int [] to = {R.id.image,R.id.text};
		
		sim_adapter = new SimpleAdapter(this,data_list,R.layout.griditem,from,to);
		//配置适配器
		gview.setAdapter(sim_adapter);
		
		gview.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				switch(position){
				//跳转到借出Activity
				case 0:
					Intent intentlend = new Intent();
					intentlend.setClass(MainActivity.this,LendOutActivity.class);
					startActivity(intentlend);					
					break;
				case 1:
				//跳转到返还Activity
					Intent intent = new Intent();
					intent.setClass(MainActivity.this,ReturnActivity.class);
					startActivity(intent);
					break;
				case 2:
				//跳转到查询Activity
/*					Intent intent = new Intent();
					intent.setClass(MainActivity.this,SearchActivitiy.class);
					startActivity(intent);*/
					Toast.makeText(getApplicationContext(),"Plz start SearchActivity",Toast.LENGTH_SHORT).show();
					break;
				}		
			}
		});
	}


	public List<Map<String,Object>> getData() {
		// TODO Auto-generated method stub
		//icon和iconName的长度是相同的，这里任选其一即可
		for(int i=0;i<icon.length;i++){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("image",icon[i]);
			map.put("text",iconName[i]);
			data_list.add(map);
		}
		
		return data_list;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
super.onOptionsItemSelected(item);
		switch (item.getItemId())
        {
        case R.id.export:	
            operation.writeToExcel(sample, key_sample);
            operation.writeToExcel(lend, key_lend);
            operation.writeToExcel(lend_history, key_lend_history);
            operation.writeToExcel(employee, key_employee);
            Toast.makeText(MainActivity.this,R.string.export_success,Toast.LENGTH_SHORT).show();
            break;
        case R.id.import_model:	
            long rowId = operation.insertToDb(sample, key_sample);
            operation.toast(rowId);
            break;
        case R.id.import_user:	
            long rowId1 = operation.insertToDb(employee, key_employee);
            operation.toast(rowId1);
            break;
        case R.id.import_lend:	
            long rowId2 = operation.insertToDb(lend,key_lend);
            operation.toast(rowId2);
            break;
        case R.id.manage:	
            Cursor mCrusor = dbm.queryAllData(sample);
           while (mCrusor.moveToNext()) {
               System.out.print("zlp--");
               for (int i=0;i<mCrusor.getColumnCount();i++) {
                   System.out.print(mCrusor.getString(i)+",");
               }
               System.out.println();
           }
            break; 
        }
		return true;
	}
         private void createDB(){
	    	DBOpenHandler dbHandler = new DBOpenHandler(getApplicationContext());// Create DB file  
	        db = dbHandler.getWritableDatabase();  
	        db.close();
	        Log.i(TAG, "createDB sucessfully!!!");
	    }


        @Override
        protected void onDestroy() {
            super.onDestroy();
            dbm.closeDataBase();
        }
}
