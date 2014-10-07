package net.linybin7.core.frame.org.service;

import java.util.List;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserOrg;
import net.linybin7.core.frame.org.dao.OrgDao;


/**
 * 部门管理服务类
 * 
 * 
 * 
 */
public interface OrgSve {
	/**
	 * 新增部门
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void save(Org org) throws DuplicateKeyException;

	/**
	 * 修改部门
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void update(Org org);

	/**
	 * 删除部门
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String id);
	
	/**
	 * 删除部门
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids);

	/**
	 * 删除部门
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(List ids);

	/**
	 * 获得部门
	 * 
	 * @param id
	 * @return
	 * @throws Exception
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
	 * 启用
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void start(String[] ids);

	/**
	 * 停用
	 * 
	 * @param id
	 */
	public void stop(String[] ids);

	/**
	 * 获得当前用户所在部门以及所有下级部门;
	 * 
	 * @param user
	 * @param list
	 */
	public List<Org> orgsList(User user);

	/**
	 * 根据部门id获得部门名称
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds);

	/**
	 * 根据部门id获得部门名称
	 * 
	 * @param orgId
	 * @return
	 */
	public String orgsNC(String orgId);

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
