package net.linybin7.common.hbmdao;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * BaseDAO.java <br>
 *  <br>
 * Bensir <br>
 * 2008-6-30 ����05:53:54 <br>
 */

public interface HbmDAO {
	/**
	 * ����id����
	 * 
	 * @param id
	 * @return
	 */
	public Object get(Serializable id);

	/**
	 * ����
	 */
	public void save(Object entity);

	/**
	 * ����
	 */
	public void update(Object entity);

	/**
	 * ɾ��
	 */
	public void delete(Object entity);

	/**
	 * ����idɾ��
	 * 
	 * @param id
	 * @return
	 */
	public void delete(String id);

	/**
	 * 
	 * @param all
	 */
	public void deleteAll(List entities);

	/**
	 * 
	 * @param id
	 * @param ids
	 */
	public void deleteAll(String id, String[] ids);

	/**
	 * ����������ҳ����
	 * 
	 * @param query
	 * @param currPage
	 *            ��ǰҳ
	 * @param pageSize
	 *            ÿҳ��¼��
	 * @return
	 */
	public List find(HQuery query, int currPage, int pageSize);

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByAll(String propertyName, Object value);

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public Object findByOne(String propertyName, Object value);

	/**
	 * 
	 * @param query
	 * @return
	 */
	public int count(HQuery query);
	
	
	public List find(String hql, Object[] args, int currPage, int pageSize);
}