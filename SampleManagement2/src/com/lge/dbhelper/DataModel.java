package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * 样机表SAMPLE_TABLE_NAME = "SampleTable"
 * @author qiangyt.zhang
 *
 */
public interface DataModel extends DataCommon {

	/**
	 *  查询手机表里的所有信息
	 * @return
	 */
	public Cursor queryAllDataModel();

	/**
	 * 根据指定的值来查询数据
	 * @param phone_id
	 * @param vague true 模糊查询
	 * @return
	 */
	public Cursor queryModelByPhoneIdIfVague(String phone_id, boolean vague);
	public Cursor queryModelByNameIfVague(String model_name, boolean vague);

	/**
	 * 向手机里插入数据
	 * @return
	 */
	public int insertDataToModel(String phone_id, String model_name);
	public long insertDataToModel(ContentValues values);

	/**
	 * 根据手机码删除手机表里的相关信息
	 * @param phone_id
	 * @return
	 */
	public int deleteModelByPhoneId(String phone_id);

}
