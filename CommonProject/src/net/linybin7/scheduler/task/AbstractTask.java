package net.linybin7.scheduler.task;

import java.util.Date;

import net.linybin7.scheduler.BaseTrigger;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-7-����04:23:09 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractTask {
	/**
	 * ����ID
	 */
	private String id;
	/**
	 * �������ͣ�TODO��
	 */
	private String type;

	/**
	 * ״̬�� 1����ʼ���� 2������У� 3���ȴ���⣻ 4�����ɹ��� 0�����ʧ�ܣ�';
	 */
	// public static final String STATE_INIT = "1";
	public static final String STATE_WAITING = "3";
	public static final String STATE_EXECUTING = "2";
	public static final String STATE_SUCCESS = "4";
	public static final String STATE_FAILURE = "0";

	private String state = STATE_WAITING;

	/**
	 * ��������
	 */
	private String name;
	/**
	 * ����ӵ����
	 */
	private String owner;
	/**
	 * ��������
	 */
	private String description;

	/**
	 * ����ʼʱ��
	 */
	private Date startTime;

	/**
	 * ������ʼʱ��
	 */
	private Date endTime;

	/**
	 * ���ȼ� ���ͣ���һ�㣻����
	 */
	private int priority;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public abstract BaseProgress getProgress();

	public abstract BaseTrigger getTrigger();

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

	public boolean isRunning() {
		return STATE_WAITING.equals(this.state) || STATE_EXECUTING.equals(this.state);
	}
}
