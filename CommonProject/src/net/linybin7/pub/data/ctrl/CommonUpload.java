package net.linybin7.pub.data.ctrl;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.linybin7.common.tag.Msg;
import net.linybin7.common.tag.Option;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.util.Constants;
import net.linybin7.core.web.context.SessionManager;
import net.linybin7.core.web.util.SpringBeanFactory;
import net.linybin7.pub.data.bo.DataMgr;
import net.linybin7.pub.data.cmd.FileUploadCmd;
import net.linybin7.pub.data.service.CommonUploadGlobalProcess;
import net.linybin7.pub.data.service.CommonUploadRowProcess;
import net.linybin7.pub.data.service.CommonUploadService;
import net.linybin7.pub.data.util.FileHelper;
import net.linybin7.pub.data.util.Files;
import net.linybin7.pub.data.util.TextFileReader;
import net.linybin7.pub.init.service.InitService;
import net.linybin7.pub.tools.OrgTablePre;
import net.linybin7.pub.tools.TableNameHelper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.openxml4j.exceptions.InvalidOperationException;
import org.hibernate.mapping.Column;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Title:ͨ�����ݵ���
 * Description:�ı���ʽ�����ݵ������ݿ��
 * 
 * Copyright: 2012 . All rights reserved.
 * Company: eshine
 * CreateDate:2012-10-15
 * 
 * @author LinYuBin
 * Date	Mender	Reason
 * 2012-12-28	LinYuBin	�ϲ�txt��excel��������
 * 2012-12-28	LinYuBin	�޸������е��д������̣����������е�ȫ�ִ�������
 */
public class CommonUpload extends MultiActionController {
	public static String ROOTPATH;
	public static Map<String, FileUploadCmd> synMap = Collections
			.synchronizedMap(new HashMap<String, FileUploadCmd>());
	private InitService initService;
	private CommonUploadService service;

	public InitService getInitService() {
		return initService;
	}

	public void setInitService(InitService initService) {
		this.initService = initService;
	}

	public CommonUploadService getService() {
		return service;
	}

	public void setService(CommonUploadService service) {
		this.service = service;
	}
	boolean fileError=false;
	String fileSuc;

	public ModelAndView uploadMain(HttpServletRequest req, HttpServletResponse res,
			FileUploadCmd cmd) throws Exception {
		ModelAndView mav = new ModelAndView("pub/data/commonUpload");

		Option importFileType1 = new Option("1", "�滻");
		Option importFileType2 = new Option("2", "׷��");
		List<Option> importFileType = new ArrayList<Option>();
		importFileType.add(importFileType1);
		importFileType.add(importFileType2);
		mav.addObject("importFileType", importFileType);

		// �����ļ�
		String configFile = req.getParameter("configFile");
		List<Column> columns = service.getColumns(configFile);
		mav.addObject("columns", columns);
		mav.addObject("configFile", configFile);
		mav.addObject("tableName", service.getTableName(configFile));
		mav.addObject("tableComment", service.getTableComment(configFile));

		// �������ϵ��ļ��б�
		FileHelper helper = new FileHelper();
		String fileDir = service.getUploadDir(configFile);
		List<Files> list = helper.getFileInfo(fileDir);
		mav.addObject("fileList", list);

		HttpSession se = req.getSession();
		mav.addObject("jsession", se.getId());

		mav.addObject("sysName", cmd.getSysName());

		mav.addObject("des", service.getDes(configFile));

		String returnUrl = req.getParameter("returnUrl");
		mav.addObject("returnUrl", returnUrl);
		return mav;
	}

	private String getUploadRootPath(HttpServletRequest req, String sessionid) {
		ServletContext ctx = req.getSession().getServletContext();
		HttpSession session = SessionManager.getInstance().getSession(sessionid);
		Visitor visitor = (Visitor) session.getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String orgId = user.getOrgId();
		if (orgId == null || orgId.length() == 0)
			orgId = "admin";
		String separator = File.separator;
		String root = ctx.getRealPath("WEB-INF");
		String path = root + separator + orgId + separator;
		File dir = new File(path);
		if (!(dir.exists() && dir.isDirectory())) {
			dir.delete();
			dir.mkdirs();
		}
		return path;
	}

