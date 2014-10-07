package net.linybin7.core.frame.func.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.linybin7.common.page.AbstractPageDAO;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.Sys;
import net.linybin7.core.util.Constants;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;


/**
 * ���ܹ���
 * 
 * 
 */
public class FuncDaoImp extends AbstractPageDAO implements FuncDao {

	public FuncDaoImp() {

	}

	/**
	 * ��������
	 */
	@Override
	public void save(Func func) {
		List list = null;
		if (StringHelper.isEmpty(func.getParentCode())) {
			func.setParentCode(null);
			String hql = "select count(*) from Func f where f.parentCode is null";
			list = getHibernateTemplate().find(hql);
		} else {
			String hql = "select count(*) from Func f where f.parentCode = ?";
			list = getHibernateTemplate().find(hql, func.getParentCode());
		}

		// System.out.println(":::" + list.get(0).getClass() + ";" +
		// ((Long)list.get(0)).longValue());
		int order = list.size() > 0 ? ((Number) list.get(0)).intValue() + 1 : 1;
		func.setOrder(order);
		getHibernateTemplate().save(func);
	}

	/**
	 * ����ϵͳ
	 * 
	 * @param sys
	 */
	@Override
	public void save(Sys sys) {
		getHibernateTemplate().save(sys);
	}

	/**
	 * ɾ������
	 */
	// public void delete(String funcCode) {
	// Func func = get(funcCode);
	// getHibernateTemplate().delete(func);
	// }

	/**
	 * ɾ����ɫ����
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	@Override
	public void deleteRoleFuncs(final String[] funcCodes) {
		if (funcCodes == null || funcCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"delete from RoleFunc rf where rf.funcCode in(");
				for (String userCode : funcCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				for (int i = 0; i < funcCodes.length; i++) {
					query.setString(i, funcCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	/**
	 * ɾ������
	 */
	@Override
	public void delete(Func func) {
		getHibernateTemplate().delete(func);
	}

	/**
	 * ���¹���
	 */
	@Override
	public void update(Func func) {
		if (StringHelper.isEmpty(func.getParentCode())) {
			func.setParentCode(null);
		}
		Func f = (Func) this.getHibernateTemplate().get(Func.class, func.getFuncCode());
		f.setFuncName(func.getFuncName());
		f.setLink(func.getLink());
		f.setFuncProp(func.getFuncProp());
		f.setTitle(func.getTitle());
		f.setIcon(func.getIcon());
		f.setIsStop(func.getIsStop());
		f.setDescript(func.getDescript());
		getHibernateTemplate().update(f);

	}

	/**
	 * ����ϵͳ
	 * 
	 * @param func
	 */
	@Override
	public void update(Sys sys) {
		getHibernateTemplate().update(sys);
	}

	/**
	 * ��ù���
	 */
	@Override
	public Func get(String funcCode) {
		return (Func) getHibernateTemplate().get(Func.class, funcCode);
	}

	/**
	 * ���ϵͳ
	 * 
	 * @param sysId
	 * @return
	 */
	@Override
	public Sys getSys(String sysId) {
		return (Sys) getHibernateTemplate().get(Sys.class, sysId);
	}

