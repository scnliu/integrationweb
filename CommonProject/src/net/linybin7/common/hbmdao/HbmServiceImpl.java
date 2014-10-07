package net.linybin7.common.hbmdao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * BaseServiceImpl.java <br>
 *  <br>
 * Bensir <br>
 * 2008-7-11 ÉÏÎç11:05:34 <br>
 */

public abstract class HbmServiceImpl implements HbmService {
	protected HbmDAO dao;

	public int count(HQuery query) {
		return dao.count(query);
	}

	public void delete(Object entity) {
		dao.delete(entity);
	}

	public HbmDAO getDao() {
		return dao;
	}

	public void setDao(HbmDAO dao) {
		this.dao = dao;
	}

	public void delete(String id) {
		dao.delete(id);
	}

	public void deleteAll(List entities) {
		dao.deleteAll(entities);
	}

	public List find(HQuery query, int currPage, int pageSize) {
		return dao.find(query, currPage, pageSize);
	}

	public List findByAll(String propertyName, Object value) {
		return dao.findByAll(propertyName, value);
	}

	public Object findByOne(String propertyName, Object value) {
		return dao.findByOne(propertyName, value);
	}

	public Object get(Serializable id) {
		return dao.get(id);
	}

	public void save(Object entity) {
		dao.save(entity);
	}

	public void update(Object entity) {
		dao.update(entity);
	}

	public void deleteAll(String id, String[] ids) {
		this.dao.deleteAll(id, ids);
	}

}
