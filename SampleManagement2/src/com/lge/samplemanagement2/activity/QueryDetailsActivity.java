package com.lge.samplemanagement2.activity;

import android.R.color;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lge.samplemanagement2.R;

public class QueryDetailsActivity extends Activity{


	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_details);
		
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		DisplayMetrics dm = getResources().getDisplayMetrics();
	    p.height=(int) (dm.heightPixels*0.7);//设置activity大小：高度
	    p.width=(int) (dm.widthPixels*0.7); //设置activity大小：宽度
	    p.alpha = 0.95f;      //设置本身透明度  
	    p.dimAmount = 0.6f;  //设置黑暗度  
	           
	    getWindow().setAttributes(p);     //设置生效
	
	    TextView tv0=(TextView)findViewById(R.id.details_id);	    
		TextView tv1=(TextView)findViewById(R.id.details_model);
		TextView tv2=(TextView)findViewById(R.id.details_spmsId);	
		TextView tv3=(TextView)findViewById(R.id.details_email);	
		TextView tv4=(TextView)findViewById(R.id.details_name);		
		TextView tv5=(TextView)findViewById(R.id.details_lendDate);		
		TextView tv6=(TextView)findViewById(R.id.details_expireDate);	
		TextView tv7=(TextView)findViewById(R.id.details_memo);
				
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		String[] s=bundle.getStringArray("text");
		
		tv0.setText(s[0]);
		tv1.setText(s[1]);
		tv2.setText(s[2]);
		tv3.setText(s[3]);
		tv4.setText(s[4]);
		tv5.setText(s[5]);
		tv6.setText(s[6]);
		tv7.setText(s[7]);
		
		//显示“签名”到ImageView
		byte[] b = bundle.getByteArray("sign");
		ImageView iv=(ImageView)findViewById(R.id.imageView1);
		Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
		iv.setImageBitmap(bmp);
				
	}
}
