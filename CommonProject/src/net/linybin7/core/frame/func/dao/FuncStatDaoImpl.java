package net.linybin7.core.frame.func.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.linybin7.common.database.AbstractDataDAO;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;
import net.linybin7.core.frame.func.cmd.FuncStatBean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;


public class FuncStatDaoImpl extends AbstractDataDAO implements FuncStatDao {
	

	@Override
	public void add(FuncStat bo) {
		// TODO Auto-generated method stub
		getHibernateTemplate().save(bo);
	}

	@Override
	public FuncStat get(String sysId, String funcCode) {
		// TODO Auto-generated method stub
		List list = getHibernateTemplate().find(
				"from FuncStat where sysId = ? and funcCode = ?",
				new Object[] { sysId, funcCode });
		if (list == null || list.size() == 0)
			return null;
		return (FuncStat) list.get(0);
	}

	@Override
	public void update(FuncStat bo) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(bo);
	}

	@Override
	public void insertOrUpdate(FuncStat bo) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdate(bo);
	}

	@Override
	public void update(List<FuncStat> list) {
		// TODO Auto-generated method stub
		getHibernateTemplate().saveOrUpdateAll(list);
	}

	@Override
	public List stat() {
		// TODO Auto-generated method stub
		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select a.funccode,a.parentcode,a.funcname,a.cnt,b.funcname parentName from (select s.funccode,s.funcname,s.parentcode," +
						"fs.cnt from sa_function_stat fs,sa_function s "+
						"where fs.funccode = s.funccode)a,sa_function b where a.parentcode=b.funccode order by a.cnt desc";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List<FuncStatBean> beanList = new ArrayList<FuncStatBean>();
				List list = sqlQuery.list();
				Iterator iterator = list.iterator();
				FuncStatBean bean = null;
				while (iterator.hasNext()) {
					Object[] objArr = (Object[]) iterator.next();
					bean = new FuncStatBean();
					Func func = new Func();
					func.setFuncCode(objArr[0].toString());
					func.setParentCode(objArr[1].toString());
					func.setFuncName(objArr[2].toString());
					bean.setFunc(func);
					BigDecimal scnt = (BigDecimal)objArr[3];
					bean.setNums(scnt.intValue());
					bean.setParentName(objArr[4].toString());
					beanList.add(bean);
				}
				return beanList;
			}
		});
	}

	@Override
	public List statModule() {
		// TODO Auto-generated method stub
		return (List) getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				String sql = "select f.funcCode,f.parentCode,f.funcName,b.scnt from(select fs.parentCode,sum(cnt) scnt from sa_function_stat fs "
						+ "group by fs.parentCode)b,sa_function f where b.parentCode = f.funcCode order by b.scnt desc";
				SQLQuery sqlQuery = session.createSQLQuery(sql);
				List<FuncStatBean> beanList = new ArrayList<FuncStatBean>();
				List list = sqlQuery.list();
				Iterator iterator = list.iterator();
				FuncStatBean bean = null;
				while (iterator.hasNext()) {
					Object[] objArr = (Object[]) iterator.next();
					bean = new FuncStatBean();
					Func func = new Func();
					func.setFuncCode(objArr[0].toString());
					func.setParentCode(objArr[1].toString());
					func.setFuncName(objArr[2].toString());
					bean.setFunc(func);
					BigDecimal scnt = (BigDecimal)objArr[3];
					bean.setNums(scnt.intValue());
					beanList.add(bean);
				}
				return beanList;
			}
		});
	}

	@Override
	public int statCount() {
		// TODO Auto-generated method stub
		Integer count = (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				// TODO Auto-generated method stub
				Criteria criteria = session.createCriteria(FuncStat.class);
				return criteria.setProjection(Projections.rowCount()).uniqueResult();
			}
		});
		return count == null ? 0 : count.intValue();
	}

	@Override
	protected Class getClazz() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getEntityName() {
		// TODO Auto-generated method stub
		return null;
	}


}
