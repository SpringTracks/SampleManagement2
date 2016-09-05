package com.lge.strcture;

import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import com.lge.samplemanagement2.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.TextView;

public class EmployeeAdapter extends CursorAdapter implements Filterable {

	private Context context;
	private DBManager mDBManager;
	
	public EmployeeAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder viewHolder = new ViewHolder();
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
		view = inflater.inflate(R.layout.item_actv_employeename,null);
		viewHolder.tv_ad = (TextView)view.findViewById(R.id.tv_ad);
		viewHolder.tv_name = (TextView)view.findViewById(R.id.tv_name);
		view.setTag(viewHolder);
		
		return view;
	}
	  @Override
	    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
	        Cursor mCursor = null;
	        Log.i("ModelAdapter","runQueryOnBackgroundThread");
	        if(getDBManager()==null){
	        	Log.i("ModelAdapter","dbmanager is null");
	        }
	        mCursor = getDBManager().queryDataByKey(DBOpenHandler.EMPLOYEE_TABLE_NAME,DBOpenHandler.EMPLOYEE_TABLE_KEY[0],constraint.toString());
	        getDBManager().closeDataBase();
	        return mCursor;
	    }
	    public DBManager getDBManager() {
	    	 if (mDBManager == null){
	    		 mDBManager = new DBManager(context);
	         }
	    	 return mDBManager;
	    }
	public class ViewHolder {
		public TextView tv_ad;  
	    public TextView tv_name;  
	    
	}
}
