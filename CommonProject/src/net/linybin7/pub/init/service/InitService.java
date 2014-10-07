package net.linybin7.pub.init.service;

import java.text.ParseException;
import java.util.List;

import net.linybin7.core.frame.bo.User;
import net.linybin7.pub.data.bo.DataMgr;

public interface InitService {
	/**
	 * create or drop ���ݱ��
	 *@param sql ִ�е� sql
	 */
	public void execute(String sql); 
	
	/**
	 * ��ѯDataMgr��by orgId
	 *@param orgId ���ű��
	 *@return List DataMgr����
	 *@param queryType ��ѯ���� ��1���������ñ�0�����������ñ�
	 */
	public List query(String orgId,String toolName, int queryType);
	
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
	public void deleteTables(List<DataMgr> tabCodes);
	
	/**
	 * ɾ��DataMgr
	 *@param tabCodes 
	 */
	public void delete(String tabCode);
	
	/**
	 *�鿴���Ƿ����
	 *@param tabCodes 
	 */
	public int isTab(String tabName);
	
	/**
	 * ͨ���û���ѯ����Ϣ
	 * @author HuangHuaSheng
	 * 2010-11-2 ����11:29:52
	 * @param u
	 * @param tableType
	 * @return
	 */
	public DataMgr getDataMgrByUser(User u,  String tableName,String tableType);
	
	public boolean checkTables(String orgId,String tabName);
	
	/**
	 * �����Ƿ����
	 */
	public boolean checkTable(User u,String tableName,String tableType);
}
