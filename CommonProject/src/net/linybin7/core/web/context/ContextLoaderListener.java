package net.linybin7.core.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.linybin7.core.config.Config;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.filter.Purview;
import net.linybin7.core.web.util.SpringBeanFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 上下文加载器
 * 
 * 
 * 
 */
public class ContextLoaderListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebContext.contextPath = event.getServletContext().getRealPath("/");
		System.out.println("WebContext.contextPath:" + WebContext.contextPath);
		SysCfg sysCfg = Config.instance().getTopic();
		sysCfg.setPrefix(event.getServletContext().getContextPath());
		event.getServletContext().setAttribute(Constants.KEY_SYSCFG, sysCfg);
		Purview.instance();
		try {
			ServletContext context = event.getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils
					.getRequiredWebApplicationContext(context);
			SpringBeanFactory.setBeanFactory(ctx);
			
			FuncSve funcService = (FuncSve)SpringBeanFactory.getBean("funcServiceImp");
			funcService.initPubFunc();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
