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
		// 初始化当前session
		Constants.sessionMap.put(session.getId(), session);
		session.setAttribute("loginUserCode", "");
		System.out.println("登录sessionID：" + session.getId());
		sessionManager.AddSession(session);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		sessionManager.DelSession(session);
		// 判断当前session user是否有值
		if (session.getAttribute("loginUserCode") != null
				&& session.getAttribute("loginUserCode").toString().length() > 0) {
			// session销毁清空map 更新map
			long beginTime = session.getCreationTime();
			Date d = new Date();
			UserSve userService = (UserSve)SpringBeanFactory.getBean("userServiceImp");
			User user = userService.get(session.getAttribute("loginUserCode").toString());
			Long l = new Long(d.getTime()-beginTime);
			user.setOnTime(user.getOnTime()+l.intValue());
			userService.update(user);
			Constants.sessionMap.remove(session.getAttribute("loginUserCode").toString());
			session.invalidate();
			System.out.println("销毁sessionID：" + session.getId());
		}
	}
}