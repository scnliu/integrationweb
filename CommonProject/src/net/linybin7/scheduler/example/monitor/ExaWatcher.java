package net.linybin7.scheduler.example.monitor;

import net.linybin7.scheduler.monitor.Watcher;
import net.linybin7.scheduler.task.AbstractTask;
import net.linybin7.scheduler.task.BaseProgress;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-14-����11:10:21 <br>
 * @description <br>
 *              TODO
 **/
public class ExaWatcher implements Watcher {

	@Override
	public boolean keepWatching(final AbstractTask task) {
		return AbstractTask.STATE_EXECUTING.equals(task.getState())
				|| AbstractTask.STATE_WAITING.equals(task.getState());
	}

	@Override
	public void watch(final AbstractTask task) {
		BaseProgress progress = task.getProgress();
		System.out.println("����[" + task.getId() + "]״̬��" + task.getState() + " ���ͣ�"
				+ task.getType() + " -- " + ((progress == null) ? "" : progress.getMessage()));
	}

}