	/**
	 * ��ҳ��ѯ����
	 */
	@Override
	public List query(final Func condition, final int currPage, final int pageSize) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria funcs = session.createCriteria(Func.class);
				addCondition(condition, funcs, true);
				funcs.setFirstResult((currPage - 1) * pageSize).setMaxResults(pageSize);
				return funcs.list();
			}
		});
	}

	/**
	 * �������
	 * 
	 * @param condition
	 * @param funcs
	 * @param isQuery
	 */
	private void addCondition(Func condition, Criteria funcs, boolean isQuery) {
		if (!StringHelper.isEmpty(condition.getParentCode())) {
			funcs.add(Restrictions.eq("parentCode", condition.getParentCode()));
		} else {
			funcs.add(Restrictions.isNull("parentCode"));
		}
		if (isQuery) {
			funcs.addOrder(Order.asc("order"));
		}
	}

	/**
	 * �������
	 */
	@Override
	public int getCount(final Func condition) {
		Integer count = (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria funcs = session.createCriteria(Func.class);
				addCondition(condition, funcs, false);
				return funcs.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count == null ? 0 : count.intValue();
	}

	/**
	 * ���ϵͳ����
	 * 
	 * @return
	 */
	@Override
	public int getSysCont() {
		String hql = "select count(*) from Sys";
		List list = getHibernateTemplate().find(hql);
		return ((Number) list.get(0)).intValue();
	}

	/**
	 * ����ϵͳ
	 * 
	 * @return
	 */
	@Override
	public List allSys() {
		String hql = "from Sys s order by s.order";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * ���ϵͳ����
	 * 
	 * @param syss
	 * @return
	 */
	@Override
	public List syssFuncs(String[] syss) {
		StringBuffer hql = new StringBuffer("from Func f");
		if (syss != null && syss.length != 0) {
			hql.append(" where f.sys in (");
			for (int i = 0; i < syss.length; i++) {
				hql.append("'");
				hql.append(syss[i]);
				hql.append("'");
				if (i == syss.length - 1) {
					hql.append(")");
				} else {
					hql.append(",");
				}
			}
		}
		hql.append(" order by f.level, f.order");
		return getHibernateTemplate().find(hql.toString());
	}

	/**
	 * ������й���
	 * 
	 * @return
	 */
	@Override
	public List all() {
		String hql = "from Func f order by f.level, f.order";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * ������й��ܱ��
	 */
	@Override
	public List allCode() {
		String hql = "select f.funcCode from Func f";
		return getHibernateTemplate().find(hql);
	}

	/**
	 * ����û��˵�
	 * 
	 * @param userCode
	 * @param syss
	 * @return
	 */
	@Override
	public List getMenu(String userCode, String[] syss) {
		StringBuffer hql = new StringBuffer(
				"select distinct f from Func f, RoleFunc rf, UserRole ur, Role r"
						+ " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode and ur.roleCode = r.roleCode "
						+ " and ur.userCode = ? and f.isStop = ? and r.isStop = ? and f.funcProp = ?");
		if (syss != null && syss.length != 0) {
			hql.append(" and f.sys in (");
			for (int i = 0; i < syss.length; i++) {
				hql.append("'");
				hql.append(syss[i]);
				hql.append("'");
				if (i == syss.length - 1) {
					hql.append(")");
				} else {
					hql.append(",");
				}
			}
		}
		hql.append(" order by f.level, f.order");
		return getHibernateTemplate().find(
				hql.toString(),
				new Object[] { userCode, Constants.STOP_NO, Constants.STOP_NO,
						Constants.FUNC_TYPE_MENU });
	}

	/**
	 * ���ָ���û������й���
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public List getFuncs(String userCode) {
		String hql = "select distinct f from Func f, RoleFunc rf, UserRole ur, Role r"
				+ " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode and ur.roleCode = r.roleCode "
				+ " and ur.userCode = ? and f.isStop = ? and r.isStop = ? order by f.level, f.order";
		return getHibernateTemplate().find(hql,
				new Object[] { userCode, Constants.STOP_NO, Constants.STOP_NO });
	}

	/**
	 * ��ð�ť
	 * 
	 * @return
	 */
	@Override
	public List getButton(String userCode, String parentCode) {
		String hql = "select distinct f from Func f, RoleFunc rf, UserRole ur, Role r"
				+ " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode and ur.roleCode = r.roleCode "
				+ " and ur.userCode = ? and f.isStop = ? and r.isStop = ? and f.funcProp = ? and f.parentCode = ? order by f.order";
		return getHibernateTemplate().find(
				hql,
				new Object[] { userCode, Constants.STOP_NO, Constants.STOP_NO,
						Constants.FUNC_TYPE_BUTTON, parentCode });
	}

	/**
	 * ��ð�ť
	 * 
	 * @return
	 */
	@Override
	public List getButton(String userCode, String[] funcCodes) {
		if (funcCodes == null || funcCodes.length == 0) {
			return new ArrayList();
		}
		StringBuffer hql = new StringBuffer(
				"select distinct f from Func f, RoleFunc rf, UserRole ur, Role r"
						+ " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode and ur.roleCode = r.roleCode "
						+ " and ur.userCode = ? and f.isStop = ? and r.isStop = ? and f.funcProp = ? and f.funcCode in (");

		for (int i = 0; i < funcCodes.length; i++) {
			hql.append("'");
			hql.append(funcCodes[i]);
			hql.append("'");
			if (i != funcCodes.length - 1) {
				hql.append(",");
			}
		}
		hql.append(") order by f.order");
		return getHibernateTemplate().find(
				hql.toString(),
				new Object[] { userCode, Constants.STOP_NO, Constants.STOP_NO,
						Constants.FUNC_TYPE_BUTTON });
	}

	// /**
	// * ����û����ܱ��
	// * @param userCode
	// * @return
	// */
	// public List getFuncCodes(String userCode){
	// String hql = "select distinct f.funcCode from Func f, RoleFunc rf, UserRole ur" +
	// " where f.funcCode = rf.funcCode and rf.roleCode = ur.roleCode " +
	// " and ur.userCode = ? and f.isStop = ? order by f.level, f.order";
	// return getHibernateTemplate().find(hql, new Object[]{userCode,
	// String.valueOf(Constants.STOP_NO)});
	// }

	/**
	 * ����˳��
	 * 
	 * @param funcs
	 */
	@Override
	public void saveOrder(String[] funcs) {
		for (int i = 0; funcs != null && i < funcs.length; i++) {
			Func func = (Func) getHibernateTemplate().get(Func.class, funcs[i]);
			func.setOrder(i + 1);
			getHibernateTemplate().update(func);
		}
	}

	/**
	 * ����ϵͳ˳��
	 * 
	 * @param funcs
	 */
	@Override
	public void saveSysOrder(String[] funcs) {
		for (int i = 0; funcs != null && i < funcs.length; i++) {
			Sys sys = (Sys) getHibernateTemplate().get(Sys.class, funcs[i]);
			sys.setOrder(i + 1);
			getHibernateTemplate().update(sys);
		}
	}

	/**
	 * �ı�״̬
	 */
	@Override
	public void changState(final String[] funcCodes, final int state) {
		if (funcCodes == null || funcCodes.length == 0) {
			return;
		}
		getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder hql = new StringBuilder(
						"update Func f set f.isStop = ? where f.funcCode in(");
				for (String funcCode : funcCodes) {
					hql.append("?,");
				}
				hql.deleteCharAt(hql.length() - 1);
				hql.append(")");
				Query query = session.createQuery(hql.toString());
				query.setInteger(0, state);
				for (int i = 0; i < funcCodes.length; i++) {
					query.setString(i + 1, funcCodes[i]);
				}
				query.executeUpdate();
				return null;
			}
		});
	}

	@Override
	protected Class getClazz() {
		// TODO Auto-generated method stub
		return Func.class;
	}

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delByList(final List<String> ids) {
		logger.info(ids);
		final String identityName = getHibernateTemplate().getSessionFactory().getClassMetadata(
				this.getClazz()).getIdentifierPropertyName();
		//	
		for (String id : ids) {
			delete(id);
		}
	}

	/**
	 * �ж�ָ�����û��Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public boolean exist(String funcCode) {
		List list = getHibernateTemplate().find(
				"select obj.funcCode from Func obj where obj.funcCode = ?", funcCode);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
