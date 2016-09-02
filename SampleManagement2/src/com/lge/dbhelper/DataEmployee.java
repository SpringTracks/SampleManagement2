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
     * ����employee_idɾ��ָ����Ϣ
     * @param employee_id
     * @return
     */
    public int deletePersonByEmployeeID(String employee_id);
}
