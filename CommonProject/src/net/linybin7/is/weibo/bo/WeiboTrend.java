package net.linybin7.is.weibo.bo;

import java.util.Date;
import java.util.List;

import weibo4j.model.Trend;

/**
 * Title:
 * Description: 
 *
 * Copyright: 2013 . All rights reserved.
 * Company: eshine
 * CreateDate:2013-4-23
 * @author LinYuBin 
 * ----------------------------------------------
 * Date			Mender			Reason
 */
public class WeiboTrend {
	private String queryName;
	private Date startTime;
	private long id;
	
	private int weekCount;
	private int dayCount;
	private int houCout;
	
	private List<Trend>weekDelta;
	private List<Trend>dayDelta;
	private List<Trend>hourDelta;
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public int getWeekCount() {
		return weekCount;
	}
	public void setWeekCount(int weekCount) {
		this.weekCount = weekCount;
	}
	public int getDayCount() {
		return dayCount;
	}
	public void setDayCount(int dayCount) {
		this.dayCount = dayCount;
	}
	public int getHouCout() {
		return houCout;
	}
	public void setHouCout(int houCout) {
		this.houCout = houCout;
	}
	public List<Trend> getWeekDelta() {
		return weekDelta;
	}
	public void setWeekDelta(List<Trend> weekDelta) {
		this.weekDelta = weekDelta;
	}
	public List<Trend> getDayDelta() {
		return dayDelta;
	}
	public void setDayDelta(List<Trend> dayDelta) {
		this.dayDelta = dayDelta;
	}
	public List<Trend> getHourDelta() {
		return hourDelta;
	}
	public void setHourDelta(List<Trend> hourDelta) {
		this.hourDelta = hourDelta;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
}
