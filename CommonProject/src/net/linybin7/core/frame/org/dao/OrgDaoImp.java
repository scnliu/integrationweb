package net.linybin7.core.frame.org.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.util.StringHelper;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.UserOrg;
import net.linybin7.core.frame.bo.UserRole;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 部门管理
 * 
 * 
 */
public class OrgDaoImp extends HibernateDaoSupport implements OrgDao {

	public OrgDaoImp() {

	}

	/**
	 * 新增部门
	 */
	public void save(Org org) {
		//String hql = "select f.id from Org f where f.Id = ?";
		//List list = getHibernateTemplate().find(hql, org.getId());
		// long order = list.size() > 0 ? (Long)list.get(0) + 1 : 1;
		// org.setOrder((int)order);
		//String hql = "insert into Org  values (?)";
		//String hql = "insert into Org (id) values(?)";
		getHibernateTemplate().save(org);
	}

	/**
	 * 删除部门
	 */
	public void delete(String id) {
		Org org = get(id);
		getHibernateTemplate().delete(org);
	}

	/**
	 * 删除部门
	 */
	public void delete(Org org) {
		getHibernateTemplate().delete(org);
	}

	/**
	 * 更新部门
	 */
	public void update(Org org) {
		getHibernateTemplate().update(org);

	}

	/**
	 * 获得部门
	 */
	public Org get(String id) {
		return (Org) getHibernateTemplate().get(Org.class, id);
	}

