package net.linybin7.core.frame.role.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;


/**
 * ��ɫ����DAO
 * 
 * 
 */
public interface RoleDao {

	/**
	 * ������ɫ
	 * 
	 * @param role
	 */
	public void save(Role role);

	/**
	 * ɾ����ɫ
	 * 
	 * @param roleCode
	 */
	public void delete(String roleCode);

	/**
	 * ɾ���û���ɫ
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void deleteUserRoles(String[] roleCodes);

	/**
	 * ɾ����ɫ����
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void deleteRoleFuncs(String[] roleCodes);

	/**
	 * ɾ����ɫ
	 * 
	 * @param role
	 */
	public void delete(Role role);

	/**
	 * ���½�ɫ
	 * 
	 * @param role
	 */
	public void update(Role role);

	/**
	 * ��ý�ɫ
	 * 
	 * @param roleCode
	 * @return
	 */
	public Role get(String roleCode);

	/**
	 * ��ö����ɫ
	 * 
	 * @param userCodes
	 * @return
	 */
	public List get(String[] roleCodes);

	/**
	 * ��ȡ��Ӧ���ŵĽ�ɫ
	 * 
	 * @param orgCodes
	 * @return
	 */
	public List getByOrgs(String[] orgIds);

	/**
	 * ��ѯ��ɫ
	 * 
	 * @param curUser
	 * @param conditionRole
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(User curUser, Role conditionRole, int currPage, int pageSize);

	/**
	 * �������
	 * 
	 * @param curUser
	 * @param condition
	 * @return
	 */
	public int getCount(final User curUser, final Role condition);

	/**
	 * ����Ȩ��
	 * 
	 * @param roleCode
	 * @param funcCodes
	 * @throws Exception
	 */
	public void assignPurview(String roleCode, String[] funcCodes);

	/**
	 * ���Ȩ��
	 * 
	 * @param roleCode
	 * @return
	 */
	public List getPurviewCodes(String roleCode);

	/**
	 * ���н�ɫ
	 * 
	 * @return
	 */
	public List all();

	/**
	 * ���н�ɫ����
	 * 
	 * @return
	 */
	public List allCodes();

	/**
	 * �ı�״̬
	 * 
	 * @param roleCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] roleCodes, int state);

	/**
	 * �ж�ָ���Ľ�ɫ�Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode);
}
