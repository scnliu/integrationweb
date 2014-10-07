package net.linybin7.common.uploader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ProgressRandomAccessFile {
	ProgressReport report;

	RandomAccessFile raf;

	public ProgressRandomAccessFile(RandomAccessFile raf, ProgressReport report)
			throws FileNotFoundException {
		this.raf = raf;
		this.report = report;
	}

	public String readLine() throws IOException {
		String line = this.raf.readLine();
		this.report.setDoneSize(this.report.getDoneSize()
				+ (line == null ? 0 : line.length()));
		return line;
	}
}
