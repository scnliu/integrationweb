package net.linybin7.core.frame.individuation.ctrl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.linybin7.core.frame.bo.Individuation;
import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.frame.individuation.service.IndividuationService;
import net.linybin7.core.frame.user.cmd.UserCmd;
import net.linybin7.core.util.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


public class IndividuationCtrl extends MultiActionController {

	private IndividuationService iService;
	
	public IndividuationService getiService() {
		return iService;
	}

	public void setiService(IndividuationService iService) {
		this.iService = iService;
	}

	public ModelAndView index(HttpServletRequest req, HttpServletResponse rsp, Object cmd) {
		ModelAndView mav = new ModelAndView("core/individuation/Theme");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		Map<String,String> map = new HashMap<String, String>();
		List<Individuation> ids = iService.getAll(user.getUserCode());
		for(Individuation id:ids){
			map.put(id.getSetCode(), id.getSetting());
		}
		mav.addObject("map", map);
		return mav;
	}
	
	public void editTheme(HttpServletRequest req, HttpServletResponse rsp, UserCmd cmd) {
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User user = visitor.getUser();
		String bg = req.getParameter("background");
		iService.editTheme(user, "topic", bg);
	}
	
	public void setDefault(HttpServletRequest req, HttpServletResponse rsp, Object cmd) throws Exception{
		String kind = req.getParameter("keys");
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User u = visitor.getUser();
		Map<String,String> map = iService.setDefault(u.getUserCode(), kind);
		Iterator<String> it=map.keySet().iterator();
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		while(it.hasNext()){
			JSONObject jo=new JSONObject();
			String next=it.next();
			jo.put("setcode", next);
			jo.put("setting", map.get(next));
			array.put(jo);
		}
		obj.put("values", array);
		rsp.setContentType("application/x-www-form-urlencoded; charset=GBK");
		rsp.getWriter().write(obj.toString());
	}
	
	public void update(HttpServletRequest req, HttpServletResponse rsp, Object cmd) throws Exception{
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User u = visitor.getUser();
		String params = req.getParameter("params");
		String kind = req.getParameter("kind");
		params=java.net.URLDecoder.decode(params, "UTF-8");
		iService.update(u,params,kind);
	}
	
	public void resetAll(HttpServletRequest req, HttpServletResponse rsp, Object cmd) throws Exception{
		Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
		User u = visitor.getUser();
		iService.resetAll(u);
	}
	
}
