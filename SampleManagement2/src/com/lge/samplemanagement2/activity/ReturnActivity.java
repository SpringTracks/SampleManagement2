package com.lge.samplemanagement2.activity;

import java.awt.font.NumericShaper;
import java.util.Calendar;
import java.util.TimeZone;

import com.lge.samplemanagement2.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SyncStatusObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReturnActivity   extends Activity{

	protected static final int SCANNIN_GREQUEST_CODE = 1;

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return);
        
        Calendar c = Calendar.getInstance(); 
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00")); 
        String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份 
        String mMonth = String.valueOf(c.get(Calendar.MONTH) +1);// 获取当前月份 
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码 
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK)); 
        String nowString=((mYear)+"."+(mMonth)+"."+mDay);
        TextView rTextView = (TextView) findViewById(R.id.textView9);
        rTextView.setText(nowString);
        TextView lTextView = (TextView) findViewById(R.id.textView8);
        lTextView.setText(nowString);
//        DBOpenHandler dbHandler = new DBOpenHandler(getApplicationContext());
//        db = dbHandler.getWritableDatabase();


        Button scan = (Button) findViewById((Integer) R.id.button);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了扫描");
                Intent intent = new Intent();
				intent.setClass(ReturnActivity.this,Sign.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
				Toast.makeText(getApplicationContext(),"Plz start ReturnActivity",Toast.LENGTH_SHORT).show();
                
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
                System.out.println("点击了确定归还");
            }
        });
	}
//        TextView tv = (TextView)findViewById(R.id.textView7);
//        tv.setText("str");
//        EditText et  = (EditText)findViewById(R.id.editText);
//        et.setText("str");


//        db.close();
        
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
		System.out.println("111111111111111111111111111");
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			System.out.println("222222222222222222");
			if(resultCode == RESULT_OK){
				System.out.println("222222222222222222");
				Bundle bundle = data.getExtras();
				byte[]  bts = bundle.getByteArray("result");
				System.out.println(bts);
				ImageView imageSign = (ImageView) findViewById(R.id.imageView1);
				Bitmap bmpout = BitmapFactory.decodeByteArray(bts, 0, bts.length);
				imageSign.setImageBitmap(bmpout);
				//显示扫描到的内容
			//	mTextView.setText(bundle.getString("result"));
			}
			break;
		}
    }

}
