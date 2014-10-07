package net.linybin7.core.frame.func.ctl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.component.table.Column;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.page.PageService;
import net.linybin7.common.tag.Msg;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.config.Config;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Sys;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.cmd.FuncCmd;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.tree.TreeModel;
import net.linybin7.core.web.component.tree.TreeNode;
import net.linybin7.core.web.component.util.ButtonUtil;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * ���ܹ��������
 * 
 * 
 * 
 */
public class FuncCtl extends MultiActionController {
	private FuncSve funcService;

	private PageService pageService;

	public void page(HttpServletRequest req, HttpServletResponse rsp, FuncCmd grid) {
		grid.page(pageService, rsp);

	}

	@Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
			throws Exception {
		super.initBinder(req, binder);
		// DateEditor dateEditor = new DateEditor("yyyy-mm-dd");
		// binder.registerCustomEditor(Date.class, dateEditor);
	}

	public FuncSve getFuncService() {
		return funcService;
	}

	public PageService getPageService() {
		return pageService;
	}

	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	/**
	 * ��ҳ
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView index(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/Index");
		return modelAndView;
	}

	/**
	 * ����
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView leftTree(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/LeftTree");
		try {
			List<TreeModel> tms = buildTreeModel(req, req.getContextPath());
			modelAndView.addObject("tms", tms);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "���ɹ���������");
		}

		return modelAndView;
	}

	/**
	 * �Ƿ��Ǹ��ڵ�.����Ǹ��ڵ�,����ϵͳid��Ϊ��,�����ù��ܶ����������
	 * 
	 * @param cmd
	 * @return
	 */
	private boolean rootConext(FuncCmd cmd) {

		boolean isRoot = false;
		if (StringHelper.isEmpty(cmd.getFunc().getParentCode())) {
			isRoot = true;
			if (!StringHelper.isEmpty(cmd.getFunc().getSys())) {
				Sys sys = funcService.getSys(cmd.getFunc().getSys());
				if (sys != null) {
					cmd.getFunc().setContext(sys.getContext());
				}
			}
		}
		return isRoot;
	}

	/**
	 * ��������
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView addFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/AddFunc");

		cmd = createClear(cmd);
		String _path = "js/jquery/themes/zTreeStyle/img/main";
		_path = req.getSession().getServletContext().getRealPath(_path);
		String _imgList = funcService.getimglist(_path);
		cmd.setMsgImgLsit(_imgList);

		modelAndView.addObject("cmd", cmd);

		modelAndView.addObject("isRoot", rootConext(cmd));
		return modelAndView;
	}

	private FuncCmd createClear(FuncCmd cmd) {
		Func clearFunc = new Func();
		clearFunc.setParentCode(cmd.getFunc().getParentCode());
		clearFunc.setLevel(cmd.getFunc().getLevel());
		clearFunc.setFuncProp(1);
		clearFunc.setIsStop(0);
		FuncCmd newCmd = new FuncCmd();
		newCmd.setFunc(clearFunc);
		return newCmd;
	}

	/**
	 * �жϹ��ܱ���Ƿ����
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 */
	public void existFuncCode(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		Msg msg = new Msg(rsp);
		try {
			String id = cmd.getFunc().getFuncCode();
			if (funcService.exist(id)) {
				msg.error();
			} else {
				msg.addSuccess();
			}
		} catch (Exception e) {
			msg.error();
			// msg.addError("�û��Ѿ����ڣ������ԣ�");
			e.printStackTrace();
		}
	}

