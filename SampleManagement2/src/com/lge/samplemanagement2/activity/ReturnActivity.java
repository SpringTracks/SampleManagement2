package com.lge.samplemanagement2.activity;

import java.awt.font.NumericShaper;
import java.util.Calendar;
import java.util.Currency;
import java.util.TimeZone;

import com.lge.samplemanagement2.R;
import com.lge.dbhelper.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public  class ReturnActivity   extends Activity {

	protected static final int SCANNIN_GREQUEST_CODE = 1;
	private  DataReturn dbReturn ;
    private  String phonename;
    private String person;
    private String personid;
    private  String date;
    private String  imei;
    private  String nowString;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("Return");
        setContentView(R.layout.activity_return);
        dbReturn = new DBManager(ReturnActivity.this);
       //DBManager mdbm= new DBManager(ReturnActivity.this);
        
//        public void getDBManager(){
//        	if (mDBManager!=null){ 
//        		return mDBManager;}
//        	else {
//        		mDBManager = new DBManager();}
//        	}

        
        Calendar c = Calendar.getInstance(); 
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
        String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份 
        String mMonth = String.valueOf(c.get(Calendar.MONTH) +1);// 获取当前月份 
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码 
 //       String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK)); 
         nowString=((mYear)+"."+(mMonth)+"."+mDay);
        TextView rTextView = (TextView) findViewById(R.id.textView9);
        rTextView.setText(nowString);

//        DBOpenHandler dbHandler = new DBOpenHandler(getApplicationContext());
//        db = dbHandler.getWritableDatabase();


        Button scan = (Button) findViewById((Integer) R.id.button);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了扫描");
                
            //调用扫描并请求结果    
                Intent intent = new Intent();
				intent.setClass(ReturnActivity.this,MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
                
             //测试签名及返回   
//                Intent intent = new Intent();
//				intent.setClass(ReturnActivity.this,Sign.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
//				Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
                
            }
        });
//

        Button ok=(Button)findViewById((Integer) R.id.button1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了确定");
            }
        });

        Button delete=(Button)findViewById((Integer) R.id.button3);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            	ContentValues cv = new ContentValues();
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[0],imei);
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[1],phonename);
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[2],personid);
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[3],person);
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[4],date);
//            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[5],nowString);
//            	dbReturn.insertDataToLendHistory(cv);
//            	dbReturn.deleteLendByPhoneId(imei);
                System.out.println("点击了确定归还");
                
            }
        });
	}
//        TextView tv = (TextView)findViewById(R.id.textView7);
//        tv.setText("str");
//        EditText et  = (EditText)findViewById(R.id.editText);
//        et.setText("str");


//        db.close();
	
	
	 //测试签名及返回  
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//		System.out.println("111111111111111111111111111");
//        switch (requestCode) {
//		case SCANNIN_GREQUEST_CODE:
//			System.out.println("222222222222222222");
//			if(resultCode == RESULT_OK){
//				System.out.println("222222222222222222");
//				Bundle bundle = data.getExtras();
//				byte[]  bts = bundle.getByteArray("result");
//				System.out.println(bts);
//				ImageView imageSign = (ImageView) findViewById(R.id.imageView1);
//				Bitmap bmpout = BitmapFactory.decodeByteArray(bts, 0, bts.length);
//				imageSign.setImageBitmap(bmpout);
//				//显示扫描到的内容
//			//	mTextView.setText(bundle.getString("result"));
//			}
//			break;
//		}
//    }
	
//接收扫描返回值及动作
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
//	System.out.println("111111111111111111111111111");
      switch (requestCode) {
	    case SCANNIN_GREQUEST_CODE:
//   		System.out.println("222222222222222222");
		    if(resultCode == RESULT_OK){
//			System.out.println("222222222222222222");
			    Bundle bundle = data.getExtras();
			     imei = bundle.getString("result");
		    	TextView imeiEt = (TextView) findViewById(R.id.editText);
			    imeiEt.setText(imei);

//查询借出信息并显示
//                Cursor cursor =dbReturn. queryLendByPhoneId(imei);
//			    if  (cursor==null){
//			    	Toast.makeText(getApplicationContext(),"请检查条码值是否正确",Toast.LENGTH_SHORT).show();
//			    }else{
//               phonename = cursor.getString(cursor.getColumnIndex("model_name"));
//               person = cursor.getString(cursor.getColumnIndex("employee_name"));
//               date = cursor.getString(cursor.getColumnIndex("lend_date"));
//               personid = cursor.getString(cursor.getColumnIndex("employee_id"));			    
//               TextView lTextView = (TextView) findViewById(R.id.textView8);
//               lTextView.setText(date);
//               TextView pnTextView = (TextView) findViewById(R.id.textView3);
//               pnTextView.setText(phonename);
//               TextView personTextView = (TextView) findViewById(R.id.textView7);
//               personTextView.setText(person);
			    
//			    }
		}
		break;
	}
}

	
}
