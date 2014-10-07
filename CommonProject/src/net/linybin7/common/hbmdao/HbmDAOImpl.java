package net.linybin7.common.hbmdao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;


/**
 * 
 * BaseDAOHbnImpl.java <br>
 * <br>
 * Bensir <br>
 * 2008-6-30 下午05:57:20 <br>
 */

public abstract class HbmDAOImpl extends HibernateDaoSupport implements HbmDAO {

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public Object get(Serializable id) {
		return isClazz() ? getHibernateTemplate().get(this.getClazz(), id)
				: this.getHibernateTemplate().get(this.getEntityName(), id);
	}

	@Override
	public void update(final Object entity) {
		if (isClazz())
			getHibernateTemplate().merge(mapEntity(entity));
		else if (entity instanceof Map)
			getHibernateTemplate().merge(this.getEntityName(), entity);
		getHibernateTemplate().flush();
	}

	@Override
	public void save(final Object entity) {
		if (isClazz())
			getHibernateTemplate().save(mapEntity(entity));
		else if (entity instanceof Map)
			getHibernateTemplate().save(this.getEntityName(), entity);
		getHibernateTemplate().flush();
	}

	@Override
	public void delete(final Object entity) {
		if (isClazz())
			getHibernateTemplate().delete(mapEntity(entity));
		else if (entity instanceof Map) {
			this.getHibernateTemplate().execute(new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException {
					session.delete(getEntityName(), entity);
					return null;
				}
			});
		}
		getHibernateTemplate().flush();
	}

	@Override
	public void delete(String id) {
		Object obj = get(id);
		if (obj != null)
			this.delete(obj);
	}

	@Override
	public void deleteAll(List entities) {
		for (Object en : entities)
			delete(en);
	}

	@Override
	public int count(HQuery query) {
		query.setClazz(this.getClazz());
		query.setEntityName(this.getEntityName());

		DetachedCriteria c = query.buildCriteria();
		c.setProjection(Projections.rowCount());
		List list = getHibernateTemplate().findByCriteria(c);
		// if (list != null && list.size() > 0)
		// return (Integer) list.get(0);
		// else
		// return 0;

		if (list != null && list.size() > 0)
			return Integer.valueOf(list.get(0) + "");
		else
			return 0;
	}

	@Override
	public List find(HQuery query, int currPage, int pageSize) {
		query.setClazz(this.getClazz());
		query.setEntityName(this.getEntityName());

		DetachedCriteria c = query.buildCriteria();
		return getHibernateTemplate().findByCriteria(c,
				(currPage - 1) * pageSize, pageSize);
	}

	@Override
	public List findByAll(String field, Object value) {
		HQueryMap query = new HQueryMap();
		query.addCriterion(field, HQueryMap.eq, value);
		return find(query, 0, 0);
	}

	@Override
	public Object findByOne(String field, Object value) {
		List list = this.findByAll(field, value);
		if (list != null && list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	protected abstract Class getClazz();

	protected abstract String getEntityName();

	protected boolean isClazz() {
		Assert.state(this.getClazz() != null || this.getEntityName() != null,
				"没指定bo类型！");
		return this.getClazz() != null;
	}

	private Object mapEntity(Object entity) {
		if (!this.getClazz().equals(entity.getClass())) {
			Object subEntity = null;
			try {
				subEntity = this.getClazz().newInstance();
				BeanUtils.copyProperties(entity, subEntity);

				logger.info("BaseDAO map '" + entity.getClass().getName()
						+ "' to '" + subEntity.getClass().getName() + "'");

			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			return subEntity;
		} else {
			return entity;
		}
	}

	@Override
	public void deleteAll(String id, String[] ids) {
		HQueryMap map = new HQueryMap();
		map.addCriterion(id, HQueryMap.in, ids);
		List list = find(map, 0, 0);
		this.deleteAll(list);
	}

	@Override
	public List find(final String hql, final Object[] args, final int currPage,
			final int pageSize) {
		return getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				Query query = s.createQuery(hql);
				if (currPage > 0 && pageSize > 0)
					query.setFirstResult((currPage - 1) * pageSize)
							.setMaxResults(pageSize);
				if (args != null)
					for (int i = 0; i < args.length; i++)
						query.setParameter(i, args[i]);
				List list = query.list();
				return list;
			}
		});
	}
}
