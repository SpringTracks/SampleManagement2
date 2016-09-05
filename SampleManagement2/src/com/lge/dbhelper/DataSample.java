package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * 样机表SAMPLE_TABLE_NAME = "SampleTable"
 * @author qiangyt.zhang
 *
 */
public interface DataSample extends DataCommon {

	/**
	 *  查询手机表里的所有信息
	 * @return
	 */
	public Cursor queryAllDataSample();

	/**
	 * 根据指定的值来查询数据
	 * @param phone_id
	 * @param vague true 模糊查询
	 * @return
	 */
	public Cursor querySampleByPhoneIdIfVague(String phone_id, boolean vague);
	public Cursor querySampleByNameIfVague(String model_name, boolean vague);
	/**
	 * 根据手机二维码和型号名来搜索结果
	 * @param phone_id
	 * @param model_name
	 * @return
	 */
	public Cursor querySampleByIdAndNmae(String phone_id, String model_name);

	/**
	 * 向手机里插入数据
	 * @return
	 */
	public int insertDataToSample(String phone_id, String model_name);
	public long insertDataToSample(ContentValues values);

	/**
	 * 根据手机码删除手机表里的相关信息
	 * @param phone_id
	 * @return
	 */
	public int deleteSampleByPhoneId(String phone_id);

}
