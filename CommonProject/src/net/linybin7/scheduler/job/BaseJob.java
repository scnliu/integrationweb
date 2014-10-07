package net.linybin7.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.scheduling.quartz.QuartzJobBean;

import net.linybin7.scheduler.adapter.BaseTaskAdapter;
import net.linybin7.scheduler.task.AbstractTask;

public class BaseJob extends QuartzJobBean implements StatefulJob {
	private AbstractTask task;
	private BaseTaskAdapter taskAdapter;

	public BaseJob() {
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		taskAdapter.execute(task);
	}

	public AbstractTask getTask() {
		return task;
	}

	public void setTask(AbstractTask task) {
		this.task = task;
	}

	public BaseTaskAdapter getTaskAdapter() {
		return taskAdapter;
	}

	public void setTaskAdapter(BaseTaskAdapter taskAdapter) {
		this.taskAdapter = taskAdapter;
	}

}