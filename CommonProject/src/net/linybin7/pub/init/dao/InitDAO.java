package net.linybin7.pub.init.dao;

import java.util.List;

import net.linybin7.pub.data.bo.DataMgr;


public interface InitDAO {
	/**
	 * create or drop 数据表的
	 *@param sql 执行的 sql
	 */
	public void execute(String sql); 
	
	/**
	 * 查询DataMgr的by orgId
	 *@param orgId 部门编号
	 *@return List DataMgr对象集
	 *
	 *@param queryType 查询类型 （1：包含共用表；0：不包含共用表）
	 */
	public List query(String orgId,String toolName,int queryType);
	
	/**
	 * 新增或修改DataMgr
	 *@param dataMgr 
	 */
	public void saveOrUpdate(DataMgr dataMgr);
	
	/**
	 * 查询数据表的数据量
	 *@param tabName 
	 */
	public int getTabCount(String tabName);
	
	/**
	 * 删除DataMgr
	 *@param tabCodes 
	 */
	public void deleteTables(List tabCodes);
	/**
	 * 删除DataMgr
	 *@param tabCodes 
	 */
	public void dropTable(String tabCode);
	
	/**
	 *查看表是否存在
	 *@param tabCodes 
	 */
	public int isTab(String tabName);
	/**
	 * 获取表信息
	 * @author HuangHuaSheng
	 * 2010-11-2 上午11:31:38
	 * @param tabCode
	 * @return
	 */
	public DataMgr getDataMgrByCode(String tabCode);
	
	public boolean checkTables(String orgId,String tabName);
	
	public boolean checkTable(String tableName);
}
