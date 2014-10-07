package net.linybin7.core.frame.org.ctl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.table.Column;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.tag.Msg;
import net.linybin7.common.util.StringHelper;
import net.linybin7.core.config.Config;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.frame.org.cmd.OrgCmd;
import net.linybin7.core.frame.org.cmd.ZTreeCmd;
import net.linybin7.core.frame.org.service.OrgSve;
import net.linybin7.core.frame.user.service.UserSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.tree.TreeModel;
import net.linybin7.core.web.component.tree.TreeNode;
import net.linybin7.core.web.component.util.ButtonUtil;
import net.sf.json.JSONSerializer;

import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * 部门管理控制类
 * 
 * 
 * 
 */
public class OrgCtl extends MultiActionController {
	private OrgSve orgService;

	private FuncSve funcService;
	
	private UserSve userService;

	@Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
			throws Exception {
		super.initBinder(req, binder);
		// DateEditor dateEditor = new DateEditor("yyyy-mm-dd");
		// binder.registerCustomEditor(Date.class, dateEditor);
	}

	public OrgSve getOrgService() {
		return orgService;
	}

	public void setOrgService(OrgSve orgService) {
		this.orgService = orgService;
	}

	public FuncSve getFuncService() {
		return funcService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	/**
	 * 主页
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView index1(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/Index");
		return modelAndView;
	}

	/**
	 * 左树
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView leftTree(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/LeftTree");
		try {
			TreeModel model = buildTreeModel(req.getContextPath(), true, null, null);
			modelAndView.addObject("treeModel", model);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "生成工作区树出错");
		}

		return modelAndView;
	}

	public void selectOrgToTree(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);

			boolean isAll = visitor.getUser().getUserProp() == Constants.USER_TYPE_SYS ? true
					: false;
			String inputType = "radio";
			TreeModel model = buildTreeModel(req.getContextPath(), isAll, inputType, visitor);
			String s = model.ykbuild();
			// 利用Json插件将Array转换成Json格式
			rsp.setContentType("text/html;charset=GBK");
			rsp.getWriter().print(s);
			// Msg.writeObj(g, rsp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ModelAndView selectOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/SelectOrg");
		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);

			boolean isAll = visitor.getUser().getUserProp() == Constants.USER_TYPE_SYS ? true
					: false;
			String inputType = req.getParameter("inputType");
			if (StringHelper.isEmpty(inputType)) {
				inputType = "radio";
			}
			TreeModel model = buildTreeModel(req.getContextPath(), isAll, inputType, visitor);
			modelAndView.addObject("treeModel", model);
			modelAndView.addObject("inputType", inputType);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "生成工作区树出错");
		}

		return modelAndView;
	}
	
	/*
	 * 选择工作区
	 */
	public ModelAndView getScene(HttpServletRequest req,HttpServletResponse rsp,OrgCmd cmd){
		ModelAndView modelAndView = new ModelAndView("core/org/SelectScene");
		try{
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User user = visitor.getUser();
			List orgs ;
			if(user.getUserProp()==3)
				orgs =  orgService.all();
			else
				orgs =orgService.getUserOrgs(user.getUserCode());
			modelAndView.addObject("user",user);
			modelAndView.addObject("orgs",orgs);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return modelAndView;
	}

	/**
	 * 新增工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView addOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/AddOrg");
		try{
			List<User> unselected = userService.all();
			List<Option> selected = new ArrayList<Option>();
			modelAndView.addObject("unselected",toOption(unselected));
			modelAndView.addObject("selected",selected);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 * 判断字符是否字母
	 * 
	 * @param rsp
	 * @return
	 */
	private boolean isA2Z(String s) {
		boolean bool = false;
		if (s.getBytes().length == s.length()) {
			if (s.length() > 0) {
				String s1 = s.substring(0, 1);
				String reg = "[a-zA-Z]";
				if (s1.matches(reg)) {
					bool = true;
				}
			}
		} else {
			bool = false;
		}
		return bool;
	}

	/**
	 * 判断工作区编号是否存在
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 */
	public void existOrgCode(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		Msg msg = new Msg(rsp);
		try {
			String id = cmd.getOrg().getId();
			if (id.length() < 9) {
				if (this.isA2Z(id)) {
					if (orgService.exist(id)) {
						msg.error();
					} else {
						msg.addSuccess();
					}
				} else {
					msg.error();
				}
			} else {
				msg.error();
			}
		} catch (Exception e) {
			msg.error();
			// msg.addError("用户已经存在，请重试！");
			e.printStackTrace();
		}
	}

	/**
	 * 新增工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView saveOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/AddOrg");
		String msg = "新增工作区成功";
		String sucess = "true";
		try {
			orgService.save(cmd.getOrg());
			orgService.assignUser(cmd.getOrg().getId(), cmd.getUserCodes());
		} catch (DuplicateKeyException e) {
			modelAndView.addObject("cmd", cmd);
			e.printStackTrace();
			// msg = e.getMessage();
			msg = "新增工作区失败";
			sucess = "false";
		}

		modelAndView.addObject("sucess", sucess);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * 删除工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public void deleteOrg1(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		String msg = "删除工作区成功";
		String sucess = "true";
		try {
			orgService.delete(cmd.getOrg().getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "删除工作区出错";
			sucess = "false";
		}
	}
	
	/**
	 * 删除工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView deleteOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		String msg = "删除工作区成功";
		String sucess = "true";
		try {
			orgService.delete(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "删除工作区出错";
			sucess = "false";
		}
		ModelAndView modelAndView = queryOrg(req, rsp, cmd);
		modelAndView.addObject("sucess", sucess);
		modelAndView.getModelMap().addObject(Constants.KEY_MSG, msg);
		return modelAndView;
	}

	/**
	 * 编辑工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView editOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/EditOrg");
		List unselected = null;
		List seleted = null;
		try {
			String id = "";
			if(cmd.getOrg().getId()==null || "".equals(cmd.getOrg().getId())){
				id = cmd.getSelectedIds()[0];
			}else{
				id = cmd.getOrg().getId();
			}
			Org org = orgService.get(id);
			unselected = orgService.getUnssignUser(id);
			seleted = orgService.getUser(id);
			cmd.setOrg(org);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "修改工作区出错");
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("unselected", toOption(unselected));
		modelAndView.addObject("selected", toOption(seleted));
		return modelAndView;
	}

	/**
	 * 更新工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView updateOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/EditOrg");
		String msg = "修改工作区成功";
		String sucess = "true";
		List unselected = null;
		List seleted = null;
		
		try {
			Org org = orgService.get(cmd.getOrg().getId());
			org.setDescript(cmd.getOrg().getDescript());
			org.setId(cmd.getOrg().getId());
			org.setOrgName(cmd.getOrg().getOrgName());
			org.setOrgFullName(cmd.getOrg().getOrgFullName());
			org.setChief(cmd.getOrg().getChief());
			org.setEmail(cmd.getOrg().getEmail());
			org.setPhone(cmd.getOrg().getPhone());
			
			orgService.update(org);
			orgService.assignUser(org.getId(), cmd.getUserCodes());
			unselected = orgService.getUnssignUser(cmd.getOrg().getId());
			seleted = orgService.getUser(cmd.getOrg().getId());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "修改工作区出错";
			sucess = "false";
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("sucess", sucess);
		modelAndView.addObject("MSG", msg);
		modelAndView.addObject("unselected", toOption(unselected));
		modelAndView.addObject("selected", toOption(seleted));
		return modelAndView;
	}

	/**
	 * 查看工作区
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView viewOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/ViewOrg");
		try {
			Org org = orgService.get(cmd.getOrg().getId());
			cmd.setOrg(org);
			List<User> users = orgService.getUser(cmd.getOrg().getId());
			modelAndView.addObject("users",users);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "查看工作区出错");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	public ModelAndView saveOrder(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {

		String msg = "保存顺序成功";
		String sucess = "true";
		try {
			String parentId = cmd.getOrg().getParentId();
			Org parent = orgService.get(parentId);
			String porder = parent == null ? "" : parent.getOrder();
			String[] ids = cmd.getSelectedIds();
			int c = 0;

			for (String id : ids) {
				// orgService.saveOrder(cmd.getSelectedIds());
				Org o = orgService.get(id);
				if (o != null) {
					int seq = ++c;
					String sc = "" + seq;
					if (seq < 10) {
						sc = "00" + seq;
					} else if (seq < 100) {
						sc = "0" + seq;
					}
					o.setOrder(porder + "" + sc);
					orgService.update(o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "保存顺序出错";
			sucess = "false";
		}
		ModelAndView modelAndView = queryOrg(req, rsp, cmd);
		modelAndView.addObject(Constants.KEY_MSG, msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * 启用
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView start(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		// String msg = "启用成功";
		String sucess = "true";
		try {
			orgService.start(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			// msg = "启用出错";
			sucess = "false";
		}
		ModelAndView modelAndView = queryOrg(req, rsp, cmd);
		// modelAndView.addObject("MSG", msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * 停用
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView stop(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		// String msg = "停用成功";
		String sucess = "true";
		try {
			orgService.stop(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			// msg = "停用出错";
			sucess = "false";
		}
		ModelAndView modelAndView = queryOrg(req, rsp, cmd);
		// modelAndView.addObject("MSG", msg);
		modelAndView.addObject("sucess", sucess);
		return modelAndView;
	}

	/**
	 * 查询部门
	 * 
	 * @param req
	 * @param rsp
	 * @param org
	 * @return
	 */
	public ModelAndView queryOrg(HttpServletRequest req, HttpServletResponse rsp, OrgCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/org/OrgMain");
		try {
			try {
				List<Org> orgs = orgService.all();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// 组装模型
			preparedModel(cmd.getTableModel(), cmd.getOrg(), req);
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			List orgs = funcService.getButton(visitor.getUser().getUserCode(), "0101");
			List<Button> buttons = ButtonUtil.buildButtons(orgs);
			modelAndView.addObject("buttons", buttons);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "查询工作区出错");
		}
		modelAndView.addObject("sucess", "false");
		modelAndView.addObject("cmd", cmd);

		return modelAndView;
	}

	/**
	 * 组装表格模型
	 * 
	 * @param tableModel
	 * @param condition
	 */
	private void preparedModel(TableModel<Org> tableModel, Org condition, HttpServletRequest req) {
		// 表格列对象
		SysCfg sysCfg = Config.instance().getTopic();
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		String topic = visitor.getTopic();
		String imgPath = sysCfg.img(null);

		String context = req.getContextPath();
		String link = context + "/org/orgManager.do?action=viewOrg&selectedIds=${id}&"
				+ Constants.WINDOW_ISOPEN + "=yes";
		List<Column> columns = new ArrayList<Column>();
		Column col1 = new Column("id", null, "6%", "center", null, 0, null, null, true);
		Column col2 = new Column("id", "工作区编码", "10%", "center", null, 0, null, null, false);
		Column col3 = new Column("orgName", "工作区简称", "15%", "center", link, 1, "查看工作区信息",
				"orgName", false);
		Column col4 = new Column("orgFullName", "工作区全称", "18%", "center", link, 1, "查看工作区信息",
				"orgName", false);
		Column col5 = new Column("chief", "工作区负责人姓名", "18%", "center", null, 0, null, null, false);
		Column col6 = new Column("phone", "工作区负责人电话", "18%", "center", null, 0, null, null, false);
		Column col7 = new Column("stop", "状态", "10%", "center", null, 0, null, null, false);
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col4);
		columns.add(col5);
		columns.add(col6);
		columns.add(col7);
		tableModel.setColumns(columns);

		// 表格分页链接
		// tableModel.setDisplayCount(false);
		tableModel.setHasIndex(true);
		tableModel.getPage().setAction(context + "/org/orgManager.do?action=queryOrg");

		// 数据总数
//		int count = orgService.getCount(condition);
//		tableModel.getPage().setCount(count);
//		if (count == 0) {
//			tableModel.getPage().setPageSize(20);
//		} else {
//			tableModel.getPage().setPageSize(count);
//		}
		
		tableModel.getPage().setPageSize(100);

		// 当页数据
		List<Org> tableList = orgService.query(condition, tableModel.getPage().getCurrentPage(),
				tableModel.getPage().getPageSize());

		// Org[] orgs = tableList.toArray(new Org[] {});
		// Arrays.sort(orgs, new Comparator() {
		// public int compare(Object o1, Object o2) {
		// Org org1 = (Org) o1;
		// Org org2 = (Org) o2;
		// try {
		// return Integer.parseInt(org1.getOrder())
		// - Integer.parseInt(org2.getOrder());
		// } catch (Exception e) {
		// }
		// return 0;
		// }
		// });

		tableModel.setData(tableList);
	}

	/**
	 * 构造树模型
	 * 
	 * @return
	 */
	private TreeModel buildTreeModel(String context, boolean isAll, String type, Visitor visitor) {
		TreeModel model = new TreeModel("a");
		model.setTarget("OrgMain");
		model.setTabType("notab");
		List<Org> orgs = null;
		if (isAll) {
			orgs = orgService.all();
		} else {
			// orgs = orgService.orgsList(visitor.getUser());
			orgs = visitor.getOrgs();
		}
		if (!StringHelper.isEmpty(type)) {
			model.setHasCheckbox(true);
			model.setInputType(type);
			model.setCbDisabled(false);
			model.setCheckBoxName("orgIds");
		}
		List<TreeNode> nodes = new ArrayList<TreeNode>();

		TreeNode root = new TreeNode();
		if (!isAll) {
			root.setNodeID(orgs.size() > 0 ? orgs.get(0).getParentId() : null);
		} else {
			root.setNodeID("Root");
		}
		root.setParentID("");
		root.setNodeName("工作区");
		root.setHasInput(false);
		if (StringHelper.isEmpty(type)) {
			root
					.setLink(context
							+ "/org/orgManager.do?action=queryOrg&fc=0101&org.parentId=Root&org.level=1&fc=0101");
		}

		nodes.add(root);

		if (orgs != null) {
			for (int i = 0; i < orgs.size(); i++) {
				Org org = orgs.get(i);
				String link = context + "/org/orgManager.do?action=queryOrg&fc=0101&org.parentId="
						+ org.getId() + "&org.level=" + (org.getLevel() + 1);
				TreeNode node = new TreeNode();
				node.setNodeID(org.getId());
				node.setParentID(org.getParentId());
				node.setValue(org.getId());
				String orgName = org.getOrgName();
				if (org.getIsStop() == Constants.STOP_YES) {
					orgName += "(" + org.getStop() + ")";
				}
				node.setNodeName(orgName);

				if (StringHelper.isEmpty(type)) {
					node.setLink(link);
				}
				// }else if(org.getLevel() == 1){
				// node.setHasInput(false);
				// }

				//				
				// node.setTitle(org.getTitle());
				nodes.add(node);
			}
		}
		model.setNodesList(nodes);
		return model;
	}

	public ModelAndView exist(HttpServletRequest req, HttpServletResponse rsp) {
		String msg = "false";
		try {
			String field = req.getParameter("field");
			String value = req.getParameter("value");
			if ("id".equalsIgnoreCase(field)) {
				if (orgService.get(value) != null) {
					msg = "true";
				}
			} else if ("orgRuleCode".equalsIgnoreCase(field)) {
				Org o = new Org();
				// o.setOrgRuleCode(value);
				List list = orgService.query(o, 1, 1);
				if (list != null && list.size() > 0) {
					msg = "true";
				}
			}
		} catch (Exception e) {
			msg = "error";
			e.printStackTrace();
		}

		try {
			Writer out = rsp.getWriter();
			out.write(msg);
			out.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public void getAjaxOrgName(HttpServletRequest req,HttpServletResponse rsp) throws Exception{
		try{
			String id = req.getParameter("orgId");
			Org org = orgService.get(id);
			Msg.writeObj(org, rsp);
		}catch(Exception ex){
			ex.printStackTrace();
			rsp.getWriter().write("error");
		}
	}
	
	
	//
	public ModelAndView index(HttpServletRequest req, HttpServletResponse rsp,
			OrgCmd cmd) {
		ModelAndView view = new ModelAndView("core/org/FaultMain1");
		try{
			List<Org> list = orgService.all();
			List<ZTreeCmd> nodes = new ArrayList<ZTreeCmd>();
			ZTreeCmd node = new ZTreeCmd();
			node.setId("root");
			node.setName("工作区");
			node.setOpen(true);
			node.setpId("0");
			nodes.add(node);
			for(Org o : list){
				node = new ZTreeCmd();
				node.setId(o.getId());
				node.setName("["+o.getUser().size()+"]"+o.getOrgName()+"-"+o.getId());
				node.setpId("root");
				node.setOpen(true);
				nodes.add(node);
			}
			String orgStr = JSONSerializer.toJSON(nodes).toString();
			view.addObject("list",list);
			view.addObject("orgStr",orgStr);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return view;
	}
	
	//刷新工作区
	public void refresh(HttpServletRequest req,HttpServletResponse rsp){
		try{
			List<Org> list = orgService.all();
			List<ZTreeCmd> nodes = new ArrayList<ZTreeCmd>();
			ZTreeCmd node = new ZTreeCmd();
			node.setId("root");
			node.setName("工作区");
			node.setOpen(true);
			node.setpId("0");
			nodes.add(node);
			for(Org o : list){
				node = new ZTreeCmd();
				node.setId(o.getId());
				node.setName("["+o.getUser().size()+"]"+o.getOrgName()+"-"+o.getId());
				node.setpId("root");
				node.setOpen(true);
				nodes.add(node);
			}
			String orgStr = JSONSerializer.toJSON(nodes).toString();
			Msg.writeObj(orgStr, rsp);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public ModelAndView treeIndex(HttpServletRequest req, HttpServletResponse rsp,
			OrgCmd cmd) {
		ModelAndView view = new ModelAndView("core/org/FaultKindTree");
		//List<Org> faultTypeItemList = orgService.getOrgItemList();
		TreeModel tree = new TreeModel("fault");
		tree.setTabType("notab");
		tree.setTarget("faultManage");
		List<TreeNode> nodes = new ArrayList<TreeNode>();
		String context = req.getContextPath();
		TreeNode node = new TreeNode();
		node.setParent(false);
		node.setDrop(false);
		node.setDrag(false);
		node.setParentID("");
		node.setNodeID("parentFault");
		node.setNodeName("工作区");
		node.setTitle("工作区");
//		if(faultTypeItemList != null)
//		{
//			node.setLink(context
//					+ "/org/orgManager.do?action=faultManage&faultKindId=" + faultTypeItemList.get(0).getId());
//		}
		
		node.setOpen(true);
		nodes.add(node);
//		if(faultTypeItemList!=null){
//			for(Org kind:faultTypeItemList){
//				node = new TreeNode();
//				node.setDrop(false);
//				node.setDrag(false);
//				node.setNodeID(kind.getId());
//				node.setParentID("parentFault");
//				node.setNodeName(kind.getOrgName());
//				node.setTitle(kind.getOrgName());
//				node.setLink(context
//						+ "/org/orgManager.do?action=faultManage&faultKindId="
//						+ kind.getId());
//				node.setParent(false);
//				nodes.add(node);
//			}
//		}
		tree.setNodesList(nodes);
		List<TreeModel> tms = new ArrayList<TreeModel>();
		tms.add(tree);
		view.addObject("tms", tms);
		return view;
	}
	
	public ModelAndView faultManage(HttpServletRequest req, HttpServletResponse rsp,
			Object cmd) {
		ModelAndView view = new ModelAndView("core/org/FaultManage");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String kindId = req.getParameter("faultKindId");
		initFaultMange(view,kindId);
		if("true".equals(req.getParameter("openAdd"))){
			view.addObject("openAdd", true);
			
		}
		return view;
	}
	
	private void initFaultMange(ModelAndView view,String kindId){
//		List<Fault> faults = orgService.getFaultItemList(kindId);
//		view.addObject("faults", faults);
		try
		{
			//Org kind = orgService.getOrg(kindId);
			//List unselected = orgService.getUser(kindId);
			//List seleted = orgService.getUsero(kindId);
//			if(kind != null)
//			{
//				if(kind.getUser() != null)
//				{
//				Set<User> user = kind.getUser();
//				Iterator<User> iterator = user.iterator();
//				List l = new ArrayList();
//			
//				while(iterator.hasNext())
//				{
//					User u = iterator.next();
//				
//					l.add(u.getUserCode());
//					l.add(u.getUserName());
//					l.add(u.getType());
//				}
//				
//				view.addObject("usercode",l.get(0));
//				view.addObject("username",l.get(1));
//				view.addObject("userprop",l.get(2));
//				}
//			}
//			if(kind!=null){
//				view.addObject("faultKindRemark", kind.getOrgName());
//				view.addObject("faultKindId", kind.getId());
//				view.addObject("Phone", kind.getPhone());
//				view.addObject("orgFullName", kind.getOrgFullName());
//				view.addObject("chief", kind.getChief());
//				view.addObject("email", kind.getEmail());
//				view.addObject("descript", kind.getDescript());
//				view.addObject("level", kind.getLevel());
//				view.addObject("order", kind.getOrder());
//				view.addObject("stop", kind.getStop());
//				view.addObject("unselected", toOption(unselected));
//				view.addObject("selected", toOptiono(seleted));
//				view.addObject("tel", kind.getMobile());
//			}
		}
		catch(Exception e)
		{
			System.out.println("中间表没数据");
		}
		
	}
	
	/**
	public ModelAndView qureyFaultList(HttpServletRequest req, HttpServletResponse rsp,
			OrgCmd cmd) {
		ModelAndView view = new ModelAndView("core/org/FaultConfig");
		try {
			
			String faultId = URLDecoder.decode(req.getParameter("faultId"),"UTF-8");
			String faultId2 = URLDecoder.decode(req.getParameter("faultId2"),"UTF-8");
			List<Fault> faultItemList = orgService.getFaultItemList(faultId);
			if(faultItemList !=null && faultItemList.size() > 0){
				for(int i=0;i<faultItemList.size();i++){
					faultItemList.get(i).setId2(i+"");
				}
			}
			view.addObject("faultItemList", faultItemList);
			
			List<Org> faultTypeItemList = orgService.getOrgItemList();
			int num = 0;
			if(faultTypeItemList !=null && faultTypeItemList.size() > 0){
				for(int i=0;i<faultTypeItemList.size();i++){
					faultTypeItemList.get(i).setId2(i+"");
					num = orgService.getFaultItemCount(faultTypeItemList.get(i).getId());
					faultTypeItemList.get(i).setFaultCounts(num);
				}
			}
			view.addObject("faultTypeItemList", faultTypeItemList);
			view.addObject("faultId2", faultId2);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return view;
	}
	
	public ModelAndView addFault(HttpServletRequest req, HttpServletResponse rsp,
			Fault cmd) {
		ModelAndView view = new ModelAndView("core/org/FaultManage");
		try {
			if(cmd.getRemark()!=null&&cmd.getRemark().trim().length()!=0){
				orgService.addFaultItem(cmd);
			}
			initFaultMange(view,cmd.getOrgId());
			return view;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return view;
	}
	
	public void addFaultKind(HttpServletRequest req, HttpServletResponse rsp,
			OrgCmd cmd) {
		try {
			Org faultKind = new Org();
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User user = visitor.getUser();Set<User> u = new HashSet();
			u.add(user);
			faultKind.setUser(u);
			//System.out.println(faultKind.getUser());
			faultKind.setOrgName("新建工作区");
			faultKind.setParentId("Root");
			
			Long count = orgService.getCount(faultKind);
//			Org parent = orgService.get(parentId);
//			String parentOrder = faultKind == null ? "" : faultKind.getOrder();
			faultKind.setOrder("" + (count + 1));
			faultKind.setOrgFullName("工作区全称");
			
			
			orgService.addOrgItem(faultKind);
			//orgService.save(faultKind);
			JSONObject obj = new JSONObject();
			obj.put("id", faultKind.getId());
			rsp.getWriter().write(obj.toString());
			
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void editFault(HttpServletRequest req, HttpServletResponse rsp,
			Fault cmd) {
		try {
			String remark = java.net.URLDecoder.decode(cmd.getRemark(), "UTF-8");
			cmd.setRemark(remark);
			orgService.editFault(cmd);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void editFaultKind(HttpServletRequest req, HttpServletResponse rsp,
			Org cmd) {
		try {
			String remark = java.net.URLDecoder.decode(cmd.getOrgName(), "UTF-8");
			cmd.setOrgName(remark);
			orgService.editOrg(cmd);
		} catch (UnsupportedEncodingException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	*/
	/**
	 * @return acFaultService
	 */
	public OrgSve getOrgSve() {
		return orgService;
	}

	/**
	 * @param acFaultService 要设置的 acFaultService
	 */
	public void setOrgSve(OrgSve acFaultService) {
		this.orgService = acFaultService;
	}
	
	private List<Option> toOption(List<User> users) {
		List<Option> options = new ArrayList<Option>();
		for (int i = 0; i < users.size(); i++) {
			Option option = new Option();
			option.setValue( users.get(i).getUserCode());
			option.setLabel( users.get(i).getUserName());
			options.add(option);
		}
		return options;
	}

	public void setUserService(UserSve userService) {
		this.userService = userService;
	}

	public UserSve getUserService() {
		return userService;
	}
	
}
