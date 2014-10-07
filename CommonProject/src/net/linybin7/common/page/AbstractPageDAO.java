package net.linybin7.common.page;

import java.util.List;

import net.linybin7.common.hbmdao.HbmDAOImpl;
import net.linybin7.common.tag.Grid;
import net.linybin7.common.tag.Pager;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.orm.hibernate3.HibernateCallback;


public abstract class AbstractPageDAO extends HbmDAOImpl implements PageDAO {
	@Override
	public Long countRows(final List<SimpleExpression> sExpr) {
		Object execute = getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException{
				Criteria c=s.createCriteria(getClazz());
				c.setProjection(Projections.rowCount());
				if(sExpr!=null)
				for(SimpleExpression se:sExpr){
//				SimpleExpression se= Restrictions.like("name", "%ss");
				c.add(se);
				}
				return c.uniqueResult();
			}});
		if(execute==null)return 0L;
		return (Long)execute;
	}

	@Override
	public List queryPageRows(final Grid grid,final List<SimpleExpression> sExpr,final int startIndex,final int pageSize) {
		List execute = getHibernateTemplate().executeFind(new HibernateCallback() { 
			@Override
			public Object doInHibernate(Session s) throws HibernateException{
				Criteria c=s.createCriteria(getClazz());
				if(sExpr!=null)
				for(SimpleExpression se:sExpr){
				c.add(se);
				}
				if(grid.isAsc())
				c.addOrder(Order.asc(grid.getSortField()));
				if(grid.isDesc())
					c.addOrder(Order.desc(grid.getSortField()));
				c.setFirstResult(startIndex);
				c.setMaxResults(pageSize);
				return c.list();
			}});
		return execute;
	}
	@Override
	public void delByList(final List<String> ids){ 
		logger.info(ids);
	final String identityName=getHibernateTemplate().getSessionFactory().getClassMetadata(this.getClazz()).getIdentifierPropertyName();
//	final boolean isPrimitive; 
//	try {
//		Class typeClass = this.getClazz().getDeclaredField(identityName).getType();
//		typeClass.getSimpleName();
//		if(typeClass.isPrimitive())
//		{
//			isPrimitive=true;
//		}
//	} catch (SecurityException e) {
//		e.printStackTrace();
//	} catch (NoSuchFieldException e) {
//		e.printStackTrace();
//	}
	getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException{
				StringBuffer idsStr=new StringBuffer();
				for(int i=0;i<ids.size();i++){
					idsStr.append("'");
					idsStr.append(ids.get(i));
					idsStr.append("'");
					if(i!=ids.size()-1)idsStr.append(",");
				}
				String sql="delete "+getClazz().getName()+" where "+identityName+" in ("+idsStr.toString()+")";
				logger.info(sql);
			    Query q=s.createQuery(sql);
				return q.executeUpdate();
			}});
	}

	@Override
	public List pageModel(final String sql,Pager p) {
		final int start=p.getStartIndex();
		final int pageSize=p.getPageSize();
		List execute = getHibernateTemplate().executeFind(new HibernateCallback() { 
			@Override
			public Object doInHibernate(Session s) throws HibernateException{
				System.out.println("sql:..............."+sql);
				SQLQuery q=s.createSQLQuery(sql);
				q.addEntity(getClazz());
				q.setFirstResult(start);
				q.setMaxResults(pageSize);
				return q.list();
			}});
		return execute;
	}
	public Long countRows(final String sql) {
		Object execute = getHibernateTemplate().execute(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException{
				SQLQuery q=s.createSQLQuery(sql);
				return q.uniqueResult();
			}});
		if(execute==null)return 0L;
		return (Long)execute;
	}

	

}
