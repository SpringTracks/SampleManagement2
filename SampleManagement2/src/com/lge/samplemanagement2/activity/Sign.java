package com.lge.samplemanagement2.activity;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.lge.samplemanagement2.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class Sign extends Activity {
//	private ImageView imageSign;
	private PaintView mView;

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
			}
		});

		Button btnOk = (Button) findViewById(R.id.tablet_ok);
		btnOk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap imageBitmap = mView.getCachebBitmap();
//				imageSign.setImageBitmap(imageBitmap);
				byte[] imgbyte = img(imageBitmap);
				
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
}
