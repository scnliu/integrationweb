package net.linybin7.is.weibo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.linybin7.common.database.CommonDao;
import net.linybin7.common.db.SqlHelper;
import net.linybin7.common.tag.Pager;
import net.linybin7.is.weibo.bo.WeiboTrend;
import net.linybin7.is.weibo.cmd.PageCmd;

import org.springframework.jdbc.core.RowCallbackHandler;

import weibo4j.model.Trend;


/**
 * Title:
 * Description: 
 *
 * Copyright: 2013 . All rights reserved.
 * Company: eshine
 * CreateDate:2013-4-7
 * @author LinYuBin 
 * ----------------------------------------------
 * Date			Mender			Reason
 */
public class WeiboTrendsServiceImpl implements WeiboTrendsService {
	
	private String sysName="T1";
	private String accessTable="WEIBOUSER";
	private String weiboTable="TRENDS";
	private String weiboTableH="TRENDSHOUR";
	private String weiboTableD="TRENDSDAYLY";
	private String weiboTableW="TRENDSWEEK";
	private Set<Integer> trends=null;
	private CommonDao dao;
	
	@Override
	public void saveAccess(String code,String accesscode) {
		try{
			String tableName=sysName+accessTable;
			String delSql="delete from "+tableName;
			dao.getJdbc().execute(delSql);
			String sql="insert into "+tableName+"(USERCODE,STARTTIME,SECRET,ACCESSTOKEN,EXPIRED)values(?,?,?,?,?)";
			dao.getJdbc().update(sql, new Object[]{"admin",new Date(),code,accesscode,1});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//0:从未授权过	1：正常状态	2：accesscode过期
	public int isExistAccess(){
		String tableName=sysName+accessTable;
		String sql="select EXPIRED,"+SqlHelper.getDialect().toDateTimeChar("STARTTIME")+" STARTTIMESTR from "+tableName;
		List<Map<String,Object>>list=dao.getJdbc().queryForList(sql);
		if(list.size()>0){
			Map<String,Object>map=list.get(0);
			try{
				String dateStr=map.get("STARTTIMESTR").toString();
				int expired=Integer.valueOf(map.get("EXPIRED").toString());
				
				//获取过期时间
				Calendar cal=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime=sdf.parse(dateStr);
				cal.setTime(startTime);
				cal.add(Calendar.DATE, expired);
				startTime=cal.getTime();
				
				Date now=new Date();
				if(now.before(startTime)){
					//初始化微博列表
					initTrends();
					return 1;
				}
				else return 2;
			}catch(Exception e){
				return 2;
			}
		}else return 0;
	}
	
	//获取访问码
	public String getAccessCode(){
		String code="";
		String tableName=sysName+accessTable;
		String sql="select * from "+tableName;
		List<Map<String,Object>>list=dao.getJdbc().queryForList(sql);
		if(list.size()>0){
			Map<String,Object>map=list.get(0);
			code=map.get("ACCESSTOKEN").toString();
		}
		return code;
	}
	
	//获取过期时间
	public Date getExpiredTime(){		
		String tableName=sysName+accessTable;
		String sql="select EXPIRED,"+SqlHelper.getDialect().toDateTimeChar("STARTTIME")+" STARTTIMESTR from "+tableName;
		List<Map<String,Object>>list=dao.getJdbc().queryForList(sql);
		if(list.size()>0){
			Map<String,Object>map=list.get(0);
			try{
				String dateStr=map.get("STARTTIMESTR").toString();
				int expired=Integer.valueOf(map.get("EXPIRED").toString());
				
				//计算过期时间
				Calendar cal=Calendar.getInstance();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime=sdf.parse(dateStr);
				cal.setTime(startTime);
				cal.add(Calendar.DATE, expired);
				return cal.getTime();
			}catch(Exception e){
				return null;
			}
		}else return null;
	}
	
	//保存话题列表
	public int[] saveTrends(final Trend[] ts,final Date date,int type){
		String tableName=sysName+weiboTable;
		String sql="insert into "+tableName+"(ID,TRENDSNAME,STARTTIME)values(?,?,?)";
		
		int[] result=new int[]{};
		for(int i=0;i<ts.length;i++){
			Trend trend=ts[i];
			try{
				if(!trends.add(trend.getQuery().hashCode()))continue;
				dao.getJdbc().update(sql,new Object[]{trend.getQuery().hashCode(),trend.getName(),
						date});
			}catch(Exception e){
				System.out.println(trend.getQuery()+""+date);
			}
		}
		/**
		dao.getJdbc().batchUpdate(sql, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Trend trend=ts[i];
				
				if(!trends.add(trend.getQuery().hashCode()))return;
				ps.setInt(1, trend.getQuery().hashCode());
				ps.setString(2, trend.getName());
				ps.setTimestamp(3, new Timestamp(date.getTime()));
				
			}
			@Override
			public int getBatchSize() {
				return ts.length;
			}
		});
		*/
		String cntTable=sysName;
		if(type==1){
			cntTable+="TRENDSHOUR";
		}else if(type==2){
			cntTable+="TRENDSDAYLY";
		}else{
			cntTable+="TRENDSWEEK";
		}
		String insert2Delta="insert into "+cntTable+"(ID,STATTIME,HIT,DELTA)values(?,?,?,?)";
		for(Trend t:ts){
			try{
				dao.getJdbc().update(insert2Delta,new Object[]{t.getQuery().hashCode(),
						date,t.getAmount(),t.getDelta()});
			}catch(Exception e){
				
			}
		}
		return result;
	}
	
	public boolean isTrendsExist(String trendsName) {
		boolean isExist=false;
		return isExist; 
	}
	
	public void initTrends(){
		if(trends==null){
			trends=new HashSet<Integer>();
			String tableName=sysName+weiboTable;
			String sql="select * from "+tableName;
			dao.getJdbc().query(sql, new RowCallbackHandler(){

				@Override
				public void processRow(ResultSet rs) throws SQLException {
					int id=rs.getInt("ID");
					trends.add(id);
				}
			});
		}
	}
	
	public List<WeiboTrend> queryTrend(Pager p,PageCmd cmd){
		final List<WeiboTrend>list=new ArrayList<WeiboTrend>();;
		String tableName=sysName+weiboTable;
		String sql4Count="select count(*) from "+tableName;
		int total=dao.getJdbc().queryForInt(sql4Count);
		String tableH=sysName+weiboTableH;
		String tableD=sysName+weiboTableD;
		String tableW=sysName+weiboTableW;
		String sql="select H.COUNTH,D.COUNTD,W.COUNTW,T.TRENDSNAME,T.ID,T.STARTTIME from "+tableName+" T "
			+" left join (select ID,count(*) COUNTH from "+tableH+" group by ID) H on T.ID=H.ID "
			+" left join (select ID,count(*) COUNTD from "+tableD+" group by ID) D on T.ID=D.ID "
			+" left join (select ID,count(*) COUNTW from "+tableW+" group by ID) W on T.ID=W.ID ";
			
		p.setTotalRow(total);
		if(cmd.isPage()){
			sql=SqlHelper.getDialect().getPaginSQL(sql, p.getCurrentpage(), p.getPageSize());
		}
		dao.getJdbc().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				WeiboTrend w=new WeiboTrend();
				w.setQueryName(rs.getString("TRENDSNAME"));
				w.setHouCout(rs.getInt("COUNTH"));
				w.setDayCount(rs.getInt("COUNTD"));
				w.setWeekCount(rs.getInt("COUNTW"));
				w.setStartTime(new Date(rs.getTimestamp("STARTTIME").getTime()));
				w.setId(rs.getLong("ID"));
				
				list.add(w);
			}
		});
		
		return list;
	}

	public CommonDao getDao() {
		return dao;
	}

	public void setDao(CommonDao dao) {
		this.dao = dao;
	}
}
