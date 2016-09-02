package com.lge.dbhelper;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * ���ݿ�Ĳ������ڴ˴�ʵ���˶���
 * @author qiangyt.zhang
 *
 */
public interface DataCommon {
	/**
	 * ���ݱ�������ѯ���е�����
	 * @param table
	 * @return ��ѯ���
	 */
	public Cursor queryAllData(String table);
	
	/**
	 * ��ָ�������ѯָ������
	 * @param table ����
	 * @param key  ����ָ������
	 * @param value  ��ѯ��ֵ
	 * @return ��ѯ���
	 */
	public Cursor queryDataByKey(String table, String key, String value);
	
	/**
	 * ģ�����ұ��������
	 * @param table ����
	 * @param key  ����ָ������
	 * @param value  ��ѯ��ֵ
	 * @return  ��ѯ���
	 */
	public Cursor queryVagueDataByKey(String table, String key, String value);
	
	/**
	 * ��ѯָ���������ݵ�����
	 * @param table ����
	 * @param key  ����ָ������
	 * @param value ��ѯ��ֵ
	 * @return
	 */
	public int queryDataCount(String table, String key, String value);
	
	/**
	 * ��ָ���ı����������
	 * @param table  ����
	 * @param values  ContentValues���������Ҫ���������
	 * @return
	 */
	public long insertDataToDB(String table, ContentValues values);
	
	/**
	 * ��ѯָ���������ݵ�����
	 * @param table ����
	 * @param key  ����ָ������
	 * @param value ��ѯ��ֵ
	 * @return
	 */
	public int deleteDataBykey(String table, String key, String value);
	/**
	 * ���ݿ�������֮��
	 */
	public void closeDataBase();
	
}
