package net.linybin7.core.frame.user.dao;

import java.util.List;
import java.util.Map;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;


/**
 * 用户管理DAO
 * 
 * 
 */
public interface UserDao {

	/**
	 * 新增用户
	 * 
	 * @param user
	 */
	public void save(User user);

	/**
	 * 删除用户
	 * 
	 * @param userCode
	 */
	public void delete(String userCode);

	/**
	 * 删除用户角色
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void deleteUserRoles(String[] userCodes);

	/**
	 * 删除用户
	 * 
	 * @param user
	 */
	public void delete(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void update(User user);

	/**
	 * 获得用户
	 * 
	 * @param userCode
	 * @return
	 */
	public User get(String userCode);

	/**
	 * 获得多个用户
	 * 
	 * @param userCodes
	 * @return
	 */
	public List get(String[] userCodes);

	/**
	 * 获得用户密码
	 * 
	 * @param userCode
	 * @return
	 */
	public String getPassword(String userCode);

	/**
	 * 查询用户
	 * 
	 * @param curUser
	 * @param conditionUser
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(User curUser, User conditionUser, int currPage, int pageSize);

	/**
	 * 获得总数
	 * 
	 * @param curUser
	 * @param condition
	 * @return
	 */
	public int getCount(User curUser, final User condition);

	/**
	 * 分配角色
	 * 
	 * @param userCode
	 * @param roleCodes
	 * @throws Exception
	 */
	public void assignRole(String userCode, String[] roleCodes);

	/**
	 * 获得角色
	 * 
	 * @param userCode
	 * @return
	 */
	public List getRole(String userCode);

	/**
	 * 获得未分配的角色
	 * 
	 * @param userCode
	 * @return
	 */
	public List getUnssignRole(String userCode);

	/**
	 * 改变状态
	 * 
	 * @param userCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] userCodes, int state);

	/**
	 * 获得用户权限代码
	 * 
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getPuerviewCodes(String userCode);

	/**
	 * 获得指定系统对应用户个性化设置
	 * 
	 * @param sysId
	 * @param userCode
	 * @return
	 */
	public Map<String, String> getIndividuation(String sysId, String userCode);

	/**
	 * 获取个性化设置
	 * 
	 * @param id
	 * @return
	 */
	public Individuation getIndividuation(Individuation id);

	/**
	 * 新增或更新个性化设置
	 * 
	 * @param individuation
	 */
	public void saveOrUpdate(Individuation individuation);

	/**
	 * 判断指定的用户是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String userCode);

	public List queryBy(String field, String value);

	public void saveOrUpdateIndividuation(Individuation individuation);

	public Individuation getIndividuation(String userCode);
	
	public List all();

}
