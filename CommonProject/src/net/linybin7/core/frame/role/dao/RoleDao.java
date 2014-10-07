package net.linybin7.core.frame.role.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;


/**
 * 角色管理DAO
 * 
 * 
 */
public interface RoleDao {

	/**
	 * 新增角色
	 * 
	 * @param role
	 */
	public void save(Role role);

	/**
	 * 删除角色
	 * 
	 * @param roleCode
	 */
	public void delete(String roleCode);

	/**
	 * 删除用户角色
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void deleteUserRoles(String[] roleCodes);

	/**
	 * 删除角色功能
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void deleteRoleFuncs(String[] roleCodes);

	/**
	 * 删除角色
	 * 
	 * @param role
	 */
	public void delete(Role role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 */
	public void update(Role role);

	/**
	 * 获得角色
	 * 
	 * @param roleCode
	 * @return
	 */
	public Role get(String roleCode);

	/**
	 * 获得多个角色
	 * 
	 * @param userCodes
	 * @return
	 */
	public List get(String[] roleCodes);

	/**
	 * 获取对应部门的角色
	 * 
	 * @param orgCodes
	 * @return
	 */
	public List getByOrgs(String[] orgIds);

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
	public int getCount(final User curUser, final Role condition);

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
	 * 改变状态
	 * 
	 * @param roleCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] roleCodes, int state);

	/**
	 * 判断指定的角色是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode);
}
