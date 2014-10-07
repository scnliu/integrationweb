package net.linybin7.core.sys.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.cmd.FuncCmd;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.frame.individuation.service.IndividuationService;
import net.linybin7.core.frame.login.cmd.LoginCmd;
import net.linybin7.core.sys.cmd.MenuCmd;
import net.linybin7.core.util.Constants;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 
 * @author HuangHuaSheng
 * @date 2012-1-12
 * @description 菜单管理和显示
 */
public class MenuCtrl extends MultiActionController {
	private FuncSve funcService;
	private IndividuationService iService;

	/**
	 * 进入主页
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView index(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/PageIndex");
		return modelAndView;
	}

	/**
	 * 进入我的空间
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView home(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/" + cmd.getPinCode());
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		Individuation theme = iService.getIndividuation(visitor.getUser().getUserCode(),"topic");
		modelAndView.addObject("individuation_theme", (theme!=null?theme.getSetting():""));
		modelAndView.addObject("user", visitor.getUser());
		return modelAndView;
	}

	/**
	 * 进入功能菜单页面
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView manage(HttpServletRequest req, HttpServletResponse rsp, MenuCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/Manage");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		modelAndView.addObject("visitor", visitor);
		modelAndView.addObject(visitor);
		SysCfg cfg = (SysCfg) req.getSession().getServletContext().getAttribute(
				Constants.KEY_SYSCFG);
		Map<String, List<Func>> sysFuncs = funcService.getFuncs(visitor.getUser(), cfg.getSyss());
		List<Func> funcs = sysFuncs.get("frame");
		List<Func> menu = new ArrayList<Func>();
		for (Func func : funcs) {
			if (func.getParentCode() != null && func.getParentCode().equals(cmd.getMenuCode())) {
				menu.add(func);
			}
		}
		if (menu.size() > 0) {
			String funcCode = menu.get(0).getFuncCode();
			modelAndView.addObject("menuCode", funcCode);
		}
		modelAndView.addObject("menu", menu);
		modelAndView.addObject("toolCode", cmd.getToolCode());
		return modelAndView;
	}

	/**
	 * 弹出窗口右边tab页
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView mainFrame(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/MainFrame");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		modelAndView.addObject("visitor", visitor);
		String funcCode = cmd.getFunc().getFuncCode();
		Func func = funcService.get(funcCode);
		modelAndView.addObject("func", func);
		return modelAndView;
	}

	public ModelAndView sys(HttpServletRequest req, HttpServletResponse rsp, LoginCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("npi/sysManage");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		modelAndView.addObject("visitor", visitor);
		modelAndView.addObject(visitor);
		return modelAndView;
	}

	/**
	 * @return the funcService
	 */
	public FuncSve getFuncService() {
		return funcService;
	}

	/**
	 * @param funcService
	 *            the funcService to set
	 */
	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	/**
	 * @return the iService
	 */
	public IndividuationService getiService() {
		return iService;
	}

	/**
	 * @param iService the iService to set
	 */
	public void setiService(IndividuationService iService) {
		this.iService = iService;
	}
	
	
}
