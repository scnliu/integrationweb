package net.linybin7.core.web.context;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 管理session
 */
public class SessionManager {

	/**
	 * 获取初始化对象
	 */
	private static SessionManager instance;

	/**
	 * session存放容器
	 */
	private final HashMap<String, HttpSession> mymap;

	private SessionManager() {
		mymap = new HashMap<String, HttpSession>();
	}

	/**
	 * 获取单例对象
	 * 
	 * @return 单例对象
	 */

	public static SessionManager getInstance() {
		if (instance == null) {
			instance = new SessionManager();
		}
		return instance;
	}

	/**
	 * 增加session
	 * 
	 * @param session
	 *            会话
	 */

	public synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);

		}
	}

	/**
	 * 
	 * 移除一个session
	 * 
	 * @param session
	 *            会话
	 */

	public synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	/**
	 * 通过sessionid获取session对象
	 * 
	 * @param session_id
	 *            会话ID sessionid
	 * @return session对象
	 */

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return mymap.get(session_id);
	}

}
