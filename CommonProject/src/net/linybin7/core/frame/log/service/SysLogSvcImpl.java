
package net.linybin7.core.frame.log.service;

import net.linybin7.core.frame.log.dao.SysLogDao;


public class SysLogSvcImpl implements SysLogSvc {

	private SysLogDao sysLogDao;

	public SysLogDao getSysLogDao() {
		return sysLogDao;
	}

	public void setSysLogDao(SysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}
}
