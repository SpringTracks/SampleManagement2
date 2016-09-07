package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * ��Ա�� EMPLOYEE_TABLE_NAME = "EmployeeTable"
 * @author qiangyt.zhang
 *
 */
public interface DataEmployee extends DataCommon {
	/**
	 * ��ѯ��Ա��������Ϣ
	 * @return
	 */
	public Cursor queryAllDataEmployee();

	/**
	 * ����employee_id��ѯ��Ա
	 * @param employee_id
	 * @return
	 */
    public Cursor queryPersonByEmployeeIDIfVague(String employee_id, boolean vague);

    /**
     * ����Ա���������Ϣ
     * @return
     */
    public int insertDataToEmployee(String employee_id, String employee_name);
    public long insertDataToEmployee(ContentValues values);
    
    /**
     * ����Ա���в����������ȸ���employee_id�ж������Ƿ����ظ�
     * @param values
     * @return ���ظ����ݷ���-1�����������ݣ����򷵻ز���Ӱ�������
     */
    public long insertDataToEmployeeIfNoRepeatValue(ContentValues values);

    /**
     * ����employee_idɾ��ָ����Ϣ
     * @param employee_id
     * @return
     */
    public int deletePersonByEmployeeID(String employee_id);
    
    /**
     * ���ݱ��еĵ�һ�е�����������Ա��Ϣ
     * @param values
     * @param _id
     * @return ���ظ���Ӱ��ļ�¼����
     */
    public int updatePersonBy_Id(ContentValues values, int _id);
}
