package net.linybin7.core.frame.role.service;

import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.org.dao.OrgDao;
import net.linybin7.core.frame.role.dao.RoleDao;
import net.linybin7.core.util.Constants;


/**
 * 

 * 
 */
public class RoleSveImp implements RoleSve {
	private RoleDao roleDao;

	private OrgDao orgDao;

	public RoleSveImp() {
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	/**
	 * 新增角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void save(Role role) throws DuplicateKeyException {
		if (roleDao.get(role.getRoleCode()) != null) {
			throw new DuplicateKeyException("角色编号已经存在");
		}
		roleDao.save(role);

	}

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void update(Role role) {
		roleDao.update(role);

	}

	/**
	 * 删除角色
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(String[] roleCodes) throws Exception {
		String msg = null;
		List<Role> roles = roleDao.get(roleCodes);
		for (Role role : roles) {
			if (role.getRoleProp() == Constants.ROLE_TYPE_SYS) {
				msg = "不能删除超级管理角色[" + role.getRoleName() + "(" + role.getRoleCode() + ")]";
				break;
			}
		}

		if (msg != null) {
			throw new Exception(msg);
		}
		for (Role role : roles) {
			roleDao.delete(role);
		}
		roleDao.deleteUserRoles(roleCodes);
		roleDao.deleteRoleFuncs(roleCodes);

	}

	/**
	 * 删除角色
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(List roles) {
		for (int i = 0; i < roles.size(); i++) {
			roleDao.delete((Role) roles.get(i));
		}
	}

	/**
	 * 获得角色
	 * 
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public Role get(String roleCode) {

		Role role = roleDao.get(roleCode);
		if (role != null && !StringHelper.isEmpty(role.getOrgId())) {
			Org org = orgDao.get(role.getOrgId());
			if (org != null) {
				role.setOrgName(org.getOrgName());
			}
		}
		return role;
	}

	/**
	 * 获取对应部门的角色
	 * 
	 * @param orgs
	 * @return
	 */
	public List getByOrgs(List<Org> orgs) {
		if (orgs == null || orgs.size() == 0) {
			return new ArrayList();
		}
		String[] orgCodes = new String[orgs.size()];
		for (int i = 0; i < orgs.size(); i++) {
			orgCodes[i] = orgs.get(i).getId();
		}
		return roleDao.getByOrgs(orgCodes);
	}

	/**
	 * 查询角色
	 * 
	 * @param conditionRole
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(User curUser, Role conditionRole, int currPage, int pageSize) {
		List<Role> list = roleDao.query(curUser, conditionRole, currPage, pageSize);
		if (list.size() > 0) {
			List orgIds = new ArrayList();
			for (Role role : list) {
				if (!StringHelper.isEmpty(role.getOrgId())) {
					orgIds.add(role.getOrgId());
				}
			}
			List<Object[]> ncs = orgDao.orgsNC(orgIds);
			for (Role role : list) {
				for (Object[] nc : ncs) {
					if (!StringHelper.isEmpty(role.getOrgId()) && role.getOrgId().equals(nc[1])) {
						role.setOrgName((String) nc[0]);
						break;
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获得总数
	 * 
	 * @param condition
	 * @return
	 */
	public int getCount(User curUser, final Role condition) {
		return roleDao.getCount(curUser, condition);
	}

	/**
	 * 分配权限
	 * 
	 * @param roleCode
	 * @param funcCodes
	 * @throws Exception
	 */
	public void assignPurview(String roleCode, String[] funcCodes) {
		roleDao.assignPurview(roleCode, funcCodes);
	}

	/**
	 * 获得权限
	 * 
	 * @param roleCode
	 * @return
	 */
	public List getPurviewCodes(String roleCode) {
		return roleDao.getPurviewCodes(roleCode);
	}

	/**
	 * 所有角色
	 * 
	 * @return
	 */
	public List all() {
		return roleDao.all();
	}

	/**
	 * 所有角色代码
	 * 
	 * @return
	 */
	public List allCodes() {
		return allCodes();
	}

	/**
	 * 启用
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void start(String[] roleCodes) {
		roleDao.changState(roleCodes, Constants.STOP_NO);
	}

	/**
	 * 停用
	 * 
	 * @param funcCode
	 */
	public void stop(String[] roleCodes) throws Exception {
		List<Role> roles = roleDao.get(roleCodes);
		String msg = null;
		for (Role role : roles) {
			if (role.getRoleProp() == Constants.ROLE_TYPE_SYS) {
				msg = "不能停用超级管理角色[" + role.getRoleName() + "(" + role.getRoleCode() + ")]";
				break;
			}
		}
		if (msg != null) {
			throw new Exception(msg);
		}
		roleDao.changState(roleCodes, Constants.STOP_YES);
	}

	/**
	 * 判断指定的用户是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode) {
		return roleDao.exist(roleCode);
	}
}
