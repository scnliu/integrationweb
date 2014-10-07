package net.linybin7.core.web.context;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.user.service.UserSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.util.SpringBeanFactory;


public class SessionListener implements HttpSessionListener {
	private final SessionManager sessionManager = SessionManager.getInstance();

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		// ��ʼ����ǰsession
		Constants.sessionMap.put(session.getId(), session);
		session.setAttribute("loginUserCode", "");
		System.out.println("��¼sessionID��" + session.getId());
		sessionManager.AddSession(session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		sessionManager.DelSession(session);
		// �жϵ�ǰsession user�Ƿ���ֵ
		if (session.getAttribute("loginUserCode") != null
				&& session.getAttribute("loginUserCode").toString().length() > 0) {
			// session�������map ����map
			long beginTime = session.getCreationTime();
			Date d = new Date();
			UserSve userService = (UserSve)SpringBeanFactory.getBean("userServiceImp");
			User user = userService.get(session.getAttribute("loginUserCode").toString());
			Long l = new Long(d.getTime()-beginTime);
			user.setOnTime(user.getOnTime()+l.intValue());
			userService.update(user);
			Constants.sessionMap.remove(session.getAttribute("loginUserCode").toString());
			session.invalidate();
			System.out.println("����sessionID��" + session.getId());
		}
	}
}