package net.linybin7.scheduler.task;

import java.util.Date;

import net.linybin7.scheduler.BaseTrigger;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-7-下午04:23:09 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractTask {
	/**
	 * 任务ID
	 */
	private String id;
	/**
	 * 任务类型（TODO）
	 */
	private String type;

	/**
	 * 状态： 1：初始化； 2：入库中； 3：等待入库； 4：入库成功； 0：入库失败；';
	 */
	// public static final String STATE_INIT = "1";
	public static final String STATE_WAITING = "3";
	public static final String STATE_EXECUTING = "2";
	public static final String STATE_SUCCESS = "4";
	public static final String STATE_FAILURE = "0";

	private String state = STATE_WAITING;

	/**
	 * 任务名称
	 */
	private String name;
	/**
	 * 任务拥有者
	 */
	private String owner;
	/**
	 * 任务描述
	 */
	private String description;

	/**
	 * 任务开始时间
	 */
	private Date startTime;

	/**
	 * 结束开始时间
	 */
	private Date endTime;

	/**
	 * 优先级 ：低；：一般；：高
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
