package com.lge.samplemanagement2.manage;

import com.lge.samplemanagement2.R;
import com.lge.dbhelper.DBManager;
import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ModelFragment extends Fragment {

	private Button btn_ok;
	private Button btn_cancle;
	private EditText editText1;
	private EditText editText2;

	String model_name = "";
	String model_id = "";
	private ContentValues model_values;

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
				DBManager modelManage = new DBManager(null);
				model_values = new ContentValues();

				editText1 = (EditText) getActivity().findViewById(
						R.id.ModelEditText);
				model_name = editText1.getText().toString();

				editText2 = (EditText) getActivity().findViewById(
						R.id.ModelIDEditText);
				model_id = editText2.getText().toString();

				model_values.put("phone_id", model_id);
				model_values.put("model_name", model_name);

				modelManage.insertDataToModel(model_values);

				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "数据插入成功", 0).show();
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
}