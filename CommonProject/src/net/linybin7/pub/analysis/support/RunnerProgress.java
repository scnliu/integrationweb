package net.linybin7.pub.analysis.support;

import java.util.Date;
import java.util.HashMap;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-12-����12:24:17 <br>
 * @description <br>
 *              TODO
 **/
public class RunnerProgress extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ����ִ�еĲ���
	 */
	private int step = 0;

	/**
	 * ������ʾ��Ϣ
	 */
	private String message;
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

	private String toolCode;

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
		// if (message == null) {
		// if (this.getStartTime() == null)
		// return " ";
		//
		// String st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.getStartTime());
		// String cs = TimeUtil.format(this.getConsumeTime());
		// String pc = new DecimalFormat(".00").format(this.getPercent()) + "%";
		// String rm = TimeUtil.format(this.getRemainingTime());
		//
		// if (determinate)
		// return concat("��ʼʱ�䣺#������ɣ�#�� ����ʱ��#�����ʣ�#�� ʣ��ʱ�䣺#", st, pc, cs, this.getSpeed(), rm);
		// else
		// return concat("��ʼʱ�䣺#������ɣ�#�� ����ʱ��#�����ʣ�#", st, this.getDonenum(), cs, this
		// .getSpeed());
		// }
		return message;
	}

	public void setMessage(String message, Object... args) {
		this.message = concat(message, args);
	}

	public void setMessage(String message) {
		this.message = message;
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

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public Integer getInt(String key) {
		Object obj = this.get(key);

		if (obj == null)
			return null;
		if (obj instanceof Integer) {
			return (Integer) obj;
		}
		String str = (String) obj;
		try {
			return Integer.valueOf(str);
		} catch (Exception e) {
		}
		return null;
	}

	public void setToolCode(String toolCode) {
		this.toolCode = toolCode;
	}

	public String getToolCode() {
		return toolCode;
	}
}
