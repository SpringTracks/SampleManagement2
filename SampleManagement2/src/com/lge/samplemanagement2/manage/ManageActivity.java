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

        // �õ�Activity��ActionBar
        ActionBar actionBar = getActionBar();
        // ����AcitonBar�Ĳ���ģ��
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // ��Activity��ͷ��ȥ��
        actionBar.setDisplayShowTitleEnabled(false);
        // ����Tab
        Tab model = actionBar.newTab().setText("�ͺŹ���");
        Tab user = actionBar.newTab().setText("��Ա����");
        // Ϊÿ��Tab���Listener
        MyTabListener modelListener = new MyTabListener(new ModelFragment());
        model.setTabListener(modelListener);
        MyTabListener userListener = new MyTabListener(new UserFragment());
        user.setTabListener(userListener);
        // ��Tab����ActionBar��
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
     * ʵ��ActionBar.TabListener�ӿ�
     */
    class MyTabListener implements TabListener
    {
        // ����ÿ��Tab��Ӧ��Fragment������
        private Fragment fragment;

        public MyTabListener(Fragment fragment)
        {
            this.fragment = fragment;
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft)
        {

        }

        // ��Tab��ѡ�е�ʱ����Ӷ�Ӧ��Fragment
        public void onTabSelected(Tab tab, FragmentTransaction ft)
        {
            ft.add(android.R.id.content, fragment, null);
        }

        // ��Tabû��ѡ�е�ʱ��ɾ����Ӧ�Ĵ�Tab��Ӧ��Fragment
        public void onTabUnselected(Tab tab, FragmentTransaction ft)
        {
            ft.remove(fragment);
        }
    }

}
