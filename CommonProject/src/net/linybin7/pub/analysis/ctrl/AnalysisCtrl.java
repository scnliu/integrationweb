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
 * ���ſƼ�
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
	 * �������ҳ��
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
	 * �첽�������� �߳�
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

			// ���ؽ��
			JSONObject json = parseJSON(runner);
			rsp.getWriter().write(json.toString());

			logger.info("startAnalysis>>>��Ӧ�����" + json.toString());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * ��ֹ�����߳�
	 * 
	 * @param req
	 * @param rsp
	 * @param cmd
	 * @return <br>
	 *         TODO:��δ�ṩ֧��
	 */
	public ModelAndView stopAnalysis(HttpServletRequest req, HttpServletResponse rsp,
			AnalysisCmd cmd) {

		try {
			Visitor visitor = (Visitor) req.getSession().getAttribute(Constants.KEY_VISITOR);
			User u = visitor.getUser();

			AnalysisRunner runner = this.analysisService.stop(new Owner(u, "Analysis"));

			JSONObject json = new JSONObject();
			if (runner == null) {// �Ѿ���ֹ
				json.put("stopOK", 1);
			} else {// ��ֹʧ��
				json.put("stopOK", 0);
			}
			rsp.getWriter().write(json.toString());

			logger.info("startAnalysis>>>��Ӧ�����" + json.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return null;
	}

	/**
	 * �첽��ȡ�����߳̽���
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
			if (runner != null) {// ���ڷ�����
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
					json.put("state", -1); // ��û������
				} else {
					json.put("state", -2); // �Ѿ��з������
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
	 * ����JSON����
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
