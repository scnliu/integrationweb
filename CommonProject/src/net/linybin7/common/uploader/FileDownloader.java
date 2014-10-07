package net.linybin7.common.uploader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

public class FileDownloader {
	private HttpServletResponse res;

	public FileDownloader(HttpServletResponse res) {
		this.res = res;
	}

	public void download(File downf) throws Exception {
		this.download(downf, downf.getName());
	}

	public void download(File downf, String fileName) throws Exception {
		this.download(downf, fileName, false);
	}

	public void download(File downf, String fileName, boolean online)
			throws Exception {
		InputStream is = null;
		try {
			is = new FileInputStream(downf);
			this.download(is, fileName, online);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				is.close();
		}
	}

	public void download(InputStream is, String fileName, boolean online)
			throws Exception {
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(is);
			byte[] buf = new byte[1024];
			int len = 0;

			res.reset();
			if (online) { // 在线打开方式
				URL u = new URL("file:///" + fileName);
				res.setContentType(u.openConnection().getContentType());
				res.setHeader("Content-Disposition", "inline; filename="
						+ fileName);
			} else { // 纯下载方式
				res.setContentType("application/x-msdownload");
				res.setHeader("Content-Disposition", "attachment; filename="
						+ new String(fileName.getBytes(),"GBK"));
			}
			out = res.getOutputStream();
			while ((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			out.close();
		}
	}

	public void download(InputStream is, String fileName) throws Exception {
		this.download(is, fileName, false);
	}

}
