package net.linybin7.core.web.context;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * ����session
 */
public class SessionManager {

	/**
	 * ��ȡ��ʼ������
	 */
	private static SessionManager instance;

	/**
	 * session�������
	 */
	private final HashMap<String, HttpSession> mymap;

	private SessionManager() {
		mymap = new HashMap<String, HttpSession>();
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return ��������
	 */

	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	/**
	 * ����session
	 * 
	 * @param session
	 *            �Ự
	 */

	public synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);

		}
	}

	/**
	 * 
	 * �Ƴ�һ��session
	 * 
	 * @param session
	 *            �Ự
	 */

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	/**
	 * ͨ��sessionid��ȡsession����
	 * 
	 * @param session_id
	 *            �ỰID sessionid
	 * @return session����
	 */

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return mymap.get(session_id);
	}

}
