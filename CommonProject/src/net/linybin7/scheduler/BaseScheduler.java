package net.linybin7.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.JobDetailBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;

import net.linybin7.scheduler.adapter.BaseTaskAdapter;
import net.linybin7.scheduler.job.BaseJob;
import net.linybin7.scheduler.task.AbstractTask;
import net.linybin7.scheduler.util.PropertiesUtil;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-8-下午03:11:58 <br>
 * @description <br>
 *              TODO
 **/
public class BaseScheduler {
	private static final Logger logger = Logger.getLogger(BaseScheduler.class);
	private Properties properties;
	private Scheduler scheduler;
	private BaseTaskAdapter taskAdapter;

	public BaseScheduler() throws SchedulerException {
		this(new Properties());
	}

	public BaseScheduler(String propertiesFilePath) throws SchedulerException {
		this(PropertiesUtil.loadProperty(propertiesFilePath));
	}

	public BaseScheduler(Properties userProperties) throws SchedulerException {
		Properties defaultProperties = PropertiesUtil.loadDefaultProperties();
		properties = PropertiesUtil.union(userProperties, defaultProperties);

		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException e) {
			if (logger.isDebugEnabled())
				logger.error("创建调度器失败：" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 执行任务
	 * 
	 * @param task
	 * @throws Exception
	 */
	public void schedule(AbstractTask task) throws Exception {
		if (this.taskAdapter == null)
			throw new Exception("TaskAdapter is null");

		BaseTrigger _trigger = task.getTrigger();

		if (_trigger == null) {// 一次性任务执行
			taskAdapter.execute(task);
		} else {// 定时或者循环等多任务执行
			JobDetailBean jobDetail = new JobDetailBean();
			Map<String, Object> jobDataAsmap = new HashMap<String, Object>();
			jobDataAsmap.put("task", task);
			jobDataAsmap.put("taskAdapter", taskAdapter);
			jobDetail.setJobClass(BaseJob.class);
			jobDetail.setBeanName(task.getId());
			jobDetail.setJobDataAsMap(jobDataAsmap);
			jobDetail.afterPropertiesSet();

			try {
				Trigger t = null;
				// cron触发器
				if (_trigger.getInterval() <= 0) {
					CronTriggerBean trigger = new CronTriggerBean();
					trigger.setBeanName(task.getId());
					trigger.setJobDetail(jobDetail);
					trigger.setCronExpression(_trigger.getCronExpression());

					if (task.getStartTime() != null) {
						trigger.setStartTime(task.getStartTime());
					}
					if (_trigger.getEndTime() != null) {
						trigger.setEndTime(_trigger.getEndTime());
					}
					trigger.setMisfireInstruction(1);
					trigger.afterPropertiesSet();

					t = trigger;
				}
				// simple触发器
				else {
					SimpleTriggerBean trigger = new SimpleTriggerBean();
					trigger.setBeanName(task.getId());
					trigger.setJobDetail(jobDetail);
					trigger.setRepeatInterval(_trigger.getInterval());
					trigger.setStartDelay(_trigger.getDelay());
					if (task.getStartTime() != null) {
						trigger.setStartTime(task.getStartTime());
					}
					if (_trigger.getEndTime() != null) {
						trigger.setEndTime(_trigger.getEndTime());
					}
					trigger.setMisfireInstruction(1);
					trigger.afterPropertiesSet();

					t = trigger;
				}

				if (scheduler.getJobDetail(jobDetail.getName(), jobDetail.getGroup()) == null) {
					scheduler.addJob(jobDetail, true);
				}

				if ((scheduler.getTrigger(t.getName(), t.getGroup()) == null)) {
					scheduler.scheduleJob(t);
				} else {
					scheduler.rescheduleJob(t.getName(), t.getGroup(), t);
				}

				if (!scheduler.isStarted())
					this.scheduler.start();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// private void createBaseAdapter() {
	// String sDelay = this.properties.getProperty("monitor.delay");
	// String sPeriod = this.properties.getProperty("monitor.period");
	// String sCacheCapacity = this.properties
	// .getProperty("monitor.cacheCapacity");
	//
	// long delay = StringUtils.getLong(sDelay, 1000);
	// long period = StringUtils.getLong(sPeriod, 1000);
	// int cacheCapacity = StringUtils.getInt(sCacheCapacity, 100);
	//
	// taskAdapter = new BaseTaskAdapter(new SimpleRunnerFactory(),
	// this.properties);
	// taskAdapter.setMonitor(new BaseMonitor(new Watcher() {
	// @Override
	// public boolean keepWatching(AbstractTask task) {
	// return false;
	// }
	//
	// @Override
	// public void watch(AbstractTask task) {
	// // just print something
	// System.out.println(task);
	// }
	// }, delay, period, cacheCapacity));
	// taskAdapter.getMonitor().start();
	// }

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	public BaseTaskAdapter getTaskAdapter() {
		return taskAdapter;
	}

	public void setTaskAdapter(BaseTaskAdapter taskAdapter) {
		this.taskAdapter = taskAdapter;
	}

}
