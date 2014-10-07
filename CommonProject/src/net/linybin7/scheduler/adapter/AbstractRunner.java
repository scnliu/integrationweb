package net.linybin7.scheduler.adapter;

import net.linybin7.scheduler.task.AbstractTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-23-上午09:10:15 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractRunner implements Runnable {
	/**
	 * 线程运行状态 -1:初始化； 0：排队；1：执行中；2：完成/终止；3：拒绝
	 */
	public final static int STATE_INIT = -1;
	public final static int STATE_WAIT = 0;
	public final static int STATE_RUN = 1;
	public final static int STATE_DONE = 2;
	public final static int STATE_REJ = 3;

	private int state = STATE_INIT;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	/**
	 * 优先级 10：低；100：一般；1000：高
	 */
	public final static int PRIORITY_LOW = 10;
	public final static int PRIORITY_NORMAL = 100;
	public final static int PRIORITY_HIGH = 1000;

	private int priority = PRIORITY_NORMAL;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	private String name;

	/**
	 * 线程被拒绝时回调的方法
	 */
	public abstract void rejectedExecute();

	/**
	 * 线程执行完成时回调的方法
	 */
	public abstract void afterExecute();

	/**
	 * 线程开始执行前调用的方法
	 */
	public abstract void beforeExecute();

	public abstract AbstractTask getTask();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name == null ? this.getTask().getId() : name;
	}

}
