package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * 手机借出表 LEND_TABLE_NAME = "LendTable";
 * @author qiangyt.zhang
 *
 */
public interface DataLend extends DataCommon {
	/**
	 * 查询所有借出表的相关信息
	 * @return
	 */
	public Cursor queryAllDataLend();
	
	/**
	 * 根据手机码查询手机信息
	 * @param phone_id
	 * @return
	 */
	public Cursor queryLendByPhoneIdIfVague(String phone_id, boolean vague);
	
	/**
	 * 向手机借出表中插入数据，因为数据比较多，因此不提供另外一种方法
	 * @param values
	 * @return
	 */
	public long insertDataToLend(ContentValues values);
	
	/**
	 * 更新手机借用表的数据
	 * @param values
	 * @param _id
	 * @return 返回更新影响的记录条数
	 */
	public int updateLendBy_Id(ContentValues values, int _id);
}
