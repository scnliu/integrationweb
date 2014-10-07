package net.linybin7.pub.init.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.linybin7.common.db.SqlHelper;
import net.linybin7.pub.data.bo.DataMgr;
import net.linybin7.pub.tools.OrgTablePre;

import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class InitDAOImpl extends SimpleJdbcDaoSupport implements InitDAO {
	protected HibernateTemplate hbmTemp;
	public void setSessionFactory(SessionFactory fac){
		hbmTemp=new HibernateTemplate(fac);
	}
	public HibernateTemplate getHbmTemp() {
		return hbmTemp;
	}
	
	/**
	 * create or drop 数据表的
	 *@param sql 执行的 sql
	 */
	@Override
	public void execute(String sql) {
		getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 查询DataMgr的by orgId
	 *@param orgId 部门编号
	 *@return List DataMgr对象集
	 */
	@Override
	public List query(String orgId ,String toolName, int queryType) {
		if(orgId == null) orgId = "-1";
		String sql = "select t.* from DATAMGR t where t.orgId = '"+orgId+"'";
		//if(queryType==1) sql += " or t.tools = 'DATA'";System.out.println(sql);
		return getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 新增或修改DataMgr
	 *@param dataMgr 
	 */
	@Override
	public void saveOrUpdate(DataMgr dataMgr) {
		DataMgr entityObj = getDataMgrByCode(dataMgr.getTabCode());
		if(entityObj==null)
			getHbmTemp().save(dataMgr);
		else{
			if(dataMgr.getTabName()!=null&&!dataMgr.getTabName().equals(""))
				entityObj.setTabName(dataMgr.getTabName());
			if(dataMgr.getTools()!=null&&!dataMgr.getTools().equals(""))
				entityObj.setTools(dataMgr.getTools());
			entityObj.setOrgId((dataMgr.getOrgId()==null||dataMgr.getOrgId().equals(""))?"-1":dataMgr.getOrgId());
			if(dataMgr.getUserCode()!=null&&!dataMgr.getUserCode().equals(""))
				entityObj.setUserCode(dataMgr.getUserCode());
			if(dataMgr.getUserName()!=null&&!dataMgr.getUserName().equals(""))
				entityObj.setUserName(dataMgr.getUserName());
			if(dataMgr.getCreateTime()!=null&&!dataMgr.getCreateTime().equals(""))
				entityObj.setCreateTime(dataMgr.getCreateTime());
			if(dataMgr.getImpotTime()!=null&&!dataMgr.getImpotTime().equals(""))
				entityObj.setImpotTime(dataMgr.getImpotTime());
			if(dataMgr.getUserCode2()!=null&&!dataMgr.getUserCode2().equals(""))
				entityObj.setUserCode2(dataMgr.getUserCode2());
			if(dataMgr.getUserName2()!=null&&!dataMgr.getUserName2().equals(""))
				entityObj.setUserName2(dataMgr.getUserName2());
			getHbmTemp().setFlushMode(2);
			getHbmTemp().update(entityObj);
		}
	}
	@Override
	public DataMgr getDataMgrByCode(String tabCode){
		DataMgr dataMgr = (DataMgr)getHbmTemp().get(DataMgr.class, tabCode);
		return dataMgr;
	}
	
	/**
	 * 查询数据表的数据量
	 *@param tabName 
	 */
	@Override
	public int getTabCount(String tabName) {
		String hql = "select count(*) from "+ tabName;
		int i = getJdbcTemplate().queryForInt(hql);
		return i;
	}
	/**
	 * 删除TableMgr记录
	 * 
	 * @param DataMgr
	 * @param tabCodes
	 * @throws Exception
	 */
	@Override
	public void deleteTables(List tabCodes){
		for (int i = 0; i < tabCodes.size(); i++) {
			
			Map map = (Map)tabCodes.get(i);
			delete(map.get("tabCode").toString());
			try{
				int count = isTab(map.get("tabCode").toString());
				if(count>0)
					getJdbcTemplate().execute("DROP TABLE "+map.get("tabCode").toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 删除DataMgr
	 *@param tabCode 
	 */
	
	 void delete(String tabCode) {
		String sql = "delete from DataMgr t where t.tabCode=?";
		getJdbcTemplate().update(sql, new Object[]{tabCode});
	}
	 @Override
	 public void dropTable(String tabCode){
		//delete(tabCode);//删除在管理表的数据
		String sql = "DROP TABLE "+tabCode;
		getJdbcTemplate().execute(sql);
	 }
	
	/**
	 *查看表是否存在
	 *@param tabCodes 
	 */
	@Override
	public int isTab(String tabName) {
		String hql=SqlHelper.sql("com.eshine.pub.init.dao.InitDAOImpl.isTab");
		int count = getJdbcTemplate().queryForInt(hql,new Object[]{tabName});
		return count;
	}
	
	
	@Override
	public boolean checkTables(String orgId,String tabName){
		String table=OrgTablePre.getOrgTable(orgId,tabName);
		String sql="select count(*) from "+table;
		boolean count=true;
		try{
		   getJdbcTemplate().queryForInt(sql);
		}catch (Exception e) {
			count=false;
			
		}
		return count;
	}
	
	@Override
	public boolean checkTable(String tableName){
		String sql="select count(*) from "+tableName;
		try{
			getJdbcTemplate().queryForInt(sql);
			
		}catch (Exception e) {
		   return false;
		}
		return true;
	}
}
