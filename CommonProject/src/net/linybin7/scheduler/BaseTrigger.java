package net.linybin7.scheduler;

import java.util.Date;

public class BaseTrigger {
	/**
	 * ����������
	 */
	private String name;
	/**
	 * �Ƿ�򵥴�����
	 */
	private boolean simple = true;
	/**
	 * �������ڣ��򵥴��������ã�
	 */
	private long interval;
	/**
	 * �ӳٴ���ʱ�䣨���룩
	 */
	private long delay;
	/**
	 * ��ȷ���������ʽ��simple=false ʱ���ã�
	 */
	private String cronExpression;
	/**
	 * ������Ч�ڿ�ʼʱ��
	 */
	private Date startTime;
	/**
	 * ������Ч�ڽ���ʱ��
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
