package net.linybin7.core.frame.ds.cmd;

import java.io.Serializable;
import java.util.List;

import net.linybin7.common.component.select.Option;
import net.linybin7.common.component.select.OptionUtil;
import net.linybin7.core.frame.bo.DataSource;


/**
 * 
 * 
 * 
 */
public class DataSourceCmd implements Serializable {

	/**
	 * 核心模块数据源
	 */
	private DataSource coreDs = new DataSource();

	/**
	 * 系统数据源
	 */
	private DataSource sysDs = new DataSource();

	public DataSource getCoreDs() {
		return coreDs;
	}

	public void setCoreDs(DataSource coreDs) {
		this.coreDs = coreDs;
	}

	public DataSource getSysDs() {
		return sysDs;
	}

	public void setSysDs(DataSource sysDs) {
		this.sysDs = sysDs;
	}

	public DataSourceCmd() {

	}

	private final String[][] dbType = new String[][] { { "1", "Oracle" }, { "2", "SyBase" },
			{ "3", "SQL2000" }, { "4", "SQL2005" }, { "5", "MySQL" } };

	private final String[][] dsType = new String[][] { { "1", "服务器数据源" }, { "2", "普通数据源" } };

	/**
	 * 数据源类型选项
	 * 
	 * @return
	 */
	public List<Option> getDbTypeOption() {
		return OptionUtil.toOptions(dbType);
	}

	/**
	 * 数据源类型选项
	 * 
	 * @return
	 */
	public List<Option> getDsTypeOption() {
		return OptionUtil.toOptions(dsType);
	}
}
