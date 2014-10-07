package net.linybin7.core.frame.ds.ctl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.util.ResourceUtil;
import net.linybin7.core.frame.bo.DataSource;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.ds.DataSourceCfg;
import net.linybin7.core.frame.ds.cmd.DataSourceCmd;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.util.ButtonUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * 蔡锐劝
 * 
 * 
 */
public class DataSourceCtl extends MultiActionController {
	private FuncSve funcService;

	private DataSourceCfg dsCfg;

	public DataSourceCtl() {
		initDsCfg();
	}

	public FuncSve getFuncService() {
		return funcService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	private void initDsCfg() {
		try {
			dsCfg = new DataSourceCfg(ResourceUtil.classPathFile("config/DBConfig.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 编辑数据源
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView startEditDs(HttpServletRequest req, HttpServletResponse rsp,
			DataSourceCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/DataSourceConfig");
		try {
			List<DataSource> list = dsCfg.getDataSource(new String[] { "coreDs" });
			cmd.setCoreDs(list.get(0));
			// cmd.setSysDs(list.get(1));//取消一个
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "加载数据源出错");
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", req.getAttribute("MSG"));
		return modelAndView;
	}

	/**
	 * 保存数据源
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView startSaveDs(HttpServletRequest req, HttpServletResponse rsp,
			DataSourceCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/login/DataSourceConfig");
		String msg = "修改源数据源配置成功！请重启服务器！";
		try {
			List<DataSource> dss = new ArrayList<DataSource>();
			cmd.getCoreDs().setName("系统管理数据源");
			// cmd.getSysDs().setName("业务功能数据源");//取消一个
			dsCfg.testDs(cmd.getCoreDs());
			// dsCfg.testDs(cmd.getSysDs());//取消一个
			dss.add(cmd.getCoreDs());
			// dss.add(cmd.getSysDs());//取消一个

			dsCfg.updateDataSource(dss);
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * 编辑数据源
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView viewDs(HttpServletRequest req, HttpServletResponse rsp, DataSourceCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/ds/ViewDSConfig");
		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			List funcs = funcService.getButton(visitor.getUser().getUserCode(),
					new String[] { "010501" });
			List<Button> buttons = ButtonUtil.buildButtons(funcs);
			modelAndView.addObject("buttons", buttons);
			List<DataSource> list = dsCfg.getDataSource(new String[] { "coreDs" });
			cmd.setCoreDs(list.get(0));
			// cmd.setSysDs(list.get(1)); //取消一个
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "加载数据源出错");
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", req.getAttribute("MSG"));
		return modelAndView;
	}

	/**
	 * 编辑数据源
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView editDs(HttpServletRequest req, HttpServletResponse rsp, DataSourceCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/ds/EditDSConfig");
		try {
			List<DataSource> list = dsCfg.getDataSource(new String[] { "coreDs" });
			cmd.setCoreDs(list.get(0));
			// cmd.setSysDs(list.get(1));//取消一个
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "加载数据源出错");
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", req.getAttribute("MSG"));
		return modelAndView;
	}

	/**
	 * 保存数据源
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView saveDs(HttpServletRequest req, HttpServletResponse rsp, DataSourceCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/ds/EditDSConfig");
		String msg = "修改源数据源配置成功！请重启服务器！";
		try {
			List<DataSource> dss = new ArrayList<DataSource>();
			cmd.getCoreDs().setName("系统管理数据源");
			// cmd.getSysDs().setName("业务功能数据源");//取消一个
			dsCfg.testDs(cmd.getCoreDs());
			// dsCfg.testDs(cmd.getSysDs());//取消一个
			dss.add(cmd.getCoreDs());
			// dss.add(cmd.getSysDs());//取消一个

			dsCfg.updateDataSource(dss);
		} catch (Exception e) {
			e.printStackTrace();
			// msg = e.getMessage();
			msg = "修改源数据源配置出错！";
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}
}
