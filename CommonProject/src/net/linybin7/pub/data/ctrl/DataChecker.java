package net.linybin7.pub.data.ctrl;

import java.util.List;

import net.linybin7.pub.data.bo.DataCheck;

/**
 * @author Linyubin
 * 
 */
public interface DataChecker {
	/**
	 * 
	 * @author Linyubin 2012-2-28����10:47:32
	 * @param title
	 *            �����ļ�����
	 * @param objs
	 *            ��������
	 * @param datachek
	 *            ���״̬
	 * @return
	 */
	public DataCheck check(List title, Object[] objs, DataCheck datachek);
}
