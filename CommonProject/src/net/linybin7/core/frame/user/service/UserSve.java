package net.linybin7.core.frame.user.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;


/**
 * �û����������
 * 
 * 
 * 
 */
public interface UserSve {
	/**
	 * �����û����÷����Ѿ���������м��ܴ���
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void save(User user) throws DuplicateKeyException;

	/**
	 * �޸��û����÷����Ѿ���������м��ܴ���
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void update(User user);

	/**
	 * ɾ���û�
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	public void delete(User curUser, String[] userCodes) throws Exception;

	/**
	 * ɾ���û�
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void delete(List userCodes);

	/**
	 * ����û�
	 * 
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User get(String userCode);

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
	 * @param curUser
	 * @param orgs
	 * @param userCode
	 * @return
	 */
	public List getUnssignRole(User curUser, List<Org> orgs, String userCode);

	/**
	 * ����
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void start(String[] userCodes) throws Exception;

	/**
	 * ͣ��
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	public void stop(User curUser, String[] userCodes) throws Exception;

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
	 * @param individuations
	 */
	public void saveOrUpdate(List<Individuation> individuations);

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String userCode);
	
	public void updatePhotoType(String userCode,String type);

	public List queryBy(final String field, final String value);

	public String updatePersonInfo(User user);
	
	public List all();
}