	/**
	 * 分页查询部门
	 */
	public List query(final Org condition, final int currPage, final int pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria orgs = session.createCriteria(Org.class);
				addCondition(condition, orgs, true);
				orgs.setFirstResult((currPage - 1) * pageSize).setMaxResults(pageSize);
				return orgs.list();
			}
		});
	}

	/**
	 * 获得条件
	 * 
	 * @param condition
	 * @param orgs
	 * @param isQuery
	 */
	private void addCondition(Org condition, Criteria orgs, boolean isQuery) {
		if (condition.getOrgName() != null && condition.getOrgName().length() > 0) {
			orgs.add(Restrictions.like("orgName", "%" + condition.getOrgName().trim() + "%"));
		}
		//
		if (condition.getId() != null && condition.getId().length() > 0) {
			orgs.add(Restrictions.like("id", "%" + condition.getId() + "%"));
		}
		if (!StringHelper.isEmpty(condition.getParentId())) {
			orgs.add(Restrictions.eq("parentId", condition.getParentId()));
		}
		if (isQuery) {
			orgs.addOrder(Order.asc("order"));
		}
	}

	/**
	 * 获得总数
	 */
	public Long getCount(final Org condition) {
//		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
//			public Object doInHibernate(Session session) throws HibernateException {
//				Criteria orgs = session.createCriteria(Org.class);
//				addCondition(condition, orgs, false);
//				return orgs.setProjection(Projections.rowCount()).uniqueResult();
//			}
//		});
//		return count == null ? 0 : count.intValue();
		
		//String hql = "select f.id from Org f where f.Id = ?";
		//List list = getHibernateTemplate().find(hql, org.getId());
		// long order = list.size() > 0 ? (Long)list.get(0) + 1 : 1;
		// org.setOrder((int)order);
		//String hql = "insert into Org  values (?)";
		//String hql = "insert into Org (id) values(?)";
		String hql = "select count(*) from Org";
		List list = getHibernateTemplate().find(hql);
		Long i =  (Long)list.get(0);
		return i;
	}

	/**
	 * 获得所有部门
	 * 
	 * @return
	 */
	public List all() {
		String hql = "from Org f order by f.level, f.order";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 获得所有部门编号
	 */
	public List allCode() {
		String hql = "select f.id from Org f";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * 判断指定的部门是否存在
	 * 
	 * @param id
	 * @return
	 */
	public boolean exist(String id) {
		List list = getHibernateTemplate().find("select obj.id from Org obj where obj.id = ?", id);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	// /**
	// * 获得用户部门编号
	// * @param userCode
	// * @return
	// */
	// public List getIds(String userCode){
	// String hql = "select distinct f.id from Org f, RoleOrg rf, UserRole ur" +
	// " where f.id = rf.id and rf.roleCode = ur.roleCode " +
	// " and ur.userCode = ? and f.isStop = ? order by f.level, f.order";
	// return getHibernateTemplate().find(hql, new Object[]{userCode,
	// String.valueOf(Constants.STOP_NO)});
	// }

	/**
	 * 保存顺序
	 * 
	 * @param orgs
	 */
	public void saveOrder(String[] orgs) {
		for (int i = 0; orgs != null && i < orgs.length; i++) {
			Org org = (Org) getHibernateTemplate().get(Org.class, orgs[i]);
			org.setOrder((i + 1) + "");
			getHibernateTemplate().update(org);
		}
	}

	/**
	 * 改变状态
	 */
	public void changState(final String[] ids, final int state) {
		if (ids == null || ids.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"update Org f set f.isStop = ? where f.id in(");
				for (String id : ids) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				query.setInteger(0, state);
				for (int i = 0; i < ids.length; i++) {
					query.setString(i + 1, ids[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * 获得所有下级部门
	 * 
	 * @param curUser
	 * @param list
	 */
	public void orgsList(Org curOrg, List list) {
		String hql = "from Org org where org.parentId = ?";
		List children = getHibernateTemplate().find(hql, curOrg.getId());
		if (children.size() > 0) {
			list.addAll(children);
			for (int i = 0; i < children.size(); i++) {
				orgsList((Org) children.get(i), list);
			}
		}
	}

	/**
	 * 根据部门id获得部门名称
	 * 
	 * @param orgIds
	 * @return
	 */
	public List orgsNC(List orgIds) {
		if (orgIds == null || orgIds.size() == 0) {
			return new ArrayList();
		}
		StringBuilder hql = new StringBuilder("select o.orgName,o.id from Org o where o.id in(");
		for (int i = 0; i < orgIds.size(); i++) {
			hql.append("'");
			hql.append(orgIds.get(i));
			if (i == orgIds.size() - 1) {
				hql.append("')");
			} else {
				hql.append("',");
			}
		}
		return getHibernateTemplate().find(hql.toString());
	}

	@Override
	public void assignUser(final String orgId,final String[] userCodes) {
		// TODO Auto-generated method stub
		final String deleteHql = "delete from UserOrg ur where ur.id = :orgId";
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				session.createQuery(deleteHql).setString("orgId", orgId).executeUpdate();
				if (userCodes == null) {
					return null;
				}
				for (String userCode : userCodes) {
					UserOrg userOrg = new UserOrg(userCode, orgId);
					session.save(userOrg);
				}
				return null;
			}
		});
	}

	@Override
	public List<User> getUser(String orgId) {
		// TODO Auto-generated method stub
		String hql = "select ur from UserOrg r, User ur where r.userCode = ur.userCode and r.id = ?";
		return getHibernateTemplate().find(hql, orgId);
	}

	@Override
	public List<User> getUnssignUser(final String orgId) {
		// TODO Auto-generated method stub
		String hql = "from User u where not exists(select ur.userCode from UserOrg ur where u.userCode = ur.userCode and ur.id = ?) and isStop=0 and userProp<>3";
		return getHibernateTemplate().find(hql, orgId);
//		return (List<User>)getHibernateTemplate().execute(new HibernateCallback() {
//			
//			@Override
//			public Object doInHibernate(Session session) throws HibernateException,
//					SQLException {
//				// TODO Auto-generated method stub
//				Criteria userCodeCriteria = session.createCriteria(UserOrg.class).setProjection(Projections.property("userCode"));
//				userCodeCriteria.add(Restrictions.eq("id", orgId));
//				List userCodes = userCodeCriteria.list();
//				session.flush();
//				Criteria criteria = session.createCriteria(User.class);
//				criteria.add(Restrictions.not(Restrictions.in("userCode", userCodes)));
//				return criteria.list();
//			}
//		});
	}

	@Override
	public List getUserOrgs(String userCode) {
		// TODO Auto-generated method stub
		return getHibernateTemplate().find("select r from Org r,UserOrg ur where r.id = ur.id and ur.userCode = ?",userCode);
	}
	
}
