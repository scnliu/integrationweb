package net.linybin7.core.frame.org.service;

import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.org.dao.OrgDao;
import net.linybin7.core.util.Constants;


/**
 * 

 * 
 */
public class OrgSveImp implements OrgSve {
	private OrgDao orgDao;

	public OrgSveImp() {
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	/**
	 * ��������
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void save(Org org) throws DuplicateKeyException {
//		if (orgDao.get(org.getId()) != null) {
//			throw new DuplicateKeyException("���ű���Ѿ�����");
//		}

		orgDao.save(org);
	}

	/**
	 * �޸Ĳ���
	 * 
	 * @param org
	 * @throws Exception
	 */
	public void update(Org org) {
		orgDao.update(org);
	}

	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String id) {
		orgDao.delete(id);
	}
	
	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(String[] ids) {
		for(String id : ids){
			orgDao.delete(id);
		}
	}

	/**
	 * ɾ������
	 * 
	 * @param ids
	 * @throws Exception
	 */
	public void delete(List orgs) {
		for (int i = 0; i < orgs.size(); i++) {
			orgDao.delete((Org) orgs.get(i));
		}
	}

	/**
	 * ���ݲ��ű�Ż�ȡ����
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Org get(String id) {
		return orgDao.get(id);

	}

	/**
	 * ��ѯ����
	 * 
	 * @param conditionOrg
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(Org conditionOrg, int currPage, int pageSize) {
		return orgDao.query(conditionOrg, currPage, pageSize);
	}

	/**
	 * �������
	 * 
	 * @param condition
	 * @return
	 */
	public Long getCount(final Org condition) {
		return orgDao.getCount(condition);
	}

	/**
	 * ��ȡ���в���,��level,order����
	 * 
	 * @return
	 */
	public List all() {
		return orgDao.all();
	}

	/**
	 * ������в��ű��
	 */
	public List allCode() {
		return orgDao.allCode();
	}

	// /**
	// * ����û����ű��
	// * @param userCode
	// * @return
	// */
	// public List getIds(String userCode){
	// return orgDao.getIds(userCode);
	// }

	/**
	 * ����˳��
	 * 
	 * @param orgs
	 */
	public void saveOrder(String[] orgs) {
		orgDao.saveOrder(orgs);
	}

	/**
	 * ����
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void start(String[] ids) {
		orgDao.changState(ids, Constants.STOP_NO);
	}

	/**
	 * ͣ��
	 * 
	 * @param id
	 */
	public void stop(String[] ids) {
		orgDao.changState(ids, Constants.STOP_YES);
	}

	/**
	 * ��õ�ǰ�û����ڲ����Լ������¼�����;
	 * 
	 * @param user
	 * @param list
	 */
	public List<Org> orgsList(User user) {
		List<Org> list = new ArrayList<Org>();
		if (StringHelper.isEmpty(user.getOrgId())) {
			return list;
		}
		Org org = get(user.getOrgId());
		list.add(org);
		orgDao.orgsList(org, list);
		return list;
	}

	/**
	 * ���ݲ���id��ò�������
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds) {
		return orgDao.orgsNC(orgIds);
	}

	/**
	 * ���ݲ���id��ò�������
	 * 
	 * @param orgId
	 * @return
	 */
	public String orgsNC(String orgId) {
		Org org = orgDao.get(orgId);
		if (org == null) {
			return null;
		}
		return org.getOrgName();
	}

	/**
	 * �ж�ָ���Ĳ����Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String id) {
		return orgDao.exist(id);
	}

	@Override
	public void assignUser(String orgId, String[] userCodes) {
		// TODO Auto-generated method stub
		orgDao.assignUser(orgId, userCodes);
	}

	@Override
	public List<User> getUser(String orgId) {
		// TODO Auto-generated method stub
		return orgDao.getUser(orgId);
	}

	@Override
	public List<User> getUnssignUser(String orgId) {
		// TODO Auto-generated method stub
		return orgDao.getUnssignUser(orgId);
	}

	@Override
	public List getUserOrgs(String userCode) {
		// TODO Auto-generated method stub
		return orgDao.getUserOrgs(userCode);
	}

}
