package net.linybin7.scheduler.task;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.linybin7.scheduler.util.TimeUtil;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-8-����02:10:02 <br>
 * @description <br>
 *              TODO
 **/
public class BaseProgress {
	/**
	 * �Ƿ�ɼ������
	 */
	private boolean determinate;
	/**
	 * ��ʼʱ��
	 */
	private Date startTime;
	/**
	 * ����������determinate = true ʱ��Ч��
	 */
	private int maximum;
	/**
	 * �������������determinate = true ʱ��Ч��
	 */
	private int donenum;
	/**
	 * ������ʾ��Ϣ
	 */
	private String message;

	/**
	 * ������ɰٷֱȣ�determinate = true ʱ��Ч��
	 * 
	 * @param precision
	 *            �ٷֱȾ���(С��λ��)
	 * @return
	 */
	public float getPercent() {
		if (this.maximum <= 0)
			return 0;

		float value = new Float(this.donenum) * 100 / new Float(this.maximum);

		return value;
	}

	/**
	 * �Ѻ�ʱ�䣨���룩
	 */
	public long getConsumeTime() {
		if (this.startTime == null)
			return 0;
		return System.currentTimeMillis() - this.startTime.getTime();
	}

	/**
	 * �����ٶȣ���Ԫ/�룩
	 * 
	 * @return
	 */
	public long getSpeed() {
		long consume = this.getConsumeTime();
		if (consume <= 0)
			return 0;
		return this.donenum * 1000 / consume;
	}

	/**
	 * ʣ��ʱ�䣨determinate = true ʱ��Ч��
	 * 
	 * @return
	 */
	public long getRemainingTime() {
		long speed = this.getSpeed();
		if (speed <= 0)
			return 0;
		int remain = this.maximum - this.donenum;
		if (remain <= 0)
			return 0;
		return remain * 1000 / speed;
	}

	public boolean isDeterminate() {
		return determinate;
	}

	public void setDeterminate(boolean determinate) {
		this.determinate = determinate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

	public int getDonenum() {
		return donenum;
	}

	public void setDonenum(int donenum) {
		this.donenum = donenum;
	}

	public String getMessage() {
		if (message == null) {
			if (this.getStartTime() == null)
				return " ";

			String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getStartTime());
			String cs = TimeUtil.format(this.getConsumeTime());
			String pc = new DecimalFormat(".00").format(this.getPercent()) + "%";
			String rm = TimeUtil.format(this.getRemainingTime());

			if (determinate)
				return concat("��ʼʱ�䣺#�� ����ʱ��#", st, cs);
			else
				return concat("��ʼʱ�䣺#�� ����ʱ��#", st, cs);
		}
		return message;
	}

	public void setMessage(String message, Object... args) {
		this.message = concat(message, args);
	}

	private String concat(String message, Object... args) {
		String msg = message;
		if (msg == null)
			return null;
		else {
			for (Object o : args) {
				msg = msg.replaceFirst("#", o == null ? "" : o.toString());
			}
		}
		return msg;
	}

}
