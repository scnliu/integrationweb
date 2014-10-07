package net.linybin7.core.frame.user.ctl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.component.button.Button;
import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.table.Column;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.common.tag.Msg;
import net.linybin7.core.config.Config.SysCfg;
import net.linybin7.core.exception.DuplicateKeyException;
import net.linybin7.core.frame.bo.Func;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.func.service.FuncSve;
import net.linybin7.core.frame.role.service.RoleSve;
import net.linybin7.core.frame.user.cmd.UserCmd;
import net.linybin7.core.frame.user.service.UserSve;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.component.tree.TreeModel;
import net.linybin7.core.web.component.tree.TreeNode;
import net.linybin7.core.web.component.util.ButtonUtil;
import net.linybin7.core.web.context.WebContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


/**
 * �û����������
 * 
 * 
 * 
 */
public class UserCtl extends MultiActionController {
	private UserSve userService;

	private RoleSve roleService;

	private FuncSve funcService;

	private final String[][] typeOrg = new String[][] { { "-1", "&nbsp;" },
			{ String.valueOf(Constants.USER_TYPE_COMMON), "��ͨ�û�" },
			{ String.valueOf(Constants.USER_TYPE_ORG), "����Ա" } };

	private final String[][] typeSys = new String[][] { { "-1", "&nbsp;" },
			{ String.valueOf(Constants.USER_TYPE_COMMON), "��ͨ�û�" },
			{ String.valueOf(Constants.USER_TYPE_ORG), "����Ա" },
			{ String.valueOf(Constants.USER_TYPE_SYS), "��������Ա" } };

