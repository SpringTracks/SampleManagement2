package com.lge.samplemanagement2.activity;

import java.util.Calendar;

import com.lge.samplemanagement2.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LendOutActivity extends Activity {
	private ImageView mImageview;
	private EditText mExpiredDate;
	private EditText mLendDate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lendout);		
		mImageview = (ImageView)findViewById(R.id.Scan);
		mLendDate = (EditText)findViewById(R.id.Lend_Date_Edit);
		mExpiredDate = (EditText)findViewById(R.id.Return_Date_Edit);
		//用Calendar类获取系统当前日期传递给日期选择器
		final Calendar ca = Calendar.getInstance();
		final int currentyear = ca.get(Calendar.YEAR);
		final int currentmonth = ca.get(Calendar.MONTH);
		final int currentday = ca.get(Calendar.DAY_OF_MONTH);
		//借出日期是当天日期，不可编辑
		
		mLendDate.setFocusable(false);
		mLendDate.setText(currentyear+"-"+(currentmonth+1)+"-"+currentday);
		
		//ReturnDate cannot be set only choose from dialog
		mExpiredDate.setFocusable(false);
		//DateDialog callback
		final DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener(){

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				mExpiredDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
			}
			
		};
		mExpiredDate.setOnClickListener(new OnClickListener(){
			//Date dialog
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog mDatePickerDialog = new DatePickerDialog(LendOutActivity.this, mOnDateSetListener, currentyear, currentmonth, currentday);
				mDatePickerDialog.show();
			}
			
		});	
		//click to start scan activity
		mImageview.setOnClickListener(new OnClickListener() {
			public void onClick(View v){					
				Toast to = Toast.makeText(getApplicationContext(), "Need start scan activity", 1);
				to.show();
			}
		});
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dymantic_edit_text, menu);
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
	*/
	
 }	
		
