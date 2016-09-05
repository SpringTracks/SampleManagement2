package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * 数据库的操作都在此处实现了定义
 * @author qiangyt.zhang
 *
 */
public interface DataCommon {
	/**
	 * 根据表名，查询所有的数据
	 * @param table
	 * @return 查询结果
	 */
	public Cursor queryAllData(String table);
	
	/**
	 * 在指定表里查询指定数据
	 * @param table 表名
	 * @param key  表中指定列名
	 * @param value  查询的值
	 * @return 查询结果
	 */
	public Cursor queryDataByKey(String table, String key, String value);
	
	/**
	 * 模糊查找表里的数据
	 * @param table 表名
	 * @param key  表中指定列名
	 * @param value  查询的值
	 * @return  查询结果
	 */
	public Cursor queryVagueDataByKey(String table, String key, String value);
	
	/**
	 * 根据条件A和条件B查询table中的数据
	 * @param table 要查询的表
	 * @param a  条件A
	 * @param b  条件B
	 * @return
	 */
	public Cursor queryDataByBAndA(String table, String KeyA, String KeyB, String a, String b);
	
	/**
	 * 查询指定表中数据的数量
	 * @param table 表名
	 * @param key  表中指定列名
	 * @param value 查询的值
	 * @return
	 */
	public int queryDataCount(String table, String key, String value);
	
	/**
	 * 向指定的表里插入数据
	 * @param table  表名
	 * @param values  ContentValues，里面包含要插入的数据
	 * @return
	 */
	public long insertDataToDB(String table, ContentValues values);
	
	/**
	 * 查询指定表中数据的数量
	 * @param table 表名
	 * @param key  表中指定列名
	 * @param value 查询的值
	 * @return
	 */
	public int deleteDataBykey(String table, String key, String value);
	
	/**
	 * 根据表中_id的值来更新数据
	 * @param table  要更新的表
	 * @param values  表中的数据
	 * @param id     表中的主键_id
	 * @return  返回更新影响的记录条数
	 */
	public int updateDataBy_id(String table, ContentValues values, int _id);
	/**
	 * 数据库操作完成之后
	 */
	public void closeDataBase();
	
}
