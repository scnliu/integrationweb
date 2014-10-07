package net.linybin7.pub.tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadDispose {
	private String filePath = "";// 上传路径
	private int sizeThreshold = 2 * 1024;// 上传缓存默认2K
	private String repository = null;// 临时目录
	private int sizeMax = 100 * 1024 * 1024;// 上传文件最大限制，默认10M
	private String headerEncoding = "UTF-8";// 页面编码，默认UTF-8
	private final List<String> acceptFix = new ArrayList<String>();// 允许上传的文件后缀名
	private final List<String> notAcceptFix = new ArrayList<String>();// 不允许上传的文件后缀名
	private final List<ErrorMsg> uploadError = new ArrayList<ErrorMsg>();// 上传错误

	/**
	 * 文件上传处理
	 * 
	 * @param request
	 *            文件传递对象
	 */
	public List<UploadMsg> upload(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置上传缓存
		factory.setSizeThreshold(sizeThreshold);
		// 设置上传临时缓存目录
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			rootPath = URLDecoder.decode(rootPath, "utf-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		String tempPath;
		if (repository == null) {
			tempPath = appendPath(rootPath, "temp");
		} else {
			tempPath = appendPath(rootPath, repository);
		}
		File repository_F = new File(tempPath);
		if (!repository_F.exists()) {
			repository_F.mkdirs();
		}
		factory.setRepository(repository_F);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(sizeMax);
		upload.setHeaderEncoding(headerEncoding);
		// 文件上传，解释request
		List items = null;
		List<UploadMsg> uploadList = new ArrayList<UploadMsg>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			appendUploadError(UploadError.TooLarger, null);
			return uploadList;
		}
		Iterator ite = items.iterator();
		Pattern pattern = Pattern
				.compile("(.*[\\\\/])?([^\\\\/\\|\\*<>:\"\\?]+\\.([^\\\\/\\|\\*<>:\"\\?]+))");
		while (ite.hasNext()) {
			FileItem fileItem = (FileItem) ite.next();
			if (!fileItem.isFormField()) {
				String fileName = fileItem.getName();
				fileName = formatFile(fileName);
				Matcher matcher = pattern.matcher(fileName);
				if (matcher.matches()) {
					fileName = matcher.group(2);
					String fix = matcher.group(3).trim().toLowerCase();
					if (acceptFix.size() > 0) {// 定义了上传后缀名限制
						if (!acceptFix.contains(fix)) {
							appendUploadError(UploadError.NotAcceptFix, fileName);
							continue;
						}
					}
					if (notAcceptFix.size() > 0) {// 未定义后缀名限制
						if (acceptFix.contains(fix)) {
							appendUploadError(UploadError.NotAcceptFix, fileName);
							continue;
						}
					}
					String serverFileName = System.currentTimeMillis() + "." + fix;
					String webPath = appendPath(filePath, serverFileName);
					String serverPath = appendPath(rootPath, webPath);
					File serverFile = new File(serverPath);
					File serverDir = serverFile.getParentFile();
					if (!serverDir.exists())
						serverDir.mkdirs();
					try {
						fileItem.write(serverFile);
						UploadMsg uploadMsg = new UploadMsg();
						uploadMsg.setFileFix(fix);
						uploadMsg.setFileName(fileName);
						uploadMsg.setServerFileName(serverFileName);
						uploadMsg.setServerPath(serverPath);
						uploadMsg.setWebPath(webPath);
						uploadList.add(uploadMsg);
					} catch (Exception e) {
						appendUploadError(UploadError.NotAcceptFix, fileName);
						continue;
					}

				} else {
					appendUploadError(UploadError.NotHasFix, fileName);
					continue;
				}
			}
		}
		return uploadList;
	}

	/**
	 * 增加上传文件类型后缀名限制，增加设置允许上传的后缀名
	 * 
	 * @param fix
	 *            文件后缀名
	 */
	public void appendAcceptFix(String fix) {
		acceptFix.add(fix.trim().toLowerCase());
	}

	/**
	 * 增加上传文件类型后缀名限制，增加设置允许上传的后缀名，可以输入jpg,jpeg类型的后缀名
	 * 
	 * @param fix
	 *            文件后缀名
	 */
	public void appendAcceptFixString(String fixString) {
		String[] fixes = fixString.split(",");
		for (String fix : fixes) {
			appendAcceptFix(fix);
		}
	}

	/**
	 * 增加上传文件类型后缀名限制，增加设置不允许上传的后缀名
	 * 
	 * @param fix
	 *            文件后缀名
	 */
	public void appendNotAcceptFix(String fix) {
		notAcceptFix.add(fix.trim().toLowerCase());
	}

	/**
	 * 增加上传文件类型后缀名限制，增加设置不允许上传的后缀名，可以输入jpg,jpeg类型的后缀名
	 * 
	 * @param fix
	 *            文件后缀名
	 */
	public void appendNotAcceptFixString(String fixString) {
		String[] fixes = fixString.split(",");
		for (String fix : fixes) {
			appendNotAcceptFix(fix);
		}
	}

	/**
	 * 增加新的文件子路径并格式化
	 * 
	 * @param oldFile
	 *            原文件路径
	 * @param newFile
	 *            子路径
	 * @return 增加结果
	 */
	private String appendPath(String oldFile, String newFile) {
		oldFile = oldFile + File.separator + newFile;
		return formatFilePath(oldFile);
	}

	/**
	 * 格式化路径，即将“\ ”转为“/”并去除重复
	 * 
	 * @param filePath
	 *            格式化的路径字符串
	 * @return 结果字符串
	 */
	private String formatFilePath(String filePath) {
		return filePath.replaceAll("\\\\+", "/").replaceAll("/+", "/");
	}

	public List<ErrorMsg> getUploadError() {
		return uploadError;
	}

	/**
	 * 增加上传错误
	 * 
	 * @param uploadError
	 */
	private void appendUploadError(UploadError uploadError, String fileName) {
		this.uploadError.add(new ErrorMsg(uploadError, fileName));
	}

	/**
	 * 上传错误类型枚举
	 * 
	 * @author Gaojt
	 * 
	 */
	public enum UploadError {
		TooLarger("上传文件太大，超出限制文件大小"), NotAcceptFix("上传文件类型不对，不允许上传该后缀名文件"), NotHasFix(
				"上传文件没有后缀名，不允许上传此文件");

		private final String errorMsg;

		UploadError(String errorMsg) {
			this.errorMsg = errorMsg;

		}

		public String getErrorMsg() {
			return this.errorMsg;
		}
	}

	/**
	 * 上传错误记录
	 * 
	 * @author Gaojt
	 * 
	 */
	public class ErrorMsg {
		/**
		 * 上传错误的文件名
		 */
		private String fileName;
		/**
		 * 错误类型
		 */
		private UploadError uploadError;

		public ErrorMsg(UploadError uploadError, String fileName) {
			this.uploadError = uploadError;
			this.fileName = fileName;
		}

		public ErrorMsg() {
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public UploadError getUploadError() {
			return uploadError;
		}

		public void setUploadError(UploadError uploadError) {
			this.uploadError = uploadError;
		}

	}

	/**
	 * 文件上传信息存放类
	 * 
	 * @author Gaojt
	 * 
	 */
	public class UploadMsg {
		private String serverPath;// 上传到服务器文件路径
		private String serverFileName;// 上传至服务器文件名称
		private String webPath;// 上传文件相对路径
		private String fileName;// 上传文件原名称
		private String fileFix;// 上传文件后缀名

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getServerFileName() {
			return serverFileName;
		}

		public void setServerFileName(String serverFileName) {
			this.serverFileName = serverFileName;
		}

		public String getServerPath() {
			return serverPath;
		}

		public void setServerPath(String serverPath) {
			this.serverPath = serverPath;
		}

		public String getWebPath() {
			return webPath;
		}

		public void setWebPath(String webPath) {
			this.webPath = webPath;
		}

		public String getFileFix() {
			return fileFix;
		}

		public void setFileFix(String fileFix) {
			this.fileFix = fileFix;
		}

	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getHeaderEncoding() {
		return headerEncoding;
	}

	public void setHeaderEncoding(String headerEncoding) {
		this.headerEncoding = headerEncoding;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public int getSizeMax() {
		return sizeMax;
	}

	public void setSizeMax(int sizeMax) {
		this.sizeMax = sizeMax;
	}

	public int getSizeThreshold() {
		return sizeThreshold;
	}

	public void setSizeThreshold(int sizeThreshold) {
		this.sizeThreshold = sizeThreshold;
	}

	/**
	 * 格式化文件路径，去除“\/”等不规范的文件路径
	 * 
	 * @param file
	 * @return
	 */
	public static String formatFile(String file) {
		if (file == null)
			return null;
		String rfile = file.replaceAll("\\\\", "/").replaceAll("/{2,}", "/");
		return rfile;
	}
}
