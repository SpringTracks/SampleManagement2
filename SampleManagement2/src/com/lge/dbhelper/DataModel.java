package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataModel extends DataCommon {

	// 查询所有型号数据
	public Cursor queryAllDataModel();

	// 根据Phone_id和model_name准确查找信息
	public Cursor queryModelByPhoneId(String phone_id);
	public Cursor queryModelByName(String model_name);

	// 根据Phone_id和model_name模糊查询
	public Cursor queryVagueModelByPhoneId(String phone_id);
	public Cursor queryVagueModelByName(String model_name);

	// 插入数据成功返回 1， 插入数据失败，返回 -1
	public int insertDataToModel(String phone_id, String model_name);
	public long insertDataToModel(ContentValues values);

	// 删除数据成功,返回 1， 删除数据失败返回 -1
	public int deleteModelByPhoneId(String phone_id);

}
