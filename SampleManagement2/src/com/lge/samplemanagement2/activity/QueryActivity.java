package com.lge.samplemanagement2.activity;

import java.util.HashSet;
import java.util.Set;

import com.lge.samplemanagement2.R;
import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import com.lge.excel.EIOperation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.graphics.Color;

public class QueryActivity extends Activity{

	private DBManager dbM;
	private DBOpenHandler dbOH;
	private Cursor cursorLend;
	private Cursor cursorEmployee;
	private Cursor cursorSample;
	private Cursor c=null;
	private AutoCompleteTextView autoModelName;
	private AutoCompleteTextView autoEmployeeID;
	private ListView listview;
	private Button search;
	//private View v=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.query);
		setContentView(R.layout.activity_query);
		
		dbOH = new DBOpenHandler(QueryActivity.this);
		dbM = new DBManager(QueryActivity.this);
		
		listview= (ListView)findViewById(R.id.list_view);		
		listview.setOnItemClickListener(new OnItemClickListener(){   
            @Override   
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {   
             
                   	cursorLend.moveToPosition(position);
                   	String spmsId = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[0])));
                   	String model = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[1])));
                   	String email = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[2])));
                   	String name = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[3])));
                   	String lendDate = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[4])));                   	
                   	String expireDate = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[5])));                   	
                   	String memo = cursorLend.getString((cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[6])));
                   	
                  	String[] s = new String[]{String.valueOf(position+1), model, spmsId, email, name, lendDate, expireDate, memo};
            
                   	
                   	byte[]	b= cursorLend.getBlob(cursorLend.getColumnIndex(dbOH.LEND_TABLE_KEY[7]));               
                   	Intent intent=new Intent();            	
                    intent.setClass(QueryActivity.this, QueryDetailsActivity.class);//从一个activity跳转到另一个activity  
                    intent.putExtra("text", s);
                    intent.putExtra("sign", b);
                    startActivity(intent);
            	
            }                            
        });
		
		Button btShowAll = (Button)findViewById(R.id.showAll);
		btShowAll.setOnClickListener(new View.OnClickListener(){ 
		    @Override 
		    public void onClick(View v){ 
		    	
		    	initListView();		    	
		    } 
		});
		
		Button btClear = (Button)findViewById(R.id.clear);
		btClear.setOnClickListener(new View.OnClickListener(){ 
		    @Override 
		    public void onClick(View v){ 
		    	
		    	 cursorLend = null;
		    	 updateListView(cursorLend);
		    } 
		});
		
		Button btExport = (Button)findViewById(R.id.export);
		btExport.setOnClickListener(new View.OnClickListener(){ 
		    @Override 
		    public void onClick(View v){ 
		   
		    	/*
		    	 * 调用导出功能，接口处传入参数 cursorLend 
		    	 */
		    	Toast.makeText(getApplicationContext(),"尚未实现导出功能！",Toast.LENGTH_SHORT).show();

		    } 
		});
	
		autoModelName=(AutoCompleteTextView)findViewById(R.id.auto1);
		autoModelName.setDropDownHeight(500);  
	    autoModelName.setThreshold(1); 
	    
		autoEmployeeID=(AutoCompleteTextView)findViewById(R.id.auto2);
		autoEmployeeID.setDropDownHeight(500);  
		autoEmployeeID.setThreshold(1);
		
		search = (Button)findViewById(R.id.search);		
		search.setOnClickListener(new View.OnClickListener(){ 
		    @Override 
		    public void onClick(View v){ 
		    	
		    	searchByModelNameOrEmployeeID();
		    	
		    } 
		});
		

	     initAutoModelName();
	     initAutoEmployeeID();
	     initListView();
	      
	}//void onCreate

	/*
	 *************************************************************************************
	 继承 SimpleCursorAdapter类，实现自己的MyListViewAdapter
	 *************************************************************************************
	 */
	private class MyListViewAdapter extends SimpleCursorAdapter {  

	
        public MyListViewAdapter(Cursor c) 
        {  
        	
            super(QueryActivity.this, R.layout.activity_query_list_item, c,new String[]{dbOH.LEND_TABLE_KEY[1],dbOH.LEND_TABLE_KEY[0],dbOH.LEND_TABLE_KEY[2],dbOH.LEND_TABLE_KEY[3],
    	    		dbOH.LEND_TABLE_KEY[4],dbOH.LEND_TABLE_KEY[5]},new int[]{R.id.list_body_model,R.id.list_body_spmsId,R.id.list_body_email,R.id.list_body_name,
    		    			R.id.list_body_lendDate,R.id.list_body_expireDate} ,0);  
        }  
        @Override  
        public void bindView(View view, Context context, final Cursor cursor)
        {  
            super.bindView(view, context, cursor);  
            
            TextView tvID = (TextView)view.findViewById(R.id.list_body_id);

            tvID.setText(String.valueOf(cursor.getPosition()+1));
       
        }
    }// class MyListViewAdapter  
  
 
	
	/*
	 *************************************************************************************
	 初始化AutoCompleteTextView auto1控件，绑定Sample表中Model Name这一列的数据（去除重复值） 
	 *************************************************************************************
	 */
	void initAutoModelName()
	{
		 Set<String> set = new HashSet<String>();  
		 cursorSample=dbM.queryAllDataSample();
		 cursorSample.moveToFirst();
		 String t;
	     while(cursorSample.moveToNext())
	     {
	    	 t=cursorSample.getString(cursorSample.getColumnIndex(dbOH.SAMPLE_TABLE_KEY[1]));
	    	 set.add(t);
	     }
	     String[] s = (String[])set.toArray(new String[set.size()]);
	  	     	     	     	     
	     ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, s);  
	     autoModelName.setAdapter(a);
	}
	
	/*
	 ***************************************************************************************
	 初始化AutoCompleteTextView auto2控件，绑定Employee表中EmployeeID这一列的数据（去除重复值） 
	 ***************************************************************************************
	 */
	void initAutoEmployeeID()
	{
		/*
	     cursorEmployee = dbM.queryAllDataEmployee();
	
		SimpleCursorAdapter s = new SimpleCursorAdapter(this, android.R.layout.simple_dropdown_item_1line, 
				cursorEmployee, new String[] {dbOH.EMPLOYEE_TABLE_KEY[0]}, 
	              new int[] { android.R.id.text1},0);
	     			     
		autoEmployeeID.setAdapter(s); 
		 */
		Set<String> set = new HashSet<String>();  
		cursorEmployee=dbM.queryAllDataEmployee();
		cursorEmployee.moveToFirst();
		 String t;
	     while(cursorEmployee.moveToNext())
	     {
	    	 t=cursorEmployee.getString(cursorEmployee.getColumnIndex(dbOH.EMPLOYEE_TABLE_KEY[0]));
	    	 set.add(t);
	     }
	     String[] s = (String[])set.toArray(new String[set.size()]);
	  	     	     	     	     
	     ArrayAdapter<String> a = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, s);  
	     autoEmployeeID.setAdapter(a);
		
	}
	
	/*
	 *************************************************************
	 初始化ListView，显示全部记录
	 *************************************************************
	 */
	void initListView()
	{
		cursorLend = dbM.queryAllDataLend();
		
		updateListView(cursorLend);
	}
	
	/*
	 *************************************************************
	 更新ListView
	 *************************************************************
	 */
	void updateListView(Cursor cursor)
	{   
		Cursor c = cursor;

		MyListViewAdapter adapter= new MyListViewAdapter(c);
	    listview.setAdapter(adapter);
	}
	
	/*
	 *************************************************************
	 点击“Search”按钮，根据条件在Lend表中进行筛选，并更新到ListView上
	 *************************************************************
	 */
	void searchByModelNameOrEmployeeID()
	{
		String metext1 = autoModelName.getText().toString(); 
    	String metext2 = autoEmployeeID.getText().toString();
	     if(metext1.isEmpty()&&metext2.isEmpty())
	     {
	    	/* cursorLend = null;
	    	 updateListView(cursorLend);
	    	 */
	    	 Toast.makeText(getApplicationContext(),"请输入查询条件！",Toast.LENGTH_SHORT).show();	    	 
	     }
	     else 
	     {
	    	 if(metext1.isEmpty()||metext2.isEmpty())
	    	 {
	    		 if(metext1.isEmpty())
	    		 {
	    			 c= dbM.queryVagueDataByKey(dbOH.LEND_TABLE_NAME,dbOH.LEND_TABLE_KEY[2],metext2);
	    			 if(c.getCount()==0)
	    			 {
	    				 Toast.makeText(getApplicationContext(),"lend表中没有该 员工 的借出记录！",Toast.LENGTH_SHORT).show();
	    			 }	    		
	    			 else
	    			 {
	    				 cursorLend = c;
	    				 updateListView(cursorLend);
	    			 }	    			 	      			 
	    		 }
	    		 else if(metext2.isEmpty())
	    		 {
	    			 c = dbM.queryVagueDataByKey(dbOH.LEND_TABLE_NAME,dbOH.LEND_TABLE_KEY[1],metext1);
	    			 if(c.getCount()==0)
	    			 {
	    				 Toast.makeText(getApplicationContext(),"lend表中没有该 Email ID 的借出记录！",Toast.LENGTH_SHORT).show();
	    			 }
	    			 else
	    			 {
	    				 cursorLend = c;
	    				 updateListView(cursorLend);
	    			 }   			
	    		 }
	    	 }
	    	 else
	    	 {
	    		 c = dbM.queryVagueDataByBAndA(dbOH.LEND_TABLE_NAME,dbOH.LEND_TABLE_KEY[1], dbOH.LEND_TABLE_KEY[2], metext1, metext2);	    	    	
	    		 if(c.getCount()==0)
	    		 {
	    			 Toast.makeText(getApplicationContext(),"lend表中没有该 型号+Email ID 的借出记录！",Toast.LENGTH_SHORT).show();
	    		 }
	    		 else
	    		 {
	    			 cursorLend =c;
		    		 updateListView(cursorLend);
	    		 }	    			    		 
	    	 }
	     }	     
	}
	
	@Override
	public void onDestroy()
	{		
		if(cursorLend!=null)			
		cursorLend.close();
		
		if(cursorEmployee!=null)
		cursorEmployee.close();
		
		if(cursorSample!=null)
		cursorSample.close();
	
	    if(c!=null)
		   c.close();
		
		dbM.closeDataBase();
		
		super.onDestroy();		
	}
	
}// class QueryActivity
