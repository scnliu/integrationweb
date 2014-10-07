package net.linybin7.is.weibo.ctrl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.common.tag.Pager;
import net.linybin7.is.weibo.bo.WeiboTrend;
import net.linybin7.is.weibo.cmd.PageCmd;
import net.linybin7.is.weibo.service.WeiboTrendsService;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import weibo4j.Oauth;
import weibo4j.Trend;
import weibo4j.http.AccessToken;
import weibo4j.model.Trends;
import weibo4j.model.WeiboException;

/**
 * Title:
 * Description: 
 *
 * Copyright: 2013 . All rights reserved.
 * Company: eshine
 * CreateDate:2013-3-30
 * @author LinYuBin 
 * ----------------------------------------------
 * Date			Mender			Reason
 */
public class WeiboTrendsCtrl extends MultiActionController {
	
	private WeiboTrendsService service;
	
	public ModelAndView main(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView("is/weibo/main");
		Oauth oauth = new Oauth();
		String oauthUrl="";
		try {
			oauthUrl=oauth.authorize("code","");
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("oauthUrl",oauthUrl);
		
		int isExist=service.isExistAccess();
		if(isExist==1){
			Date date=service.getExpiredTime();
			mav.addObject("expireDate",date);
		}
		mav.addObject("isExist",isExist);
		
		return mav;
	}
	
	//新浪微博回调后执行的方法，保存新浪微博访问码
	public ModelAndView auth(HttpServletRequest req, HttpServletResponse res){
		ModelAndView mav = new ModelAndView("is/weibo/auth");
		String code=req.getParameter("code");
		mav.addObject("code",code);
		String accessCode="";

		Oauth oauth = new Oauth();
		try {
			AccessToken token=oauth.getAccessTokenByCode(code);
			accessCode=token.getAccessToken();
			service.saveAccess(code,accessCode);
		}catch(Exception e){
			e.printStackTrace();
		}
		mav.addObject("accessCode",accessCode);		
		//System.out.println(code);		
		return mav;
	}
	
	
	//获取话题
	public void getData(HttpServletRequest req, HttpServletResponse rsp){
		String requesssCode=service.getAccessCode();
		try {
			Trend tm = new Trend();
			tm.client.setToken(requesssCode);
			List<Trends> trends = null;
			trends = tm.getTrendsHourly();
			for(Trends ts : trends){
				service.saveTrends(ts.getTrends(), ts.getAsOf(),1);
			}
			trends = tm.getTrendsDaily();
			for(Trends ts : trends){
				service.saveTrends(ts.getTrends(), ts.getAsOf(),2);
			}
			trends = tm.getTrendsWeekly();
			for(Trends ts : trends){
				service.saveTrends(ts.getTrends(), ts.getAsOf(),3);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}
	
	public ModelAndView list(HttpServletRequest req, HttpServletResponse rsp,PageCmd cmd){
		ModelAndView mav = new ModelAndView("is/weibo/list");
		Pager p=new Pager();
		p.setPageSize(15);
		p.setPageMethod(cmd.getPageMethod());
		p.setCurrentpage(cmd.getCurrentPage());
		List<WeiboTrend>list=service.queryTrend(p, cmd);
		mav.addObject("cmd",cmd);
		mav.addObject("pager",p);
		mav.addObject("list",list);
		return mav;
	}

	public WeiboTrendsService getService() {
		return service;
	}

	public void setService(WeiboTrendsService service) {
		this.service = service;
	}
}