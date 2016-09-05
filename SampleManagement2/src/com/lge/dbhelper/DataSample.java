package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * ������SAMPLE_TABLE_NAME = "SampleTable"
 * @author qiangyt.zhang
 *
 */
public interface DataSample extends DataCommon {

	/**
	 *  ��ѯ�ֻ������������Ϣ
	 * @return
	 */
	public Cursor queryAllDataSample();

	/**
	 * ����ָ����ֵ����ѯ����
	 * @param phone_id
	 * @param vague true ģ����ѯ
	 * @return
	 */
	public Cursor querySampleByPhoneIdIfVague(String phone_id, boolean vague);
	public Cursor querySampleByNameIfVague(String model_name, boolean vague);
	/**
	 * �����ֻ���ά����ͺ������������
	 * @param phone_id
	 * @param model_name
	 * @return
	 */
	public Cursor querySampleByIdAndNmae(String phone_id, String model_name);

	/**
	 * ���ֻ����������
	 * @return
	 */
	public int insertDataToSample(String phone_id, String model_name);
	public long insertDataToSample(ContentValues values);

	/**
	 * �����ֻ���ɾ���ֻ�����������Ϣ
	 * @param phone_id
	 * @return
	 */
	public int deleteSampleByPhoneId(String phone_id);

}
