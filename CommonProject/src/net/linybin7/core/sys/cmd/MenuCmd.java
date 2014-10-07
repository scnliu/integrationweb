package net.linybin7.core.sys.cmd;

public class MenuCmd {
	private String menuCode;
	private String toolCode;

	public String getToolCode() {
		return toolCode;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	/**
	 * 
	 */
	public MenuCmd() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param menuCode
	 */
	public MenuCmd(String menuCode) {
		super();
		this.menuCode = menuCode;
	}

	/**
	 * @return the menuCode
	 */
	public String getMenuCode() {
		return menuCode;
	}

	/**
	 * @param menuCode
	 *            the menuCode to set
	 */
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

}
