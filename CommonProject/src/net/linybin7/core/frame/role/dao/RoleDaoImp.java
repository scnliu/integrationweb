package net.linybin7.core.frame.role.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.RoleFunc;
import net.linybin7.core.frame.bo.User;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * ��ɫ����
 * 
 * 
 */
public class RoleDaoImp extends HibernateDaoSupport implements RoleDao {

	public RoleDaoImp() {

	}

	/**
	 * ������ɫ
	 */
	public void save(Role role) {
		getHibernateTemplate().save(role);

	}

	/**
	 * ɾ����ɫ
	 */
	public void delete(String roleCode) {
		Role role = get(roleCode);
		getHibernateTemplate().delete(role);
	}

	/**
	 * ɾ���û���ɫ
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void deleteUserRoles(final String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"delete from UserRole ur where ur.roleCode in(");
				for (String userCode : roleCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				for (int i = 0; i < roleCodes.length; i++) {
					query.setString(i, roleCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * ɾ����ɫ����
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void deleteRoleFuncs(final String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"delete from RoleFunc rf where rf.roleCode in(");
				for (String userCode : roleCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				for (int i = 0; i < roleCodes.length; i++) {
					query.setString(i, roleCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * ɾ����ɫ
	 */
	public void delete(Role role) {
		getHibernateTemplate().delete(role);
	}

	/**
	 * ���½�ɫ
	 */
	public void update(Role role) {
		getHibernateTemplate().update(role);

	}

	/**
	 * ��ý�ɫ
	 */
	public Role get(String roleCode) {
		return (Role) getHibernateTemplate().get(Role.class, roleCode);
	}

	public List get(String[] roleCodes) {
		if (roleCodes == null || roleCodes.length == 0) {
			return new ArrayList();
		}
		String hql = "from Role r where r.roleCode in(";
		for (int i = 0; i < roleCodes.length; i++) {
			if (i == roleCodes.length - 1) {
				hql += "?)";
			} else {
				hql += "?,";
			}
		}
		return getHibernateTemplate().find(hql, roleCodes);
	}

	/**
	 * ��ȡ��Ӧ���ŵĽ�ɫ
	 * 
	 * @param orgCodes
	 * @return
	 */
	public List getByOrgs(String[] orgIds) {
		if (orgIds == null || orgIds.length == 0) {
			return new ArrayList();
		}
		String hql = "from Role r where r.orgId in(";
		for (int i = 0; i < orgIds.length; i++) {
			if (i == orgIds.length - 1) {
				hql += "?)";
			} else {
				hql += "?,";
			}
		}
		return getHibernateTemplate().find(hql, orgIds);
	}

	/**
	 * ��ҳ��ѯ��ɫ
	 */
	public List query(final User curUser, final Role condition, final int currPage,
			final int pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria roles = session.createCriteria(Role.class);
				addCondition(curUser, condition, roles);
				roles.setFirstResult((currPage - 1) * pageSize).setMaxResults(pageSize);
				return roles.list();
			}
		});
	}

	/**
	 * �������
	 * 
	 * @param condition
	 * @param roles
	 */
	private void addCondition(User curUser, Role condition, Criteria roles) {
		if (condition.getRoleCode() != null && condition.getRoleCode().length() > 0) {
			roles.add(Restrictions.like("roleCode", "%" + condition.getRoleCode() + "%"));
		}

		if (condition.getRoleName() != null && condition.getRoleName().length() > 0) {
			roles.add(Restrictions.like("roleName", "%" + condition.getRoleName().trim() + "%"));
		}

		if (condition.getRoleProp() != -1) {
			roles.add(Restrictions.eq("roleProp", new Integer(condition.getRoleProp())));
		}
	}

	/**
	 * �������
	 */
	public int getCount(final User curUser, final Role condition) {
		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria roles = session.createCriteria(Role.class);
				addCondition(curUser, condition, roles);
				return roles.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count == null ? 0 : count.intValue();
	}

	/**
	 * ����Ȩ��
	 * 
	 * @param roleCode
	 * @param funcCodes
	 * @throws Exception
	 */
	public void assignPurview(final String roleCode, final String[] funcCodes) {
		final String deleteHql = "delete from RoleFunc rf where rf.roleCode = :roleCode";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(deleteHql).setString("roleCode", roleCode).executeUpdate();
				if (funcCodes == null) {
					return null;
				}
				// RoleFunc rootRoleFunc = new RoleFunc(roleCode, Constants.TREE_ROOT);
				// session.save(rootRoleFunc);
				for (String funcCode : funcCodes) {
					RoleFunc roleFunc = new RoleFunc(roleCode, funcCode);
					session.save(roleFunc);
				}
				return null;
			}
		});

	}

	/**
	 * ���Ȩ��
	 * 
	 * @param roleCode
	 * @return
	 */
	public List getPurviewCodes(String roleCode) {
		if (roleCode == null) {
			return new ArrayList();
		}
		String hql = "select rf.funcCode from RoleFunc rf where rf.roleCode = ?";
		return getHibernateTemplate().find(hql, roleCode);
	}

	/**
	 * ���н�ɫ
	 * 
	 * @return
	 */
	public List all() {
		String hql = "from Role";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * ���н�ɫ����
	 * 
	 * @return
	 */
	public List allCodes() {
		String hql = "select r.roleCode from Role r";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * �ı�״̬
	 */
	public void changState(final String[] roleCodes, final int state) {
		if (roleCodes == null || roleCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"update Role r set r.isStop = ? where r.roleCode in(");
				for (String funcCode : roleCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				query.setInteger(0, state);
				for (int i = 0; i < roleCodes.length; i++) {
					query.setString(i + 1, roleCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String roleCode) {
		List list = getHibernateTemplate().find(
				"select obj.roleCode from Role obj where obj.roleCode = ?", roleCode);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
