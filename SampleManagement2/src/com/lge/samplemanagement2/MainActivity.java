package com.lge.samplemanagement2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lge.dbhelper.DBOpenHandler;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		createDB();

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
					Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
         private void createDB(){
	    	DBOpenHandler dbHandler = new DBOpenHandler(getApplicationContext());// Create DB file  
	        db = dbHandler.getWritableDatabase();  
	        db.close();
	        Log.i(TAG, "createDB sucessfully!!!");
	    }
}
