package net.linybin7.scheduler;

import java.util.Date;

public class BaseTrigger {
	/**
	 * 触发器名称
	 */
	private String name;
	/**
	 * 是否简单触发器
	 */
	private boolean simple = true;
	/**
	 * 出发周期（简单触发器适用）
	 */
	private long interval;
	/**
	 * 延迟触发时间（毫秒）
	 */
	private long delay;
	/**
	 * 精确触发器表达式（simple=false 时适用）
	 */
	private String cronExpression;
	/**
	 * 触发有效期开始时间
	 */
	private Date startTime;
	/**
	 * 触发有效期截至时间
	 */
	private Date endTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(boolean simple) {
		this.simple = simple;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
