package net.linybin7.pub.init.dao;

import java.util.List;

import net.linybin7.pub.data.bo.DataMgr;


public interface InitDAO {
	/**
	 * create or drop ���ݱ��
	 *@param sql ִ�е� sql
	 */
	public void execute(String sql); 
	
	/**
	 * ��ѯDataMgr��by orgId
	 *@param orgId ���ű��
	 *@return List DataMgr����
	 *
	 *@param queryType ��ѯ���� ��1���������ñ�0�����������ñ�
	 */
	public List query(String orgId,String toolName,int queryType);
	
	/**
	 * �������޸�DataMgr
	 *@param dataMgr 
	 */
	public void saveOrUpdate(DataMgr dataMgr);
	
	/**
	 * ��ѯ���ݱ��������
	 *@param tabName 
	 */
	public int getTabCount(String tabName);
	
	/**
	 * ɾ��DataMgr
	 *@param tabCodes 
	 */
	public void deleteTables(List tabCodes);
	/**
	 * ɾ��DataMgr
	 *@param tabCodes 
	 */
	public void dropTable(String tabCode);
	
	/**
	 *�鿴���Ƿ����
	 *@param tabCodes 
	 */
	public int isTab(String tabName);
	/**
	 * ��ȡ����Ϣ
	 * @author HuangHuaSheng
	 * 2010-11-2 ����11:31:38
	 * @param tabCode
	 * @return
	 */
	public DataMgr getDataMgrByCode(String tabCode);
	
	public boolean checkTables(String orgId,String tabName);
	
	public boolean checkTable(String tableName);
}
