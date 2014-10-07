package net.linybin7.core.frame.org.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.common.component.table.TableModel;
import net.linybin7.core.frame.bo.Org;
import net.linybin7.core.util.Constants;


/**
 * 部门命令对象
 * 
 * 
 * 
 */
public class OrgCmd implements Serializable {

	private TableModel<Org> tableModel = new TableModel<Org>();

	private Org org = new Org();

	private String[] selectedIds;

	private final String[][] type = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.FUNC_TYPE_MENU), "菜单" },
			{ String.valueOf(Constants.FUNC_TYPE_BUTTON), "按钮" },
			{ String.valueOf(Constants.FUNC_TYPE_LINK), "链接" } };

	private final String[][] stop = new String[][] { { "-1", "" },
			{ String.valueOf(Constants.STOP_NO), "否" }, { String.valueOf(Constants.STOP_YES), "是" } };

	private String[] userCodes;
	
	public OrgCmd() {

	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public TableModel<Org> getTableModel() {
		return tableModel;
	}

	public void setTableModel(TableModel<Org> tableModel) {
		this.tableModel = tableModel;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
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

	// String[]:[0]--orgId,[1]--orgName
	private List<String[]> orgOption = null;

	public void setOrgOption(List<String[]> orgOption) {
		this.orgOption = orgOption;
	}

	/**
	 * 获得单位名称选项
	 * 
	 * @return
	 */
	public List<Option> getOrgOption() {
		return OptionUtil.toOptions(orgOption);
	}

	/**
	 * @return the userCodes
	 */
	public String[] getUserCodes()
	{
		return userCodes;
	}

	/**
	 * @param userCodes the userCodes to set
	 */
	public void setUserCodes(String[] userCodes)
	{
		this.userCodes = userCodes;
	}

}
