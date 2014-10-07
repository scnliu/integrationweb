package net.linybin7.core.frame.serviceMsg.ctl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.linybin7.common.tag.Msg;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.serviceMsg.cmd.ServiceMsgCmd;
import net.linybin7.core.util.Constants;

import org.json.JSONObject;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class ServiceMsgCtrl extends MultiActionController {

	public void readRunMsg(HttpServletRequest req, HttpServletResponse rsp, ServiceMsgCmd cmd) {
		ServiceMsgCmd g = cmd;
		Runtime rt = Runtime.getRuntime();
		double mem = 0.0;

		mem = rt.maxMemory() / 1024 / 1024;
		g.setJvmMaxMemory(Double.toString(mem) + "MB");

		mem = rt.totalMemory() / 1024 / 1024;
		g.setJvmTotalMemory(Double.toString(mem) + "MB");

		mem = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;
		g.setJvmUsedMemory(Double.toString(mem) + "MB");

		mem = (rt.maxMemory() - (rt.totalMemory() - rt.freeMemory())) / 1024 / 1024;
		g.setJvmFreeMemory(Double.toString(mem) + "MB");

		String loginName = "";
		HashMap userMap = new HashMap();
		userMap = Constants.sessionMap;
		Iterator it = userMap.entrySet().iterator();
		HttpSession session = null;
		int loginCount = 0;
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			if (m.getKey().toString().length() < 32) {
				session = (HttpSession) m.getValue();
				loginName += m.getKey() + "," + session.getCreationTime() +","+session.getAttribute("loginIP").toString()+";";
				loginCount++;
			}
		}
		if (loginName != null && loginName.length() != 0) {
			loginName = loginName.substring(0, loginName.length() - 1);
		}
		String sContextPath = req.getContextPath();
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		/**
		loginName = getLoginNameLink(loginName, sContextPath, user.getUserProp(), user
				.getUserCode());
		cmd.setLoginName(loginName);
		cmd.setLoginCount(loginCount);
		*/
		try {
			Msg.writeObj(g, rsp);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	public String getLoginNameLink(String loginName, String sContextPath, int userprop,
			String usercode) {
		if (loginName != null && loginName.length() != 0) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String[] tmps = loginName.split(";");
			StringBuffer sb = new StringBuffer();
			sb
					.append("<table width='100%'><tr><td style='border: 0;' width='35%'>&nbsp;&nbsp;登陆用户</td><td align='center' width='35%' style='border: 0;'>登录IP</td><td align='center' width='30%' style='border: 0;'>在线时长</td>");
			List<Map<String, Object>> user = new ArrayList<Map<String, Object>>();
			Map<String, Object> newUser = new HashMap<String, Object>();
			for (int i = 0; i < tmps.length; i++) {
				String[] loginMessage = tmps[i].split(",");
				if (loginMessage[0].toString().length() > 0) {// onmouseout=onMouseOutCell()
					user = parserdao.queryList("select USERNAME from SA_USER where USERCODE='"
							+ loginMessage[0].trim() + "'");
					newUser = user.get(0);
					sb.append("<tr><td style='border: 0;'>");
					sb
							.append("<div><a style='text-decoration: none;' onmouseover=showDelete(this,'"
									+ loginMessage[0].trim()
									+ "','"
									+ (usercode.trim().equals(loginMessage[0].trim()) ? 1
											: userprop)
									+ "') onclick=onMouseOverCell('"
									+ loginMessage[0].trim()
									+ "','"
									+ loginMessage[0].trim()
									+ "','',event,this) href='#'");
					sb.append("><image src='" + sContextPath + "/images/head/loginName.png'/>"
							+ loginMessage[0].trim() + "-" + newUser.get("USERNAME")
							+ "&nbsp;&nbsp;");
					sb.append("</a></div></td>");
					sb.append("<td align='center' style='border: 0;'>"
							+ loginMessage[2] + "</td>");
					sb
							.append("<td align='center' style='border: 0;'>"
									+ loginTime(date.getTime() - Long.parseLong(loginMessage[1]))
									+ "</td>");
					sb.append("</tr>");
				}
			}
			sb.append("</table>");
			return sb.toString();
		}
		return "";
	}
	*/

	private String loginTime(long millisecond) {
		StringBuffer sb = new StringBuffer();
		int hms;
		boolean exist = false;
		if ((hms = (int) (millisecond / (1000 * 60 * 60))) > 0) {
			exist = true;
			sb.append(hms + "小时");
			millisecond = millisecond % (1000 * 60 * 60);
		}
		if ((hms = (int) (millisecond / (1000 * 60))) > 0) {
			sb.append(hms + "分");
			millisecond = millisecond % (1000 * 60);
			exist = true;
		} else if (exist) {
			sb.append(0 + "分");
		}
		if ((hms = (int) (millisecond / 1000)) > 0) {
			sb.append(hms + "秒");
			millisecond = millisecond % 1000;
		} else {
			sb.append(0 + "秒");
		}
		return sb.toString();
	}
	
	/**
	public ModelAndView querySeviceMsg(HttpServletRequest req, HttpServletResponse rsp,
			ServiceMsgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/serviceMsg/ServiceMsgMain");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		Properties props = System.getProperties(); // 获得系统属性集
		String systemName = props.getProperty("os.name");
		String systemVersion = props.getProperty("os.version");
		cmd.setSystemName(systemName);
		cmd.setSystemVersion(systemVersion);

		String serverName = "";
		String serverVersion = "";
		ServletContext sContext = req.getSession().getServletContext();
		if (sContext != null) {
			String serverStr = sContext.getServerInfo();
			;
			String[] strs = serverStr.split("/");
			if (strs != null && strs.length == 2) {
				serverName = strs[0];
				serverVersion = strs[1];
			} else {
				serverName = serverStr;
				serverVersion = serverStr;
			}
		} else {
			serverName = "未知";
			serverVersion = "未知";
		}
		cmd.setServerName(serverName);
		cmd.setServerVersion(serverVersion);

		DatabaseMetaData dmd = null;
		Connection conn = null;
		String dbName = "";
		String dbVersion = "";
		String dbDriver = "";
		String dbUrl = "";
		conn = parserdao.getConnection();
		try {
			if (conn != null) {
				dmd = conn.getMetaData();
				dbName = dmd.getDatabaseProductName();
				String version = dmd.getDatabaseProductVersion();
				if (version.indexOf("Oracle") != -1) {
					// if ("Oracle".equals(version)){
					dbName = "Oracle";
					dbVersion = "Oracle";
					int startIndex = version.indexOf("Release") + "Release".length() + 1;
					int endIndex = version.indexOf("-");
					dbVersion = version.substring(startIndex, endIndex);
				} else {
					dbName = version.replace("\n", " ");
					dbVersion = "";
				}
				dbDriver = dmd.getDriverName();
				dbUrl = dmd.getURL().replace("\n", " ");
			} else {
				dbVersion = "未知";
				dbDriver = "未知";
				dbUrl = "未知";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
			}
		}
		cmd.setDbName(dbName);
		cmd.setDbVersion(dbVersion);
		cmd.setDbDriver(dbDriver);
		cmd.setDbUrl(dbUrl);

		Runtime rt = Runtime.getRuntime();
		double mem = 0.0;
		cmd.setJvmid(System.getProperty("java.vm.version"));
		mem = rt.maxMemory() / 1024 / 1024;
		cmd.setJvmMaxMemory(Double.toString(mem) + "MB");
		mem = rt.totalMemory() / 1024 / 1024;
		cmd.setJvmTotalMemory(Double.toString(mem) + "MB");
		mem = rt.freeMemory() / 1024 / 1024;
		cmd.setJvmFreeMemory(Double.toString(mem) + "MB");
		mem = (rt.maxMemory() - rt.freeMemory()) / 1024 / 1024;
		cmd.setJvmUsedMemory(Double.toString(mem) + "MB");

		String loginName = "";
		HashMap userMap = new HashMap();
		userMap = Constants.sessionMap;
		Iterator it = userMap.entrySet().iterator();
		HttpSession session = null;
		int loginCount = 0;
		while (it.hasNext()) {
			Map.Entry m = (Map.Entry) it.next();
			if (m.getKey().toString().length() < 32) {
				try {
					session = (HttpSession) m.getValue();
					loginName += m.getKey() + "," + session.getCreationTime() + ","+session.getAttribute("loginIP").toString()+";";
					loginCount++;
				} catch (Exception e) {

				}

			}
		}
		String sContextPath = req.getContextPath();
		loginName = getLoginNameLink(loginName, sContextPath, user.getUserProp(), user
				.getUserCode());
		cmd.setLoginName(loginName);
		cmd.setLoginCount(loginCount);
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}
	public void readUserMsg(HttpServletRequest req, HttpServletResponse rsp, ServiceMsgCmd cmd) {

		String userID = req.getParameter("USERID").toString().trim();
		List<Map<String, Object>> user = parserdao
				.queryList("select * from SA_USER where USERCODE='" + userID + "'");
		Map<String, Object> newUser = user.get(0);
		HttpSession session = (HttpSession) Constants.sessionMap.get(userID);
		StringBuffer sb = new StringBuffer();
		sb.append("<center>"
				+ painColor("<span style='font-size:15px;font-weight: bold;'>" + userID
						+ "</span>&nbsp;<span style='font-size:15px;'>的信息</span>", "#0290fd")
				+ "</center>");
		if (session != null) {
			sb
					.append("<table width='100%'><tr><td style='line-height: 10px;'>登录IP:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ session.getAttribute("loginIP").toString() + "</td></tr>");
			sb
					.append("<tr><td style='line-height: 10px;'>登录时间:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ session.getAttribute("loginTime").toString() + "</td></tr>");
			sb
					.append("<tr><td style='line-height: 10px;'>用户名:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ (newUser.get("USERNAME") == null ? "" : newUser.get("USERNAME"))
							+ "</td></tr>");
			sb
					.append("<tr><td style='line-height: 10px;'>用户邮箱:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ (newUser.get("EMAIL") == null ? "" : newUser.get("EMAIL"))
							+ "</td></tr>");
			sb
					.append("<tr><td style='line-height: 10px;'>用户类型:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ getPROP(Integer.parseInt(newUser.get("USERPROP") + ""))
							+ "</td></tr>");
			sb
					.append("<tr><td style='line-height: 10px;'>工作区:</td><td style='line-height: 10px;color:#ec8c02;'>"
							+ (newUser.get("ORGID") == null ? "" : newUser.get("ORGID"))
							+ "</td></tr></table>");
		} else {
			sb.append("<center>" + painColor("[" + userID + "]已经离线", "red") + "</center>");
		}
		List datas = new ArrayList();
		datas.add(sb.toString());
		try {
			Msg.writeObj(datas, rsp);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	*/
	String painColor(String arf, String color) {
		return "<font color='" + color + "'>" + arf + "</font>";
	}

	String getPROP(int userProp) {
		String sResult = "";
		if (userProp == 1) {
			sResult = "普通用户";
		} else if (userProp == 2) {
			sResult = "管理员";
		} else if (userProp == 3) {
			sResult = "超级管理员";
		}
		return sResult;
	}

	public void logout(HttpServletRequest req, HttpServletResponse rsp, Object cmd) {
		String username = req.getParameter("username");
		Constants.sessionMap.remove(username);
	}

	public void getServerTime(HttpServletRequest req, HttpServletResponse rsp, Object cmd) {
		DateFormat df = new SimpleDateFormat("yyyy/M/d_HH:mm:ss");
		String dateTime = df.format(new Date());
		String sec = dateTime.substring(dateTime.lastIndexOf(":") + 1);
		dateTime = dateTime.substring(0, dateTime.lastIndexOf(":"));
		String date = dateTime.substring(0, dateTime.indexOf("_"));
		String time = dateTime.substring(dateTime.indexOf("_") + 1);
		JSONObject obj = new JSONObject();
		obj.put("date", date);
		obj.put("time", time);
		obj.put("sec", sec);
		try {
			rsp.getWriter().write(obj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}