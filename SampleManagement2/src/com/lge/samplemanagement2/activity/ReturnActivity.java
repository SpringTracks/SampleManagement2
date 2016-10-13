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
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public  class ReturnActivity   extends Activity {

	protected static final int SCANNIN_GREQUEST_CODE = 1;
	private  DataReturn dbReturn=null ;
    private  String phonename =null;
    private String person =null;
    private String personid =null;
    private  String date =null;
    private String  imei=null;
    private  String nowString=null;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle(R.string.icon2);
        setContentView(R.layout.activity_return);
        
        final EditText input = (EditText) findViewById(R.id.editText2);
        input.setRawInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        input.setRawInputType(InputType.TYPE_CLASS_TEXT);
        dbReturn = new DBManager(ReturnActivity.this);
       //DBManager mdbm= new DBManager(ReturnActivity.this);
        
//        public void getDBManager(){
//        	if (mDBManager!=null){ 
//        		return mDBManager;}
//        	else {
//        		mDBManager = new DBManager();}
//        	}
        //获取当前时间
        Calendar c = Calendar.getInstance(); 
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
        String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份 
        String mMonth = String.valueOf(c.get(Calendar.MONTH) +1);// 获取当前月份 
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码 
 //       String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK)); 
         nowString=((mYear)+"."+(mMonth)+"."+mDay);
        




        Button scan = (Button) findViewById((Integer) R.id.scan);
        scan.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                System.out.println("点击了扫描");
                
            //调用扫描并请求结果    
                Intent intent = new Intent();
				intent.setClass(ReturnActivity.this,MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			//	Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
                
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
            	 imei = input.getText().toString();
             Cursor cursor =dbReturn. queryLendByPhoneId(imei);
             if (cursor.getCount()==0) {
          	   Toast.makeText(getApplicationContext(),R.string.Return_warning2,Toast.LENGTH_SHORT).show();
			} 
			  if  (cursor==null){
			    	Toast.makeText(getApplicationContext(),R.string.Return_warning2,Toast.LENGTH_SHORT).show();
			    }else{
			    	while (cursor.moveToNext()) {
             phonename = cursor.getString(cursor.getColumnIndex("model_name"));
             person = cursor.getString(cursor.getColumnIndex("employee_name"));
             date = cursor.getString(cursor.getColumnIndex("lend_date"));
             personid = cursor.getString(cursor.getColumnIndex("employee_id"));	
			    	
             TextView lTextView = (TextView) findViewById(R.id.textView8);
             lTextView.setText(date);
             TextView pnTextView = (TextView) findViewById(R.id.textView3);
             pnTextView.setText(phonename);
             TextView personTextView = (TextView) findViewById(R.id.textView7);
             personTextView.setText(person); 
                TextView rTextView = (TextView) findViewById(R.id.textView9);
                rTextView.setText(nowString);
              //  System.out.println("点击了确定"+imei);
			    	}

			    	cursor.close();
            }
            }
        });

        Button clearall =(Button)findViewById((Integer)R.id.button4);
        clearall.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                TextView lTextView = (TextView) findViewById(R.id.textView8);
                lTextView.setText(null);
                TextView pnTextView = (TextView) findViewById(R.id.textView3);
                pnTextView.setText(null);
                TextView personTextView = (TextView) findViewById(R.id.textView7);
                personTextView.setText(null);
		        TextView rTextView = (TextView) findViewById(R.id.textView9);
		        rTextView.setText(null);
		        TextView imeiEt = (TextView) findViewById(R.id.editText2);
			    imeiEt.setText(null);
			    
			    personid =null;
			}
		});
        
        Button delete=(Button)findViewById((Integer) R.id.button3);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	if (personid==null) {
               	   Toast.makeText(getApplicationContext(),R.string.Return_warning1,Toast.LENGTH_SHORT).show();
     			} else {
			
            	ContentValues cv = new ContentValues();
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[0],imei);
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[1],phonename);
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[2],personid);
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[3],person);
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[4],date);
            	cv.put(DBOpenHandler.LEND_HISTORY_TABLE_KEY[5],nowString);
            	dbReturn.insertDataToLendHistory(cv);
 //           	Log.e("tag", "55555555555555555555555");
            	dbReturn.deleteLendByPhoneId(imei);
                System.out.println("点击了确定归还");
                TextView lTextView = (TextView) findViewById(R.id.textView8);
                lTextView.setText(null);
                TextView pnTextView = (TextView) findViewById(R.id.textView3);
                pnTextView.setText(null);
                TextView personTextView = (TextView) findViewById(R.id.textView7);
                personTextView.setText(null);
		        TextView rTextView = (TextView) findViewById(R.id.textView9);
		        rTextView.setText(null);
		        TextView imeiEt = (TextView) findViewById(R.id.editText2);
			    imeiEt.setText(null);
			    Toast.makeText(getApplicationContext(),R.string.Return_success,Toast.LENGTH_SHORT).show();
			    personid =null;
			    //清除persionid 用于下次判断是否点击了OK或扫描
				}  
            }
        });
	}

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
		    	TextView imeiEt = (TextView) findViewById(R.id.editText2);
			    imeiEt.setText(imei);

//查询借出信息并显示
                Cursor cursor =dbReturn. queryLendByPhoneId(imei);
                //判断数据是否存在
               if (cursor.getCount()==0) {
            	   Toast.makeText(getApplicationContext(),R.string.Return_warning2,Toast.LENGTH_SHORT).show();
			} 
			    if  (cursor==null){
			    	Toast.makeText(getApplicationContext(),R.string.Return_warning2,Toast.LENGTH_SHORT).show();
			    }else{
			    	while (cursor.moveToNext()) {
			  //  	Log.e("tag", "1111111111111111111111111111111111111");
                    phonename = cursor.getString(cursor.getColumnIndex("model_name"));
                //   Log.e("tag", "1111111111111111111111111111111111111");
                   person = cursor.getString(cursor.getColumnIndex("employee_name"));
                   date = cursor.getString(cursor.getColumnIndex("lend_date"));
                   personid = cursor.getString(cursor.getColumnIndex("employee_id"));			    
                   TextView lTextView = (TextView) findViewById(R.id.textView8);
                   lTextView.setText(date);
                   TextView pnTextView = (TextView) findViewById(R.id.textView3);
                   pnTextView.setText(phonename);
                   TextView personTextView = (TextView) findViewById(R.id.textView7);
                   personTextView.setText(person);

   		          TextView rTextView = (TextView) findViewById(R.id.textView9);
   		          rTextView.setText(nowString);
			    	}

			    }
			    cursor.close();

		}
		break;
	}
}

	
}
