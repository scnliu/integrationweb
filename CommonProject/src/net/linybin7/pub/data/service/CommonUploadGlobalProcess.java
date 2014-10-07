package net.linybin7.pub.data.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Column;

/**
 * Title:
 * Description: 
 *
 * Copyright: 2013 . All rights reserved.
 * Company: eshine
 * CreateDate:2013-1-4
 * @author LinYuBin 
 * ----------------------------------------------
 * Date			Mender			Reason
 */
public interface CommonUploadGlobalProcess {
	public void processData(List<Column> dataCol, List<Integer> checks,File file, HttpServletRequest req,
			 CommonUploadService service);
}
