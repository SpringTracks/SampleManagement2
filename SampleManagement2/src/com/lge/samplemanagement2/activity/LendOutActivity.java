package com.lge.samplemanagement2.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import com.lge.samplemanagement2.R;
import com.lge.strcture.EmployeeAdapter;

public class LendOutActivity extends Activity {
	private static final String TAG = "LendOutActivity";
	
	protected static final int SCANNIN_GREQUEST_CODE = 1;
	
	protected static final int SIGN_GREQUEST_CODE = 2;
	
	private ImageView mImageview;
	
	private EditText mExpiredDate;
	
	private EditText mLendDate;

	private EditText mEmployeeName;
	
	private AutoCompleteTextView mEmployeeID;
	
	private EmployeeAdapter mEmployeeAdapter;

	private EditText mSampleID;
	
	private EditText mModelName;
	
	private Button mSign;
	
	private Button mSave;
	
	private DBManager mDBManager = null;
	
	private byte[] mSignInfo;
	
	private ImageView mDisplaySign,mModelSearch;
	
	private EditText mMemoEdit;

	private Button mClearAllEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setTitle(R.string.icon1);
		setContentView(R.layout.activity_lendout);		
		mSampleID = (EditText)findViewById(R.id.Phone_ID_Edit);
		mModelName = (EditText)findViewById(R.id.Model_Name_Edit);
		mImageview = (ImageView)findViewById(R.id.Scan);
		mLendDate = (EditText)findViewById(R.id.Lend_Date_Edit);
		mExpiredDate = (EditText)findViewById(R.id.Return_Date_Edit);
		mEmployeeID = (AutoCompleteTextView)findViewById(R.id.AD_ID_Edit);
		mEmployeeName = (EditText)findViewById(R.id.Peple_Name_Edit);
		mSign = (Button)findViewById(R.id.Sign_Button);
		mDisplaySign = (ImageView)findViewById(R.id.Return_Sign);
		mSave = (Button)findViewById(R.id.Save_Button);
		mMemoEdit = (EditText)findViewById(R.id.MemoEdit);		
		mModelSearch = (ImageView)findViewById(R.id.Search);
		mClearAllEdit = (Button)findViewById(R.id.Clear_All_Button);
		//用Calendar类获取系统当前日期传递给日期选择器
		final Calendar ca = Calendar.getInstance();
		final int currentyear = ca.get(Calendar.YEAR);
		final int currentmonth = ca.get(Calendar.MONTH);
		final int currentday = ca.get(Calendar.DAY_OF_MONTH);

		//Init employee Adapter
		mEmployeeAdapter = new EmployeeAdapter(this,null,false,getDBManager());
		mEmployeeID.setThreshold(1);
		mEmployeeID.setAdapter(mEmployeeAdapter);
		
		//This is the click implement for Employee ID Search
		