	/**
	 * ��������
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView saveFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/AddFunc");
		String msg = "�������ܳɹ�";
		String sucess = "true";
		try {
			funcService.save(cmd.getFunc());
			cmd = createClear(cmd);
			String _path = "js/jquery/themes/zTreeStyle/img/main";
			_path = req.getSession().getServletContext().getRealPath(_path);
			String _imgList = funcService.getimglist(_path);
			cmd.setMsgImgLsit(_imgList);
			modelAndView.addObject("cmd", cmd);
		} catch (DuplicateKeyException e) {
			cmd = createClear(cmd);
			String _path = "js/jquery/themes/zTreeStyle/img/main";
			_path = req.getSession().getServletContext().getRealPath(_path);
			String _imgList = funcService.getimglist(_path);
			cmd.setMsgImgLsit(_imgList);
			modelAndView.addObject("cmd", cmd);
			e.printStackTrace();
			msg = e.getMessage();
			sucess = "false";
		}

		modelAndView.addObject("sucess", sucess);
		modelAndView.addObject("MSG", msg);

		modelAndView.addObject("isRoot", rootConext(cmd));
		return modelAndView;
	}

	/**
	 * ɾ������
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView deleteFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		String msg = "ɾ�����ܳɹ�";
		String sucess = "true";
		try {
			funcService.delete(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "ɾ�����ܳ���";
			sucess = "false";
		}
		ModelAndView modelAndView = queryFunc(req, rsp, cmd);
		modelAndView.addObject("sucess", sucess);
		// modelAndView.getModelMap().addObject(Constants.KEY_MSG, msg);"MSG"
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * �༭����
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView editFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/EditFunc");
		try {
			Func func = funcService.get(cmd.getSelectedIds()[0]);
			cmd.setFunc(func);

			String _path = "js/jquery/themes/zTreeStyle/img/main";
			_path = req.getSession().getServletContext().getRealPath(_path);
			String _imgList = funcService.getimglist(_path);
			cmd.setMsgImgLsit(_imgList);

		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "�޸Ĺ��ܳ���");
		}

		modelAndView.addObject("cmd", cmd);

		modelAndView.addObject("isRoot", rootConext(cmd));

		return modelAndView;
	}

	/**
	 * ���¹���
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView updateFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/EditFunc");
		String msg = "�޸Ĺ��ܳɹ�";
		String sucess = "true";
		try {
			funcService.update(cmd.getFunc());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "�޸Ĺ��ܳ���";
			sucess = "false";
		}
		String _path = "js/jquery/themes/zTreeStyle/img/main";
		_path = req.getSession().getServletContext().getRealPath(_path);
		String _imgList = funcService.getimglist(_path);
		cmd.setMsgImgLsit(_imgList);

		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("sucess", sucess);
		modelAndView.addObject("MSG", msg);

		modelAndView.addObject("isRoot", rootConext(cmd));

		return modelAndView;
	}

	/**
	 * �鿴����
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView viewFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/ViewFunc");
		try {
			Func func = funcService.get(cmd.getSelectedIds()[0]);
			cmd.setFunc(func);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "�鿴���ܳ���");
		}
		modelAndView.addObject("cmd", cmd);

		modelAndView.addObject("isRoot", rootConext(cmd));

		return modelAndView;
	}

	public ModelAndView saveOrder(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		String msg = "����˳��ɹ�";
		String sucess = "true";
		try {
			if (StringHelper.isEmpty(cmd.getFunc().getParentCode())) {
				funcService.saveSysOrder(cmd.getSelectedIds());
			} else {
				funcService.saveOrder(cmd.getSelectedIds());
			}

		} catch (Exception e) {
			e.printStackTrace();
			msg = "����˳�����";
			sucess = "false";
		}
		ModelAndView modelAndView = queryFunc(req, rsp, cmd);
		modelAndView.addObject(Constants.KEY_MSG, msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * ����
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView start(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		// String msg = "���óɹ�";
		String sucess = "true";
		try {
			funcService.start(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			// msg = "���ó���";
			sucess = "false";
		}
		ModelAndView modelAndView = queryFunc(req, rsp, cmd);
		// modelAndView.addObject("MSG", msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * ͣ��
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView stop(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		// String msg = "ͣ�óɹ�";
		String sucess = "true";
		try {
			funcService.stop(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			// msg = "ͣ�ó���";
			sucess = "false";
		}
		ModelAndView modelAndView = queryFunc(req, rsp, cmd);
		// modelAndView.addObject("MSG", msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * ��ѯ����
	 * 
	 * @param req
	 * @param rsp
	 * @param func
	 * @return
	 */
	public ModelAndView queryFunc(HttpServletRequest req, HttpServletResponse rsp, FuncCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/func/FuncMain");
		try {
			// ��װģ��
			preparedModel(cmd.getTableModel(), cmd.getFunc(), req);
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			List funcs = funcService.getButton(visitor.getUser().getUserCode(), "0104");
			List<Button> buttons = ButtonUtil.buildButtons(funcs);
			modelAndView.addObject("buttons", buttons);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "��ѯ���ܳ���");
		}
		modelAndView.addObject("sucess", "false");
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	/**
	 * ��װ���ģ��
	 * 
	 * @param tableModel
	 * @param condition
	 */
	private void preparedModel(TableModel<Func> tableModel, Func condition, HttpServletRequest req) {
		// ����ж���
		SysCfg sysCfg = Config.instance().getTopic();
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		String topic = visitor.getTopic();
		String imgPath = sysCfg.img(null);
		String img1 = Constants.TABLE_LITERAL
				+ "<img src=\""
				+ imgPath
				+ "/up.gif\" style=\"cursor:hand\"  onclick=\"up(event,'${funcCode}');\" title=\"�����ƶ�\"/>";
		String img2 = Constants.TABLE_LITERAL
				+ "<img src=\""
				+ imgPath
				+ "/down.gif\" style=\"cursor:hand\"  onclick=\"down(event, '${funcCode}');\" title=\"�����ƶ�\"/>";
		String context = req.getContextPath();
		String link = context + "/func/funcManager.do?action=viewFunc&selectedIds=${funcCode}&"
				+ Constants.WINDOW_ISOPEN + "=yes";
		String sicon = "";
		String _path = context + "/js/jquery/themes/zTreeStyle/img/main";
		sicon = Constants.TABLE_LITERAL + "<img src=\"" + _path
				+ "\\${icon}.png\" title=\"${icon}\" />";

		List<Column> columns = new ArrayList<Column>();

		Column col1 = new Column("funcCode", null, "5%", "center", null, 0, null, null, true);
		Column col2 = new Column("funcCode", "���", "12%", "center", link, 1, "�鿴����", "funcCode",
				false);
		Column col3 = new Column("funcName", "����", "20%", "center", link, 1, "�鿴����", "funcCode",
				false);
		Column col4 = new Column("link", "����", "25%", "left", null, 0, null, null, false);
		Column col5 = new Column("type", "����", "7%", "center", null, 0, null, null, false);
		Column col6 = new Column(sicon, "ͼ��", "8%", "center", null, 0, null, null, false);
		Column col7 = new Column("stop", "״̬", "7%", "center", null, 0, null, null, false);
		Column col8 = new Column(img1, "����", "6%", "center", null, 0, null, null, false, false);
		Column col9 = new Column(img2, "����", "6%", "center", null, 0, null, null, false, false);
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col4);
		columns.add(col5);
		columns.add(col6);
		columns.add(col7);
		columns.add(col8);
		columns.add(col9);
		tableModel.setColumns(columns);

