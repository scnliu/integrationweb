package net.linybin7.pub.analysis.runner;

import org.apache.log4j.Logger;

import net.linybin7.pub.analysis.support.AnalysisConsole;
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
public class AnalysisRunner implements Runnable {
	private static final Logger logger = Logger.getLogger(AnalysisRunner.class);

	/**
	 * 线程运行状态 -1:初始化； 0：排队；1：执行中；2：完成/终止；3：拒绝
	 */
	public final static int STATE_INITIAL = 0;
	public final static int STATE_WAITING = 1;
	public final static int STATE_RUNNING = 2;
	// public final static int STATE_FINISHED = 3;
	public final static int STATE_REJECTED = 4;

	public final static int STATE_SUCCESS = 31;
	public final static int STATE_FAILURE = 32;

	private int state = STATE_INITIAL;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	private Owner owner;
	private RunnerProgress runnerProgress;
	private boolean running = false;

	public RunnerProgress getRunnerProgress() {
		return runnerProgress;
	}

	public void setRunnerProgress(RunnerProgress runnerProgress) {
		this.runnerProgress = runnerProgress;
	}

	public AnalysisRunner(Owner u) {
		this.owner = u;
	}

	public AnalysisRunner() {
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		try {
			Executor executer = newExecuter();
			if (executer == null) {

			} else {
				try {
					this.setState(STATE_RUNNING);

					executer.setOwner(owner);
					executer.setAnalysisParam(analysisParam);
					executer.beforeExecute(runnerProgress);
					executer.execute(runnerProgress);
					executer.afterExecute(runnerProgress);

					this.setState(STATE_SUCCESS);
				} catch (Exception e) {
					this.setState(STATE_FAILURE);
					e.printStackTrace();
					logger.error(e);
					this.runnerProgress.put("error", e.getMessage());
				}
			}
		} finally {
			analysisConsole.removeRunner(this.owner);
		}
	}

	private Executor newExecuter() {
		try {
			Class<?> executer = this.getExecuterClass();
			return (Executor) executer.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void checkRunstate() throws StopException {
		if (!isRunning()) {
			throw new StopException("用户终止线程!");
		}
	}

	class StopException extends Exception {
		private static final long serialVersionUID = 1L;

		public StopException(String e) {
			super(e);
		}
	}

	private AnalysisParam analysisParam;

	public AnalysisParam getAnalysisParam() {
		return analysisParam;
	}

	public void setAnalysisParam(AnalysisParam analysisParam) {
		this.analysisParam = analysisParam;
	}

	private Class<?> executerClass;

	public Class<?> getExecuterClass() {
		return executerClass;
	}

	public void setExecuterClass(Class<?> executerClass) {
		this.executerClass = executerClass;

	}

	private AnalysisConsole analysisConsole;

	public AnalysisConsole getAnalysisConsole() {
		return analysisConsole;
	}

	public void setAnalysisConsole(AnalysisConsole analysisConsole) {
		this.analysisConsole = analysisConsole;
	}

}
