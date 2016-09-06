package com.lge.samplemanagement2.manage;

import com.lge.samplemanagement2.R;
import com.lge.samplemanagement2.R.layout;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.out.println("ComputerFragment--->onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("ConputerFragment--->onCreateView");
		return inflater.inflate(R.layout.manage_uesr, container, false);
	}

	@Override
	public void onStop() {
		System.out.println("ConputerFragment--->onStop");
		super.onStop();
	}
}