package net.linybin7.pub.data.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.linybin7.common.util.PropertiesUtil;
import net.linybin7.pub.data.dao.CommonUploadDao;

import org.hibernate.mapping.Column;



/**
 * 
 * Title: 通用数据导入
 * Description:
 * Copyright: 2012 . All rights reserved.
 * Company: eshine
 * CreateDate:2012-10-15
 * 
 * @author LinYuBin
 * ----------------------------------------------
 * Date Mender Reason
 */
public class CommonUploadServiceImp implements CommonUploadService {

	private CommonUploadDao dao;

	public CommonUploadDao getDao() {
		return dao;
	}

	public void setDao(CommonUploadDao dao) {
		this.dao = dao;
	}
	@Override
	public void begin() throws Exception {
		dao.beginTransaction();
	}

	@Override
	public void end() throws Exception {
		dao.commit();
	}
	
	@Override
	public void rollback() {
		try {
			dao.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void preparedStatement(String insertSql) throws Exception{
		dao.preparedStatement(insertSql);
	}

	@Override
	public List<Column> getColumns(String fileName) {
		List<Column> list = new ArrayList<Column>();
		Properties p;
		if (fileName == null || fileName.length() == 0)
			return list;
		else
			p = PropertiesUtil.loadProperty(fileName);
		int count = Integer.parseInt(p.getProperty("data.length"));
		
		List<String>specialColumn=new ArrayList<String>();
		try{
			String specialCol2=p.getProperty("data.specialCol2");
			if(specialCol2!=null){
				String[] specials=specialCol2.split(",");		
				
				for(String cols:specials){
					if(cols.trim().length()>0)
						specialColumn.add(cols);				
				}
			}
		}catch(Exception e){
			
		}
		for (int i = 1; i <= count; i++) {
			try {
				String dataName = "col" + i;
				Column c = new Column();
				c.setName(p.getProperty(dataName + ".name"));
				c.setDefaultValue(p.getProperty(dataName + ".fileCol"));
				c.setComment(p.getProperty(dataName + ".comment"));
				c.setSqlType(p.getProperty(dataName + ".type"));
				c.setNullable(Boolean.parseBoolean(p.getProperty(dataName + ".nullable")));
				if(specialColumn.indexOf(c.getName())>=0){
					c.setUnique(true);
				}else{
					c.setUnique(false);
				}
				list.add(c);
			} catch (Exception e) {
			}
		}
		return list;
	}

	@Override
	public String getTableName(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return "";
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				return p.getProperty("data.tableName");
			} catch (Exception e) {
				return "";
			}
		}
	}

	@Override
	public String getTableComment(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return "";
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				return p.getProperty("data.tableComment");
			} catch (Exception e) {
				return "";
			}
		}
	}

	@Override
	public String getUploadDir(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return "";
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				return p.getProperty("data.serverDirectory");
			} catch (Exception e) {
				return "";
			}
		}
	}

	@Override
	public String getRowProcessor(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return "";
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				return p.getProperty("data.checker");
			} catch (Exception e) {
				return "";
			}
		}
	}

	@Override
	public boolean allowNullCol(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return false;// 默认不给导入
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				if (p.getProperty("data.allowNullCol").equals("true"))
					return true;
				else
					return false;
			} catch (Exception e) {
				return false;
			}
		}
	}

	@Override
	public String getDes(String fileName) {
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			return "";
		} else {
			try {
				p = PropertiesUtil.loadProperty(fileName);
				return p.getProperty("data.des");
			} catch (Exception e) {
				return "";
			}
		}
	}
	
	public Map<String,String>getDataProperty(String fileName) throws Exception{
		Map<String,String>map=new HashMap<String,String>();
		Properties p;
		if (fileName == null || fileName.length() == 0) {
			throw new Exception("没有配置文件");
		} else {
			p = PropertiesUtil.loadProperty(fileName);
			try {
				map.put("tableName", p.getProperty("data.tableName"));
			} catch (Exception e) {
			}
			try {
				map.put("tableComment", p.getProperty("data.tableComment"));
			} catch (Exception e) {
			}
			try {
				map.put("des", p.getProperty("data.des"));
			} catch (Exception e) {
			}
			try {
				map.put("allowNullCol", p.getProperty("data.allowNullCol"));				
			} catch (Exception e) {
			}
			try {
				map.put("checker", p.getProperty("data.checker"));				
			} catch (Exception e) {
			}
			try {
				map.put("globalProcessor", p.getProperty("data.globalProcessor"));				
			} catch (Exception e) {
			}
			try {
				map.put("serverDirectory", p.getProperty("data.serverDirectory"));				
			} catch (Exception e) {
			}
			try {
				map.put("specialCol", p.getProperty("data.specialCol"));				
			} catch (Exception e) {
			}
			try {
				map.put("specialCol2", p.getProperty("data.specialCol2"));				
			} catch (Exception e) {
			}
		}
		return map;
	}

	@Override
	public void clearTable(String tableName) {
		dao.clearTable(tableName);
	}

	@Override
	public int[] insert2DB(List<String> typeList, final List<Object[]> list) throws SQLException{
		return dao.insert2DB(typeList, list);
	}
}