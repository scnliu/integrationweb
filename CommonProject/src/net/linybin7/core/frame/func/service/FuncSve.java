package net.linybin7.core.frame.func.service;

import java.util.List;
import java.util.Map;

import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;
import net.linybin7.core.frame.bo.User;


/**
 * ���ܹ��������
 * 
 * 
 * 
 */
public interface FuncSve {

	/**
	 * ��������
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
	 * �޸Ĺ���
	 * 
	 * @param func
	 * @throws Exception
	 */
	public void update(Func func);

	/**
	 * ɾ������
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void delete(String[] funcCodes);

	/**
	 * ɾ������
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void delete(List funcCodes);

	/**
	 * ��ù���
	 * 
	 * @param funcCode
	 * @return
	 * @throws Exception
	 */
	public Func get(String funcCode);

	/**
	 * ���ϵͳ
	 * 
	 * @param sysId
	 * @return
	 */
	public Sys getSys(String sysId);

	/**
	 * ��ѯ����
	 * 
	 * @param conditionFunc
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List query(Func conditionFunc, int currPage, int pageSize);

	/**
	 * �������
	 * 
	 * @param condition
	 * @return
	 */
	public int getCount(final Func condition);

	/**
	 * ��ø���ϵͳ�Ĺ��ܲ˵�
	 * 
	 * @param syss
	 * @return
	 */
	public Map<String, List<Func>> sysFuncs(String syss);

	/**
	 * ������й���
	 * 
	 * @return
	 */
	public List all();

	/**
	 * ������й��ܱ��
	 */
	public List allCode();

	/**
	 * ����û��˵�
	 * 
	 * @param userCode
	 * @param syss
	 * @return
	 */
	public Map<String, Sys> getMenu(String userCode, String syss);

	/**
	 * ����û�����
	 * 
	 * @param currUser
	 * @param syss
	 * @return
	 */
	public Map<String, List<Func>> getFuncs(User currUser, String syss);

	// /**
	// * ����û��˵�,��level,order����
	// * @param userCode
	// * @return
	// */
	// public List getMenu(String userCode);
	//	
	// /**
	// * ���ָ���û������й���
	// * @param userCode
	// * @return
	// */
	// public List getFuncs(String userCode);

	/**
	 * �����û������,���ڵ��Ż�ȡ��ť
	 * 
	 * @return
	 */
	public List getButton(String userCode, String parentCode);

	/**
	 * ��ð�ť
	 * 
	 * @return
	 */
	public List getButton(String userCode, String[] funcCodes);

	// /**
	// * ����û����ܱ��
	// * @param userCode
	// * @return
	// */
	// public List getFuncCodes(String userCode);

	/**
	 * ����˳��
	 * 
	 * @param funcs
	 */
	public void saveOrder(String[] funcs);

	/**
	 * ����ϵͳ˳��
	 * 
	 * @param funcs
	 */
	public void saveSysOrder(String[] funcs);

	/**
	 * ����
	 * 
	 * @param funcCode
	 * @throws Exception
	 */
	public void start(String[] funcCodes);

	/**
	 * ͣ��
	 * 
	 * @param funcCode
	 */
	public void stop(String[] funcCodes);

	public boolean exist(String funcCode);
	
	public void initPubFunc();

}
