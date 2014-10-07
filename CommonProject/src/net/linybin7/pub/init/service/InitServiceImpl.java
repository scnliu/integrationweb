package net.linybin7.pub.init.service;

import java.text.ParseException;
import java.util.List;

import net.linybin7.core.frame.bo.User;
import net.linybin7.pub.data.bo.DataMgr;
import net.linybin7.pub.init.dao.InitDAO;
import net.linybin7.pub.tools.OrgTablePre;


public class InitServiceImpl implements InitService {
	InitDAO dao;
	
	public InitDAO getDao() {
		return dao;
	}

	public void setDao(InitDAO dao) {
		this.dao = dao;
	}

	/**
	 * create or drop 数据表的
	 *@param sql 执行的 sql
	 */
	@Override
	public void execute(String sql) {
		dao.execute(sql);
	}

	/**
	 * 查询DataMgr的by orgId
	 *@param orgId 部门编号
	 *@return List DataMgr对象集
	 */
	@Override
	public List<DataMgr> query(String orgId,String toolName, int queryType) {
		// TODO Auto-generated method stub
		List list = dao.query(orgId,toolName,queryType);
//		List<DataMgr> tList = new ArrayList<DataMgr>();
//		if(list!=null&&list.size()>0){
//			for(int i=0;i<list.size();i++){
//				DataMgr tableMgr = (DataMgr)list.get(i);
//				String tabCode = tableMgr.getTabCode();
//				if(tabCode!=null&&!tabCode.equals("")){
//					int count = -1;
//					try{
//						if(dao.isTab(tabCode)>0)
//							count = dao.getTabCount(tabCode);
//					}catch(Exception e){
//						e.printStackTrace();
//					}
//					if(count==-1)
//						tableMgr.setTabCount("表不存在");
//					else
//						tableMgr.setTabCount(count+"");
//				}
//				tList.add(tableMgr);
//			}
//		}
		return list;
	}

	/**
	 * 新增或修改DataMgr
	 *@param dataMgr 
	 */
	@Override
	public void saveOrUpdate(DataMgr tableMgr) {
		dao.saveOrUpdate(tableMgr);
		
	}
	
	/**
	 * 查询数据表的数据量
	 *@param tabName 
	 */
	@Override
	public int getTabCount(String tabCode) {
		// TODO Auto-generated method stub
		return dao.getTabCount(tabCode);
	}
	
	/**
	 * 删除DataMgr
	 *@param tabCodes 
	 */
	@Override
	public void deleteTables(List<DataMgr> tabCodes){
		dao.deleteTables(tabCodes);
	}
	
	@Override
	public void delete(String tabCode){
		dao.dropTable(tabCode);
	}
	
	/**
	 *查看表是否存在
	 *@param tabCodes 
	 */
	@Override
	public int isTab(String tabName) {
		return dao.isTab(tabName);
	}

	@Override
	public DataMgr getDataMgrByUser(User u, String tableName,String tableType) {
		String table=OrgTablePre.getOrgTable(u.getOrgId(), tableName);
		return dao.getDataMgrByCode(table);
	}
	
	
	@Override
	public boolean checkTables(String orgId,String tabName){
		return dao.checkTables(orgId, tabName);
	}
	
	/**
	 * 检查表是否存在
	 */
	@Override
	public boolean checkTable(User u,String tableName,String tableType){
		String table=OrgTablePre.getOrgTable(u.getOrgId(), tableName);
		return dao.checkTable(table);
	}
}
