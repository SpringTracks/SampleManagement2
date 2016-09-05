package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataReturn extends DataCommon {
	
	/**
	 * 根据手机码查询手机信息
	 * @param phone_id
	 * @return
	 */
	public Cursor queryLendByPhoneId(String phone_id);
	
	
	/**
	 * 向手机借用历史表中插入数据
	 * @param values
	 * @return
	 */
	public long insertDataToLendHistory(ContentValues values);
	
	/**
     * 根据phone_id删除指定信息
     * @param employee_id
     * @return
     */
    public int deleteLendByPhoneId(String phone_id);


}
