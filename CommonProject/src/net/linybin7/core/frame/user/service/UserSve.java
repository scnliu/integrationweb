package net.linybin7.core.frame.user.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;


/**
 * 用户管理服务类
 * 
 * 
 * 
 */
public interface UserSve {
	/**
	 * 新增用户，该方法已经对密码进行加密处理
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void save(User user) throws DuplicateKeyException;

	/**
	 * 修改用户，该方法已经对密码进行加密处理
	 * 
	 * @param user
	 * @throws Exception
	 */
	public void update(User user);

	/**
	 * 删除用户
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	public void delete(User curUser, String[] userCodes) throws Exception;

	/**
	 * 删除用户
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void delete(List userCodes);

	/**
	 * 获得用户
	 * 
	 * @param userCode
	 * @return
	 * @throws Exception
	 */
	public User get(String userCode);

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
	 * @param curUser
	 * @param orgs
	 * @param userCode
	 * @return
	 */
	public List getUnssignRole(User curUser, List<Org> orgs, String userCode);

	/**
	 * 启用
	 * 
	 * @param userCodes
	 * @throws Exception
	 */
	public void start(String[] userCodes) throws Exception;

	/**
	 * 停用
	 * 
	 * @param curUser
	 * @param userCodes
	 * @throws Exception
	 */
	public void stop(User curUser, String[] userCodes) throws Exception;

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
	 * @param individuations
	 */
	public void saveOrUpdate(List<Individuation> individuations);

	/**
	 * 判断指定的用户是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String userCode);
	
	public void updatePhotoType(String userCode,String type);

	public List queryBy(final String field, final String value);

	public String updatePersonInfo(User user);
	
	public List all();
}
