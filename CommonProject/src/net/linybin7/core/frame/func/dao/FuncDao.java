package net.linybin7.core.frame.func.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;


/**
 * 功能管理DAO
 * 
 * 
 */
public interface FuncDao {

	/**
	 * 新增功能
	 * 
	 * @param func
	 */
	public void save(Func func);

	/**
	 * 新增系统
	 * 
	 * @param sys
	 */
	public void save(Sys sys);

	/**
	 * 删除功能
	 * 
	 * @param funcCode
	 */
	public void delete(String funcCode);

	/**
	 * 删除功能
	 * 
	 * @param func
	 */
	public void delete(Func func);

	/**
	 * 删除角色功能
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void deleteRoleFuncs(String[] funcCodes);

	/**
	 * 更新功能
	 * 
	 * @param func
	 */
	public void update(Func func);

	/**
	 * 更新系统
	 * 
	 * @param func
	 */
	public void update(Sys sys);

	/**
	 * 获得功能
	 * 
	 * @param funcCode
	 * @return
	 */
	public Func get(String funcCode);

	/**
	 * 获得系统
	 * 
	 * @param sysId
	 * @return
	 */
	public Sys getSys(String sysId);

	/**
	 * 查询功能
	 * 
	 * @param conditionFunc
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(Func conditionFunc, int currPage, int pageSize);

	/**
	 * 获得总数
	 * 
	 * @param condition
	 * @return
	 */
	public int getCount(final Func condition);

	/**
	 * 获得系统总数
	 * 
	 * @return
	 */
	public int getSysCont();

	/**
	 * 所有系统
	 * 
	 * @return
	 */
	public List allSys();

	/**
	 * 获得系统功能
	 * 
	 * @param syss
	 * @return
	 */
	public List syssFuncs(String[] syss);

	/**
	 * 获得所有功能
	 * 
	 * @return
	 */
	public List all();

	/**
	 * 获得所有功能编号
	 */
	public List allCode();

	/**
	 * 获得用户菜单
	 * 
	 * @param userCode
	 * @param syss
	 * @return
	 */
	public List getMenu(String userCode, String[] syss);

	/**
	 * 获得指定用户的所有功能
	 * 
	 * @param userCode
	 * @return
	 */
	public List getFuncs(String userCode);

	/**
	 * 获得按钮
	 * 
	 * @return
	 */
	public List getButton(String userCode, String parentCode);

	/**
	 * 获得按钮
	 * 
	 * @return
	 */
	public List getButton(String userCode, String[] funcCodes);

	// /**
	// * 获得用户功能编号
	// * @param userCode
	// * @return
	// */
	// public List getFuncCodes(String userCode);

	/**
	 * 保存顺序
	 * 
	 * @param funcs
	 */
	public void saveOrder(String[] funcs);

	/**
	 * 保存系统顺序
	 * 
	 * @param funcs
	 */
	public void saveSysOrder(String[] funcs);

	/**
	 * 改变状态
	 * 
	 * @param funcCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] funcCodes, int state);

	/**
	 * 判断指定的功能编号是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String funcCode);


}