	@SuppressWarnings("unchecked")
	public void fileUpload(HttpServletRequest req, HttpServletResponse rsp, FileUploadCmd command)
			throws IOException {
		req.setCharacterEncoding("UTF-8");

		String sessionid = command.getSessionId();
		HttpSession session = SessionManager.getInstance().getSession(sessionid);
		Visitor visitor = (Visitor) session.getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String orgId = user.getOrgId();

		String configFile = req.getParameter("configFile");
		String tableName = service.getTableName(configFile);

		// ������Ϣ���浽map
		String key = "upload" + tableName + OrgTablePre.getPre(orgId);
		FileUploadCmd cmd = command;
		synMap.put(key, cmd);

		// �����Ŀ¼
		String savePaths = getUploadRootPath(req, sessionid);
		savePaths = savePaths + tableName + File.separator ;//+ time + File.separator;
		cmd.setParentPath(savePaths);

		String savePath = cmd.getParentPath();
		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		// upload.setHeaderEncoding("GBK");
		try {
			List<FileItem> fileList = upload.parseRequest(req);
			Iterator<FileItem> it = fileList.iterator();
			String name = "";
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					name = item.getName();
					// String ss=URLDecoder.decode(name, "UTF-8");
					// name=new String(name.getBytes("GBK"),"UTF-8");
					// long size = item.getSize();
					// String type = item.getContentType();
					if (name == null || name.trim().equals("")) {
						continue;
					}
					File saveFile = new File(savePath + name);
					// OutputStream op=new FileOutputStream(saveFile);
					try {
						item.write(saveFile);
						rsp.getWriter().write("success");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileUploadException e1) {
			e1.printStackTrace();
			try {
				rsp.getWriter().write("failure");
			} catch (IOException ee) {
			}
		}
	}

	// �����ļ�-�ļ���ȡ��ʽ��������ϴ�
	public void doStatClient(HttpServletRequest req, HttpServletResponse rsp, FileUploadCmd command)
			throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");

		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String orgId = user.getOrgId();

		String configFile = req.getParameter("configFile");
		String tableName = service.getTableName(configFile);

		String key = "upload" + tableName + OrgTablePre.getPre(orgId);
		FileUploadCmd g = synMap.get(key);// �����Ŀ¼��Ϣ
		String savePath = g.getParentPath();
		File f1 = new File(savePath);

		FileUploadCmd cmd = new FileUploadCmd();// ���������Ϣ
		req.getSession().setAttribute("upload" + tableName, cmd);
		cmd.setUploadLength(0);
		cmd.setContentLength(1);

		String sysName = command.getSysName();
		if (sysName == null)
			sysName = "";

		// �жϵ��뷽ʽ
		String importType = req.getParameter("importType");
		if (importType != null && importType.equals("1")) {
			String importTable = TableNameHelper.getTableName(user, sysName + tableName);
			service.clearTable(importTable);
		}

		File[] fs = f1.listFiles();
		processImport(cmd, sysName, fs, req, user, configFile);
		f1.delete();

		synMap.remove(key);
	}

	// �����ļ�-�ļ���ȡ��ʽ���������ϴ�
	public void doStatServer(HttpServletRequest req, HttpServletResponse rsp, FileUploadCmd command)
			throws UnsupportedEncodingException {
		req.setCharacterEncoding("UTF-8");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();

		String configFile = req.getParameter("configFile");
		String tableName = service.getTableName(configFile);

		String files = req.getParameter("file");
		files = java.net.URLDecoder.decode(files, "UTF-8");
		File[] f1 = new File[files.split(",").length];
		List<String> list = Arrays.asList(files.split(","));

		File ff = new File(FileHelper.getPath(service.getUploadDir(configFile)));
		int j = 0;
		if (ff.isDirectory()) {
			File[] fs = ff.listFiles();
			for (int i = 0; i < fs.length; i++) {
				if (list.contains(fs[i].getName())) {
					f1[j] = fs[i];
					j++;
				}
			}
		}
		FileUploadCmd cmd = new FileUploadCmd();
		req.getSession().setAttribute("upload" + tableName, cmd);
		cmd.setUploadLength(0);
		cmd.setContentLength(1);

		String sysName = command.getSysName();
		if (sysName == null)
			sysName = "";

		// �жϵ��뷽ʽ
		String importType = req.getParameter("importType");
		if (importType != null && importType.equals("1")) {
			String importTable = TableNameHelper.getTableName(user, sysName + tableName);
			service.clearTable(importTable);
		}

		processImport(cmd, sysName, f1, req, user, configFile);
	}

