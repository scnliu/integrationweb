package net.linybin7.core.web.fileupload;

public class ProgressReport implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String file = "unknown";

	private String info = "";

	private long startTime = -1;

	private long totalSize = -1;

	private long doneSize = 0;

	public ProgressReport() {

	}

	public long getRemainTime() {
		long speed = this.getSpeed();
		long remain = this.totalSize - this.doneSize;
		if (speed > 0 && remain > 0) {
			return remain / speed;
		}
		return -1;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public long getSpanTime() {
		return System.currentTimeMillis() - this.startTime;
	}

	public long getSpeed() {
		long span = this.getSpanTime();
		if (span > 0) {
			long speed = this.doneSize / span;
			return speed;
		}
		return -1;
	}

	public int getPercent() {
		if (this.totalSize > 0) {
			return (int) (this.doneSize * 100 / this.totalSize);
		}
		return -1;
	}

	public long getDoneSize() {
		return doneSize;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String fileName) {
		this.file = fileName;
	}

	public long getStartTime() {
		return this.startTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setDoneSize(long doneSize) {
		this.doneSize = doneSize;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

}
