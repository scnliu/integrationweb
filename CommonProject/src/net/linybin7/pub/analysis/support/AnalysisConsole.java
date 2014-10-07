package net.linybin7.pub.analysis.support;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import net.linybin7.pub.analysis.pool.AnalysisThreadPoolExecutor;
import net.linybin7.pub.analysis.runner.AnalysisRunner;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-12-����08:18:12 <br>
 * @description <br>
 *              TODO
 **/
public class AnalysisConsole {
	private static final Logger logger = Logger.getLogger(AnalysisConsole.class);
	private AnalysisThreadPoolExecutor threadPoolExecutor;

	protected void initial() {
		int corePoolSize = 5;
		int maxPoolSize = 5;
		int maxQueueSize = 50;

		this.threadPoolExecutor = new AnalysisThreadPoolExecutor(corePoolSize, maxPoolSize,
				maxQueueSize);
		this.threadPoolExecutor.resume();
	}

	public AnalysisConsole() {
		super();
		this.initial();
	}

	private final Map<String, AnalysisRunner> _runnerMap = new HashMap<String, AnalysisRunner>();

	private synchronized AnalysisRunner putRunner(Owner owner, AnalysisRunner thread) {
		String groupId = owner.getExecuteGroupId();
		AnalysisRunner runner = _runnerMap.put(groupId, thread);

		logger.info("�����̣߳�" + groupId + " :: " + _runnerMap);
		return runner;
	}

	public synchronized AnalysisRunner removeRunner(Owner owner) {
		String groupId = owner.getExecuteGroupId();
		AnalysisRunner runner = _runnerMap.remove(groupId);

		logger.info("�Ƴ��̣߳�" + groupId + " :: " + _runnerMap);
		return runner;
	}

	private synchronized AnalysisRunner getRunner(Owner owner) {
		String groupId = owner.getExecuteGroupId();
		return _runnerMap.get(groupId);
	}

	public AnalysisRunner start(Owner owner, Class<?> executerClass, AnalysisParam param) {

		AnalysisRunner runner = this.getRunner(owner);
		if (runner == null) {
			AnalysisRunner newRunner = new AnalysisRunner();
			this.putRunner(owner, newRunner);
			runner = newRunner;

			runner.setOwner(owner);
			runner.setAnalysisParam(param);
			runner.setExecuterClass(executerClass);
			runner.setRunnerProgress(new RunnerProgress());
			runner.setAnalysisConsole(this);
		}

		logger.info("�̣߳�" + owner.getExecuteGroupId() + "����״̬��" + runner.getState());
		if (runner.getState() == AnalysisRunner.STATE_INITIAL
				|| runner.getState() == AnalysisRunner.STATE_REJECTED)
			execute(runner);

		return runner;
	}

	public AnalysisRunner getAnalysisRunner(Owner owner) {
		return this.getRunner(owner);
	}

	public RunnerProgress getRunnerProgress(Owner owner) {
		AnalysisRunner runner = this.getRunner(owner);
		return runner == null ? null : runner.getRunnerProgress();
	}

	public AnalysisRunner stop(Owner owner) {

		AnalysisRunner runner = this.getAnalysisRunner(owner);
		long waitTime = 1 * 1000;

		if (runner != null) {
			// ���پ��߳�(TODO)
			runner.setRunning(false);
			long s = System.currentTimeMillis();
			while (true) {
				if (runner.getState() == 2) {
					this.removeRunner(owner);
					return null; // �ɹ�ֹͣ
				}
				if (System.currentTimeMillis() - s > waitTime) {
					// ֹͣ��ʱ���̻߳���ִ�У���Ҫ�ٴ�ֹͣ
					return runner;
				}
			}
		} else {
			return null;
		}
	}

	protected void execute(AnalysisRunner runner) {
		// ����ִ�е��߳�
		if (runner.getState() == AnalysisRunner.STATE_WAITING
				|| runner.getState() == AnalysisRunner.STATE_RUNNING
				|| runner.getState() == AnalysisRunner.STATE_FAILURE
				|| runner.getState() == AnalysisRunner.STATE_SUCCESS) {
			return;
		}

		if (this.threadPoolExecutor.getActiveCount() >= threadPoolExecutor.getCorePoolSize()) {

		} else {
			runner.setState(AnalysisRunner.STATE_WAITING);
			runner.setRunnerProgress(new RunnerProgress());
			threadPoolExecutor.execute(runner);
		}
	}

}
