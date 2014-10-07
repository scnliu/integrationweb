package net.linybin7.common.hbmdao;
import java.io.Serializable;
import java.util.List;

/**
 * 
 * BaseService.java <br>
 *  <br>
 * Bensir <br>
 * 2008-7-11 上午11:04:32 <br>
 */

public interface HbmService {
	/**
	 * 根据id查找
	 * 
	 * @param id
	 * @return
	 */
	public Object get(Serializable id);

	/**
	 * 添加
	 */
	public void save(Object entity);

	/**
	 * 更新
	 */
	public void update(Object entity);

	/**
	 * 删除
	 */
	public void delete(Object entity);

	/**
	 * 根据id删除
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
	 * 根据条件分页查找
	 * 
	 * @param map
	 * @param currPage
	 *            当前页
	 * @param pageSize
	 *            每页记录数
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
