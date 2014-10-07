package net.linybin7.scheduler.adapter;

import net.linybin7.scheduler.task.AbstractTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-9-下午02:17:03 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractRunnerFactory {
	private static AbstractRunnerFactory runnerFactory;

	public static AbstractRunnerFactory getInstance(Class<?> runnerFactorySubClass) {
		if (runnerFactory == null) {
			try {
				Class<?> c = Class.forName(runnerFactorySubClass.getName());
				runnerFactory = (AbstractRunnerFactory) c.newInstance();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return runnerFactory;
	}

	public abstract AbstractRunner assemble(AbstractTask task);
}