		tableModel.setHasIndex(true);
		// ����ҳ����
		tableModel.getPage().setAction(context + "/func/funcManager.do?action=queryFunc");

		// ��������
		int count = funcService.getCount(condition);
		tableModel.getPage().setPageSize(100);
		tableModel.getPage().setCount(count);

		// ��ҳ����
		List tableList = funcService.query(condition, tableModel.getPage().getCurrentPage(),
				tableModel.getPage().getPageSize());
		tableModel.setData(tableList);
	}

	/**
	 * ������ģ��
	 * 
	 * @param req
	 * @param context
	 * @return
	 */
	private List<TreeModel> buildTreeModel(HttpServletRequest req, String context) {
		SysCfg cfg = (SysCfg) req.getSession().getServletContext().getAttribute(
				Constants.KEY_SYSCFG);
		Map<String, List<Func>> sysFuncs = funcService.sysFuncs(cfg.getSyss());

		List<TreeModel> tms = new ArrayList<TreeModel>();
		int index = 0;
		for (Entry<String, List<Func>> entry : sysFuncs.entrySet()) {
			if (entry.getValue().size() == 0) {
				continue;
			}
			TreeModel model = new TreeModel("a" + (++index));
			model.setTarget("FuncMain");
			model.setTabType("notab");
			List<Func> funcs = entry.getValue();

			List<TreeNode> nodes = new ArrayList<TreeNode>();
			if (funcs != null) {
				for (int i = 0; i < funcs.size(); i++) {
					Func func = funcs.get(i);
					String link = context
							+ "/func/funcManager.do?action=queryFunc&fc=0104&func.parentCode="
							+ func.getFuncCode() + "&func.level=" + (func.getLevel() + 1);
					TreeNode node = new TreeNode();
					node.setNodeID(func.getFuncCode());
					node.setParentID(func.getParentCode());
					String funcName = func.getFuncName();
					if (func.getIsStop() == Constants.STOP_YES) {
						funcName += "(" + func.getStop() + ")";
					}
					node.setNodeName(funcName);
					if (func.getFuncProp() == Constants.FUNC_TYPE_MENU) {
						node.setLink(link);
					} else {
						node.setLink("");
					}

					node.setTitle(func.getTitle());
					node.setIcon(func.getIcon());
					// node.setTabType("notab");
					nodes.add(node);
				}
			}
			model.setNodesList(nodes);
			tms.add(model);
		}
		return tms;
	}

}
