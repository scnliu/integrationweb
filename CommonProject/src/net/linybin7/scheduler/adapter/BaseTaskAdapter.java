package net.linybin7.scheduler.adapter;

import java.util.Collection;
import java.util.Properties;

import net.linybin7.common.util.StringUtils;
import net.linybin7.scheduler.monitor.BaseMonitor;
import net.linybin7.scheduler.pool.BaseThreadPoolExecutor;
import net.linybin7.scheduler.task.AbstractTask;
import net.linybin7.scheduler.util.PropertiesUtil;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-8-下午03:32:06 <br>
 * @description <br>
 *              TODO
 **/
public class BaseTaskAdapter {
	private AbstractRunnerFactory runnerFactory;
	private Properties properties;

	public BaseTaskAdapter() {
		this(null, null);
	}

	public BaseTaskAdapter(String propertiesFilePath) {
		this(null, PropertiesUtil.loadProperty(propertiesFilePath));
	}

	public BaseTaskAdapter(AbstractRunnerFactory runnerFactory, Properties userProperties) {
		Properties defaultProperties = PropertiesUtil.loadDefaultProperties();
		properties = PropertiesUtil.union(userProperties, defaultProperties);

		this.runnerFactory = runnerFactory;

		// System.err.println("BaseTaskAdapter 初始化。。。" + this);
	}

	public synchronized void execute(AbstractTask task) {

		if (task == null)
			return;

		// // 正在排队/运行的任务
		// if (task.isRunning()) {
		// return;
		// }

		AbstractRunner runner = runnerFactory.assemble(task);
		if (runner == null || runner.getName() == null)
			throw new RuntimeException("runner or runner's name is null");

		// System.out.println(runner.getName() + " "
		// + this.getExecutor().getActinve(runner));

		if (this.getExecutor().getActinve(runner) != null) {
			return;
		}
		// System.out.println("-------------" + this);
		// if (!AbstractTask.STATE_EXECUTING.equals(task.getState()))
		task.setState(AbstractTask.STATE_WAITING);
		this.offer(runner);

		if (this.monitor != null) {
			this.monitor.addWatch(runner);
		}
	}

	protected void offer(AbstractRunner runner) {
		// if (executor == null) {
		// this.init();
		// }

		this.getExecutor().execute(runner);
	}

	private BaseThreadPoolExecutor executor;

	protected void init() {
		String sCorePoolSize = this.properties.getProperty("pool.corePoolSize");
		String sMaxPoolSize = this.properties.getProperty("pool.maxPoolSize");
		String sMaxQueueSize = this.properties.getProperty("pool.maxQueueSize");

		int corePoolSize = StringUtils.getInt(sCorePoolSize, 10);
		int maxPoolSize = StringUtils.getInt(sMaxPoolSize, 10);
		int maxQueueSize = StringUtils.getInt(sMaxQueueSize, 100);

		if (executor == null) {
			executor = new BaseThreadPoolExecutor(corePoolSize, maxPoolSize, maxQueueSize);
		}
		executor.resume();
	}

	public Properties getProperties() {

		return properties;
	}

	public void setProperties(Properties properties) {
		Properties defaultProperties = PropertiesUtil.loadDefaultProperties();
		this.properties = PropertiesUtil.union(properties, defaultProperties);
	}

	public AbstractRunnerFactory getRunnerFactory() {
		return runnerFactory;
	}

	public void setRunnerFactory(AbstractRunnerFactory runnerFactory) {
		this.runnerFactory = runnerFactory;
	}

	public Collection<AbstractRunner> allActive() {
		// if (executor == null) {
		// this.init();
		// }
		return this.getExecutor().allActinve();
	}

	public BaseThreadPoolExecutor getExecutor() {
		if (executor == null) {
			this.init();
			// System.err.println("BaseThreadPoolExecutor 初始化。。。" + this + ""
			// + this.executor);
		}
		return executor;
	}

	/***********************************************
	 * 任务监控器（线程）
	 ***********************************************/
	private BaseMonitor monitor;

	public BaseMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(BaseMonitor monitor) {
		this.monitor = monitor;
	}

}
