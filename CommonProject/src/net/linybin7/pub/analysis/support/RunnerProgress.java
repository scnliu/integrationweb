package net.linybin7.pub.analysis.support;

import java.util.Date;
import java.util.HashMap;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-12-下午12:24:17 <br>
 * @description <br>
 *              TODO
 **/
public class RunnerProgress extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 正在执行的步骤
	 */
	private int step = 0;

	/**
	 * 进度提示信息
	 */
	private String message;
	/**
	 * 是否可计算进度
	 */
	private boolean determinate;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 进度总量（determinate = true 时有效）
	 */
	private int maximum;
	/**
	 * 进度已完成量（determinate = true 时有效）
	 */
	private int donenum;

	private String toolCode;

	/**
	 * 进度完成百分比（determinate = true 时有效）
	 * 
	 * @param precision
	 *            百分比精度(小数位数)
	 * @return
	 */
	public float getPercent() {
		if (this.maximum <= 0)
			return 0;

		float value = new Float(this.donenum) * 100 / new Float(this.maximum);

		return value;
	}

	/**
	 * 已耗时间（毫秒）
	 */
	public long getConsumeTime() {
		if (this.startTime == null)
			return 0;
		return System.currentTimeMillis() - this.startTime.getTime();
	}

	/**
	 * 处理速度（单元/秒）
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
	 * 剩余时间（determinate = true 时有效）
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
		// return concat("开始时间：#，已完成：#， 已用时：#，速率：#， 剩余时间：#", st, pc, cs, this.getSpeed(), rm);
		// else
		// return concat("开始时间：#，已完成：#， 已用时：#，速率：#", st, this.getDonenum(), cs, this
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
