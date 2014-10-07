package net.linybin7.core.frame.role.service;

import java.util.List;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;


/**
 * 角色管理服务类
 * 
 * 
 * 
 */
public interface RoleSve {
	/**
	 * 新增角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void save(Role role) throws DuplicateKeyException;

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void update(Role role);

	/**
	 * 删除角色
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(String[] roleCodes) throws Exception;

	/**
	 * 删除角色
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(List roleCodes);

	/**
	 * 获得角色
	 * 
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public Role get(String roleCode);

	/**
	 * 获取对应部门的角色
	 * 
	 * @param orgs
	 * @return
	 */
	public List getByOrgs(List<Org> orgs);

	/**
	 * 查询角色
	 * 
	 * @param curUser
	 * @param conditionRole
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(User curUser, Role conditionRole, int currPage, int pageSize);

	/**
	 * 获得总数
	 * 
	 * @param curUser
	 * @param condition
	 * @return
	 */
	public int getCount(User curUser, final Role condition);

	/**
	 * 分配权限
	 * 
	 * @param roleCode
	 * @param funcCodes
	 * @throws Exception
	 */
	public void assignPurview(String roleCode, String[] funcCodes);

	/**
	 * 获得权限
	 * 
	 * @param roleCode
	 * @return
	 */
	public List getPurviewCodes(String roleCode);

	/**
	 * 所有角色
	 * 
	 * @return
	 */
	public List all();

	/**
	 * 所有角色代码
	 * 
	 * @return
	 */
	public List allCodes();

	/**
	 * 启用
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void start(String[] roleCodes);

	/**
	 * 停用
	 * 
	 * @param roleCodes
	 */
	public void stop(String[] roleCodes) throws Exception;

	/**
	 * 判断指定的角色是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode);
}
