package net.linybin7.core.web.filter;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.linybin7.common.db.DBUtil;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.context.SessionManager;

import org.springframework.web.context.WebApplicationContext;


/**
 * 安会检查过滤器
 * 

 * 
 */
public class SecurityFilter implements Filter {
	private FilterConfig _filterConfig;

	private WebApplicationContext wac = null;
	
	final SessionManager sessionManager=SessionManager.getInstance();
	
	private final String SERVLET_PATH_LOGIN = "/login/login.do";

	private final String SERVLET_PATH_DS = "/ds/dsCfg.do";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp,
			FilterChain chain) throws IOException, ServletException {
		long begin = System.currentTimeMillis();
		HttpServletRequest httpReq = (HttpServletRequest) req;
		HttpServletResponse httpRsp = (HttpServletResponse) rsp;
		HttpSession session = httpReq.getSession(true);
		Visitor visitor = (Visitor) session.getAttribute(Constants.KEY_VISITOR);

		String action = req.getParameter("action");
		if(!action.equals("fileUpload")&&!action.equals("upload")&&!action.equals("auth")&&!action.equals("submit")){
			if (!checkDB(httpReq, httpRsp)) {
				return;
			}
	
			if (!checkLogin(httpReq, httpRsp, visitor)) {
				return;
			}
			if (!checkPermit(httpReq, httpRsp, visitor)) {
				return;
			}
			if(!action.equals("submit")){
				if(!checkCookie(httpReq, httpRsp)){
					return;
				}
			}
		}else{
			/**
			if(visitor==null){
				String orgId=req.getParameter("orgId");
				visitor=new Visitor();
				User user=new User();
				user.setOrgId(orgId);
				visitor.setUser(user);
				session.setAttribute(Constants.KEY_VISITOR,visitor);
			}
			*/
//			System.out.println("上传，不拦截");
		}
		chain.doFilter(req, rsp);
		// System.out.println(getReqFunction(httpReq)+ " 用时:" +
		// (System.currentTimeMillis() - begin));
	}
	/**
	 * 检查重启时，是否由于cookie存在，而进入无效session
	 * @author LinYuBin
	 * 2012-4-27 上午11:34:15
	 * @param req
	 * @param rsp
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean checkCookie(HttpServletRequest req, HttpServletResponse rsp)
	throws IOException, ServletException {
		String sessionId=req.getSession().getId();
		if(sessionManager.getSession(sessionId)==null){
		String jumpUrl= "/login/login.do?action=entry";
//		System.out.println(servletPath + " jumpto:" + jumpUrl);
		req.getSession().invalidate();
		req.getRequestDispatcher(jumpUrl).forward(req, rsp);
		return false;
		}else return true;
	}
	/**
	 * 检查数据库连接是否正常
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean checkDB(HttpServletRequest req, HttpServletResponse rsp)
			throws IOException, ServletException {
		String servletPath = req.getServletPath();
		if (!SERVLET_PATH_DS.equals(servletPath)
				&& (!checkDataSource("coreDs"))) {
			req.setAttribute(Constants.KEY_MSG, "数据源配置不正确，请先配置数据源。");
			req.getRequestDispatcher(SERVLET_PATH_DS + "?action=startEditDs")
					.forward(req, rsp);
			return false;
		}
		return true;
	}

	/**
	 * 检查数据源是否正常
	 * 
	 * @return
	 */
	private boolean checkDataSource(String id) {
		DataSource ds = (DataSource) wac.getBean(id);
		Connection conn = null;
		try {
			conn = ds.getConnection();
			return true;
		} catch (Exception e) {
			System.out.println("无法与数据库建立连接:" + e.getMessage());
			return false;
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 查询是否登录
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	protected boolean checkLogin(HttpServletRequest req,
			HttpServletResponse rsp, Visitor visitor) throws IOException,
			ServletException {
//		System.out.println(req.getContextPath() + " : " + req.getRequestURI()
//				+ " : " + req.getServletPath());

		String rui = req.getRequestURI().replace(req.getContextPath(), "");
		String action = req.getParameter("action");

		if (visitor == null && isMustCheck(rui, action)) {
//			req.setAttribute(Constants.KEY_MSG, "您还没有登录系统，请先登录。");
			Purview pv = Purview.instance();
			Map<String, String> urlMap = pv.getJumpUrlMap();
			String servletPath = req.getServletPath();
			String jumpUrl = urlMap.get(servletPath);
			jumpUrl = jumpUrl == null ? "/login/login.do?action=entry"
					: jumpUrl;
//			System.out.println(servletPath + " jumpto:" + jumpUrl);
			req.getRequestDispatcher(jumpUrl).forward(req, rsp);
			return false;
		}
		return true;
	}

	private boolean isMustCheck(String servletPath, String action) {
		Purview pv = Purview.instance();
		Map<String, List<String>> urlMap = pv.getNoFilterUrlMap();

//		System.out.println(urlMap);

//		System.out.println(servletPath + " : " + action);
		for (String url : urlMap.keySet()) {
			List<String> act = urlMap.get(url);
			for (String a : act) {
				if (servletPath.equals(url) && action.equals(a))
					return false;
			}
		}
		// if ((SERVLET_PATH_LOGIN.equals(servletPath) &&
		// (action.equals("entry")
		// || action.equals("caLogin") || action.equals("caLogin2")
		// || action.equals("submit") || action.equals("logout")))
		// || (SERVLET_PATH_DS.equals(servletPath) && (action
		// .equals("startEditDs") || action.equals("startSaveDs")))
		// || (servletPath.equals("/login/loginCtrl.do") && action
		// .equals("submit"))) {
		// return false;
		// }

		return true;
	}

	/**
	 * 检查权限
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean checkPermit(HttpServletRequest req,
			HttpServletResponse rsp, Visitor visitor) throws IOException,
			ServletException {

		String msg = null;
		String funcCode = Purview.instance().funcCode(req, getReqFunction(req));
		if (!StringHelper.isEmpty(funcCode)) {
			String isStop = visitor.getPurviews().get(funcCode.trim());
			if (isStop == null) {
				msg = "没有该功能访问权限";
			} else if (String.valueOf(Constants.STOP_YES).equals(isStop.trim())) {
				msg = "该功能已停用";
			}
			if (msg != null) {
				req.setAttribute(Constants.KEY_MSG, msg);
				String isOpen = req.getParameter(Constants.WINDOW_ISOPEN);
				req.setAttribute(Constants.WINDOW_ISOPEN, isOpen);
				req.getRequestDispatcher("/views/core/common/un_purview.jsp")
						.forward(req, rsp);
				return false;
			}
		}
		String path = req.getServletPath() + "?action="+req.getParameter("action");
		Map map = Constants.pubFuncMap;
		String code = (String)Constants.pubFuncMap.get(path);
		if(code!=null && !"".equals(code)){
			Integer num = (Integer)Constants.funcMap.get(code);
			if(num == null)Constants.funcMap.put(code, 1);
			else Constants.funcMap.put(code, num+1);
		}
		return true;
	}

	private String getReqFunction(HttpServletRequest req) {
		return req.getServletPath() + "-" + req.getParameter("action");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		_filterConfig = config;
		initBeanFactory(_filterConfig.getServletContext());
	}

	/**
	 * 初始化BeanFactory
	 * 
	 * @param context
	 */
	private void initBeanFactory(ServletContext context) {
		wac = (WebApplicationContext) context
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	/**
	 * 获得指定id的bean实例
	 * 
	 * @param id
	 * @return
	 */
	private Object getObject(String id) {
		return wac.getBean(id);
	}
}