	@Override
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
			throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		CustomDateEditor dateEditor = new CustomDateEditor(fmt, true);
		binder.registerCustomEditor(Date.class, dateEditor);
		super.initBinder(request, binder);
	}

	// protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws
	// Exception {
	// super.initBinder(req, binder);
	// // DateEditor dateEditor = new DateEditor("yyyy-mm-dd");
	// // binder.registerCustomEditor(Date.class, dateEditor);
	// }

	public UserSve getUserService() {
		return userService;
	}

	public void setUserService(UserSve userService) {
		this.userService = userService;
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

	private void preparedCmd(User curUser, UserCmd cmd) {
		if (curUser.getUserProp() == Constants.USER_TYPE_SYS) {
			cmd.setType(typeSys);
		} else {
			cmd.setType(typeOrg);
			// cmd.setType(typeSys);
		}
	}

	/**
	 * �����û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView addUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/AddUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);

		UserCmd newCmd = new UserCmd();
		newCmd.setUser(new User());
		newCmd.getUser().setUserProp(Constants.USER_TYPE_COMMON);
		newCmd.getUser().setIsStop(Constants.STOP_NO);
		List unselected = null;
		if (visitor.getUser().getUserProp() == Constants.USER_TYPE_SYS) {
			unselected = roleService.all();
		} else {
			unselected = roleService.getByOrgs(visitor.getOrgs());
		}
		List<Option> selected = new ArrayList<Option>();
		modelAndView.addObject("unselected", toOption(unselected));
		modelAndView.addObject("selected", selected);
		preparedCmd(visitor.getUser(), newCmd);
		modelAndView.addObject("cmd", newCmd);
		return modelAndView;
	}

	/**
	 * �����û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView saveUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/AddUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		String msg = "����û��ɹ�";
		if (!cmd.getUser().getPassword().trim().equals(cmd.getUser().getcPassword().trim())) {
			msg = "�������벻��ȷ";
		} else {
			try {
				userService.save(cmd.getUser());
				userService.assignRole(cmd.getUser().getUserCode(), cmd.getRoleCodes());
				cmd.setUser(new User());
				cmd.getUser().setUserProp(Constants.USER_TYPE_COMMON);
				cmd.getUser().setIsStop(Constants.STOP_NO);
			} catch (DuplicateKeyException e) {
				e.printStackTrace();
				// msg = e.getMessage();
				msg = "����û�����";
			}
		}
		List unselected = null;
		if (visitor.getUser().getUserProp() == Constants.USER_TYPE_SYS) {
			unselected = roleService.all();
		} else {
			unselected = roleService.getByOrgs(visitor.getOrgs());
		}
		List<Option> selected = new ArrayList<Option>();
		modelAndView.addObject("unselected", toOption(unselected));
		modelAndView.addObject("selected", selected);
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * ɾ���û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView deleteUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		String msg = "ɾ���û��ɹ�";
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		try {
			userService.delete(visitor.getUser(), cmd.getSelectedIds());
		} catch (Exception e) {
			// e.printStackTrace();
			msg = e.getMessage();
		}

		ModelAndView modelAndView = queryUser(req, rsp, cmd);
		modelAndView.addObject("MSG", msg);
		// modelAndView.getModelMap().addObject(Constants.KEY_MSG, msg);

		return modelAndView;
	}

	/**
	 * ����
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView start(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		String msg = "���óɹ�";
		try {
			userService.start(cmd.getSelectedIds());
		} catch (Exception e) {
			// e.printStackTrace();
			msg = e.getMessage();
		}
		ModelAndView modelAndView = queryUser(req, rsp, cmd);
		// modelAndView.getModelMap().addObject(Constants.KEY_MSG, msg);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * ͣ��
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView stop(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		String msg = "ͣ�óɹ�";
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		try {
			userService.stop(visitor.getUser(), cmd.getSelectedIds());
		} catch (Exception e) {
			// e.printStackTrace();
			msg = e.getMessage();
		}
		ModelAndView modelAndView = queryUser(req, rsp, cmd);
		modelAndView.addObject("MSG", msg);
		// modelAndView.getModelMap().addObject("MSG", msg);
		return modelAndView;
	}

	/**
	 * �༭�û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView editUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/EditUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);
		try {
			User user;
			if (cmd.getSelectedIds() == null) {
				user = userService.get(cmd.getUser().getUserCode());
				user.setLimitType(cmd.getUser().getLimitType());
			} else {
				user = userService.get(cmd.getSelectedIds()[0]);
			}
			List unselected = userService.getUnssignRole(visitor.getUser(), visitor.getOrgs(), user
					.getUserCode());
			List selected = userService.getRole(user.getUserCode());
			modelAndView.addObject("unselected", toOption(unselected));
			modelAndView.addObject("selected", toOption(selected));
			cmd.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "�޸��û�����");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	public ModelAndView toEditPersonInfo(HttpServletRequest req, HttpServletResponse rsp,
			UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/EditPersonInfo");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		try {
			User user = new User();
			user = userService.get(visitor.getUser().getUserCode());
			cmd.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "�޸��û�����");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	/**
	 * ��Ȩ�û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView trustUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/TrustUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);
		try {
			User user = userService.get(cmd.getSelectedIds()[0]);
			List unselected = userService.getUnssignRole(visitor.getUser(), visitor.getOrgs(), user
					.getUserCode());
			List selected = userService.getRole(user.getUserCode());
			modelAndView.addObject("unselected", toOption(unselected));
			modelAndView.addObject("selected", toOption(selected));
			cmd.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			// modelAndView.addObject(Constants.KEY_MSG, "�޸��û�����");
		}
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	/**
	 * �����û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/EditUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		String msg = "�޸��û��ɹ�";
		try {
			userService.update(cmd.getUser());
			userService.assignRole(cmd.getUser().getUserCode(), cmd.getRoleCodes());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "�޸��û�����";
		}
		try {
			List unselected = userService.getUnssignRole(visitor.getUser(), visitor.getOrgs(), cmd
					.getUser().getUserCode());
			List selected = userService.getRole(cmd.getUser().getUserCode());
			modelAndView.addObject("unselected", toOption(unselected));
			modelAndView.addObject("selected", toOption(selected));
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("MSG", msg);
		return modelAndView;
	}

	public ModelAndView updatePersonInfo(HttpServletRequest req, HttpServletResponse rsp,
			UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/EditPersonInfo");
		String msg = "�޸ĸ�����Ϣ�ɹ�";
		try {
			String message = userService.updatePersonInfo(cmd.getUser());
			if (message != null && message.trim().length() != 0) {
				msg = message;
			} else {
				modelAndView.addObject("editPersonInfoSuccess", "1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "�޸ĸ�����Ϣ����";
		}
		modelAndView.addObject("MSG", msg);
		modelAndView.addObject("cmd", cmd);
		return modelAndView;
	}

	public ModelAndView trustedUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/TrustUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		String msg = "��Ȩ�ɹ�";
		try {
			User u = userService.get(cmd.getUser().getUserCode());
			u.setTrustId(cmd.getUser().getTrustId());
			cmd.setUser(u);
			userService.update(u);
			// userService.assignRole(cmd.getUser().getUserCode(), cmd.getRoleCodes());
		} catch (Exception e) {
			e.printStackTrace();
			msg = "��Ȩ����";
		}
		// try {
		// List unselected = userService.getUnssignRole(visitor.getUser(), visitor.getOrgs(),
		// cmd.getUser().getUserCode());
		// List selected = userService.getRole(cmd.getUser().getUserCode());
		// modelAndView.addObject("unselected", toOption(unselected));
		// modelAndView.addObject("selected", toOption(selected));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject(Constants.KEY_MSG, msg);
		return modelAndView;
	}

	/**
	 * �鿴�û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView viewUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("core/user/ViewUser");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		try {
			User user = userService.get(cmd.getSelectedIds()[0]);
			modelAndView.addObject("user", user);
			modelAndView.addObject("tms", buildTreeModel(req, visitor.getUser(),
					user.getUserCode(), true));
			List roles = userService.getRole(user.getUserCode());
			modelAndView.addObject("roles", roles);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject(Constants.KEY_MSG, "�鿴�û�����");
		}

		return modelAndView;
	}

	private List<TreeModel> buildTreeModel(HttpServletRequest req, User currUser, String userCode,
			boolean cbDisabled) {
		SysCfg cfg = (SysCfg) req.getSession().getServletContext().getAttribute(
				Constants.KEY_SYSCFG);

		Map<String, List<Func>> allFuncs = funcService.getFuncs(currUser, cfg.getSyss());
		Map<String, String> assignedMap = userService.getPuerviewCodes(userCode);

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

	/**
	 * ��ѯ�û�
	 * 
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView queryUser(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		long begin = System.currentTimeMillis();
		ModelAndView modelAndView = new ModelAndView("core/user/UserMain");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		preparedCmd(visitor.getUser(), cmd);

		try {
			// ��װģ��
			preparedModel(cmd.getTableModel(), visitor.getUser(), cmd.getUser(), req
					.getContextPath());

			List funcs = funcService.getButton(visitor.getUser().getUserCode(), "0102");
			List<Button> buttons = ButtonUtil.buildButtons(funcs);
			modelAndView.addObject("buttons", buttons);
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.addObject("MSG", "��ѯ�û�����");
		}
		modelAndView.addObject("cmd", cmd);
		System.out.println("��ѯ��ʱ:" + (System.currentTimeMillis() - begin));
		return modelAndView;
	}

	/**
	 * �ж��û�����Ƿ����
	 * 
	 * @param req
	 * @param rsp
	 * @return
	 */
	public void existUserCode(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		Msg msg = new Msg(rsp);
		try {
			String id = cmd.getUser().getUserCode();
			if (userService.exist(id)) {
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

	// /**
	// * �����ɫ
	// * @param req
	// * @param rsp
	// * @param cmd
	// * @return
	// */
	// public ModelAndView assignRole(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd){
	// ModelAndView modelAndView = new ModelAndView("core/user/AssignRole");
	// try {
	// String userCode = cmd.getSelectedIds()[0];
	// User user = userService.get(userCode);
	// List unselected = userService.getUnssignRole(userCode);
	// List selected = userService.getRole(userCode);
	// modelAndView.addObject("user", user);
	// modelAndView.addObject("unselected", toOption(unselected));
	// modelAndView.addObject("selected", toOption(selected));
	// } catch (Exception e) {
	// e.printStackTrace();
	// modelAndView.addObject(Constants.KEY_MSG, "�����ɫҳ�����");
	// }
	// return modelAndView;
	// }

	/**
	 * ת��Ϊ����ѡ���б�
	 * 
	 * @param roles
	 * @return
	 */
	private List<Option> toOption(List roles) {
		List<Option> options = new ArrayList<Option>();
		for (int i = 0; i < roles.size(); i++) {
			Role role = (Role) roles.get(i);
			Option option = new Option();
			option.setValue(role.getRoleCode());
			option.setLabel(role.getRoleName());
			options.add(option);
		}
		return options;
	}

	// /**
	// * �����ɫ
	// * @param req
	// * @param rsp
	// * @param cmd
	// * @return
	// */
	// public ModelAndView saveRole(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd){
	// // ModelAndView modelAndView = new ModelAndView("core/user/AssignRole");
	// String msg = "����Ȩ�޳ɹ�";
	// try {
	// //��װģ��
	// userService.assignRole(cmd.getUser().getUserCode(), cmd.getRoleCodes());
	// } catch (Exception e) {
	// e.printStackTrace();
	// msg = "����Ȩ�޳���";
	// }
	// cmd.setSelectedIds(new String[]{cmd.getUser().getUserCode()});
	// ModelAndView modelAndView = assignRole(req, rsp, cmd);
	// modelAndView.addObject(Constants.KEY_MSG, msg);
	// return modelAndView;
	// }

	/**
	 * ��װ���ģ��
	 * 
	 * @param tableModel
	 * @param condition
	 */
	public void preparedModel(TableModel<User> tableModel, User curUser, User condition,
			String context) {
		// ����ж���
		String link = context + "/user/userManager.do?action=viewUser&selectedIds=${userCode}&"
				+ Constants.WINDOW_ISOPEN + "=yes";

		List<Column> columns = new ArrayList<Column>();
		Column col1 = new Column("userCode", null, "6%", "center", null, 0, null, null, true);
		Column col2 = new Column("userCode", "�û����", "16%", "center", link, 1, "�û��鿴", "userCode",
				false);
		Column col3 = new Column("userName", "�û�����", "20%", "center", link, 1, "�û��鿴", "userCode",
				false);
		Column col5 = new Column("type", "�û�����", "20%", "center", null, 0, null, null, false);
		Column col6 = new Column("nums", "��¼����", "10%", "center", null, 0, null, null, false);
		Column col7 = new Column("onTimeStr", "����ʱ��", "10%", "center", null, 0, null, null, false);
		Column col8 = new Column("stop", "�Ƿ�ͣ��", "20%", "center", null, 0, null, null, false);
		columns.add(col1);
		columns.add(col2);
		columns.add(col3);
		columns.add(col5);
		columns.add(col6);
		columns.add(col7);
		columns.add(col8);
		tableModel.setColumns(columns);

		tableModel.setHasIndex(true);
		// ����ҳ����
		tableModel.getPage().setAction(context + "/user/userManager.do?action=queryUser");

		// ��������
		int count = userService.getCount(curUser, condition);
		tableModel.getPage().setCount(count);
		tableModel.getPage().setPageSize(100);

		// ��ҳ����
		List tableList = userService.query(curUser, condition, tableModel.getPage()
				.getCurrentPage(), tableModel.getPage().getPageSize());
		tableModel.setData(tableList);
	}
	
	public void fileUpload(HttpServletRequest req, HttpServletResponse res){
		String separator = File.separator;
		String path = WebContext.contextPath + separator +"images"+separator+ "photo" + separator ;
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user=visitor.getUser();
		File f1 = new File(path);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		try {
//			req.setCharacterEncoding("utf8");
			File saveFile=null;
			List<FileItem> fileList = upload.parseRequest(req);
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					name = item.getName();
					if (name == null || name.trim().equals("")) {
						continue;
					}
					String fileType=null;
					try{
						fileType=name.substring(name.lastIndexOf("."));
					}catch(Exception ex){
						fileType=".jpeg";
						ex.printStackTrace();
					}
					saveFile = new File(f1 ,user.getUserCode()+fileType);
					try {
						item.write(saveFile);
						userService.updatePhotoType(user.getUserCode(), fileType);
						res.getWriter().write("success");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			try {
				res.getWriter().write("failure");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		}
	}
}