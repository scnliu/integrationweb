package net.linybin7.core.frame.org.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserOrg;


/**
 * 部门管理DAO
 * 
 * 
 */
public interface OrgDao {

	/**
	 * 新增部门
	 * 
	 * @param org
	 */
	public void save(Org org);

	/**
	 * 删除部门
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * 删除部门
	 * 
	 * @param org
	 */
	public void delete(Org org);

	/**
	 * 更新部门
	 * 
	 * @param org
	 */
	public void update(Org org);

	/**
	 * 获得部门
	 * 
	 * @param id
	 * @return
	 */
	public Org get(String id);

	/**
	 * 查询部门
	 * 
	 * @param conditionOrg
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(Org conditionOrg, int currPage, int pageSize);

	/**
	 * 获得总数
	 * 
	 * @param condition
	 * @return
	 */
	public Long getCount(final Org condition);

	/**
	 * 获得所有部门
	 * 
	 * @return
	 */
	public List all();

	/**
	 * 获得所有部门编号
	 */
	public List allCode();

	// /**
	// * 获得用户部门编号
	// * @param userCode
	// * @return
	// */
	// public List getIds(String userCode);

	/**
	 * 保存顺序
	 * 
	 * @param orgs
	 */
	public void saveOrder(String[] orgs);

	/**
	 * 改变状态
	 * 
	 * @param ids
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] ids, int state);

	/**
	 * 获得所有下级部门
	 * 
	 * @param curUser
	 * @param list
	 */
	public void orgsList(Org curOrg, List list);

	/**
	 * 根据部门id获得部门名称
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds);

	/**
	 * 判断指定的部门是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String id);
	
	public void assignUser(String orgId,String[] userCodes);
	
	public List<User> getUser(String orgId);
	
	public List<User> getUnssignUser(String orgId);
	
	public List getUserOrgs(String userCode);
	
	
	
}
