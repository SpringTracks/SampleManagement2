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
	// ��¼��ǰ�ĸ��ļ���
	File currentParent;
	// ��¼��ǰ·���µ������ļ����ļ�����
	File[] currentFiles;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_explorer);
		// ��ȡ�г�ȫ���ļ���ListView
		listView = (ListView) findViewById(R.id.listFile);
		textView = (TextView) findViewById(R.id.path);
		// ��ȡϵͳ��SD����Ŀ¼
		File root = new File(Environment.getExternalStorageDirectory().toString());
		// ��� SD������
		if (root.exists()) {
			currentParent = root;
			currentFiles = root.listFiles();
			// ʹ�õ�ǰĿ¼�µ�ȫ���ļ����ļ��������ListView
			inflateListView(currentFiles);
		}
		// ΪListView���б���ĵ����¼��󶨼�����
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// �û��������ļ��������ļ�·����������
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
				// ��ȡ�û�������ļ����µ������ļ�
				File[] tmp = currentFiles[position].listFiles();
				// ��ȡ�û��������б����Ӧ���ļ��У���Ϊ��ǰ�ĸ��ļ���
				currentParent = currentFiles[position]; // ��
				// ���浱ǰ�ĸ��ļ����ڵ�ȫ���ļ����ļ���
				currentFiles = tmp;
				// �ٴθ���ListView
				inflateListView(currentFiles);
				// }
			}
		});
		// ��ȡ��һ��Ŀ¼�İ�ť
		Button parent = (Button) findViewById(R.id.back);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				try {
					if (!currentParent.getCanonicalPath()
							.equals(Environment.getExternalStorageDirectory().toString())) {
						// ��ȡ��һ��Ŀ¼
						currentParent = currentParent.getParentFile();
						// �г���ǰĿ¼�������ļ�
						currentFiles = currentParent.listFiles();
						// �ٴθ���ListView
						inflateListView(currentFiles);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void inflateListView(File[] files) // ��
	{
		// ����һ��List���ϣ�List���ϵ�Ԫ����Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < files.length; i++) {
			Map<String, Object> listItem = new HashMap<String, Object>();
			// �����ǰFile���ļ��У�ʹ��folderͼ�ꣻ����ʹ��fileͼ��
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
			// ����List��
			listItems.add(listItem);
		}
		// ����һ��SimpleAdapter
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.file_list,
				new String[] { "icon", "fileName" }, new int[] { R.id.icon, R.id.file_name });
		// ΪListView����Adapter
		listView.setAdapter(simpleAdapter);
		try {
			textView.setText(currentParent.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}