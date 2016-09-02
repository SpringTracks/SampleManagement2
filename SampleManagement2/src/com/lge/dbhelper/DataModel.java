package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * ������SAMPLE_TABLE_NAME = "SampleTable"
 * @author qiangyt.zhang
 *
 */
public interface DataModel extends DataCommon {

	/**
	 *  ��ѯ�ֻ������������Ϣ
	 * @return
	 */
	public Cursor queryAllDataModel();

	/**
	 * ����ָ����ֵ����ѯ����
	 * @param phone_id
	 * @param vague true ģ����ѯ
	 * @return
	 */
	public Cursor queryModelByPhoneIdIfVague(String phone_id, boolean vague);
	public Cursor queryModelByNameIfVague(String model_name, boolean vague);

	/**
	 * ���ֻ����������
	 * @return
	 */
	public int insertDataToModel(String phone_id, String model_name);
	public long insertDataToModel(ContentValues values);

	/**
	 * �����ֻ���ɾ���ֻ�����������Ϣ
	 * @param phone_id
	 * @return
	 */
	public int deleteModelByPhoneId(String phone_id);

}
