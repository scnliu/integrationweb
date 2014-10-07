package net.linybin7.scheduler.example.runners;

import net.linybin7.scheduler.adapter.AbstractRunner;
import net.linybin7.scheduler.adapter.AbstractRunnerFactory;
import net.linybin7.scheduler.example.tasks.ExaTask;
import net.linybin7.scheduler.task.AbstractTask;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-9-����06:23:49 <br>
 * @description <br>
 *              TODO
 **/
public class ExaRunnerFactory extends AbstractRunnerFactory {

	@Override
	public AbstractRunner assemble(AbstractTask task) {
		if (ExaTask.class.getName().equals(task.getClass().getName())) {
			ExaTask exTask = (ExaTask) task;
			if ("A".equals(task.getType())) {
				ExaARunner runner = new ExaARunner(exTask);
				runner.setName(exTask.getId() + "-A");
				return runner;
			}
			if ("B".equals(task.getType())) {
				ExaBRunner runner = new ExaBRunner(exTask);
				runner.setName(exTask.getId() + "-B");
				return runner;
			}
		}
		return null;
	}

}
