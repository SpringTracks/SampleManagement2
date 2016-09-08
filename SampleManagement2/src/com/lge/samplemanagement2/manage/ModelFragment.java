package com.lge.samplemanagement2.manage;

import com.lge.samplemanagement2.R;
import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModelFragment extends Fragment {

	// private static final String TAG = "判断型号重复性";
	private String sample = DBOpenHandler.SAMPLE_TABLE_NAME;
	// private String employee = DBOpenHandler.EMPLOYEE_TABLE_NAME;

	private Button btn_ok;
	private Button btn_cancle;
	private EditText editText1;
	private EditText editText2;

	String sample_name = "";
	String sample_id = "";
	private ContentValues sample_values;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.manage_model, container, false);

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
			
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = getActivity();
				DBManager sampleManage = new DBManager(context);
				editText1 = (EditText) getActivity().findViewById(
						R.id.ModelEditText);
				editText2 = (EditText) getActivity().findViewById(
						R.id.ModelIDEditText);
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
				// TODO Auto-generated method stub
				//Toast.makeText(getActivity(), "数据插入成功", 0).show();
			}
		});

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "取消", 0).show();
			}
		});
	}

	public long checkRepeSample(String key) {
		int count = 0;
		Context context = getActivity();
		DBManager sampleManage2 = new DBManager(context);
		try {
			count = sampleManage2.queryDataCount(sample, "phone_id", key);
		} catch (Exception e) {
			Toast.makeText(getActivity(), "数据异常", 0).show();
		} finally {
			//Toast.makeText(getActivity(), "2", 0).show();
		}
		return count;
	}

}