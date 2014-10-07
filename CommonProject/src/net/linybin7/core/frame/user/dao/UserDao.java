package net.linybin7.core.frame.user.dao;

import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;


/**
 * �û�����DAO
 * 
 * 
 */
public interface UserDao {

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void save(User user);

	/**
	 * ɾ���û�
	 * 
	 * @param userCode
	 */
	public void delete(String userCode);

	/**
	 * ɾ���û���ɫ
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void deleteUserRoles(String[] userCodes);

	/**
	 * ɾ���û�
	 * 
	 * @param user
	 */
	public void delete(User user);

	/**
	 * �����û�
	 * 
	 * @param user
	 */
	public void update(User user);

	/**
	 * ����û�
	 * 
	 * @param userCode
	 * @return
	 */
	public User get(String userCode);

	/**
	 * ��ö���û�
	 * 
	 * @param userCodes
	 * @return
	 */
	public List get(String[] userCodes);

	/**
	 * ����û�����
	 * 
	 * @param userCode
	 * @return
	 */
	public String getPassword(String userCode);

	/**
	 * ��ѯ�û�
	 * 
	 * @param curUser
	 * @param conditionUser
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(User curUser, User conditionUser, int currPage, int pageSize);

	/**
	 * �������
	 * 
	 * @param curUser
	 * @param condition
	 * @return
	 */
	public int getCount(User curUser, final User condition);

	/**
	 * �����ɫ
	 * 
	 * @param userCode
	 * @param roleCodes
	 * @throws Exception
	 */
	public void assignRole(String userCode, String[] roleCodes);

	/**
	 * ��ý�ɫ
	 * 
	 * @param userCode
	 * @return
	 */
	public List getRole(String userCode);

	/**
	 * ���δ����Ľ�ɫ
	 * 
	 * @param userCode
	 * @return
	 */
	public List getUnssignRole(String userCode);

	/**
	 * �ı�״̬
	 * 
	 * @param userCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] userCodes, int state);

	/**
	 * ����û�Ȩ�޴���
	 * 
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getPuerviewCodes(String userCode);

	/**
	 * ���ָ��ϵͳ��Ӧ�û����Ի�����
	 * 
	 * @param sysId
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getIndividuation(String sysId, String userCode);

	/**
	 * ��ȡ���Ի�����
	 * 
	 * @param id
	 * @return
	 */
	public Individuation getIndividuation(Individuation id);

	/**
	 * ��������¸��Ի�����
	 * 
	 * @param individuation
	 */
	public void saveOrUpdate(Individuation individuation);

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String userCode);

	public List queryBy(String field, String value);

	public void saveOrUpdateIndividuation(Individuation individuation);

	public Individuation getIndividuation(String userCode);
	
	public List all();

}