		mEmployeeID.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//ViewHolder viewHolder = (ViewHolder)parent.getItemAtPosition(position);
				//modelname.setText(viewHolder.tv_name.getText());
				//osversion.setText(viewHolder.tv_osversion.getText());
				Cursor cu = (Cursor)((ListView) parent).getItemAtPosition(position);
				if(cu != null && cu.moveToPosition(position)) {
					mEmployeeID.setText(cu.getString(cu.getColumnIndex("employee_id")));
					mEmployeeName.setText(cu.getString(cu.getColumnIndex("employee_name")));
				}
			}

		});
		
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
				 Intent intent = new Intent();
					intent.setClass(LendOutActivity.this,MipcaActivityCapture.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});
		
		//Click Serch Button		
		mModelSearch.setOnClickListener(new OnClickListener() {
					public void onClick(View v){
						if (mSampleID.getText() != null) {
						    Cursor cu = getDBManager().querySampleByPhoneIdIfVague(mSampleID.getText().toString(),false);
						    if (cu.getCount() == 0){
							    Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.sample_not_found),Toast.LENGTH_SHORT).show();
						    } else {
							    if (cu.moveToFirst()) {
								    mModelName.setText(cu.getString(cu.getColumnIndex(DBOpenHandler.SAMPLE_TABLE_KEY[1])));
							    }
						    }
					        cu.close();
						} 
					}
				});
		
		//Click sign button
		mSign.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				   Intent intent = new Intent();
					intent.setClass(LendOutActivity.this,Sign.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent, SIGN_GREQUEST_CODE);
					//Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
			}
			
		});
		
		//Click clear all button
		mClearAllEdit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ClearAllRecordEdit();
			}
			
		});
		
		//Click save button
		mSave.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				cv.put(DBOpenHandler.LEND_TABLE_KEY[0], mSampleID.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[1], mModelName.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[2], mEmployeeID.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[3], mEmployeeName.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[4], mLendDate.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[5], mExpiredDate.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[6], mMemoEdit.getText().toString());
				cv.put(DBOpenHandler.LEND_TABLE_KEY[7], mSignInfo);
				if (CheckEditIsNullOrNot() == false) {
				    if (CheckReLendOutRecord(mSampleID.getText().toString()) == 0) {
				if(getDBManager().insertDataToDB(DBOpenHandler.LEND_TABLE_NAME, cv)>0) {
			        ClearAllRecordEdit();
					        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.save_successful),Toast.LENGTH_SHORT).show();
				} else {
					        Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.save_fail),Toast.LENGTH_SHORT).show();
				        }
				    } else {
					    Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.repeat_record),Toast.LENGTH_SHORT).show();
				    }
				}
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
	@Override
	public void onDestroy(){			
		Log.i(TAG,"onDestroy!!!");
		if (mEmployeeAdapter != null && mEmployeeAdapter.getCursor() != null) {  
			Log.i(TAG,"Close adapter cursor!!!");
			mEmployeeAdapter.getCursor().close();    
	    }
		if (mDBManager != null) {
			Log.i(TAG,"Close DB!!!");
			mDBManager.closeDataBase();
		}
		super.onDestroy();		
	}
	
	//Get response from scan activity
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  super.onActivityResult(requestCode, resultCode, data);
		  switch (requestCode) {
			case SCANNIN_GREQUEST_CODE:
				if(resultCode == RESULT_OK){
					Bundle bundle = data.getExtras();
					String  imei = bundle.getString("result");
					mSampleID.setText(imei);
					Cursor cu = getDBManager().querySampleByPhoneIdIfVague(imei,false);
					if (cu.getCount() == 0){
						Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.sample_not_found),Toast.LENGTH_SHORT).show();
					} else {
						if (cu.moveToFirst()) {
							mModelName.setText(cu.getString(cu.getColumnIndex(DBOpenHandler.SAMPLE_TABLE_KEY[1])));
						}
					}
				    cu.close();
				}
				break;
			case SIGN_GREQUEST_CODE:
				if(resultCode == RESULT_OK){				
				Bundle bundle = data.getExtras();
				mSignInfo = bundle.getByteArray("result");								
				Bitmap bmpout = BitmapFactory.decodeByteArray(mSignInfo, 0, mSignInfo.length);
				mDisplaySign.setImageBitmap(bmpout);
				//显示扫描到的内容
				    if (mSignInfo != null) {
				Log.i(TAG,mSignInfo.toString());
			    }
			    }
				break;
			}
		}
	
	 public DBManager getDBManager() {
		 if (mDBManager == null) {
			 mDBManager = new DBManager(this);
		 }
		 return mDBManager;
	 }
	private int CheckReLendOutRecord(String keyValue) {
		int count = 0;
		try {
			count = getDBManager().queryDataCount(DBOpenHandler.LEND_TABLE_NAME, DBOpenHandler.LEND_TABLE_KEY[0], keyValue);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Exception!!", 0).show();
		} 
		return count;
	}
	private boolean CheckEditIsNullOrNot() {
		boolean recordIsNull = true ;		
		if (mSampleID.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.sample_id_null),Toast.LENGTH_SHORT).show();
		}else if (mModelName.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.model_name_null),Toast.LENGTH_SHORT).show();
		}else if (mEmployeeID.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.ad_null),Toast.LENGTH_SHORT).show();
		}else if (mEmployeeName.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.employee_name_null),Toast.LENGTH_SHORT).show();
		}
        else if (mSignInfo == null){
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.sign_null),Toast.LENGTH_SHORT).show();
		}
		else if(mExpiredDate.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(),getApplicationContext().getString(R.string.expected_date_null),Toast.LENGTH_SHORT).show();
		}
		else {
			recordIsNull = false;
		}		
		return recordIsNull;
		
	}
	private void ClearSampleEdit(){
		mSampleID.setText("");
		mModelName.setText("");
		//mEmployeeID.setText("");
		//mEmployeeName.setText("");
		//mExpiredDate.setText("");
		//mMemoEdit.setText("");	
				
	}
	private void ClearAllRecordEdit(){
		mSampleID.setText("");
		mModelName.setText("");
		mEmployeeID.setText("");
		mEmployeeName.setText("");
		mExpiredDate.setText("");
		mMemoEdit.setText("");	
		mDisplaySign.setImageBitmap(null);
				
	}
		
 }
