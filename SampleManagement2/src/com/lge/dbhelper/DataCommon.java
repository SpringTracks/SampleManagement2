package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 所有数据库的操作
 * @author qiangyt.zhang
 *
 */
public interface DataCommon {
	/**
	 * 根据表名查询所有数据
	 * @param tablename
	 * @return 返回查询数据
	 */
	public Cursor selectAllData(String tablename);
	// 根据表名，向数据库里面插入数据
	public long insertDataToDB(String table, ContentValues values);
	// 查询数据库数据的数量
	public long getCount();
	// 数据库操作之后关闭数据库
	void closeDataBase();
	
}