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
    private static final String TAG = "EmployeeAdapter";

	private Context context;
	
	private DBManager mDBManager;
	
	public EmployeeAdapter(Context context, Cursor c, boolean autoRequery,DBManager dbm) {
		super(context, c, autoRequery);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mDBManager = dbm;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = (ViewHolder)view.getTag();
		String employee_id = cursor.getString(cursor.getColumnIndex(DBOpenHandler.EMPLOYEE_TABLE_KEY[0]));
		String employee_name = cursor.getString(cursor.getColumnIndex(DBOpenHandler.EMPLOYEE_TABLE_KEY[1]));		
		viewHolder.tv_ad.setText(context.getResources().getString(R.string.employee_id) + employee_id);
		viewHolder.tv_name.setText(context.getResources().getString(R.string.employee_name) + employee_name);
		
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
	        if(mDBManager==null){
	        	Log.i("ModelAdapter","dbmanager is null");
	        }
	        mCursor = mDBManager.queryDataByKey(DBOpenHandler.EMPLOYEE_TABLE_NAME,DBOpenHandler.EMPLOYEE_TABLE_KEY[0],constraint.toString());
	       // getDBManager().closeDataBase();
	        return mCursor;
	    }
	 
	public class ViewHolder {
		public TextView tv_ad;  
	    public TextView tv_name;  
	    
	}
}
