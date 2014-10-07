package net.linybin7.core.frame.func.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.linybin7.common.page.AbstractPageService;
import net.linybin7.common.page.PageService;
import net.linybin7.common.tag.Grid;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.func.cmd.FuncCmd;
import net.linybin7.core.frame.func.dao.FuncDao;
import net.linybin7.core.util.Constants;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;


/**
 * 

 * 
 */
public class FuncSveImp extends AbstractPageService implements FuncSve, PageService {
	private FuncDao funcDao;

	public FuncSveImp() {
	}

	public FuncDao getFuncDao() {
		return funcDao;
	}

	public void setFuncDao(FuncDao funcDao) {
		this.funcDao = funcDao;
	}

	/**
	 * 新增功能
	 * 
	 * @param func
	 * @throws Exception
	 */
	@Override
	public void save(Func func) throws DuplicateKeyException {
		if (funcDao.get(func.getFuncCode()) != null) {
			throw new DuplicateKeyException("功能编号已经存在");
		}

		String sysId = null;
		if (StringHelper.isEmpty(func.getParentCode())) {
			sysId = func.getFuncCode();
			Sys sys = new Sys();
			sys.setId(func.getFuncCode());
			sys.setName(func.getFuncName());
			sys.setContext(func.getContext());
			sys.setOrder(funcDao.getSysCont() + 1);
			funcDao.save(sys);
		} else {
			Func parentFunc = funcDao.get(func.getParentCode());
			sysId = parentFunc.getSys();
		}

		func.setSys(sysId);
		funcDao.save(func);
		if(!"".equals(func.getLink()) && func.getLink().length()>0)
			Constants.pubFuncMap.put(func.getLink(), func.getFuncCode());
		
	}

