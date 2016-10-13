package com.lge.samplemanagement2.activity;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.lge.samplemanagement2.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

public class Sign extends Activity {
private static final String TAG = null;
	//	private ImageView imageSign;
	private PaintView mView;
    private int signTag=0;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign);

		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.tablet_view);

		mView = new PaintView(this);
		frameLayout.addView(mView);
		mView.requestFocus();

		Button btnClear = (Button) findViewById(R.id.tablet_clear);
		btnClear.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mView.clear();
				signTag=0;
			}
		});

		Button btnOk = (Button) findViewById(R.id.tablet_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(signTag== 0){
					Toast.makeText(getApplicationContext(),R.string.Sign_warning,Toast.LENGTH_SHORT).show();
				}else {	
				Bitmap imageBitmap = mView.getCachebBitmap();
				System.out.println("输出压缩前大小"+getBitmapSize(imageBitmap));//输出压缩前大小
				Bitmap bt3 = ratio(imageBitmap);
				System.out.println("输出压缩hou大小"+getBitmapSize(bt3));//输出压缩hou大小
				
//				imageSign.setImageBitmap(imageBitmap);
				byte[] imgbyte = img(bt3);
				
//				Intent resultIntent = new Intent();
//				Bundle bundle = new Bundle();
//				bundle.putByteArray("result", imgbyte);
//				bundle.putParcelable("bitmap", barcode);
//				resultIntent.putExtra("result", imgbyte);
	//			this.setResult(RESULT_OK, resultIntent);
				System.out.println("111111111");
				Intent resultIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putByteArray("result", imgbyte);
//			   byte[]  bts = bundle.getByteArray("result");
//			   System.out.println(bts);
				resultIntent.putExtras(bundle);
				Sign.this.setResult(RESULT_OK, resultIntent);
			
			System.out.println(imgbyte);		
			Sign.this.finish();
				
//				Intent mIntent = new Intent(MainActivity.this, ShowImageActivity.class);
//				mIntent.putStringArrayListExtra("data", (ArrayList<String>)childList);
//				startActivity(mIntent);
				
//		        list = getIntent().getStringArrayListExtra("data");
				
			  }
			}
			private void setResult(int resultOk, Intent resultIntent) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	class PaintView extends View {
		private Paint paint;
		private Canvas cacheCanvas;
		private Bitmap cachebBitmap;
		private Path path;

		public Bitmap getCachebBitmap() {
			return cachebBitmap;
		}

		public PaintView(Context context) {
			super(context);
			init();
		}

		private void init() {
			paint = new Paint();
			paint.setAntiAlias(true);
			paint.setStrokeWidth(3);
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			path = new Path();
			cachebBitmap = Bitmap.createBitmap(10, 10, Config.ARGB_8888);
			cacheCanvas = new Canvas(cachebBitmap);
			cacheCanvas.drawColor(Color.WHITE);
		}

		public void clear() {
			if (cacheCanvas != null) {
				paint.setColor(Color.WHITE);
				cacheCanvas.drawPaint(paint);
				paint.setColor(Color.BLACK);
				cacheCanvas.drawColor(Color.WHITE);
				invalidate();
			}
		}

		@Override
		protected void onDraw(Canvas canvas) {
			// canvas.drawColor(BRUSH_COLOR);
			canvas.drawBitmap(cachebBitmap, 0, 0, null);
			canvas.drawPath(path, paint);

		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {

			int curW = cachebBitmap != null ? cachebBitmap.getWidth() : 0;
			int curH = cachebBitmap != null ? cachebBitmap.getHeight() : 0;
			if (curW >= w && curH >= h) {
				return;
			}

			if (curW < w)
				curW = w;
			if (curH < h)
				curH = h;

			Bitmap newBitmap = Bitmap.createBitmap(curW, curH, Bitmap.Config.ARGB_8888);
			Canvas newCanvas = new Canvas();
			newCanvas.setBitmap(newBitmap);
			if (cachebBitmap != null) {
				newCanvas.drawBitmap(cachebBitmap, 0, 0, null);
			}
			cachebBitmap = newBitmap;
			cacheCanvas = newCanvas;
		}

		private float cur_x, cur_y;

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			signTag=1;
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				cur_x = x;
				cur_y = y;
				path.moveTo(cur_x, cur_y);
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				path.quadTo(cur_x, cur_y, x, y);
				cur_x = x;
				cur_y = y;
				break;
			}
			case MotionEvent.ACTION_UP: {
				cacheCanvas.drawPath(path, paint);
				path.reset();
				break;
			}
			}
			invalidate();
			return true;
		}
	}
	public static byte[] img(Bitmap bitmap)
	{
	     ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	     return baos.toByteArray();
	}
	/** 
	  * 得到bitmap的大小 
	  */  
	 @SuppressLint("NewApi") public static int getBitmapSize(Bitmap bitmap) {  
	     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19  
	         return bitmap.getAllocationByteCount();  
	     }  
	     if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12  
	         return bitmap.getByteCount();  
	     }  
	     // 在低版本中用一行的字节x高度  
	     return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version  
	 }  
	 public Bitmap ratio(Bitmap image) {  
	        ByteArrayOutputStream os = new ByteArrayOutputStream();  
	        image.compress(Bitmap.CompressFormat.PNG, 100, os);  
	        if( os.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出      
	            os.reset();//重置baos即清空baos    
	            image.compress(Bitmap.CompressFormat.PNG, 50, os);//这里压缩50%，把压缩后的数据存放到baos中    
	        }    
	        ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());    
	        BitmapFactory.Options newOpts = new BitmapFactory.Options();    
	        //开始读入图片，此时把options.inJustDecodeBounds 设回true了    
	        newOpts.inJustDecodeBounds = true;  
	        newOpts.inPreferredConfig = Config.RGB_565;  
	        Bitmap bitmap = BitmapFactory.decodeStream(is, null, newOpts);    
	        newOpts.inJustDecodeBounds = false;    
	        int w = newOpts.outWidth;    
	        int h = newOpts.outHeight;    
	        float hh = 160f;// 设置高度为240f时，可以明显看到图片缩小了  
	        float ww = 240f;// 设置宽度为120f，可以明显看到图片缩小了  
	        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可    
	        int be = 1;//be=1表示不缩放    
	        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放    
	            be = (int) (newOpts.outWidth / ww);    
	        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放    
	            be = (int) (newOpts.outHeight / hh);    
	        }    
	        if (be <= 0) be = 1;    
	        newOpts.inSampleSize = be;//设置缩放比例    
	        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了    
	        is = new ByteArrayInputStream(os.toByteArray());    
	        bitmap = BitmapFactory.decodeStream(is, null, newOpts);  
	        //压缩好比例大小后再进行质量压缩  
//	      return compress(bitmap, maxSize); // 这里再进行质量压缩的意义不大，反而耗资源，删除  
	        return bitmap;  
	    }  
	 //屏幕旋转操作，不再重新绘制activity，但压缩后图像质量太差暂不使用（android:configChanges="orientation|screenSize" ）
//		@Override
//		public void onConfigurationChanged(Configuration newConfig) {
//			Log.i(TAG,"onConfigurationChanged!!!");	
//		super.onConfigurationChanged(newConfig);
//		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//		// land do nothing is ok
//			Log.i(TAG,"ORIENTATION_LANDSCAPE");
//		} else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//			Log.i(TAG,"ORIENTATION_PORTRAIT");
//		}
//		}
}
