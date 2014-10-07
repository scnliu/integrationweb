package net.linybin7.pub.analysis.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import net.linybin7.core.frame.bo.User;
import net.linybin7.core.frame.bo.Visitor;
import net.linybin7.core.util.Constants;
import net.linybin7.pub.analysis.cmd.AnalysisCmd;
import net.linybin7.pub.analysis.runner.AnalysisRunner;
import net.linybin7.pub.analysis.service.AnalysisService;
import net.linybin7.pub.analysis.support.AnalysisParam;
import net.linybin7.pub.analysis.support.Executor;
import net.linybin7.pub.analysis.support.Owner;
import net.linybin7.pub.analysis.support.RunnerProgress;

/**
 * 逸信科技
 * 
 * @author WuLinbin
 * 
 */

public class AnalysisCtrl extends MultiActionController {
	private static final Logger logger = Logger.getLogger(AnalysisCtrl.class);

	private AnalysisService analysisService;

	public AnalysisService getAnalysisService() {
		return analysisService;
	}

	public void setAnalysisService(AnalysisService analysisService) {
		this.analysisService = analysisService;
	}

	/**
	 * 进入分析页面
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */

	public ModelAndView analysis(HttpServletRequest req, HttpServletResponse rsp, AnalysisCmd cmd) {
		ModelAndView mav = new ModelAndView("pub/analysis/AnalysisMain");
		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User u = visitor.getUser();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return mav;
	}

	public ModelAndView main(HttpServletRequest req, HttpServletResponse rsp, AnalysisCmd cmd) {
		return this.analysis(req, rsp, cmd);
	}

	/**
	 * 异步启动分析 线程
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView startAnalysis(HttpServletRequest req, HttpServletResponse rsp,
			AnalysisCmd cmd) {
		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User u = visitor.getUser();

			AnalysisParam param = new AnalysisParam();
			param.put("cmd", cmd);

			AnalysisRunner runner = this.analysisService.start(new Owner(u, "Analysis"),
					Executor.class, param);

			// 返回结果
			JSONObject json = parseJSON(runner);
			rsp.getWriter().write(json.toString());

			logger.info("startAnalysis>>>响应结果：" + json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * 终止分析线程
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return <br>
	 *         TODO:尚未提供支持
	 */
	public ModelAndView stopAnalysis(HttpServletRequest req, HttpServletResponse rsp,
			AnalysisCmd cmd) {

		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User u = visitor.getUser();

			AnalysisRunner runner = this.analysisService.stop(new Owner(u, "Analysis"));

			JSONObject json = new JSONObject();
			if (runner == null) {// 已经终止
				json.put("stopOK", 1);
			} else {// 终止失败
				json.put("stopOK", 0);
			}
			rsp.getWriter().write(json.toString());

			logger.info("startAnalysis>>>响应结果：" + json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * 异步读取分析线程进度
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return
	 */
	public ModelAndView progress(HttpServletRequest req, HttpServletResponse rsp, AnalysisCmd cmd) {

		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User u = visitor.getUser();

			AnalysisRunner runner = this.analysisService
					.getAnalysisRunner(new Owner(u, "Analysis"));

			JSONObject json = parseJSON(runner);
			if (runner != null) {// 正在分析中
				int state = runner.getState();
				if (AnalysisRunner.STATE_SUCCESS == state) {
					json.put("state", -5); // 
				} else if (AnalysisRunner.STATE_FAILURE == state) {
					json.put("state", -4);
				} else {
					json.put("state", -3);
				}
			} else {
				// TODO
				if (true) {
					json.put("state", -1); // 还没分析过
				} else {
					json.put("state", -2); // 已经有分析结果
				}
			}

			rsp.getWriter().write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * 构造JSON对象
	 * 
	 * @param runner
	 * @return
	 */
	private JSONObject parseJSON(AnalysisRunner runner) {

		JSONObject json = new JSONObject();
		if (runner == null || runner.getRunnerProgress() == null) {
			return json;
		}

		RunnerProgress progress = runner.getRunnerProgress();

		Boolean determinate = progress.isDeterminate();
		Double consumeTime = new Double(progress.getConsumeTime());
		Integer donenum = progress.getDonenum();
		Integer maximum = progress.getMaximum();
		String message = progress.getMessage();
		Float percent = progress.getPercent();
		Double remainingTime = new Double(progress.getRemainingTime());
		Double speed = new Double(progress.getSpeed());
		Date startTime = progress.getStartTime();
		try {
			json.put("determinate", determinate);
			json.put("consumeTime", consumeTime);
			json.put("donenum", donenum);
			json.put("maximum", maximum);
			json.put("message", message);
			json.put("percent", percent * 100 + "%");
			json.put("remainingTime", remainingTime);
			json.put("speed", speed);
			json.put("startTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime));
		} catch (Exception e) {
		}
		return json;
	}

}
