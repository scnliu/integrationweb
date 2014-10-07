package net.linybin7.common.hbmdao;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * BaseService.java <br>
 *  <br>
 * Bensir <br>
 * 2008-7-11 ����11:04:32 <br>
 */

public interface HbmService {
	/**
	 * ����id����
	 * 
	 * @param id
	 * @return
	 */
	public Object get(Serializable id);

	/**
	 * ���
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
	 * @param map
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
	 * @param map
	 * @return
	 */
	public int count(HQuery query);

}
