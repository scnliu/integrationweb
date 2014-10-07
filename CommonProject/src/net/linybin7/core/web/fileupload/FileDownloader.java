package net.linybin7.core.web.fileupload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

public final class FileDownloader {

	private FileDownloader() {

	}

	/**
	 * 下载文件
	 * 
	 * @param res
	 * @param file
	 * @throws Exception
	 */
	public static void download(HttpServletResponse res, File file) throws Exception {
		download(res, file, false);
	}

	/**
	 * 下载文件
	 * 
	 * @param res
	 * @param file
	 * @param inline
	 * @throws Exception
	 */
	public static void download(HttpServletResponse res, File file, boolean inline)
			throws Exception {
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[1024];
			int len = 0;

			res.reset();
			if (inline) { // 在线打开方式
				URL u = new URL("file:///" + file.getAbsolutePath());
				res.setContentType(u.openConnection().getContentType());
				String fileName = FileUploadUtil.encodingFileName(file.getName());
				res.setHeader("Content-Disposition", "inline; filename=" + fileName);
			} else { // 纯下载方式
				res.setContentType("application/x-msdownload");
				String fileName = FileUploadUtil.encodingFileName(file.getName());
				res.setHeader("Content-Disposition", "attachment; filename=" + fileName);
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
}
