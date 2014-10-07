package net.linybin7.scheduler.pool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import net.linybin7.scheduler.adapter.AbstractRunner;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-23-上午09:21:58 <br>
 * @description <br>
 *              TODO
 **/
public class BaseRejectedExecutionHandler implements RejectedExecutionHandler {

	private BaseThreadPoolExecutor baseThreadPoolExecutor;

	public BaseRejectedExecutionHandler() {

	}

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			d.setState(AbstractRunner.STATE_REJ);
			d.rejectedExecute();

			try {
				getBaseThreadPoolExecutor().removeActinve(d);
			} catch (Exception e) {
			}
		}
	}

	public BaseThreadPoolExecutor getBaseThreadPoolExecutor() {
		return baseThreadPoolExecutor;
	}

	public void setBaseThreadPoolExecutor(BaseThreadPoolExecutor baseThreadPoolExecutor) {
		this.baseThreadPoolExecutor = baseThreadPoolExecutor;
	}

}
