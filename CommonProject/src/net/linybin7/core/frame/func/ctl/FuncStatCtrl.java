package net.linybin7.core.frame.func.ctl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.component.table.Column;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.FuncStat;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.cmd.FuncStatBean;
import net.linybin7.core.frame.func.cmd.FuncStatCmd;
import net.linybin7.core.frame.func.service.FuncStatSve;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.util.ButtonUtil;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class FuncStatCtrl extends MultiActionController{
	
	private static Logger log = Logger.getLogger(FuncStatCtrl.class);

	private FuncStatSve statService;
	
	private FuncSve funcService;

	public void setStatService(FuncStatSve statService) {
		this.statService = statService;
	}

	public FuncStatSve getStatService() {
		return statService;
	}
	
	public FuncSve getFuncService() {
		return funcService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	public ModelAndView main(HttpServletRequest req,HttpServletResponse rsp,FuncStatCmd cmd){
		ModelAndView model = new ModelAndView("core/func/StatFunc");
		try{
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			List funcs = funcService.getButton(visitor.getUser().getUserCode(), "0105");
			List<Button> buttons = ButtonUtil.buildButtons(funcs);
			model.addObject("buttons", buttons);
			List parentList = statService.statModule();
			List childList = statService.stat();
			model.addObject("parentList",parentList);
			model.addObject("childList",childList);
			model.addObject("cmd",cmd);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return model;
	}
	
}