	// ���봦��
	private void processImport(FileUploadCmd cmd, String sysName, File[] fs,
			HttpServletRequest req, User user, String configFile) {
		fileError=false;
		fileSuc="";
		int sfNum = 0;
		try {
			Map<String,String>dataProperty=service.getDataProperty(configFile);
			
			String rowChecker = service.getRowProcessor(configFile);
			if (rowChecker != null && !rowChecker.equals("null"))
				try {
					rp = (CommonUploadRowProcess) SpringBeanFactory.getBean(rowChecker);
				} catch (Exception ex) {
					rp = null;
				}
			else
				rp = null;
			
			String globalChecker = dataProperty.get("globalProcessor");
			if (globalChecker != null && !globalChecker.equals("null"))
				try {
					gp = (CommonUploadGlobalProcess) SpringBeanFactory.getBean(globalChecker);
				} catch (Exception ex) {
					gp = null;
				}
			else
				gp = null;
			
			if (fs.length > 0) {
				// service.delete(orgId, tableName);
				boolean isLast = false;
				cmd.setStep(0);
				List<Column> column = service.getColumns(configFile);
				for (int i = 0; i < fs.length; i++) {
					if(fileError)break;//������һ���ļ�����ʧ��ʱ����ֹ��������
					String nameStr = fs[i].getName();
					cmd.setStep(cmd.getStep() - 1);
					if (i == fs.length - 1)
						isLast = true;
					if (TextFileReader.isSupportFileType(nameStr)) {

						sfNum += readFile(req, fs[i], user, isLast, sysName, column,dataProperty);
						fileSuc+=fs[i].getName()+";";
					} else {
						continue;
					}
				}
				// �������
				cmd.setStep(2);
			}

			if (fs.length == 0) {
				cmd.setErrorContent("�����ļ������Ҳ����ļ�");
				cmd.setError(true);
				cmd.setStep(3);
			}

			for (int i = 0; i < fs.length; i++) {
				if (fs[i].exists()) {
					fs[i].delete();
				}
			}
		} catch (Exception e) {
			cmd.setStep(3);
			cmd.setError(true);
			cmd.setErrorContent("�����ļ�����");
		}
	}
	
