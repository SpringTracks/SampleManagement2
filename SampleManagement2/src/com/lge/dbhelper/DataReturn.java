package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataReturn extends DataCommon {
	
	/**
	 * �����ֻ����ѯ�ֻ���Ϣ
	 * @param phone_id
	 * @return
	 */
	public Cursor queryLendByPhoneId(String phone_id);
	
	
	/**
	 * ���ֻ�������ʷ���в�������
	 * @param values
	 * @return
	 */
	public long insertDataToLendHistory(ContentValues values);
	
	/**
     * ����phone_idɾ��ָ����Ϣ
     * @param employee_id
     * @return
     */
    public int deleteLendByPhoneId(String phone_id);


}
