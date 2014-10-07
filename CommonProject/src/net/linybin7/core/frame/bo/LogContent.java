package net.linybin7.core.frame.bo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.linybin7.core.util.Constants;

public class LogContent{
	private String levl;//等级		I:INFO	E:ERROR	W:WARN
	private String userCode;// 用户编号
	private Date operateTime;// 操作时间
	
	private String addr;// IP地址
	private String moudle;// 操作模块
	private String msg;// 信息
	
	public String getOperateTimeStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(operateTime);
	}

	public void setMessage(String levl,String model,String msg,HttpServletRequest request,Exception e) {
		Visitor visitor = (Visitor) request.getSession().getAttribute(Constants.KEY_VISITOR);
		if(visitor!=null){
			User user = visitor.getUser();
			this.userCode=user.getUserCode();
		}else{
			this.userCode="";
		}
		
		if(request!=null)
			this.addr=getRemortIP(request);
		this.operateTime=new Date();
		this.levl=levl;
		this.moudle=model;
		String errorMsg="";
		if(e!=null){
			errorMsg=e.getStackTrace()[0].getClassName() + "."
			+ e.getStackTrace()[0].getMethodName() + "(" + e.getStackTrace()[0].getLineNumber()
			+ ")"+e.getMessage();
		}
		this.msg=msg+errorMsg;
	}

	protected String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	

	public String getLevl() {
		return levl;
	}

	public void setLevl(String levl) {
		this.levl = levl;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}