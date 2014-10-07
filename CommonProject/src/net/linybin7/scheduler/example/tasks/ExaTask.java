package net.linybin7.scheduler.example.tasks;

import net.linybin7.scheduler.BaseTrigger;
import net.linybin7.scheduler.task.AbstractTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-9-下午06:21:58 <br>
 * @description <br>
 *              TODO
 **/
public class ExaTask extends AbstractTask {
	// private File file;
	private ExaProgress progress;

	public ExaTask() {

	}

	@Override
	public ExaProgress getProgress() {
		// if (progress == null) {
		// progress = new ExProgress();
		// progress.setStartTime(new Date());
		// }
		return progress;
	}

	@Override
	public BaseTrigger getTrigger() {
		return null;
	}

	// public File getFile() {
	// return file;
	// }
	//
	// public void setFile(File file) {
	// this.file = file;
	// }

	public void setProgress(ExaProgress progress) {
		this.progress = progress;
	}

}
