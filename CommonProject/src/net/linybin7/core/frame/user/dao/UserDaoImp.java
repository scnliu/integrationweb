package net.linybin7.core.frame.user.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserRole;
import net.linybin7.core.util.Constants;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 用户管理
 * 
 * 
 * 
 */
public class UserDaoImp extends HibernateDaoSupport implements UserDao {

	public UserDaoImp() {

	}

	/**
	 * 新增用户
	 */
	public void save(User user) {
		getHibernateTemplate().save(user);

	}

	/**
	 * 删除用户
	 */
	public void delete(String userCode) {
		User user = get(userCode);
		getHibernateTemplate().delete(user);
	}

	/**
	 * 删除用户角色
	 */
	public void deleteUserRoles(final String[] userCodes) {
		if (userCodes == null || userCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"delete from UserRole ur where ur.userCode in(");
				for (String userCode : userCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				for (int i = 0; i < userCodes.length; i++) {
					query.setString(i, userCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * 删除用户
	 */
	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}

	/**
	 * 更新用户
	 */
	public void update(User user) {
		getHibernateTemplate().update(user);

	}

	/**
	 * 获得用户
	 */
	public User get(String userCode) {
		return (User) getHibernateTemplate().get(User.class, userCode);
	}

	/**
	 * 获得多个用户
	 * 
	 * @param userCodes
	 * @return
	 */
	public List get(String[] userCodes) {
		if (userCodes == null || userCodes.length == 0) {
			return new ArrayList();
		}
		String hql = "from User u where u.userCode in(";
		for (int i = 0; i < userCodes.length; i++) {
			if (i == userCodes.length - 1) {
				hql += "?)";
			} else {
				hql += "?,";
			}
		}
		return getHibernateTemplate().find(hql, userCodes);
	}

	/**
	 * 获得用户密码
	 * 
	 * @param userCode
	 * @return
	 */
	public String getPassword(String userCode) {
		String hql = "select u.password from User u where u.userCode = ?";
		List list = getHibernateTemplate().find(hql, new Object[] { userCode });
		if (list != null && list.size() > 0) {
			return ((String) list.get(0)).trim();
		}
		return null;
	}

	/**
	 * 分页查询用户
	 */
	public List query(final User curUser, final User condition, final int currPage,
			final int pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria users = session.createCriteria(User.class);
				addCondition(curUser, condition, users);
				users.setFirstResult((currPage - 1) * pageSize).setMaxResults(pageSize);
				return users.list();
			}
		});
	}

	/**
	 * 获得条件
	 * 
	 * @param condition
	 * @param users
	 */
	private void addCondition(User curUser, User condition, Criteria users) {
		if (condition.getUserCode() != null && condition.getUserCode().length() > 0) {
			users.add(Restrictions.like("userCode", "%" + condition.getUserCode() + "%"));
		}

		if (condition.getUserName() != null && condition.getUserName().length() > 0) {
			users.add(Restrictions.like("userName", "%" + condition.getUserName().trim() + "%"));
		}

		if (condition.getDescript() != null && condition.getDescript().length() > 0) {
			users.add(Restrictions.like("descript", "%" + condition.getDescript() + "%"));
		}

		if (condition.getUserProp() != -1) {
			users.add(Restrictions.eq("userProp", new Integer(condition.getUserProp())));
		}

		if (condition.getIsStop() != -1) {
			users.add(Restrictions.eq("isStop", new Integer(condition.getIsStop())));
		}

		if (condition.getTel() != null && condition.getTel().length() > 0) {
			users.add(Restrictions.like("tel", "%" + condition.getTel() + "%"));
		}
		//if (curUser.getUserProp() != Constants.USER_TYPE_SYS) {
			//users.add(Restrictions.eq("orgId", curUser.getOrgId()));
			//users.add(Restrictions.ne("userProp", new Integer(Constants.USER_TYPE_SYS)));
		//}
	}

	/**
	 * 获得总数
	 */
	public int getCount(final User curUser, final User condition) {
		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria users = session.createCriteria(User.class);
				addCondition(curUser, condition, users);
				return users.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count == null ? 0 : count.intValue();
	}

