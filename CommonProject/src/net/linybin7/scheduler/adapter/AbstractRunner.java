package net.linybin7.scheduler.adapter;

import net.linybin7.scheduler.task.AbstractTask;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-23-����09:10:15 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractRunner implements Runnable {
	/**
	 * �߳�����״̬ -1:��ʼ���� 0���Ŷӣ�1��ִ���У�2�����/��ֹ��3���ܾ�
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
	 * ���ȼ� 10���ͣ�100��һ�㣻1000����
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
	 * �̱߳��ܾ�ʱ�ص��ķ���
	 */
	public abstract void rejectedExecute();

	/**
	 * �߳�ִ�����ʱ�ص��ķ���
	 */
	public abstract void afterExecute();

	/**
	 * �߳̿�ʼִ��ǰ���õķ���
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
