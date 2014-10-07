package net.linybin7.is.weibo.service;

import java.util.Date;
import java.util.List;

import net.linybin7.common.tag.Pager;
import net.linybin7.is.weibo.bo.WeiboTrend;
import net.linybin7.is.weibo.cmd.PageCmd;

import weibo4j.model.Trend;

/**
 * Title:
 * Description: 
 *
 * Copyright: 2013 . All rights reserved.
 * Company: eshine
 * CreateDate:2013-4-7
 * @author LinYuBin 
 * ----------------------------------------------
 * Date			Mender			Reason
 */
public interface WeiboTrendsService {
	public void saveAccess(String code,String accessCode);	
	public int isExistAccess();	
	public String getAccessCode();
	public Date getExpiredTime();
	public int[] saveTrends(Trend[]trends,Date date,int type);
	
	public List<WeiboTrend> queryTrend(Pager p,PageCmd cmd);
	
}
