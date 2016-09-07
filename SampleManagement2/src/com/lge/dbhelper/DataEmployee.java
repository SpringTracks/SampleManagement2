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
     * 向人员表中插入数据首先根据employee_id判断数据是否有重复
     * @param values
     * @return 有重复数据返回-1，不插入数据，否则返回插入影响的行数
     */
    public long insertDataToEmployeeIfNoRepeatValue(ContentValues values);

    /**
     * 根据employee_id删除指定信息
     * @param employee_id
     * @return
     */
    public int deletePersonByEmployeeID(String employee_id);
    
    /**
     * 根据表中的第一列的主键更新人员信息
     * @param values
     * @param _id
     * @return 返回更新影响的记录条数
     */
    public int updatePersonBy_Id(ContentValues values, int _id);
}
