package net.linybin7.core.frame.role.service;

import java.util.List;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;


/**
 * ��ɫ���������
 * 
 * 
 * 
 */
public interface RoleSve {
	/**
	 * ������ɫ
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void save(Role role) throws DuplicateKeyException;

	/**
	 * �޸Ľ�ɫ
	 * 
	 * @param role
	 * @throws Exception
	 */
	public void update(Role role);

	/**
	 * ɾ����ɫ
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(String[] roleCodes) throws Exception;

	/**
	 * ɾ����ɫ
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void delete(List roleCodes);

	/**
	 * ��ý�ɫ
	 * 
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public Role get(String roleCode);

	/**
	 * ��ȡ��Ӧ���ŵĽ�ɫ
	 * 
	 * @param orgs
	 * @return
	 */
	public List getByOrgs(List<Org> orgs);

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
	public int getCount(User curUser, final Role condition);

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
	 * ����
	 * 
	 * @param roleCodes
	 * @throws Exception
	 */
	public void start(String[] roleCodes);

	/**
	 * ͣ��
	 * 
	 * @param roleCodes
	 */
	public void stop(String[] roleCodes) throws Exception;

	/**
	 * �ж�ָ���Ľ�ɫ�Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode);
}
