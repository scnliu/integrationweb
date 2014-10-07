package net.linybin7.core.frame.org.service;

import java.util.List;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserOrg;
import net.linybin7.core.frame.org.dao.OrgDao;


/**
 * ���Ź��������
 * 
 * 
 * 
 */
public interface OrgSve {
	/**
	 * ��������
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void save(Org org) throws DuplicateKeyException;

	/**
	 * �޸Ĳ���
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void update(Org org);

	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String id);
	
	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids);

	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(List ids);

	/**
	 * ��ò���
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Org get(String id);

	/**
	 * ��ѯ����
	 * 
	 * @param conditionOrg
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(Org conditionOrg, int currPage, int pageSize);

	/**
	 * �������
	 * 
	 * @param condition
	 * @return
	 */
	public Long getCount(final Org condition);

	/**
	 * ������в���
	 * 
	 * @return
	 */
	public List all();

	/**
	 * ������в��ű��
	 */
	public List allCode();

	// /**
	// * ����û����ű��
	// * @param userCode
	// * @return
	// */
	// public List getIds(String userCode);

	/**
	 * ����˳��
	 * 
	 * @param orgs
	 */
	public void saveOrder(String[] orgs);

	/**
	 * ����
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void start(String[] ids);

	/**
	 * ͣ��
	 * 
	 * @param id
	 */
	public void stop(String[] ids);

	/**
	 * ��õ�ǰ�û����ڲ����Լ������¼�����;
	 * 
	 * @param user
	 * @param list
	 */
	public List<Org> orgsList(User user);

	/**
	 * ���ݲ���id��ò�������
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds);

	/**
	 * ���ݲ���id��ò�������
	 * 
	 * @param orgId
	 * @return
	 */
	public String orgsNC(String orgId);

	/**
	 * �ж�ָ���Ĳ����Ƿ����
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
