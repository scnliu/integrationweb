package net.linybin7.core.web.fileupload;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;

/**
 * 文件下传工具类
 * 
 * 
 */
public final class FileUploadUtil {

	private FileUploadUtil() {

	}

	/**
	 * 获取参数值
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	public static String[] getParams(HttpServletRequest req, String name) {
		List<String> list = new ArrayList<String>();
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			// upload.setHeaderEncoding("GBK");
			List items = upload.parseRequest(req);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					if (name.equals(fieldName)) {
						list.add(item.getString("GBK"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list.toArray(new String[0]);
	}

	/**
	 * 获取参数值
	 * 
	 * @param req
	 * @param name
	 * @return
	 */
	public static String getParam(HttpServletRequest req, String name) {
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = upload.parseRequest(req);
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {
					String fieldName = item.getFieldName();
					if (name.equals(fieldName)) {
						return item.getString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 文件名编码
	 * 
	 * @param fileName
	 * @return
	 */
	public static String encodingFileName(String fileName) {
		String returnFileName = "";
		try {
			// returnFileName = URLEncoder.encode(fileName, "UTF-8");
			// returnFileName = StringUtils.replace(returnFileName, "+", "%20");
			// if (returnFileName.length() > 150) {
			returnFileName = new String(fileName.getBytes("GBK"), "ISO8859-1");

			returnFileName = StringUtils.replace(returnFileName, " ", "%20");
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnFileName;
	}

}
