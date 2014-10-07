package net.linybin7.core.frame.role.ctl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.component.table.Column;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.tag.Msg;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.frame.role.cmd.RoleCmd;
import net.linybin7.core.frame.role.service.RoleSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.tree.TreeModel;
import net.linybin7.core.web.component.tree.TreeNode;
import net.linybin7.core.web.component.util.ButtonUtil;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * 角色管理控制类
 * 
 * 
 * 
 */
public class RoleCtl extends MultiActionController {
	private RoleSve roleService;
	private FuncSve funcService;

	private final String[][] typeOrg = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.ROLE_TYPE_COMMON), "普通角色" },
			{ String.valueOf(Constants.ROLE_TYPE_ORG), "管理角色" } };

	private final String[][] typeSys = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.ROLE_TYPE_COMMON), "普通角色" },
			{ String.valueOf(Constants.ROLE_TYPE_ORG), "管理角色" },
			{ String.valueOf(Constants.ROLE_TYPE_SYS), "超级管理角色" } };

	@Override
	protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
			throws Exception {
		super.initBinder(req, binder);
		// DateEditor dateEditor = new DateEditor("yyyy-mm-dd");
		// binder.registerCustomEditor(Date.class, dateEditor);
	}

	public RoleSve getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleSve roleService) {
		this.roleService = roleService;
	}

	public FuncSve getFuncService() {
		return funcService;
	}

	public void setFuncService(FuncSve funcService) {
		this.funcService = funcService;
	}

	private void preparedCmd(User curUser, RoleCmd cmd) {
		if (curUser.getUserProp() == Constants.USER_TYPE_SYS) {
			cmd.setType(typeSys);
		} else {
			cmd.setType(typeOrg);
		}
	}

	/**
	 * 新增角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView addRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/AddRole");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		RoleCmd newCmd = new RoleCmd();
		newCmd.setRole(new Role());
		newCmd.getRole().setRoleProp(Constants.ROLE_TYPE_COMMON);
		newCmd.getRole().setIsStop(Constants.STOP_NO);
		preparedCmd(visitor.getUser(), newCmd);
		modelAndView.addObject("cmd", newCmd);

		try {
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(), null, false));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "获取权限出错");

		}
		return modelAndView;
	}

	/**
	 * 新增角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView saveRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/AddRole");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		String msg = "新增角色成功";
		try {
			roleService.save(cmd.getRole());
			roleService.assignPurview(cmd.getRole().getRoleCode(), cmd.getPurviews());
			cmd.setRole(new Role());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "新增角色失败";
		}

		try {
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(), null, false));
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "获取权限出错");
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * 删除角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView deleteRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		String msg = "删除角色成功";
		try {
			roleService.delete(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		ModelAndView modelAndView = queryRole(req, rsp, cmd);
		modelAndView.addObject("MSG", msg);
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
	public ModelAndView start(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		String msg = "启用成功";
		try {
			roleService.start(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		ModelAndView modelAndView = queryRole(req, rsp, cmd);
		modelAndView.addObject("MSG", msg);
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
	public ModelAndView stop(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		String msg = "停用成功";
		try {
			roleService.stop(cmd.getSelectedIds());
		} catch (Exception e) {
			e.printStackTrace();
			msg = e.getMessage();
		}
		ModelAndView modelAndView = queryRole(req, rsp, cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * 编辑角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView editRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/EditRole");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		try {
			Role role = roleService.get(cmd.getSelectedIds()[0]);
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(),
					role.getRoleCode(), false));
			cmd.setRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "加载修改角色出错");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	/**
	 * 更新角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView updateRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/EditRole");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		String msg = "修改角色成功";
		try {
			roleService.update(cmd.getRole());
			roleService.assignPurview(cmd.getRole().getRoleCode(), cmd.getPurviews());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "修改角色出错";
		}
		try {
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(), cmd.getRole()
					.getRoleCode(), false));
		} catch (Exception e) {
			modelAndView.addObject("MSG", "获取权限出错");
			e.printStackTrace();
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * 查看角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView viewRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/ViewRole");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		try {
			Role role = roleService.get(cmd.getSelectedIds()[0]);
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(),
					role.getRoleCode(), true));
			cmd.setRole(role);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "查看角色出错");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	// /**
	// * 分配权限
	// * @param req
	// * @param rsp
	// * @param cmd
	// * @return
	// */
	// public ModelAndView assignPurview(HttpServletRequest req, HttpServletResponse rsp, RoleCmd
	// cmd){
	// ModelAndView modelAndView = new ModelAndView("core/role/AssignPurview");
	// try {
	// Role role = roleService.get(cmd.getSelectedIds()[0]);
	// modelAndView.addObject("role", role);
	// modelAndView.addObject("treeModel", buildTreeModel(role.getRoleCode(), false));
	// } catch (Exception e) {
	// e.printStackTrace();
	// modelAndView.addObject(Constants.KEY_MSG, "分配权限页面出错");
	// }
	//		
	// return modelAndView;
	// }

	private List<TreeModel> buildTreeModel(HttpServletRequest req, User currUser, String roleCode,
			boolean cbDisabled) {
		SysCfg cfg = (SysCfg) req.getSession().getServletContext().getAttribute(
				Constants.KEY_SYSCFG);

		Map<String, List<Func>> allFuncs = funcService.getFuncs(currUser, cfg.getSyss());
		List assigneds = roleService.getPurviewCodes(roleCode);

		List<TreeModel> tms = new ArrayList<TreeModel>();
		int index = 0;
		for (Entry<String, List<Func>> entry : allFuncs.entrySet()) {
			if (entry.getValue().size() == 0) {
				continue;
			}
			TreeModel model = new TreeModel("a" + (++index));
			model.setHasCheckbox(true);
			model.setCbDisabled(cbDisabled);
			model.setCheckBoxName("purviews");
			List funcs = entry.getValue();

			Map<String, String> assignedMap = new HashMap<String, String>();
			for (int i = 0; assigneds != null && i < assigneds.size(); i++) {
				String funcCode = (String) assigneds.get(i);
				assignedMap.put(funcCode, null);
			}

			List<TreeNode> nodes = new ArrayList<TreeNode>();
			if (funcs != null) {
				for (int i = 0; funcs != null && i < funcs.size(); i++) {
					Func func = (Func) funcs.get(i);
					TreeNode node = new TreeNode();
					node.setNodeID(func.getFuncCode());
					node.setParentID(func.getParentCode());
					node.setValue(func.getFuncCode());
					node.setNodeName(func.getFuncName());
					node.setLink(null);
					node.setTitle(func.getTitle());
					if (assignedMap.containsKey(func.getFuncCode())) {
						node.setChecked(true);
					}
					nodes.add(node);
				}
			}
			model.setNodesList(nodes);
			tms.add(model);
		}
		return tms;
	}

	// /**
	// * 保存权限
	// * @param req
	// * @param rsp
	// * @param cmd
	// * @return
	// */
	// public ModelAndView savePurview(HttpServletRequest req, HttpServletResponse rsp, RoleCmd
	// cmd){
	// String msg = "保存权限成功";
	// try {
	// roleService.assignPurview(cmd.getRole().getRoleCode(), cmd.getPurviews());
	// } catch (Exception e) {
	// e.printStackTrace();
	// msg = "保存权限出错";
	// }
	// cmd.setSelectedIds(new String[]{cmd.getRole().getRoleCode()});
	// ModelAndView modelAndView = assignPurview(req, rsp, cmd);
	// modelAndView.addObject(Constants.KEY_MSG, msg);
	// return modelAndView;
	// }
	/**
	 * 判断角色编号是否存在
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 * @ykchoi
	 */
	public void existRoleCode(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		Msg msg = new Msg(rsp);
		try {
			String id = cmd.getRole().getRoleCode();
			if (roleService.exist(id)) {
				msg.error();
			} else {
				msg.addSuccess();
			}
		} catch (Exception e) {
			msg.error();
			// msg.addError("用户已经存在，请重试！");
			e.printStackTrace();
		}
	}

	/**
	 * 查询角色
	 * 
	 * @param req
	 * @param rsp
	 * @param role
	 * @return
	 */
	public ModelAndView queryRole(HttpServletRequest req, HttpServletResponse rsp, RoleCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/role/RoleMain");

		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		try {
			// 组装模型
			preparedModel(cmd.getTableModel(), visitor.getUser(), cmd.getRole(), req
					.getContextPath());

			List funcs = funcService.getButton(visitor.getUser().getUserCode(), "0103");
			List<Button> buttons = ButtonUtil.buildButtons(funcs);
			modelAndView.addObject("buttons", buttons);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "查询角色出错");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	/**
	 * 组装表格模型
	 * 
	 * @param tableModel
	 * @param condition
	 */
	public void preparedModel(TableModel<Role> tableModel, User curUser, Role condition,
			String context) {
		// 表格列对象

		String link = context + "/role/roleManager.do?action=viewRole&selectedIds=${roleCode}&"
				+ Constants.WINDOW_ISOPEN + "=yes";
		List<Column> columns = new ArrayList<Column>();
		Column col1 = new Column("roleCode", null, "6%", "center", null, 0, null, null, true);
		Column col2 = new Column("roleCode", "角色编号", "16%", "center", link, 1, "查看角色", "roleCode",
				false);
		Column col3 = new Column("roleName", "角色名称", "20%", "center", link, 1, "查看角色", "roleCode",
				false);
		Column col4 = new Column("descript", "描述", "30%", "center", null, 0, null, null, false);
		Column col5 = new Column("type", "角色类型", "12%", "center", null, 0, null, null, false);
		Column col6 = new Column("stop", "是否停用", "12%", "center", null, 0, null, null, false);
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col4);
		columns.add(col5);
		columns.add(col6);
		tableModel.setColumns(columns);

		tableModel.setHasIndex(true);

		// 表格分页链接
		tableModel.getPage().setAction(context + "/role/roleManager.do?action=queryRole");

		// 数据总数
		int count = roleService.getCount(curUser, condition);
		tableModel.getPage().setCount(count);
		tableModel.getPage().setPageSize(100);

		// 当页数据
		List tableList = roleService.query(curUser, condition, tableModel.getPage()
				.getCurrentPage(), tableModel.getPage().getPageSize());
		tableModel.setData(tableList);
	}
}
