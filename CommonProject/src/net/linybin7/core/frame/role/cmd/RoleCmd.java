package net.linybin7.core.frame.role.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.frame.bo.Role;
import net.linybin7.core.util.Constants;


/**
 * 角色命令对象
 * 
 * 
 * 
 */
public class RoleCmd implements Serializable {

	private TableModel<Role> tableModel = new TableModel<Role>();

	private Role role = new Role();

	private String[] selectedIds;

	private String[] purviews;

	private String[][] type = null;

	private final String[][] stop = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.STOP_NO), "否" }, { String.valueOf(Constants.STOP_YES), "是" } };

	private final String[][] typeOrgList = new String[][] { { "1", "普通角色" }, { "2", "管理角色" } };

	public RoleCmd() {

	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String[] getPurviews() {
		return purviews;
	}

	public void setPurviews(String[] purviews) {
		this.purviews = purviews;
	}

	public TableModel<Role> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<Role> tableModel) {
		this.tableModel = tableModel;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public List<Option> getTypeOrgListOption() {
		return OptionUtil.toOptions(typeOrgList);
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
}
