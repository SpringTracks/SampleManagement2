package com.lge.samplemanagement2.manage;


import android.app.ActionBar;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.lge.samplemanagement2.R;
import com.lge.samplemanagement2.R.layout;


public class ManageActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        System.out.println("MainActivity--->onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_main_activity);

        // 得到Activity的ActionBar
        ActionBar actionBar = getActionBar();
        // 设置AcitonBar的操作模型
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // 将Activity的头部去掉
        actionBar.setDisplayShowTitleEnabled(false);
        // 生成Tab
        Tab model = actionBar.newTab().setText("型号管理");
        Tab user = actionBar.newTab().setText("人员管理");
        // 为每个Tab添加Listener
        MyTabListener modelListener = new MyTabListener(new ModelFragment());
        model.setTabListener(modelListener);
        MyTabListener userListener = new MyTabListener(new UserFragment());
        user.setTabListener(userListener);
        // 将Tab加入ActionBar中
        actionBar.addTab(model);
        actionBar.addTab(user);
    }

    @Override
    protected void onStop()
    {
        System.out.println("MainActivity--->onStop");
        super.onStop();
    }

    /**
     * 实现ActionBar.TabListener接口
     */
    class MyTabListener implements TabListener
    {
        // 接收每个Tab对应的Fragment，操作
        private Fragment fragment;

        public MyTabListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft)
        {

        }

        // 当Tab被选中的时候添加对应的Fragment
        public void onTabSelected(Tab tab, FragmentTransaction ft)
        {
            ft.add(android.R.id.content, fragment, null);
        }

        // 当Tab没被选中的时候删除对应的此Tab对应的Fragment
        public void onTabUnselected(Tab tab, FragmentTransaction ft)
        {
            ft.remove(fragment);
        }
    }

}
