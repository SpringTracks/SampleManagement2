package com.lge.samplemanagement2.manage;

import com.lge.samplemanagement2.R;
import com.lge.samplemanagement2.R.layout;
import com.lge.dbhelper.DBManager;
import com.lge.dbhelper.DBOpenHandler;
import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserFragment extends Fragment {

	private String employee = DBOpenHandler.EMPLOYEE_TABLE_NAME;

	private Button btn_ok;
	private Button btn_cancle;
	private EditText editText_AD;
	private EditText editText_Name;

	String AD = "";
	String Name = "";
	private ContentValues employee_values;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("ComputerFragment--->onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("ConputerFragment--->onCreateView");
		return inflater.inflate(R.layout.manage_employee, container, false);
	}

	@Override
	public void onStop() {
		System.out.println("ConputerFragment--->onStop");
		super.onStop();
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		btn_ok = (Button) getActivity().findViewById(R.id.bnedit);
		btn_cancle = (Button) getActivity().findViewById(R.id.bnCancel);
		editText_AD = (EditText) getActivity().findViewById(R.id.userEditText);
		editText_Name = (EditText) getActivity().findViewById(R.id.pwdEditText);

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Context context = getActivity();
				DBManager EmployeeManage = new DBManager(context);
				int editText1_length;
				int editText2_length;
				editText1_length = editText_AD.length();
				editText2_length = editText_Name.length();
				if (editText1_length == 0) {
					Toast.makeText(getActivity(), R.string.toast_user_AD, 0).show();
				} else if (editText2_length == 0) {
					Toast.makeText(getActivity(), R.string.toast_user_Name, 0).show();
				} else {
					AD = editText_AD.getText().toString();
					Name = editText_Name.getText().toString();
					employee_values = new ContentValues();
					employee_values.put("employee_id", AD);
					employee_values.put("employee_name", Name);

					String key1 = employee_values.get("employee_id").toString();
					long i = checkRepeEmployee(key1);
					if (i == 0) {
						EmployeeManage.insertDataToEmployee(employee_values);
						Toast.makeText(getActivity(), R.string.toast_Sample_manage1, 0).show();
						editText_AD.setText(null);
						editText_Name.setText(null);
					} else {
						Toast.makeText(getActivity(), R.string.toast_Sample_manage2, 0).show();
					}
					// TODO Auto-generated method stub
					// Toast.makeText(getActivity(), "数据插入成功", 0).show();
				}
			}
		});

		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int editText1_length;
				int editText2_length;
				editText1_length = editText_AD.length();
				editText2_length = editText_Name.length();
				if (editText1_length == 0 && editText2_length == 0) {
					Toast.makeText(getActivity(), R.string.toast_Sample_manage3, 0).show();
				} else {
					editText_AD.setText(null);
					editText_Name.setText(null);

					Toast.makeText(getActivity(), R.string.toast_Sample_manage4, 0).show();
				}
			}
		});
	}

	public long checkRepeEmployee(String key) {
		int count = 0;
		Context context = getActivity();
		DBManager EmployeeManage2 = new DBManager(context);
		try {
			count = EmployeeManage2
					.queryDataCount(employee, "employee_id", key);
		} catch (Exception e) {
			Toast.makeText(getActivity(), R.string.toast_Sample_manage5, 0).show();
		} finally {
		}
		return count;
	}

}