	private int readFile(HttpServletRequest request, File file, User user, boolean isLast,
			String sysName, List<Column> column,Map<String,String>dataProperty) {
		String tableName = dataProperty.get("tableName");
		FileUploadCmd cmd = (FileUploadCmd) request.getSession().getAttribute("upload" + tableName);
		String filename = file.getName();
		cmd.setFile(filename);

		String importTableName = TableNameHelper.getTableName(user, sysName + tableName);
		boolean isAllowNull = Boolean.valueOf(dataProperty.get("allowNullCol"));
		
		int numCount = 0;// ��ʱ������
		int totalCount =0 ;//
		TextFileReader fileReader=null;
		try {
			fileReader=new TextFileReader(file);
			service.begin();
			int fileLine = fileReader.getFileLine();
			
			cmd.setTableType(filename);
			cmd.setContentLength(fileLine);
			cmd.setStep(cmd.getStep() - 1);
						
			List<Object[]> updateArges = new ArrayList<Object[]>();

			String[] titles = fileReader.readLine();

			Map<String, String> paramter = getParamter(request, column); // ��ȡ�Զ��ļ�ͷ
			insertString = "";
			//��ȡ������
			String specialCol=dataProperty.get("specialCol");
			if(specialCol==null)specialCol="";
			List<Integer> checks = checkTitle(paramter, titles, column,specialCol);
			if (!isAllowNull && checks.contains(0)) {
				throw new Exception("���������ļ���ʽ���淶�����ݱ���������͵���ҳ��涨����ͬ���м䲻���п��У�");
			}
			if (!checkNullable(column)) {
				throw new Exception("���������ļ���ʽ���淶���м䲻���п��У�����û�б����ֶΣ�");
			}
			String insertSql = "insert into " + importTableName + insertString;
			service.preparedStatement(insertSql);
			
			while (fileReader.hasLine()) {
				String args[] = fileReader.readLine();
				if (args.length == 0)
					continue;
				numCount++;
				if (numCount == 200) {
					numCount = 0;
					cmd.setUploadLength(fileReader.getCurrentLine());
					service.insert2DB(typeList, updateArges);
					updateArges.clear();
				}
				Map<String,String>specialData=new HashMap<String,String>();
				Object[] objs = makeObject(checks, args,specialData);
				if (objs != null) {
					if (rp != null) {
						objs = rp.rowProcesss(dataCol, checks, objs, file, request, service,specialData);
					}
					updateArges.add(objs);
				}
			}
			service.insert2DB(typeList, updateArges);
			service.end();
			service.begin();
			if(gp!=null){
				gp.processData(column, checks, file, request, service);
			}
			service.end();
			cmd.setStop(isLast);// ������
			cmd.setUploadLength(fileLine);
			Thread.sleep(500);
			saveImportInfo(importTableName, user);
		} catch (InvalidOperationException e) {
			fileError=true;
			service.rollback();
			cmd.setError(true);
			cmd.setStep(3);
			cmd.setUploadLength(cmd.getContentLength());
			String fileInfo="";
			if(fileSuc!=null &&fileSuc.length()>0){
				fileInfo="����ɹ����ļ���"+fileSuc;
			}
			cmd.setErrorContent(fileInfo+"�ļ�:"+file.getName()+"��������ļ��������⣬����Excel���ļ��������Ժ������µ��룡");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
		} catch (DataIntegrityViolationException ex) {
			fileError=true;
			service.rollback();
			cmd.setError(true);
			cmd.setUploadLength(cmd.getContentLength());
			cmd.setStep(3);
			String fileInfo="";
			if(fileSuc!=null &&fileSuc.length()>0){
				fileInfo="����ɹ����ļ���"+fileSuc;
			}
			cmd.setErrorContent(fileInfo+"�ļ�:"+file.getName()+" ���ݵ����쳣����������ͬ�����ݻ��������쳣������.�쳣��Ϣ��"+ex.getMessage());
			ex.printStackTrace();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
		} catch (Exception e) {
			fileError=true;
			service.rollback();
			cmd.setError(true);
			cmd.setStep(3);
			cmd.setUploadLength(cmd.getContentLength());
			e.printStackTrace();
			String fileInfo="";
			if(fileSuc!=null &&fileSuc.length()>0){
				fileInfo="����ɹ����ļ���"+fileSuc;
			}
			cmd.setErrorContent(fileInfo+"�ļ�:"+file.getName()+" ��������쳣��Ϣ:"+e.toString());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
			}
		} finally {
			if(fileReader!=null)
				fileReader.close();
		}
		return totalCount;
	}

	/**
	 * 
	 * @author LinYuBin 2012-10-16����09:31:52
	 * @return <�ļ����������ݿ�����>
	 */
	private Map<String, String> getParamter(HttpServletRequest request, List<Column> orgColumn) {
		Map<String, String> titleMap = new LinkedHashMap<String, String>();
		for (int i = 0; i < orgColumn.size(); i++) {
			String columnName = (orgColumn.get(i)).getName();
			try {
				String tmp = request.getParameter(columnName);
				if (tmp != null) {
					String column = java.net.URLDecoder.decode(tmp, "UTF-8");
					column = column.toUpperCase();
					titleMap.put(column, columnName);
				} else {
					titleMap.put(columnName, columnName);
				}
			} catch (Exception e) {
			}
		}
		return titleMap;
	}

	private String insertString;

	private CommonUploadRowProcess rp;
	
	private CommonUploadGlobalProcess gp;
	
	private List<Column>dataCol;
	
	private List<Column>specCol;

	private List<String>typeList;
	/**
	 * �����м��
	 * 
	 * @author LinYuBin 2012-10-16����09:36:51
	 * @return	List:1���ݿ���	2:������		0��Ч��
	 */
	private List<Integer> checkTitle(Map<String, String> titleMap, String[] titles,
			List<Column> orgColumn,String specialCol) {
		
		List<Integer> checkStat = new ArrayList<Integer>();
		typeList = new ArrayList<String>();
		dataCol  = new ArrayList<Column>();
		specCol  = new ArrayList<Column>();
		// ������Ϣ�ŵ�map����
		Map<String, Column> columnInfo = new HashMap<String, Column>();
		for (Column c : orgColumn) {
			columnInfo.put(c.getName(), c);
		}

		String insertSql = "";
		String values = "";
		// ����ļ�ͷ
		for (int i = 0; i < titles.length; i++) {
			String fileColName = titles[i].trim().toUpperCase();
			if (titleMap.get(fileColName) != null) {
				String colName = titleMap.get(fileColName);
				Column tmpCol=columnInfo.get(colName);
				if(!tmpCol.isUnique()){
					insertSql += colName + ",";
					values += "?,";
					dataCol.add(tmpCol);
					checkStat.add(1);
					typeList.add(columnInfo.get(colName).getSqlType());
				}else{
					specCol.add(tmpCol);
					checkStat.add(2);
				}
			} else {
				checkStat.add(0);
			}
		}
		// ����������(�����������ļ��У�����Ҫ�������)
		String[] cols=specialCol.split(",");
		for(String col:cols){
			if(col.trim().length()>0){
				try{
					Column column=new Column();
					String[]nameType=col.split(":");
					column.setName(nameType[0]);
					column.setSqlType(nameType[1]);
					column.setUnique(false);
					column.setNullable(true);
					
					dataCol.add(column);
					typeList.add(nameType[1]);
					checkStat.add(1);
	
					insertSql += nameType[0] + ",";
					values += "?,";
				}catch(Exception e){
				}
			}
		}
		
		if (insertSql.length() != 0) {
			insertString += "(" + insertSql.substring(0, insertSql.length() - 1) + ")values("
					+ values.substring(0, values.length() - 1) + ")";
		}
		return checkStat;
	}

	/**
	 * ����Ƿ��зǿ��ֶ�
	 * 
	 * @author LinYuBin 2012-10-16����09:37:11
	 * @return true���ͨ��
	 */
	private boolean checkNullable(List<Column> orgColumn) {
		boolean isCheck = true;
		for (Column c : orgColumn) {
			if (!c.isNullable()) {
				String colName = c.getName();
				if (insertString.indexOf(colName) < 0)
					isCheck = false;
			}
		}
		return isCheck;
	}

	private Object[] makeObject(List<Integer> check, String fileLine[],Map<String,String>specialData)
			throws Exception {
		Object[] objs = new Object[check.size()];
		// ���е����ݺ���������һ��
		fileLine = Arrays.copyOf(fileLine, check.size());
		int cnt = 0;
		int specCnt = 0;
		for (int i = 0; i < fileLine.length; i++) {
			if (check.get(i)==1) {
				Column col = dataCol.get(cnt);
				String type = col.getSqlType();
				if(!col.isUnique()){
					try {
						if (type.indexOf("date") >= 0) {//excel�ļ��޷��涨ʱ���ʽ
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdf1 = new SimpleDateFormat(type.split("format:")[1]);
							try{
								objs[cnt] = sdf1.parse(fileLine[i]);
							}catch(Exception e){
								objs[cnt] = sdf.parse(fileLine[i]);
							}
						} else {
							if(fileLine[i] != null && (type.indexOf("int") >= 0 || type.indexOf("long") >= 0 )
									&& fileLine[i].matches("[0-9]*.0")){//�����ַ���ȥС����
								objs[cnt] = fileLine[i].replace(".0", "");
							}else{
								objs[cnt] = fileLine[i];
							}
							
						}
					} catch (Exception e) {
						objs[cnt] = null;// �������
						// e.printStackTrace();
					}
					cnt++;
				}
			}else if(check.get(i)==2){
				Column col = specCol.get(specCnt);
				specialData.put(col.getName(), fileLine[i]);
			}
		}
		Object[] results = Arrays.copyOf(objs, cnt);
		return results;
	}

	private void saveImportInfo(String tableName, User user) {
		DataMgr dataMgr = new DataMgr();
		dataMgr.setTabCode(tableName);
		dataMgr.setOrgId(user.getOrgId());
		dataMgr.setUserCode2(user.getUserCode());
		dataMgr.setUserName2(user.getUserName());
		dataMgr.setImpotTime(new Date());
		try {
			initService.saveOrUpdate(dataMgr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * ��ȡ�ļ��������
	 */
	public void readProgress(HttpServletRequest req, HttpServletResponse rsp, FileUploadCmd command) {
		try {
			FileUploadCmd g = (FileUploadCmd) req.getSession().getAttribute(
					"upload" + command.getTableName());
			g.setEdate(new Date());
			Msg.writeObj(g, rsp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {

		}
	}

	Long getLong(String num) {
		long number = 0;
		if (!"-".equals(num)) {
			number = Double.valueOf(num).longValue();
		}
		return number;
	}

	Float getFloat(String num) {
		float number = 0;
		if (!"-".equals(num)) {
			number = Double.valueOf(num).floatValue();
		}
		return number;
	}
}