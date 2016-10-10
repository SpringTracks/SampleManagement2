package com.lge.samplemanagement2.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lge.samplemanagement2.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileExplorerActivity extends Activity {
	ListView listView;
	TextView textView;
	// 记录当前的父文件夹
	File currentParent;
	// 记录当前路径下的所有文件的文件数组
	File[] currentFiles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_explorer);
		// 获取列出全部文件的ListView
		listView = (ListView) findViewById(R.id.listFile);
		textView = (TextView) findViewById(R.id.path);
		// 获取系统的SD卡的目录
		File root = new File(Environment.getExternalStorageDirectory().toString());
		// 如果 SD卡存在
		if (root.exists()) {
			currentParent = root;
			currentFiles = root.listFiles();
			// 使用当前目录下的全部文件、文件夹来填充ListView
			inflateListView(currentFiles);
		}
		// 为ListView的列表项的单击事件绑定监听器
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 用户单击了文件，传回文件路径，并返回
				if (currentFiles[position].isFile()) {// zlp
					Intent i = new Intent();
					String filePath = currentFiles[position].getPath();
					Log.i("zlp--", "selected filePath=" + filePath);
					i.putExtra("filePath", filePath);
					FileExplorerActivity.this.setResult(RESULT_OK, i);
					FileExplorerActivity.this.finish();
					onDestroy();
					return;
				}
				// 获取用户点击的文件夹下的所有文件
				File[] tmp = currentFiles[position].listFiles();
				// 获取用户单击的列表项对应的文件夹，设为当前的父文件夹
				currentParent = currentFiles[position]; // ②
				// 保存当前的父文件夹内的全部文件和文件夹
				currentFiles = tmp;
				// 再次更新ListView
				inflateListView(currentFiles);
				// }
			}
		});
		// 获取上一级目录的按钮
		Button parent = (Button) findViewById(R.id.back);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				try {
					if (!currentParent.getCanonicalPath()
							.equals(Environment.getExternalStorageDirectory().toString())) {
						// 获取上一级目录
						currentParent = currentParent.getParentFile();
						// 列出当前目录下所有文件
						currentFiles = currentParent.listFiles();
						// 再次更新ListView
						inflateListView(currentFiles);
					}
					else
						Toast.makeText(FileExplorerActivity.this,R.string.root_dir,Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void inflateListView(File[] files) // ①
	{
		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			// 如果当前File是文件夹，使用folder图标；否则使用file图标
			if (files[i].isDirectory()) {
				listItem.put("icon", R.drawable.folder);
			} else {
				String name = files[i].getName();
				if (name.contains(".xls")) {
					listItem.put("icon", R.drawable.excel);
				} else
					listItem.put("icon", R.drawable.txt);
			}
			listItem.put("fileName", files[i].getName());
			// 添加List项
			listItems.add(listItem);
		}
		// 创建一个SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.file_list,
				new String[] { "icon", "fileName" }, new int[] { R.id.icon, R.id.file_name });
		// 为ListView设置Adapter
		listView.setAdapter(simpleAdapter);
		try {
			textView.setText("    "+currentParent.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
