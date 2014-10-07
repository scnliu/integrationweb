package net.linybin7.pub.init.ctrl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.db.SqlHelper;
import net.linybin7.common.tag.Msg;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.util.Constants;
import net.linybin7.core.util.DirUtil;
import net.linybin7.pub.data.bo.DataMgr;
import net.linybin7.pub.data.cmd.FileUploadCmd;
import net.linybin7.pub.init.cmd.DataMgrCmd;
import net.linybin7.pub.init.service.InitService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * 
 * <p>Title: 数据库库初始化</p>
 * <p>Description: </p>
 *
 * <p>Copyright: 2011 . All rights reserved.</p>
 * <p>Company: eshine</p>
 * <p>CreateDate:</p>
 * @author Linyubin 
 * <p>----------------------------------------------</p>
 * <p>Date				Mender				Reason</p>
 * 2011-6-2				Linyubin			修改分段方式
 * 2011-6-7				Linyubin			段末去空格
 */
public class InitCtrl extends MultiActionController{
	
	private static String SysName ="";
	
	InitService initService;
	
	public InitService getInitService() {
		return initService;
	}

	public void setInitService(InitService initService) {
		this.initService = initService;
	}
	
	/**
	 * @return the sysName
	 */
	public static String getSysName() {
		return SysName;
	}

	/**
	 * @param sysName the sysName to set
	 */
	public static void setSysName(String sysName) {
		SysName = sysName;
	}