	/**
	 * 
	 * @author ykchoi 2011-5-25 下午04:25:40
	 * @return
	 */
	@Override
	public String getimglist(String _path) {
		StringBuilder builder = new StringBuilder();
		builder.append("[ \n");
		try {
			File directory = new File(_path);
			File[] files = directory.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					String[] names = files[i].getName().split("\\.");
					if (names[1].equals("png") || names[1].equals("gif") || names[1].equals("jpg")) {
						if (!names[0].equals("null")) {
							builder.append("{");
							builder.append("id:");
							builder.append("\"" + names[0] + "\",");
							builder.append("text:");
							builder.append("\"" + files[i].getName() + "\",");
							builder.append("}\n,");
						}
					}
				}
			}
		} catch (Exception e) {
		}
		String sResult = builder.substring(0, builder.length() - 1);
		sResult = sResult + "] \n";
		return sResult;
	}

	/**
	 * 修改功能
	 * 
	 * @param func
	 * @throws Exception
	 */
	@Override
	public void update(Func func) {
		if (StringHelper.isEmpty(func.getParentCode())) {
			Sys sys = funcDao.getSys(func.getSys());
			if (!sys.getContext().equals(func.getContext().trim())) {
				sys.setContext(func.getContext().trim());
				funcDao.update(sys);
			}
		}
		funcDao.update(func);
	}

	/**
	 * 删除功能
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	@Override
	public void delete(String[] funcCodes) {
		for (String funcCode : funcCodes) {
			funcDao.delete(funcCode);
		}
		funcDao.deleteRoleFuncs(funcCodes);

	}

	/**
	 * 删除功能
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */

	@Override
	public void delete(List funcs) {
		for (int i = 0; i < funcs.size(); i++) {
			funcDao.delete((Func) funcs.get(i));
		}
	}

	/**
	 * 根据功能编号获取功能
	 * 
	 * @param funcCode
	 * @return
	 * @throws Exception
	 */
	@Override
	public Func get(String funcCode) {
		return funcDao.get(funcCode);
	}

	/**
	 * 获得系统
	 * 
	 * @param sysId
	 * @return
	 */
	@Override
	public Sys getSys(String sysId) {
		return funcDao.getSys(sysId);
	}

	/**
	 * 查询功能
	 * 
	 * @param conditionFunc
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List query(Func conditionFunc, int currPage, int pageSize) {
		return funcDao.query(conditionFunc, currPage, pageSize);
	}

	/**
	 * 获得总数
	 * 
	 * @param condition
	 * @return
	 */
	@Override
	public int getCount(final Func condition) {
		return funcDao.getCount(condition);
	}

	/**
	 * 获得各个系统的功能菜单
	 * 
	 * @param syss
	 * @return
	 */
	@Override
	public Map<String, List<Func>> sysFuncs(String syss) {
		Map<String, List<Func>> funcs = new LinkedHashMap<String, List<Func>>();
		List<Sys> list = funcDao.allSys();
		String[] sysArray = null;
		if (!StringHelper.isEmpty(syss)) {
			sysArray = syss.split(",");
			List<Sys> result = new ArrayList<Sys>();
			for (Sys sys : list) {
				for (int i = 0; i < sysArray.length; i++) {
					if (sysArray[i].equals(sys.getId())) {
						result.add(sys);
						break;
					}
				}
			}
			list = result;
		}
		for (Sys sys : list) {
			funcs.put(sys.getId(), new ArrayList<Func>());
		}

		List<Func> allFuncs = funcDao.syssFuncs(sysArray);
		for (Func func : allFuncs) {
			funcs.get(func.getSys()).add(func);
		}

		return funcs;
	}

	/**
	 * 获取所有功能,按level,order排序
	 * 
	 * @return
	 */
	@Override
	public List all() {
		return funcDao.all();
	}

	/**
	 * 获得所有功能编号
	 */
	@Override
	public List allCode() {
		return funcDao.allCode();
	}

	/**
	 * 获得用户菜单
	 * 
	 * @param userCode
	 * @param syss
	 * @return
	 */
	@Override
	public Map<String, Sys> getMenu(String userCode, String syss) {
		Map<String, Sys> funcs = new LinkedHashMap<String, Sys>();
		List<Sys> list = funcDao.allSys();
		String[] sysArray = null;
		if (!StringHelper.isEmpty(syss)) {
			sysArray = syss.split(",");
			List<Sys> result = new ArrayList<Sys>();
			for (Sys sys : list) {
				for (int i = 0; i < sysArray.length; i++) {
					if (sysArray[i].equals(sys.getId())) {
						result.add(sys);
						break;
					}
				}
			}
			list = result;
		}
		for (Sys sys : list) {
			funcs.put(sys.getId(), sys);
		}

		List<Func> menus = funcDao.getMenu(userCode, sysArray);
		for (Func func : menus) {
			if (funcs.containsKey(func.getSys())) {
				funcs.get(func.getSys()).getFuncs().add(func);
			}
		}

		return funcs;
	}

	/**
	 * 获得用户功能
	 * 
	 * @param currUser
	 * @param syss
	 * @return
	 */
	@Override
	public Map<String, List<Func>> getFuncs(User currUser, String syss) {
		Map<String, List<Func>> funcs = new LinkedHashMap<String, List<Func>>();
		List<Sys> list = funcDao.allSys();
		if (!StringHelper.isEmpty(syss)) {
			String[] sysArray = syss.split(",");
			List<Sys> result = new ArrayList<Sys>();
			for (Sys sys : list) {
				for (int i = 0; i < sysArray.length; i++) {
					if (sysArray[i].equals(sys.getId())) {
						result.add(sys);
						break;
					}
				}
			}
			list = result;
		}
		for (Sys sys : list) {
			funcs.put(sys.getId(), new ArrayList<Func>());
		}

		List<Func> allFuncs = null;
		if (currUser.getUserProp() == Constants.USER_TYPE_SYS) {
			allFuncs = funcDao.all();
		} else {
			allFuncs = funcDao.getFuncs(currUser.getUserCode());
		}
		for (Func func : allFuncs) {
			if (funcs.containsKey(func.getSys())) {
				funcs.get(func.getSys()).add(func);
			}
		}

		return funcs;
	}

	// /**
	// * 获得用户菜单
	// *
	// * @param userCode
	// * @return
	// */
	// public List getMenu(String userCode){
	// return funcDao.getMenu(userCode);
	// }
	//	
	// /**
	// * 获得指定用户的所有功能
	// *
	// * @param userCode
	// * @return
	// */
	// public List getFuncs(String userCode){
	// return funcDao.getFuncs(userCode);
	// }

	/**
	 * 获得按钮
	 * 
	 * @return
	 */
	@Override
	public List getButton(String userCode, String parentCode) {
		return funcDao.getButton(userCode, parentCode);
	}

	/**
	 * 获得按钮
	 * 
	 * @return
	 */
	@Override
	public List getButton(String userCode, String[] funcCodes) {
		return funcDao.getButton(userCode, funcCodes);
	}

	// /**
	// * 获得用户功能编号
	// * @param userCode
	// * @return
	// */
	// public List getFuncCodes(String userCode){
	// return funcDao.getFuncCodes(userCode);
	// }

	/**
	 * 保存顺序
	 * 
	 * @param funcs
	 */
	@Override
	public void saveOrder(String[] funcs) {
		funcDao.saveOrder(funcs);
	}

	/**
	 * 保存系统顺序
	 * 
	 * @param funcs
	 */
	@Override
	public void saveSysOrder(String[] funcs) {
		funcDao.saveOrder(funcs);
		funcDao.saveSysOrder(funcs);
	}

	/**
	 * 启用
	 * 
	 * @param funcCode
	 * @throws Exception
	 */
	@Override
	public void start(String[] funcCodes) {
		funcDao.changState(funcCodes, Constants.STOP_NO);
	}

	/**
	 * 停用
	 * 
	 * @param funcCode
	 */
	@Override
	public void stop(String[] funcCodes) {
		funcDao.changState(funcCodes, Constants.STOP_YES);
	}

	// @Override
	// public List delete(Grid grid) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public List page(Grid grid) {
		FuncCmd cmd = (FuncCmd) grid;
		List list = new ArrayList();
		SimpleExpression se = Restrictions.eq("parentCode", cmd.getFunc().getParentCode());
		list.add(se);
		return super.page(grid, list);
	}

	/**
	 * 判断指定的功能编号是否存在
	 * 
	 * @param userCode
	 * @return
	 */
	@Override
	public boolean exist(String funcCode) {
		return funcDao.exist(funcCode);
	}

	@Override
	public void initPubFunc() {
		// TODO Auto-generated method stub
		List list = funcDao.all();
		Iterator<Func> iterator = list.iterator();
		while(iterator.hasNext()){
			Func func = iterator.next();
			if(func.getLink()!=null && !"".equals(func.getLink()))
				Constants.pubFuncMap.put(func.getLink(), func.getFuncCode());
		}
	}

}
