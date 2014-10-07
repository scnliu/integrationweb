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
	private String filePath = "";// �ϴ�·��
	private int sizeThreshold = 2 * 1024;// �ϴ�����Ĭ��2K
	private String repository = null;// ��ʱĿ¼
	private int sizeMax = 100 * 1024 * 1024;// �ϴ��ļ�������ƣ�Ĭ��10M
	private String headerEncoding = "UTF-8";// ҳ����룬Ĭ��UTF-8
	private final List<String> acceptFix = new ArrayList<String>();// �����ϴ����ļ���׺��
	private final List<String> notAcceptFix = new ArrayList<String>();// �������ϴ����ļ���׺��
	private final List<ErrorMsg> uploadError = new ArrayList<ErrorMsg>();// �ϴ�����

	/**
	 * �ļ��ϴ�����
	 * 
	 * @param request
	 *            �ļ����ݶ���
	 */
	public List<UploadMsg> upload(HttpServletRequest request) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ϴ�����
		factory.setSizeThreshold(sizeThreshold);
		// �����ϴ���ʱ����Ŀ¼
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
		// �ļ��ϴ�������request
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
					if (acceptFix.size() > 0) {// �������ϴ���׺������
						if (!acceptFix.contains(fix)) {
							appendUploadError(UploadError.NotAcceptFix, fileName);
							continue;
						}
					}
					if (notAcceptFix.size() > 0) {// δ�����׺������
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
	 * �����ϴ��ļ����ͺ�׺�����ƣ��������������ϴ��ĺ�׺��
	 * 
	 * @param fix
	 *            �ļ���׺��
	 */
	public void appendAcceptFix(String fix) {
		acceptFix.add(fix.trim().toLowerCase());
	}

	/**
	 * �����ϴ��ļ����ͺ�׺�����ƣ��������������ϴ��ĺ�׺������������jpg,jpeg���͵ĺ�׺��
	 * 
	 * @param fix
	 *            �ļ���׺��
	 */
	public void appendAcceptFixString(String fixString) {
		String[] fixes = fixString.split(",");
		for (String fix : fixes) {
			appendAcceptFix(fix);
		}
	}

	/**
	 * �����ϴ��ļ����ͺ�׺�����ƣ��������ò������ϴ��ĺ�׺��
	 * 
	 * @param fix
	 *            �ļ���׺��
	 */
	public void appendNotAcceptFix(String fix) {
		notAcceptFix.add(fix.trim().toLowerCase());
	}

	/**
	 * �����ϴ��ļ����ͺ�׺�����ƣ��������ò������ϴ��ĺ�׺������������jpg,jpeg���͵ĺ�׺��
	 * 
	 * @param fix
	 *            �ļ���׺��
	 */
	public void appendNotAcceptFixString(String fixString) {
		String[] fixes = fixString.split(",");
		for (String fix : fixes) {
			appendNotAcceptFix(fix);
		}
	}

	/**
	 * �����µ��ļ���·������ʽ��
	 * 
	 * @param oldFile
	 *            ԭ�ļ�·��
	 * @param newFile
	 *            ��·��
	 * @return ���ӽ��
	 */
	private String appendPath(String oldFile, String newFile) {
		oldFile = oldFile + File.separator + newFile;
		return formatFilePath(oldFile);
	}

	/**
	 * ��ʽ��·����������\ ��תΪ��/����ȥ���ظ�
	 * 
	 * @param filePath
	 *            ��ʽ����·���ַ���
	 * @return ����ַ���
	 */
	private String formatFilePath(String filePath) {
		return filePath.replaceAll("\\\\+", "/").replaceAll("/+", "/");
	}

	public List<ErrorMsg> getUploadError() {
		return uploadError;
	}

	/**
	 * �����ϴ�����
	 * 
	 * @param uploadError
	 */
	private void appendUploadError(UploadError uploadError, String fileName) {
		this.uploadError.add(new ErrorMsg(uploadError, fileName));
	}

	/**
	 * �ϴ���������ö��
	 * 
	 * @author Gaojt
	 * 
	 */
	public enum UploadError {
		TooLarger("�ϴ��ļ�̫�󣬳��������ļ���С"), NotAcceptFix("�ϴ��ļ����Ͳ��ԣ��������ϴ��ú�׺���ļ�"), NotHasFix(
				"�ϴ��ļ�û�к�׺�����������ϴ����ļ�");

		private final String errorMsg;

		UploadError(String errorMsg) {
			this.errorMsg = errorMsg;

		}

		public String getErrorMsg() {
			return this.errorMsg;
		}
	}

	/**
	 * �ϴ������¼
	 * 
	 * @author Gaojt
	 * 
	 */
	public class ErrorMsg {
		/**
		 * �ϴ�������ļ���
		 */
		private String fileName;
		/**
		 * ��������
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
	 * �ļ��ϴ���Ϣ�����
	 * 
	 * @author Gaojt
	 * 
	 */
	public class UploadMsg {
		private String serverPath;// �ϴ����������ļ�·��
		private String serverFileName;// �ϴ����������ļ�����
		private String webPath;// �ϴ��ļ����·��
		private String fileName;// �ϴ��ļ�ԭ����
		private String fileFix;// �ϴ��ļ���׺��

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
	 * ��ʽ���ļ�·����ȥ����\/���Ȳ��淶���ļ�·��
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
