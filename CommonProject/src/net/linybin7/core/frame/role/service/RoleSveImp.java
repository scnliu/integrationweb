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
	 * ������ɫ
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void save(Role role) throws DuplicateKeyException {
		if (roleDao.get(role.getRoleCode()) != null) {
			throw new DuplicateKeyException("��ɫ����Ѿ�����");
		}
		roleDao.save(role);

	}

	/**
	 * �޸Ľ�ɫ
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void update(Role role) {
		roleDao.update(role);

	}

	/**
	 * ɾ����ɫ
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(String[] roleCodes) throws Exception {
		String msg = null;
		List<Role> roles = roleDao.get(roleCodes);
		for (Role role : roles) {
			if (role.getRoleProp() == Constants.ROLE_TYPE_SYS) {
				msg = "����ɾ�����������ɫ[" + role.getRoleName() + "(" + role.getRoleCode() + ")]";
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
	 * ɾ����ɫ
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
	 * ��ý�ɫ
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
	 * ��ȡ��Ӧ���ŵĽ�ɫ
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
	 * ��ѯ��ɫ
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
	 * �������
	 * 
	 * @param condition
	 * @return
	 */
	public int getCount(User curUser, final Role condition) {
		return roleDao.getCount(curUser, condition);
	}

	/**
	 * ����Ȩ��
	 * 
	 * @param roleCode
	 * @param funcCodes
	 * @throws Exception
	 */
	public void assignPurview(String roleCode, String[] funcCodes) {
		roleDao.assignPurview(roleCode, funcCodes);
	}

	/**
	 * ���Ȩ��
	 * 
	 * @param roleCode
	 * @return
	 */
	public List getPurviewCodes(String roleCode) {
		return roleDao.getPurviewCodes(roleCode);
	}

	/**
	 * ���н�ɫ
	 * 
	 * @return
	 */
	public List all() {
		return roleDao.all();
	}

	/**
	 * ���н�ɫ����
	 * 
	 * @return
	 */
	public List allCodes() {
		return allCodes();
	}

	/**
	 * ����
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void start(String[] roleCodes) {
		roleDao.changState(roleCodes, Constants.STOP_NO);
	}

	/**
	 * ͣ��
	 * 
	 * @param funcCode
	 */
	public void stop(String[] roleCodes) throws Exception {
		List<Role> roles = roleDao.get(roleCodes);
		String msg = null;
		for (Role role : roles) {
			if (role.getRoleProp() == Constants.ROLE_TYPE_SYS) {
				msg = "����ͣ�ó��������ɫ[" + role.getRoleName() + "(" + role.getRoleCode() + ")]";
				break;
			}
		}
		if (msg != null) {
			throw new Exception(msg);
		}
		roleDao.changState(roleCodes, Constants.STOP_YES);
	}

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode) {
		return roleDao.exist(roleCode);
	}
}
