package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;
/**
 * 人员表 EMPLOYEE_TABLE_NAME = "EmployeeTable"
 * @author qiangyt.zhang
 *
 */
public interface DataEmployee extends DataCommon {
	/**
	 * 查询人员的所有信息
	 * @return
	 */
	public Cursor queryAllDataEmployee();

	/**
	 * 根据employee_id查询人员
	 * @param employee_id
	 * @return
	 */
    public Cursor queryPersonByEmployeeIDIfVague(String employee_id, boolean vague);

    /**
     * 向人员表里插入信息
     * @return
     */
    public int insertDataToEmployee(String employee_id, String employee_name);
    public long insertDataToEmployee(ContentValues values);

    /**
     * 根据employee_id删除指定信息
     * @param employee_id
     * @return
     */
    public int deletePersonByEmployeeID(String employee_id);
}