	/**
	 * 查询表记录询
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView dbInitMain(HttpServletRequest req,HttpServletResponse rsp, DataMgrCmd cmd) {
		ModelAndView modelAndView = new ModelAndView("pub/init/dbInitMain");
		
		Visitor visitor = (Visitor)req.getSession().getAttribute(Constants.KEY_VISITOR);
		String orgId = visitor.getUser().getOrgId();
		int queryType = 1;//查询类型 （1：包含共用表；0：不包含共用表
		List tableList = initService.query(orgId==null?"-1":orgId,SysName,queryType);
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("tableList",tableList);
		modelAndView.addObject("size",tableList.size());
		return modelAndView;
	} 
	
	public ModelAndView forward(HttpServletRequest req,HttpServletResponse rsp){
		ModelAndView modelAndView = new ModelAndView("pub/init/dbInitResult");
		String haveCount = req.getParameter("haveCount");
		String haveMsg = req.getParameter("haveMsg");
		String addCount = req.getParameter("addCount");
		String addMsg = req.getParameter("addMsg");
		modelAndView.addObject("haveCount", haveCount);
		modelAndView.addObject("haveMsg", haveMsg);
		modelAndView.addObject("addCount", addCount);
		modelAndView.addObject("addMsg", addMsg);
		modelAndView.addObject("SysName", SysName);
		return modelAndView;
	}
	
	public void doInitDB(HttpServletRequest req,HttpServletResponse rsp,FileUploadCmd cmd){
		Map map = new HashMap();
		map.put("cmd", cmd);
		req.getSession().setAttribute("initDBInfo", map);
		Visitor visitor = (Visitor)req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		int haveCount=0;
		String haveMsg = "";
		int addCount=0;
		String addMsg = "";
		List<String> tableNames = new ArrayList<String>();
		String initType = req.getParameter("initType");
		String initType2 = req.getParameter("initType2");
		
		int queryType = 0;
		if(initType!=null&&initType.equals("checked")) queryType = 1;
		
		//初始化前删除表
		List<DataMgr> tables = initService.query(user.getOrgId(), SysName, queryType);
		
		initService.deleteTables(tables);
		
		String fileName = "";
		
		if(SqlHelper.getDBtype()==0){//oracle脚本
			fileName = "CreateDataBase.sql";
		}else if(SqlHelper.getDBtype()==5){//Mysql脚本
			fileName = "CreateDataBase.sql";;
		}
		
		if(initType!=null&&initType.equals("checked")){
			tableNames.add(fileName);
		}
		if(SysName!=null&&SysName.trim().length()>0&&
				initType2!=null&&initType2.equals("checked")){
			tableNames.add(SysName+"_"+fileName);
		}
		if(visitor!=null){
			String orgId = visitor.getUser().getOrgId();
			int size = 0;
			int cur = 0 ;
			int countColA = 0;
			int countColH = 0;
			int curTime = 0;
			double avgTime = 0;
			Date start = new Date();
			String toolName = "DATA";
			
			size = getTableNumber(orgId, tableNames);
			cmd.setContentLength(size);
			
			for (String tableName : tableNames) {
				if(tableName.startsWith(SysName)) toolName = SysName;
				
				List<String[]> list = readFile(orgId,tableName);
					for(int i=0;i<list.size();i++){
						cur++;
						
						String[] sql = list.get(i);
						String[] str = sql[1].split(";"+'\n');
						String tabCode=sql[0].toUpperCase().trim();
						String tabName="";
						for(int j=0;j<str.length;j++){
							String temp = "IS";
							if(str[j].indexOf("COMMENT ON TABLE")>=0){
								tabName = str[j].substring(str[j].indexOf(temp)+temp.length());
								tabName = tabName.replaceAll("'", "");
							}
						}
						int count = initService.isTab(tabCode);//是否存在数据库表
						if(count<=0)
							for(int j=0;j<str.length;j++){
								try{
									String sqlString=str[j];
									sqlString=sqlString.replace("\n", "");
									if(sqlString.trim().length()>0)
									initService.execute(str[j]);
								}catch(Exception e){
									System.out.println(e.getMessage()+"执行出错:"+str[j]);
									//e.printStackTrace();
								}
							}
						if(sql[2]!=null&&sql[2].equals("1")){//当生成表格时
							if(count<=0){
								addCount++;
								addMsg+="<td>"+tabCode+" </td>";
								countColA++;
							}else{
								haveCount++;
								haveMsg+="<td>"+tabCode+" </td>";
								countColH++;
							}
							
							if(countColA==8){
								countColA=0;
								addMsg+="</tr><tr>";
							}
							if(countColH==8){
								countColH=0;
								haveMsg+="</tr><tr>";
							}
						}
						DataMgr dataMgr = new DataMgr();
						dataMgr.setTabCode(tabCode);
						dataMgr.setTabName(tabName);
						dataMgr.setTools(toolName);
						dataMgr.setUserCode(visitor.getUser().getUserCode());
						dataMgr.setUserName(visitor.getUser().getUserName());
						dataMgr.setOrgId(orgId==null?"-1":orgId);
						dataMgr.setCreateTime(new Date());
						try{
							initService.saveOrUpdate(dataMgr);
						}catch(Exception e){
							e.printStackTrace();
						}
						Date end = new Date();
						Long time = end.getTime() - start.getTime();
						curTime = curTime + time.intValue();
						avgTime = (cur/(curTime*0.001));
						cmd.setReturnInfo("<div align='center' class='showInfo'>数据表总数:<span>"+size+"</span> 已初始化:<span>"+cur+" </span>剩余:<span>"+(size-cur)+" </span>平均速度:<span>"+doubleFormat(avgTime)+" </span>个/秒"+" 剩余时间:<span>"+doubleFormat((size-cur)/avgTime)+"</span>秒</div>");
						cmd.setUploadLength(cur);	
				}
			
			}
			
			
			
			map.put("haveCount", haveCount);
			map.put("haveMsg", haveMsg);
			map.put("addCount", addCount);
			map.put("addMsg", addMsg);
		}
		
		cmd.setUploadLength(cmd.getContentLength());
	}
	
	public void readProgress(HttpServletRequest req,HttpServletResponse rsp,FileUploadCmd cmd){
		Map g = (Map) req.getSession().getAttribute("initDBInfo");
		if(g!=null){
			try {
				Msg.writeObj(g, rsp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String doubleFormat(double value){
		DecimalFormat fmt=new DecimalFormat("####");
		return fmt.format(value);
	}
	/**
	 * 删除表记录
	 * @param req
	 * @param rsp
	 * @param user
	 * @return
	 */
	public ModelAndView delDB(HttpServletRequest req, HttpServletResponse rsp, DataMgrCmd cmd) {
		String[] codes = req.getParameterValues("ckbCode");
		//initService.delete(codes);
		ModelAndView modelAndView = new ModelAndView("pub/init/dbInitMain");
		Visitor visitor = (Visitor)req.getSession().getAttribute(Constants.KEY_VISITOR);
		String orgId = visitor.getUser().getOrgId();
		int queryType = 1;//查询类型 （1：包含共用表；0：不包含共用表
		List tableList = initService.query(orgId==null?"-1":orgId,SysName,queryType);
		modelAndView.addObject("cmd", cmd);
		modelAndView.addObject("tableList",tableList);
		modelAndView.addObject("size",tableList.size());
		modelAndView.addObject("MSG","删除成功！");
		return modelAndView;
	}
	
