package net.linybin7.scheduler.example;

import java.util.Properties;

import org.quartz.SchedulerException;

import net.linybin7.scheduler.BaseScheduler;
import net.linybin7.scheduler.adapter.BaseTaskAdapter;
import net.linybin7.scheduler.example.monitor.ExaWatcher;
import net.linybin7.scheduler.example.runners.ExaRunnerFactory;
import net.linybin7.scheduler.example.tasks.ExaProgress;
import net.linybin7.scheduler.example.tasks.ExaTask;
import net.linybin7.scheduler.monitor.BaseMonitor;
import net.linybin7.scheduler.util.PropertiesUtil;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-9-下午03:59:55 <br>
 * @description <br>
 *              TODO
 **/
public class ExaMain {

	/**
	 * @param args
	 * @throws SchedulerException
	 */
	public static void main(String[] args) throws SchedulerException {
		Properties p = PropertiesUtil.loadProperty("com/eshine/scheduler/example/exa.properties");

		// 创建适配器
		BaseTaskAdapter taskAdapter = new BaseTaskAdapter(new ExaRunnerFactory(), p);

		// 启动监控线程（可选）
		BaseMonitor monitor = new BaseMonitor(new ExaWatcher(), 1000, 1000, 100);
		taskAdapter.setMonitor(monitor);
		monitor.start();

		// 启动调度器
		BaseScheduler scheduler = new BaseScheduler(p);
		scheduler.setTaskAdapter(taskAdapter);

		for (int i = 0; i < 20; i++) {
			ExaTask task = new ExaTask();
			ExaProgress progress = new ExaProgress();
			task.setProgress(progress);
			task.setName(i + "");
			task.setId(i + "");

			if (i % 2 == 0)
				task.setType("A");
			else
				task.setType("B");

			try {
				scheduler.schedule(task);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
