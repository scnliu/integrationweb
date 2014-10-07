package net.linybin7.core.frame.login.ctl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.linybin7.common.util.PropertiesUtil;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.log.LoggerManager;
import net.linybin7.core.frame.login.cmd.LoginCmd;
import net.linybin7.core.frame.org.service.OrgSve;
import net.linybin7.core.frame.user.cmd.UserCmd;
import net.linybin7.core.frame.user.service.UserSve;
import net.linybin7.core.license.Verifier;
import net.linybin7.core.security.MD5;
import net.linybin7.core.util.Constants;
import net.linybin7.core.util.SysLog;
import net.linybin7.core.web.context.SessionManager;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class LoginCtrl extends MultiActionController {
	private final String[][] typeOrg = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.USER_TYPE_COMMON), "��ͨ�û�" },
			{ String.valueOf(Constants.USER_TYPE_ORG), "���Ź���Ա" } };

	private final String[][] typeSys = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.USER_TYPE_COMMON), "��ͨ�û�" },
			{ String.valueOf(Constants.USER_TYPE_ORG), "���Ź���Ա" },
			{ String.valueOf(Constants.USER_TYPE_SYS), "ϵͳ����Ա" } };

	private UserSve userService;

	public UserSve getUserService() {
		return userService;
	}

	public void setUserService(UserSve userService) {
		this.userService = userService;
	}

	private static Logger logger = LoggerManager.getSysLogger();

	/**
	 * �����¼ҳ��
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView entry(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = cmd.isUkLogin() ? new ModelAndView("core/login/Loginuk")
				: new ModelAndView("core/login/Login");
		modelAndView.addObject("exit", false);
		return modelAndView;
	}


	/**
	 * ȡIP��ַ
	 * 
	 * @author YKChoi @ datetime 2010-11-29 ����04:26:02
	 * @param request
	 * @return
	 */
	protected static String getRemortIP(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	protected static String getDateTimeString(Date date) {
		Format format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datestr = "";
		if (date != null) {
			datestr = format.format(date);
		}
		return datestr;
	}

	public ModelAndView cancelSubmit(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		String msg = null;
		msg = "ǿ�Ƶ�¼";
		cmd.setVtype("0");
		return errorLogin(req, rsp, cmd, msg);
	}

	final SessionManager sessionManager = SessionManager.getInstance();

	/**
	 * ��¼��ť�¼�����
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView submit(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		String msg = null;
		User user = null;
		String verifyCode = (String) req.getSession().getAttribute("verifyCode");
		/**
		 * ��¼��֤�� if(!"1".equals(cmd.getVtype())&&verifyCode!=null){
		 * if(cmd.getVerifyCode()==null||!verifyCode.equals(cmd.getVerifyCode())){ ModelAndView
		 * modelAndView = new ModelAndView("core/login/Login"); modelAndView.addObject("cmd", cmd);
		 * modelAndView.addObject("verifyError", "11"); return modelAndView; } }
		 */

		// ��֤֤��
		try {
			new Verifier().verifyCert();
		} catch (Exception e) {
			msg = "<strong>��ϵͳδ����Ȩ��ʹ�����޵���</strong><br/>�������ʣ�����ϵ����Ա��";
			return errorLogin(req, rsp, cmd, msg);
		}

		try {
			MD5 md5 = new MD5();
			Date nowDate = new Date();
			long nDate = nowDate.getTime();
			String userName = cmd.getUserCode() == null ? "" : cmd.getUserCode();
			HttpSession session = null;
			if (cmd.getVtype().trim().equals("")) {
				cmd.setVtype("0");
			}
			user = userService.get(userName);
			if (user == null) {
				msg = "�û�ID������";
				cmd.setUserCode("");
				cmd.setPassword("");
			} else if (!user.getPassword().trim().equals(md5.getMD5ofStr(cmd.getPassword().trim()))) {
				cmd.setPassword("");
				msg = "���벻��ȷ";
			} else if (user.getIsStop() == Constants.STOP_YES) {
				msg = "�û���ͣ��";
			} else if (user.getLimitType() == 1 && user.getLoginNum() > user.getPermitNum()) {
				msg = "�û�������¼����";
			} else if (user.getLimitType() == 2 && nDate >= user.getLimitDate()) {
				msg = "�û��Ѿ�����";
			} else {
				session = req.getSession();

				System.out.println("ȡ��ʼ[sessionID]��" + session.getId());
				String LastingUser = session.getAttribute("loginUserCode").toString();
				if (!LastingUser.equals(userName.trim())) {
					if (Constants.sessionMap.get(userName.trim()) != null
							&& Constants.sessionMap.get(userName.trim()).toString().length() > 0) {
						String s = cmd.getVtype().trim();
						if (s.equals("0")) {
							msg = "ǿ�Ƶ�¼";
							System.out.println("ǿ�Ƶ�¼ע��ǰ[sessionID]��" + session.getId());
							destroyNewSession(session);
							System.out.println("ǿ�Ƶ�¼ע����[sessionID]��" + session.getId());
							cmd.setVtype("1");
							SysLog.info("�û���¼","ǿ�Ƶ�¼",req,null);
							return errorLogin(req, rsp, cmd, msg);
						} else if (s.equals("1")) {
							// ��ǰ�û��Ѿ����� ɾ���û�
							cmd.setVtype("0");
							HttpSession sessionold = (HttpSession) Constants.sessionMap
									.get(userName.trim());
							System.out.println("�ڶ��ε�¼ǰ��[sessionID]��" + sessionold.getId());
							Constants.sessionMap.remove(userName.trim());
							// ע���������û�session
							if (sessionold != null) {
								try {
									sessionold.invalidate();
								} catch (Exception e) {
									System.out.println("LoginSessionListener Exception:"
											+ e.getMessage());
								}
							}
							// ����������û�������map key ��ǰ�û� value session����
							Constants.sessionMap.put(userName.trim(), session);
							Constants.sessionMap.remove(session.getId());
							session.setAttribute("loginUserCode", userName);
							session.setAttribute("loginIP", getRemortIP(req));
							session.setAttribute("loginTime", getDateTimeString(new Date()));
							System.out.println("�ڶ��ε�¼����[sessionID]��" + session.getId());
						} else {
							msg = "δ֪����";
							destroyNewSession(session);
							cmd.setVtype("0");
							SysLog.info("�û���¼","��¼ʧ��",req,null);
							return errorLogin(req, rsp, cmd, msg);
						}
					} else {
						HttpSession sessionLast = (HttpSession) Constants.sessionMap
								.get(LastingUser.trim());
						String sessionLastingID = "";
						if (sessionLast == null) {
							sessionLastingID = "";
						} else {
							sessionLastingID = sessionLast.getId();
						}
						if (session.getId().equals(sessionLastingID)) {
							Constants.sessionMap.get(session.getId());
							Constants.sessionMap.put(userName.trim(), sessionLast);
							Constants.sessionMap.remove(session.getId());
						} else {
							Constants.sessionMap.get(session.getId());
							Constants.sessionMap.put(userName.trim(), session);
							Constants.sessionMap.remove(session.getId());
						}
						session.setAttribute("loginUserCode", userName);
						session.setAttribute("loginIP", getRemortIP(req));
						session.setAttribute("loginTime", getDateTimeString(new Date()));
					}
				} else {
					Constants.sessionMap.get(session.getId());
					Constants.sessionMap.put(userName.trim(), session);
					Constants.sessionMap.remove(session.getId());

				}
				if (sessionManager.getSession(session.getId()) == null) {
					sessionManager.AddSession(session);
				}
				if (user.getLimitType() == 1) {
					user.setLoginNum(user.getLoginNum() + 1);
				}
				initSession(req, user, session);
				Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
				msg = "��¼�ɹ�";

				SysLog.info("�û���¼","��¼�ɹ�",req,null);
				if (PropertiesUtil.getProperty("config/config.properties", "sys.isnew", "false")
						.equalsIgnoreCase("true")) {
					ModelAndView modelAndView = new ModelAndView("core/PageIndex");
					visitor.getUser().setOrgId(null);
					visitor.getUser().setNums(visitor.getUser().getNums()+1);
					userService.update(visitor.getUser());
					modelAndView.addObject(visitor);
					return modelAndView;
				} else {
					if (Constants.INDIVIDUATION_MODE_NO_FULL_SCREEN.equals(visitor
							.getIndividuations().get(Constants.INDIVIDUATION_MODE))) {
						return index(req, rsp, cmd);
					} else if (Constants.INDIVIDUATION_MODE_FULL_SCREEN.equals(visitor
							.getIndividuations().get(Constants.INDIVIDUATION_MODE))) {
						return fullScreen(req, rsp, cmd);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (msg == null)
				msg = "��¼����";
		}
		if (user != null) {
			SysLog.info("�û���¼","��¼����",req,null);
		}
		return errorLogin(req, rsp, cmd, msg);
	}

	OrgSve orgService;

	/**
	 * ��ʼ��Session
	 * 
	 * @param req
	 * @param user
	 */
	protected void initSession(HttpServletRequest req, User user, HttpSession session) {
		SysCfg sysCfg = (SysCfg) session.getServletContext().getAttribute(Constants.KEY_SYSCFG);
		Visitor visitor = new Visitor();
		visitor.setUser(user);
		try {
			visitor.setOrgs(orgService.orgsList(user));
		} catch (Exception e) {
		}
		session.setAttribute(Constants.KEY_VISITOR, visitor);
		Map<String, String> purviews = userService.getPuerviewCodes(user.getUserCode());
		visitor.setPurviews(purviews);
		Map<String, String> individuations = userService.getIndividuation(sysCfg.getSysKey(), user
				.getUserCode());
		// ����Ĭ�ϵĸ��Ի�
		setDefault(individuations);
		visitor.setIndividuations(individuations);
		// preparedIndexPage(sysCfg, visitor);
	}

	/**
	 * ����Session
	 * 
	 * @param req
	 */
	protected void destroySession(HttpSession session) {
		// �жϵ�ǰsession user�Ƿ���ֵ
		if (session.getAttribute("loginUserCode") != null
				&& session.getAttribute("loginUserCode").toString().length() > 0) {
			// session�������map ����map
			Constants.sessionMap.remove(session.getAttribute("loginUserCode").toString());
			session.invalidate();
			System.out.println("�ֶ�����sessionID��" + session.getId());
		}
	}

	protected void destroyNewSession(HttpSession session) {
		// �жϵ�ǰsession user�Ƿ���ֵ
		String sessionId = "";
		try {
			sessionId = session.getId();
			session.invalidate();
		} catch (Exception e) {
			System.out.println("����sessionID��[" + sessionId + "]" + e.getMessage());
		}
	}

	/**
	 * ��֤�û��Ƿ���ڣ������ڣ��򷵻ظ��û������򣬷���null
	 * 
	 * @param userCode
	 * @param password
	 * @return
	 */
	public User verifyUser(String userCode, String password) {
		User user = null;
		try {
			MD5 md5 = new MD5();
			user = userService.get(userCode);
			if (user == null) {
				return null;
			} else if (!user.getPassword().trim().equals(md5.getMD5ofStr(password.trim()))) {
				return null;
			} else if (user.getIsStop() == Constants.STOP_YES) {
				// return user;
				// } else {
				return user;
			}
		} catch (Exception e) {
		}
		return null;
	}

	public ModelAndView errorLogin(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd,
			String msg) {
		ModelAndView modelAndView = new ModelAndView("core/login/Login");
		if (msg.equals("ǿ�Ƶ�¼")) {
			modelAndView.addObject("Force_Login", msg);// Force_Login
		} else {
			if (!msg.equals("��¼����")) {
				modelAndView.addObject(Constants.KEY_MSG, msg);
			}
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	public ModelAndView mode(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		if (Constants.INDIVIDUATION_MODE_NO_FULL_SCREEN.equals(visitor.getIndividuations().get(
				Constants.INDIVIDUATION_MODE))) {
			return noFullScreen(req, rsp, cmd);
		} else if (Constants.INDIVIDUATION_MODE_FULL_SCREEN.equals(visitor.getIndividuations().get(
				Constants.INDIVIDUATION_MODE))) {
			return fullScreen(req, rsp, cmd);
		}
		return fullScreen(req, rsp, cmd);
	}

	public ModelAndView fullScreen(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/Fullscreen");
		return modelAndView;
	}

	public ModelAndView index(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/Index");
		return modelAndView;
	}

	public ModelAndView noFullScreen(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/NoFullscreen");
		return modelAndView;
	}

	/**
	 * ���µ�¼
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/Login");
		HttpSession session = req.getSession(true);
		if (session.getAttribute("loginUserCode") != null
				&& session.getAttribute("loginUserCode").toString().length() > 0) {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			if (visitor != null) {
				if (visitor.getUser() != null) {
					long beginTime = session.getCreationTime();
					Date d = new Date();
					Long l = new Long(d.getTime()-beginTime);
					visitor.getUser().setOnTime(visitor.getUser().getOnTime()+l.intValue());
					userService.update(visitor.getUser());
					
					SysLog.info("�û���¼","���µ�¼",req,null);
				}
			}
			// session�������map ����map
			Constants.sessionMap.remove(session.getAttribute("loginUserCode").toString());
			try {
				session.invalidate();
				req.getSession(false);
			} catch (Exception e) {
				System.out.println("LoginSessionListener Exception:" + e.getMessage());
			}
			System.out.println("���µ�¼ʱ����sessionID��" + session.getId());
		}
		if (req.getParameter("exit") != null) {
			modelAndView.addObject("exit", true);
		} else {
			modelAndView.addObject("exit", false);
		}
		return modelAndView;
	}

	private void preparedCmd(User curUser, UserCmd cmd) {
		if (curUser.getUserProp() == Constants.USER_TYPE_SYS) {
			cmd.setType(typeSys);
		} else {
			cmd.setType(typeOrg);
		}
	}

	/**
	 * ���Ի���Ϣ
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	/*
	 * public ModelAndView individuation(HttpServletRequest req, HttpServletResponse rsp, UserCmd
	 * cmd) { ModelAndView modelAndView = new ModelAndView("core/user/Individuation"); Map<String,
	 * String> individuations = new HashMap<String, String>(); Individuation indexPage = null;
	 * Visitor visitor = (Visitor) req.getSession().getAttribute( Constants.KEY_VISITOR); SysCfg
	 * sysCfg = (SysCfg) req.getSession().getServletContext() .getAttribute(Constants.KEY_SYSCFG);
	 * try {
	 * 
	 * individuations = visitor.getIndividuations(); indexPage = userService.getIndividuation(new
	 * Individuation(sysCfg .getSysKey(), visitor.getUser().getUserCode(),
	 * Constants.INDIVIDUATION_INDEX)); if (indexPage == null) { indexPage = new
	 * Individuation(sysCfg.getSysKey(), visitor .getUser().getUserCode(),
	 * Constants.INDIVIDUATION_INDEX, "_WELCOME_PAGE", "��ҳ", "��ӭҳ��"); } } catch (Exception e) {
	 * e.printStackTrace(); }
	 * 
	 * TreeModel model = buildSFTreeModel(req, visitor.getUser(), req .getContextPath(),
	 * indexPage.getValue()); modelAndView.addObject("treeModel", model);
	 * 
	 * modelAndView.addObject("ind", individuations); modelAndView.addObject("indexPage",
	 * indexPage); return modelAndView; }
	 */

	/**
	 * ����Ĭ�ϵĸ��Ի�
	 * 
	 * @param individuations
	 */
	private void setDefault(Map<String, String> individuations) {
		if (!individuations.containsKey(Constants.INDIVIDUATION_MEMU)) {
			individuations.put(Constants.INDIVIDUATION_MEMU, Constants.INDIVIDUATION_MEMU_DEFAULT);
		}
		if (!individuations.containsKey(Constants.INDIVIDUATION_TOPIC)) {
			individuations
					.put(Constants.INDIVIDUATION_TOPIC, Constants.INDIVIDUATION_TOPIC_DEFAULT);
		}
		if (!individuations.containsKey(Constants.INDIVIDUATION_MODE)) {
			individuations.put(Constants.INDIVIDUATION_MODE, Constants.INDIVIDUATION_MODE_DEFAULT);
		}
		if (!individuations.containsKey(Constants.INDIVIDUATION_INDEX)) {
			individuations
					.put(Constants.INDIVIDUATION_INDEX, Constants.INDIVIDUATION_INDEX_DEFAULT);
		}
	}

	// ����
	public ModelAndView help(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/Help");
		return modelAndView;
	}

	// ����
	public ModelAndView about(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/About");
		return modelAndView;
	}

	// ��ϵcontact
	public ModelAndView contact(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/Contact");
		return modelAndView;
	}

	// �л�������
	public void changeScene(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String scene = req.getParameter("scene");
		if (scene == null || "".equals(scene))
			user.setOrgId(null);
		else
			user.setOrgId(scene);
		userService.update(user);
		// userService.editTheme(user, bg);
	}

	public OrgSve getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgSve orgService) {
		this.orgService = orgService;
	}

}
