package net.linybin7.pub.data.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.mapping.Column;

/**
 * Title:
 * Description:
 * 
 * Copyright: 2012 . All rights reserved.
 * Company: eshine
 * CreateDate:2012-10-16
 * 
 * @author LinYuBin
 * ----------------------------------------------
 * Date Mender Reason
 */
public interface CommonUploadRowProcess {
	public Object[] rowProcesss(List<Column> dataCol, List<Integer> checks,
			Object[] fileLine, File file, HttpServletRequest req, CommonUploadService service,Map<String,String>specialData);
}
