package net.linybin7.core.frame.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.org.dao.OrgDao;
import net.linybin7.core.frame.role.dao.RoleDao;
import net.linybin7.core.frame.user.dao.UserDao;
import net.linybin7.core.security.MD5;
import net.linybin7.core.util.Constants;


/**
 * 

 * 
 */
public class UserSveImp implements UserSve {
	private UserDao userDao;

	private OrgDao orgDao;

	private RoleDao roleDao;

	public UserSveImp() {
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public OrgDao getOrgDao() {
		return orgDao;
	}

	public void setOrgDao(OrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public RoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/**
	 * �����û�
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Override
	public void save(User user) throws DuplicateKeyException {
		if (userDao.get(user.getUserCode()) != null) {
			throw new DuplicateKeyException("�û�����Ѿ�����");
		}
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5ofStr(user.getPassword()));
		userDao.save(user);
	}

	/**
	 * �޸��û�
	 * 
	 * @param user
	 * @throws Exception
	 */
	@Override
	public void update(User user) {
		MD5 md5 = new MD5();
		String oldPassword = userDao.getPassword(user.getUserCode());
		if (!oldPassword.equals(user.getPassword().trim())) {
			user.setPassword(md5.getMD5ofStr(user.getPassword()));
		}

		userDao.update(user);
	}

	@Override
	public String updatePersonInfo(User user) {
		String msg = "";
		MD5 md5 = new MD5();
		User oldUser = userDao.get(user.getUserCode());
		if (user.getNewPassword() != null && user.getNewPassword().trim().length() != 0) {
			if (user.getOldPassword() == null
					|| user.getOldPassword().trim().length() == 0
					|| !userDao.getPassword(user.getUserCode()).equals(
							md5.getMD5ofStr(user.getOldPassword()))) {
				msg = "�������������";
				return msg;
			} else if (!user.getNewPassword().equals(user.getcPassword())) {
				msg = "�������벻��ͬ";
				return msg;
			} else {
				oldUser.setPassword(md5.getMD5ofStr(user.getNewPassword()));
			}
		}
		oldUser.setUserName(user.getUserName());
		oldUser.setEmail(user.getEmail());
		oldUser.setTel(user.getTel());
		oldUser.setMobile(user.getMobile());
		oldUser.setIsPassWd(0);
		userDao.update(oldUser);
		return "";

		/*
		 * if(user.getOldPassword()==null||user.getOldPassword().trim().length()==0){
		 * userDao.update(user); return ""; } else{ String msg=""; MD5 md5 = new MD5(); String
		 * oldPassword = userDao.getPassword(user.getUserCode()); if
		 * (!oldPassword.equals(md5.getMD5ofStr(user.getOldPassword()))) { msg="�������������"; return
		 * msg; } else if(!user.getNewPassword().equals(user.getcPassword())){ msg="�������벻��ͬ"; return
		 * msg; } else{ user.setPassword(md5.getMD5ofStr(user.getNewPassword()));
		 * userDao.update(user); return msg; } }
		 */
	}
	
	public void updatePhotoType(String userCode,String type){
		User oldUser = userDao.get(userCode);
		oldUser.setPhotoType(type);
		userDao.update(oldUser);		
	}

	/**
	 * ɾ���û�
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	@Override
	public void delete(User curUser, String[] userCodes) throws Exception {
		String msg = null;
		List<User> users = userDao.get(userCodes);
		for (User user : users) {
			if (curUser.getUserCode().equals(user.getUserCode())) {
				msg = "����ɾ����ǰ�û�[" + user.getUserName() + "(" + user.getUserCode() + ")]";
				break;
			} else if (user.getUserProp() == Constants.USER_TYPE_SYS) {
				msg = "����ɾ����������Ա[" + user.getUserName() + "(" + user.getUserCode() + ")]";
				break;
			}
		}

		if (msg != null) {
			throw new Exception(msg);
		}
		for (User user : users) {
			userDao.delete(user);
		}
		userDao.deleteUserRoles(userCodes);

	}

	/**
	 * ɾ���û�
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	@Override
	public void delete(List users) {
		for (int i = 0; i < users.size(); i++) {
			userDao.delete((User) users.get(i));
		}
	}

	/**
	 * ����û�
	 * 
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public User get(String userCode) {
		User user = userDao.get(userCode);
		if (user != null && !StringHelper.isEmpty(user.getOrgId())) {
			Org org = orgDao.get(user.getOrgId());
			if (org != null) {
				user.setOrgName(org.getOrgName());
			}
		}
		return user;
	}

	/**
	 * ��ѯ�û�
	 */
	@Override
	public List queryBy(String field, String value) {

		List<User> list = userDao.queryBy(field, value);
		// if (list.size() > 0) {
		// List orgIds = new ArrayList();
		// for (User user : list) {
		// if (!StringHelper.isEmpty(user.getOrgId())) {
		// orgIds.add(user.getOrgId());
		// }
		// }
		// List<Object[]> ncs = orgDao.orgsNC(orgIds);
		// for (User user : list) {
		// for (Object[] nc : ncs) {
		// if (!StringHelper.isEmpty(user.getOrgId())
		// && user.getOrgId().equals(nc[1])) {
		// user.setOrgName((String) nc[0]);
		// break;
		// }
		// }
		// }
		// }

		return list;
	}

	/**
	 * ��ѯ�û�
	 */
	@Override
	public List query(User curUser, User conditionUser, int currPage, int pageSize) {

		List<User> list = userDao.query(curUser, conditionUser, currPage, pageSize);
		if (list.size() > 0) {
			List orgIds = new ArrayList();
			for (User user : list) {
				if (!StringHelper.isEmpty(user.getOrgId())) {
					orgIds.add(user.getOrgId());
				}
			}
			List<Object[]> ncs = orgDao.orgsNC(orgIds);
			for (User user : list) {
				for (Object[] nc : ncs) {
					if (!StringHelper.isEmpty(user.getOrgId()) && user.getOrgId().equals(nc[1])) {
						user.setOrgName((String) nc[0]);
						break;
					}
				}
			}
		}

		return list;
	}

	/**
	 * �������
	 */
	@Override
	public int getCount(User curUser, final User condition) {
		return userDao.getCount(curUser, condition);
	}

	/**
	 * �����ɫ
	 * 
	 * @param userCode
	 * @param roleCodes
	 * @throws Exception
	 */
	@Override
	public void assignRole(String userCode, String[] roleCodes) {
		userDao.assignRole(userCode, roleCodes);
	}

	/**
	 * ��ý�ɫ
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public List getRole(String userCode) {
		return userDao.getRole(userCode);
	}

	/**
	 * ���δ����Ľ�ɫ
	 * 
	 * @param curUser
	 * @param orgs
	 * @param userCode
	 * @return
	 */
	@Override
	public List getUnssignRole(User curUser, List<Org> orgs, String userCode) {
		List<Role> list = userDao.getUnssignRole(userCode);
		if (list.size() > 0) {
			String[] orgIds = new String[orgs.size()];
			for (int i = 0; i < orgs.size(); i++) {
				orgIds[i] = orgs.get(i).getId();
			}

			if (curUser.getUserProp() != Constants.USER_TYPE_SYS) {
				List<Role> roles = roleDao.getByOrgs(orgIds);
				List<Role> remove = new ArrayList<Role>();
				for (Role role : list) {
					boolean exist = false;
					for (Role or : roles) {
						if (role.getOrgId() != null && role.getOrgId().equals(or.getOrgId())) {
							exist = true;
						}
					}
					if (!exist) {
						remove.add(role);
					}
				}
				list.removeAll(remove);
			}
		}
		return list;
	}

	/**
	 * ����
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	@Override
	public void start(String[] userCodes) throws Exception {
		userDao.changState(userCodes, Constants.STOP_NO);
	}

	/**
	 * ͣ��
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	@Override
	public void stop(User curUser, String[] userCodes) throws Exception {
		String msg = null;
		List<User> users = userDao.get(userCodes);
		for (User user : users) {
			if (curUser.getUserCode().equals(user.getUserCode())) {
				msg = "����ͣ�õ�ǰ�û�[" + user.getUserName() + "(" + user.getUserCode() + ")]";
				break;
			} else if (user.getUserProp() == Constants.USER_TYPE_SYS) {
				msg = "����ͣ�ó�������Ա[" + user.getUserName() + "(" + user.getUserCode() + ")]";
				break;
			}
		}

		if (msg != null) {
			throw new Exception(msg);
		}
		userDao.changState(userCodes, Constants.STOP_YES);
	}

	/**
	 * ����û�Ȩ�޴���
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public Map<String, String> getPuerviewCodes(String userCode) {
		return userDao.getPuerviewCodes(userCode);
	}

	/**
	 * ���ָ��ϵͳ��Ӧ�û����Ի�����
	 * 
	 * @param sysId
	 * @param userCode
	 * @return
	 */
	@Override
	public Map<String, String> getIndividuation(String sysId, String userCode) {
		return userDao.getIndividuation(sysId, userCode);
	}

	/**
	 * ��ȡ���Ի�����
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Individuation getIndividuation(Individuation id) {
		return userDao.getIndividuation(id);
	}

	/**
	 * ��������¸��Ի�����
	 * 
	 * @param individuations
	 */
	@Override
	public void saveOrUpdate(List<Individuation> individuations) {
		for (Individuation individuation : individuations) {
			userDao.saveOrUpdate(individuation);
		}
	}

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public boolean exist(String userCode) {
		return userDao.exist(userCode);
	}


	@Override
	public List all() {
		// TODO Auto-generated method stub
		return userDao.all();
	}
}
