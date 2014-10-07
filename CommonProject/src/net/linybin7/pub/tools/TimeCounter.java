package net.linybin7.pub.tools;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-5-25-����03:52:58 <br>
 * @description <br>
 *              TODO
 **/
public class TimeCounter {
	private long start;
	private long lastStart;

	public TimeCounter() {
		this.start = System.currentTimeMillis();
		this.lastStart = start;
	}

	public long count() {
		long time = System.currentTimeMillis();
		long count = time - lastStart;
		lastStart = time;
		return count;
	}

	public long countAll() {
		return System.currentTimeMillis() - start;
	}
}
