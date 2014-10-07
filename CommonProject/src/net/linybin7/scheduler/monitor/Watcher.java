package net.linybin7.scheduler.monitor;

import net.linybin7.scheduler.task.AbstractTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-14-上午10:32:39 <br>
 * @description <br>
 *              TODO
 **/
public interface Watcher {

	public void watch(final AbstractTask task);

	public boolean keepWatching(final AbstractTask task);

}
