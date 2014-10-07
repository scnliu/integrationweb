package net.linybin7.core.frame.log.ctrl;

import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.frame.log.service.SysLogSvc;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * @author Huangyouwen 2010-11-24 ÏÂÎç03:01:50
 */
public class SysLogCtrl extends MultiActionController {

	private SysLogSvc sysLogSvc;

	private FuncSve funcService;

	public SysLogSvc getSysLogSvc() {
		return sysLogSvc;
	}

	public void setSysLogSvc(SysLogSvc sysLogSvc) {
		this.sysLogSvc = sysLogSvc;
	}

	public FuncSve getFuncService() {
		return funcService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}
}
