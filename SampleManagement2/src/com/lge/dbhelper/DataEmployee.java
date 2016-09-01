package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

public interface DataEmployee extends DataCommon {
	
	public Cursor queryAllDataEmployee();
    /**
     *  查询数据
     * @param name ""查询全部数据，否则根据employee_id查询
     * @return
     */
    public Cursor queryPersonByEmployeeID(String employee_id);

    // 根据employee_id 模糊查询
    public Cursor queryVaguePersonByEmployeeID(String employee_id);

    // 插入数据成功返回 1， 插入数据失败，返回 -1
    public int insertDataToEmployee(String employee_id, String employee_name);
    public long insertDataToEmployee(ContentValues values);

    // 删除数据成功,返回 1， 删除数据失败返回 -1
    public int deletePersonByEmployeeID(String employee_id);
}
