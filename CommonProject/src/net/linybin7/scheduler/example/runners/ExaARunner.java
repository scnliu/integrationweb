package net.linybin7.scheduler.example.runners;

import java.util.Date;

import net.linybin7.scheduler.adapter.AbstractRunner;
import net.linybin7.scheduler.example.tasks.ExaProgress;
import net.linybin7.scheduler.example.tasks.ExaTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-9-下午06:22:58 <br>
 * @description <br>
 *              TODO
 **/
public class ExaARunner extends AbstractRunner {

	public ExaARunner(ExaTask task) {
		super();
		this.task = task;
	}

	private ExaTask task;

	public void run() {
		ExaProgress progress = task.getProgress();
		if (progress == null)
			progress = new ExaProgress();
		progress.setStartTime(new Date());
		progress.setDeterminate(true);
		progress.setMaximum(100);

		// 模拟进度
		for (int i = 0; i < 100; i++) {
			// System.out.println("[" + this.getTask().getType() + "]" + this.getTask().getId() +
			// " 完成：" + i + "%");
			progress.setDonenum(i + 1);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
	}

	public ExaTask getTask() {
		return task;
	}

	public void setTask(ExaTask task) {
		this.task = task;
	}

	@Override
	public void afterExecute() {
		task.setState(ExaTask.STATE_SUCCESS);
	}

	@Override
	public void beforeExecute() {
		task.setState(ExaTask.STATE_EXECUTING);

	}

	@Override
	public void rejectedExecute() {
		System.out.println("因队列满而被拒绝：");
		task.setState(ExaTask.STATE_FAILURE);

	}

}
