package net.linybin7.common.uploader;

import java.io.IOException;
import java.io.InputStream;

public class ProgressInputStream extends InputStream {

	InputStream inputStream;

	ProgressReport report;

	public ProgressInputStream(InputStream ins, ProgressReport report) {
		this.inputStream = ins;
		this.report = report;
	}

	@Override
	public int read() throws IOException {
		int size = this.inputStream.read();
		long done = this.report.getDoneSize() + size;
		this.report.setDoneSize(done);
		return size;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		int size = this.inputStream.read(b, off, len);
		long done = this.report.getDoneSize() + size;
		this.report.setDoneSize(done);
		return size;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int size = this.inputStream.read(b);
		long done = this.report.getDoneSize() + size;
		this.report.setDoneSize(done);
		return size;
	}

	@Override
	public void close() throws IOException {
		super.close();
		this.inputStream.close();
	}

}
