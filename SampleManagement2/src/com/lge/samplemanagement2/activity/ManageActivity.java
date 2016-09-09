package com.lge.samplemanagement2.activity;

import java.util.ArrayList;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lge.samplemanagement2.R;
import com.lge.samplemanagement2.manage.*;

public class ManageActivity extends FragmentActivity {
	
	private ViewPager mPageVp;

	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;

	private TextView mTabSample, mTabEmployee;

	private ImageView mTabLineIv;

	private ModelFragment mSampleFg;
	private UserFragment mEmployeeFg;

	//private int currentIndex;

	private int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_main_activity);
		findById();
		init();
		initTabLineWidth();

	}

	private void findById() {
		mTabSample = (TextView) this.findViewById(R.id.id_sample_tv);
		mTabEmployee = (TextView) this.findViewById(R.id.id_employee_tv);

		mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);

		mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
	}

	
	private void init() {
		mSampleFg = new ModelFragment();
		mEmployeeFg = new UserFragment();
		mFragmentList.add(mSampleFg);
		mFragmentList.add(mEmployeeFg);

		mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);
	
		/**mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int state) {

			}

			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
						.getLayoutParams();

				Log.e("offset:", offset + "");

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 2) + currentIndex
							* (screenWidth / 2));
				}
				mTabLineIv.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					mTabSample.setTextColor(Color.BLUE);
					break;
				case 1:
					mTabEmployee.setTextColor(Color.BLUE);
					break;
				}
				currentIndex = position;
			}
		});*/

	}

	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		//lp.width = screenWidth / 2;
		lp.width = screenWidth ;
		mTabLineIv.setLayoutParams(lp);
	}

	/**private void resetTextView() {
		mTabSample.setTextColor(Color.BLACK);
		mTabEmployee.setTextColor(Color.BLACK);
	}*/

}
