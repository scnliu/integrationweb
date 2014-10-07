package net.linybin7.core.frame.func.dao;

import java.util.List;

import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;


/**
 * ���ܹ���DAO
 * 
 * 
 */
public interface FuncDao {

	/**
	 * ��������
	 * 
	 * @param func
	 */
	public void save(Func func);

	/**
	 * ����ϵͳ
	 * 
	 * @param sys
	 */
	public void save(Sys sys);

	/**
	 * ɾ������
	 * 
	 * @param funcCode
	 */
	public void delete(String funcCode);

	/**
	 * ɾ������
	 * 
	 * @param func
	 */
	public void delete(Func func);

	/**
	 * ɾ����ɫ����
	 * 
	 * @param funcCodes
	 * @throws Exception
	 */
	public void deleteRoleFuncs(String[] funcCodes);

	/**
	 * ���¹���
	 * 
	 * @param func
	 */
	public void update(Func func);

	/**
	 * ����ϵͳ
	 * 
	 * @param func
	 */
	public void update(Sys sys);

	/**
	 * ��ù���
	 * 
	 * @param funcCode
	 * @return
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
	 * ���ϵͳ����
	 * 
	 * @return
	 */
	public int getSysCont();

	/**
	 * ����ϵͳ
	 * 
	 * @return
	 */
	public List allSys();

	/**
	 * ���ϵͳ����
	 * 
	 * @param syss
	 * @return
	 */
	public List syssFuncs(String[] syss);

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
	public List getMenu(String userCode, String[] syss);

	/**
	 * ���ָ���û������й���
	 * 
	 * @param userCode
	 * @return
	 */
	public List getFuncs(String userCode);

	/**
	 * ��ð�ť
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
	 * �ı�״̬
	 * 
	 * @param funcCodes
	 * @param state
	 * @throws Exception
	 */
	public void changState(String[] funcCodes, int state);

	/**
	 * �ж�ָ���Ĺ��ܱ���Ƿ����
	 * 
	 * @param userCode
	 * @return
	 */
	public boolean exist(String funcCode);


}
