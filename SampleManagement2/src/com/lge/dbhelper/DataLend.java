package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * �ֻ������ LEND_TABLE_NAME = "LendTable";
 * @author qiangyt.zhang
 *
 */
public interface DataLend extends DataCommon {
	/**
	 * ��ѯ���н����������Ϣ
	 * @return
	 */
	public Cursor queryAllDataLend();
	
	/**
	 * �����ֻ����ѯ�ֻ���Ϣ
	 * @param phone_id
	 * @return
	 */
	public Cursor queryLendByPhoneIdIfVague(String phone_id, boolean vague);
	
	/**
	 * ���ֻ�������в������ݣ���Ϊ���ݱȽ϶࣬��˲��ṩ����һ�ַ���
	 * @param values
	 * @return
	 */
	public long insertDataToLend(ContentValues values);
	
	/**
	 * �����ֻ����ñ������
	 * @param values
	 * @param _id
	 * @return ���ظ���Ӱ��ļ�¼����
	 */
	public int updateLendBy_Id(ContentValues values, int _id);
}
