package net.linybin7.core.frame.func.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;
import net.linybin7.core.frame.bo.User;


/**
 * 功能管理服务类
 * 
 * 
 * 
 */
public interface FuncSve {

	/**
	 * 新增功能
	 * 
	 * @param func
	 * @throws Exception
	 */
	public void save(Func func) throws DuplicateKeyException;

	/**
	 * 
	 * 
	 */
	public String getimglist(String _path);

	/**
	 * 修改功能
	 * 
	 * @param func
	 * @throws Exception
	 */
	public void update(Func func);

	/**
	 * 删除功能
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void delete(String[] funcCodes);

	/**
	 * 删除功能
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void delete(List funcCodes);

	/**
	 * 获得功能
	 * 
	 * @param funcCode
	 * @return
	 * @throws Exception
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
	 * 获得各个系统的功能菜单
	 * 
	 * @param syss
	 * @return
	 */
	public Map<String, List<Func>> sysFuncs(String syss);

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
	public Map<String, Sys> getMenu(String userCode, String syss);

	/**
	 * 获得用户功能
	 * 
	 * @param currUser
	 * @param syss
	 * @return
	 */
	public Map<String, List<Func>> getFuncs(User currUser, String syss);

	// /**
	// * 获得用户菜单,按level,order排序
	// * @param userCode
	// * @return
	// */
	// public List getMenu(String userCode);
	//	
	// /**
	// * 获得指定用户的所有功能
	// * @param userCode
	// * @return
	// */
	// public List getFuncs(String userCode);

	/**
	 * 根据用户编号与,父节点编号获取按钮
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
	 * 启用
	 * 
	 * @param funcCode
	 * @throws Exception
	 */
	public void start(String[] funcCodes);

	/**
	 * 停用
	 * 
	 * @param funcCode
	 */
	public void stop(String[] funcCodes);

	public boolean exist(String funcCode);
	
	public void initPubFunc();

}