	public void updateTable(HttpServletRequest req, HttpServletResponse rsp){
		
		Visitor visitor = (Visitor)req.getSession().getAttribute(Constants.KEY_VISITOR);
		User u = visitor.getUser();
		String orgId = u.getOrgId();
		
		String tools = req.getParameter("tools");
		String tableName = req.getParameter("tableName");
		
		String fileName = "";
		List<String> tableNames = new ArrayList<String>();
		String msg = "更新完成！";
		try{
			//删除更新表
			initService.delete(tableName);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(SqlHelper.getDBtype()==0){//oracle脚本
			fileName = "CreateDataBase.sql";
		}else if(SqlHelper.getDBtype()==5){//Mysql脚本
			fileName = "CreateDataBase.sql";;
		}
		
		if(tools!=null&&tools.equals("DATA")){
			tableNames.add(fileName);
		}
		if(tools!=null&&tools.trim().length()>0){
			tableNames.add(tools+"_"+fileName);
		}
		
		try {
			List<String[]> list = readFile(orgId, tableNames.get(0));
			for (int i = 0; i < list.size(); i++) {

				String[] sql = list.get(i);
				String[] str = sql[1].split(";" + '\n');
				String tabCode = sql[0].toUpperCase().trim();
				if (tableName.equals(tabCode)) {//只执行更新表的脚本
					String tabName = "";
					for (int j = 0; j < str.length; j++) {
						String temp = "IS";
						if (str[j].indexOf("COMMENT ON TABLE") >= 0) {
							tabName = str[j].substring(str[j].indexOf(temp)
									+ temp.length());
							tabName = tabName.replaceAll("'", "");
						}
					}

					for (int j = 0; j < str.length; j++) {
						try {
							String sqlString = str[j];
							sqlString = sqlString.replace("\n", "");
							if (sqlString.trim().length() > 0)
								initService.execute(str[j]);
						} catch (Exception e) {
							System.out.println(e.getMessage() + "执行出错:"
									+ str[j]);
							msg = "脚本执行出错！";
							//e.printStackTrace();
						}
					}
					//更新管理表
					DataMgr dataMgr = new DataMgr();
					dataMgr.setTabCode(tabCode);
					dataMgr.setTabName(tabName);
					dataMgr.setTools(tools);
					dataMgr.setUserCode(visitor.getUser().getUserCode());
					dataMgr.setUserName(visitor.getUser().getUserName());
					dataMgr.setOrgId(orgId == null ? "-1" : orgId);
					dataMgr.setCreateTime(new Date());
					try {
						initService.saveOrUpdate(dataMgr);
					} catch (Exception e) {
						e.printStackTrace();
						msg = "更新出错！";
					}

				}

			}
			String tableType = "1";
			DataMgr dataMgr = initService.getDataMgrByUser(u, tableName, tableType);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = df.format(new Date());
			Map map = new HashMap();
			map.put("msg",msg);
			map.put("user",visitor.getUser().getUserName());
			map.put("time", date);
			
			Msg.writeObj(map, rsp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<String[]> readFile(String orgId, String fileName){
		List<String[]> list = new ArrayList<String[]>();
		
		String[] sql = null;
		String str ="";
		String filepath="";
		
		filepath="/WEB-INF/classes/config/"+fileName;
		
		File file = DirUtil.getContextPath(filepath);
		FileReader fr = null;
		BufferedReader br = null;
		int type = 0;
		String oldTableName = null;
		String newTableName = null;
		try{
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			boolean updateTable=false;
			while((str=br.readLine())!=null){
				if(str.indexOf("/*")>=0&&str.indexOf("Table:")>=0&&str.indexOf("*/")>=0){
					oldTableName=(str.substring(str.indexOf("Table:")+"Table:".length(),str.indexOf("*/"))).trim();
					if(orgId!=null)
						newTableName=orgId+"_"+oldTableName.trim();
					else
						newTableName=oldTableName.trim();
				}
				if(str.indexOf("/*")>=0&&str.indexOf("CTable:")>=0&&str.indexOf("*/")>=0){
					updateTable=true;
				}
				if(type==2&&sql==null){
					sql = new String[]{"","","1"};
				}
				if(str.indexOf("/*")>=0&&str.indexOf("=")>=0&&str.indexOf("*/")>=0){
					type++;
				}
				if(type==2&&sql!=null){
					str=str.trim();
					sql[1]+=str+'\n';
				}
				if(type==3&&sql!=null){
					sql[0]=newTableName;
					sql[1] = sql[1].replaceAll(oldTableName, newTableName.toUpperCase());
					if(updateTable){
						sql[2]="0";
						updateTable=false;
					}
					list.add(sql);
					sql=null;
					type=1;
				}
			}
//				sql[0]=newTableName;
//				sql[1] = sql[1].replaceAll(oldTableName, newTableName);
//				list.add(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(br!=null)
					br.close();
				if(fr!=null)
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
		
		
		return list;
	}
	
	public void getCount(HttpServletRequest req,HttpServletResponse rsp){
		String tabCode = req.getParameter("tabCode");
		int count = initService.getTabCount(tabCode);
		Map map = new HashMap();
		map.put("count",count);
		try {
			Msg.writeObj(map, rsp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int getTableNumber(String orgId,List<String> tableNames){
		int size = 0;
		for (String tableName : tableNames) {
			
			List<String[]> list = readFile(orgId,tableName);
			size += list.size();
		}
		return size;
	}
	
}
