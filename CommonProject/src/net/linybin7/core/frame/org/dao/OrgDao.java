package net.linybin7.core.frame.org.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserOrg;


/**
 * ���Ź���DAO
 * 
 * 
 */
public interface OrgDao {

	/**
	 * ��������
	 * 
	 * @param org
	 */
	public void save(Org org);

	/**
	 * ɾ������
	 * 
	 * @param id
	 */
	public void delete(String id);

	/**
	 * ɾ������
	 * 
	 * @param org
	 */
	public void delete(Org org);

	/**
	 * ���²���
	 * 
	 * @param org
	 */
	public void update(Org org);

	/**
	 * ��ò���
	 * 
	 * @param id
	 * @return
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
	 * �ı�״̬
	 * 
	 * @param ids
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] ids, int state);

	/**
	 * ��������¼�����
	 * 
	 * @param curUser
	 * @param list
	 */
	public void orgsList(Org curOrg, List list);

	/**
	 * ���ݲ���id��ò�������
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds);

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
