package com.lge.samplemanagement2.manage;

import com.lge.samplemanagement2.R;

import com.lge.samplemanagement2.activity.LendOutActivity;
import com.lge.samplemanagement2.activity.MipcaActivityCapture;
import com.lge.samplemanagement2.activity.QueryActivity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ModelFragment extends Fragment {

	// private static final String TAG = "判断型号重复性";
	private String sample = DBOpenHandler.SAMPLE_TABLE_NAME;
	// private String employee = DBOpenHandler.EMPLOYEE_TABLE_NAME;
	protected static final int SCANNIN_GREQUEST_CODE = 1;

	private Button btn_ok;
	private Button btn_cancle;
	private EditText editText1;
	private EditText editText2;
	private ImageView imageScan;
	String sample_name = "";
	String sample_id = "";
	private ContentValues sample_values;

	private final int result_ok = -1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.manage_sample, container, false);
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		btn_ok = (Button) getActivity().findViewById(R.id.bnedit1);
		btn_cancle = (Button) getActivity().findViewById(R.id.bnCancel1);
		editText1 = (EditText) getActivity().findViewById(R.id.ModelEditText);
		editText2 = (EditText) getActivity().findViewById(R.id.ModelIDEditText);
		imageScan = (ImageView) getActivity().findViewById(R.id.Scan);
			
		// 扫描添加Sample ID
		imageScan.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			}
		});

		// OK 添加样机进入数据库
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = getActivity();
				DBManager sampleManage = new DBManager(context);
				int editText1_length;
				int editText2_length;
				editText1_length = editText1.length();
				editText2_length = editText2.length();
				if (editText2_length == 0) {
					Toast.makeText(getActivity(), "请输入Sample ID", 0).show();
				} else if (editText1_length == 0) {
					Toast.makeText(getActivity(), "请输入Sample Name", 0).show();
				} else {
				sample_name = editText1.getText().toString();
				sample_id = editText2.getText().toString();
				sample_values = new ContentValues();
				sample_values.put("phone_id", sample_id);
				sample_values.put("model_name", sample_name);

				String key1 = sample_values.get("phone_id").toString();
				long i = checkRepeSample(key1);
				if (i == 0) {
					sampleManage.insertDataToSample(sample_values);
				Toast.makeText(getActivity(), "数据插入成功", 0).show();
				} else {
					Toast.makeText(getActivity(), "数据已存在，请检查", 0).show();
				}
				}
			}
		});

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int editText1_length;
				int editText2_length;
				editText1_length = editText1.length();
				editText2_length = editText2.length();
				if (editText1_length == 0 && editText2_length == 0) {
					Toast.makeText(getActivity(), "还未输入数据，不需清除", 0).show();
				} else {
					editText1.setText(null);
					editText2.setText(null);

					Toast.makeText(getActivity(), "数据已清除", 0).show();
				}
			}
		});
	}

	public int checkRepeSample(String key) {
		int count = 0;
		Context context = getActivity();
		DBManager sampleManage2 = new DBManager(context);
		try {
			count = sampleManage2.queryDataCount(sample, "phone_id", key);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "数据异常", 0).show();
		} finally {
		}
		return count;
	}

	// Get response from scan activity
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if (resultCode == result_ok) {
				Bundle bundle = data.getExtras();
				String imei = bundle.getString("result");
				editText2.setText(imei);

			}
			break;
		}

	}	
}