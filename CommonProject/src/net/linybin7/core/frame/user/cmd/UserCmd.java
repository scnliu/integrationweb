package net.linybin7.core.frame.user.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.util.Constants;


/**
 * 用户命令对象
 * 
 * 
 * 
 */
public class UserCmd implements Serializable {

	private TableModel<User> tableModel = new TableModel<User>();

	private User user = new User();

	private String[] selectedIds;

	private String[] roleCodes;

	private String oldPassword;

	private String menuType;

	private String topic;

	private String screenMode;

	private String indexPageType;

	private String funcId;

	private String funcName;

	private String msg = "first";

	private String[][] type = null;
	
	private final String[][] stop = new String[][] { { String.valueOf(Constants.STOP_NO), "否" },
			{ String.valueOf(Constants.STOP_YES), "是" } };

	private final String[][] userTypeAddAndEdit = new String[][] { { "1", "普通用户" }, { "2", "管理员" } };

	private final String[][] limitType = new String[][] { { "0", "无限制" }, { "1", "登录次数限制" },
			{ "2", "使用时限限制" } };

	public UserCmd() {

	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String[] getRoleCodes() {
		return roleCodes;
	}

	public void setRoleCodes(String[] roleCodes) {
		this.roleCodes = roleCodes;
	}

	public TableModel<User> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<User> tableModel) {
		this.tableModel = tableModel;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String[][] getType() {
		return type;
	}

	public void setType(String[][] type) {
		this.type = type;
	}

	/**
	 * 获得类型选项
	 * 
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getTypeOption() {
		return OptionUtil.toOptions(type);
	}

	public List<Option> getTypeAddAndEditOption() {
		return OptionUtil.toOptions(userTypeAddAndEdit);
	}

	/**
	 * 获得是否停用选项
	 * 
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getStopOption() {
		return OptionUtil.toOptions(stop);
	}

	/**
	 * 获得使用类型选项
	 * 
	 * @param value
	 * @param hasBlank
	 * @return
	 */
	public List<Option> getLimitTypeOption() {
		return OptionUtil.toOptions(limitType);
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getScreenMode() {
		return screenMode;
	}

	public void setScreenMode(String screenMode) {
		this.screenMode = screenMode;
	}

	public String getIndexPageType() {
		return indexPageType;
	}

	public void setIndexPageType(String indexPageType) {
		this.indexPageType = indexPageType;
	}

	public String getFuncId() {
		return funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