	/**
	 * 分配角色
	 * 
	 * @param userCode
	 * @param roleCodes
	 * @throws Exception
	 */
	public void assignRole(final String userCode, final String[] roleCodes) {
		final String deleteHql = "delete from UserRole ur where ur.userCode = :userCode";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(deleteHql).setString("userCode", userCode).executeUpdate();
				if (roleCodes == null) {
					return null;
				}
				for (String roleCode : roleCodes) {
					UserRole userRole = new UserRole(userCode, roleCode);
					session.save(userRole);
				}
				return null;
			}
		});
	}

	/**
	 * 获得角色
	 * 
	 * @param userCode
	 * @return
	 */
	public List getRole(String userCode) {
		String hql = "select r from Role r, UserRole ur where r.roleCode = ur.roleCode and ur.userCode = ?";
		return getHibernateTemplate().find(hql, userCode);
	}

	/**
	 * 获得未分配的角色
	 * 
	 * @param userCode
	 * @return
	 */
	public List getUnssignRole(String userCode) {
		String hql = "from Role r where  not exists(select ur.roleCode from UserRole ur where r.roleCode = ur.roleCode and ur.userCode = ?)";
		// List all = getHibernateTemplate().find(hql);
		// hql = "from UserRole ur where ur.userCode = ?";
		// List assign= getHibernateTemplate().find(hql, userCode);
		// for(int i = 0; i < all.size(); i++){
		// Role role = (Role)all.get(i);
		// for(int j = 0; j < assign.size(); j++){
		// UserRole userRole = (UserRole)assign.get(j);
		// if(role.getRoleCode().equals(userRole.getRoleCode())){
		// all.remove(i);
		// i--;
		// break;
		// }
		// }
		// }

		return getHibernateTemplate().find(hql, userCode);
	}

	/**
	 * 改变状态
	 */
	public void changState(final String[] userCodes, final int state) {
		if (userCodes == null || userCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"update User u set u.isStop = ? where u.userCode in(");
				for (String funcCode : userCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				query.setInteger(0, state);
				for (int i = 0; i < userCodes.length; i++) {
					query.setString(i + 1, userCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * 获得用户权限
	 * 
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getPuerviewCodes(String userCode) {
		Map<String, String> map = new HashMap<String, String>();
		String hql = "select distinct f.funcCode, f.isStop from Func f, RoleFunc rf, UserRole ur"
				+ " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode "
				+ " and ur.userCode = ?";
		List list = getHibernateTemplate().find(hql, new Object[] { userCode });
		for (int i = 0; i < list.size(); i++) {
			Object[] objs = (Object[]) list.get(i);
			map.put(objs[0].toString(), objs[1].toString());
		}
		return map;
	}

	/**
	 * 获得指定系统对应用户个性化设置
	 * 
	 * @param sysId
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getIndividuation(String sysId, String userCode) {
		String hql = "from Individuation i where i.sysId = ? and i.userCode = ? ";
		List list = getHibernateTemplate().find(hql, new Object[] { sysId, userCode });
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Individuation indiv = (Individuation) list.get(i);
			map.put(String.valueOf(indiv.getSetCode()), indiv.getSetting());
		}
		return map;
	}

	/**
	 * 获取个性化设置
	 * 
	 * @param id
	 * @return
	 */
	public Individuation getIndividuation(Individuation id) {
		return (Individuation) getHibernateTemplate().get(Individuation.class, id);
	}

	/**
	 * 新增或更新个性化设置
	 * 
	 * @param individuation
	 */
	public void saveOrUpdate(Individuation individuation) {
		getHibernateTemplate().saveOrUpdate(individuation);
	}

	/**
	 * 判断指定的用户是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String userCode) {
		List list = getHibernateTemplate().find(
				"select obj.userCode from User obj where obj.userCode = ?", userCode);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public List queryBy(final String field, final String value) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria users = session.createCriteria(User.class);
				if (value != null && value.length() > 0) {
					users.add(Restrictions.eq(field, value));
				}

				// users.setFirstResult((currPage - 1) *
				// pageSize).setMaxResults(
				// pageSize);
				return users.list();
			}
		});
	}

	@Override
	public void saveOrUpdateIndividuation(Individuation individuation) {
		getHibernateTemplate().saveOrUpdate(individuation);
	}

	@Override
	public Individuation getIndividuation(String userCode) {
		Individuation individuation = new Individuation();
		List list = getHibernateTemplate().find(
				"from Individuation obj where obj.userCode=? and obj.type='topic' ", userCode);
		if (list != null && list.size() > 0) {
			individuation = (Individuation) list.get(0);
		}
		return individuation;
	}

	@Override
	public List all() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("from User where isStop=0 and userProp<>3");
	}

}
