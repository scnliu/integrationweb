package net.linybin7.scheduler.pool;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import net.linybin7.scheduler.adapter.AbstractRunner;

/**
 * 逸信科技
 * 
 * @author WuLinbin
 * 
 */
public class BaseThreadPoolExecutor extends ThreadPoolExecutor {
	private static final Logger logger = Logger.getLogger(BaseThreadPoolExecutor.class);

	private String name;

	private final Map<String, AbstractRunner> activeCache = new HashMap<String, AbstractRunner>();

	private int _activeCount = -1;

	private int _queueSize = 0;

	private boolean paused = true;

	private final ReentrantLock pauseLock = new ReentrantLock();

	private final Condition unpaused = pauseLock.newCondition();

	public BaseThreadPoolExecutor(int corePoolSize, int maxPoolSize, int maxQueueSize) {
		super(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS,
				new BasePriorityBlockingQueue<Runnable>(maxQueueSize, new Comparator<Runnable>() {
					public int compare(Runnable o1, Runnable o2) {
						if (o1 instanceof AbstractRunner && o2 instanceof AbstractRunner) {
							return ((AbstractRunner) o1).getPriority()
									- ((AbstractRunner) o2).getPriority();
						}
						return 0;
					}
				}), new BaseRejectedExecutionHandler());

		BaseRejectedExecutionHandler bre = (BaseRejectedExecutionHandler) this
				.getRejectedExecutionHandler();
		bre.setBaseThreadPoolExecutor(this);

		if (logger.isDebugEnabled()) {
			logger.info("---------------------线程池配置参数-------------------");
			logger.info("核心线程数\t最大线程数\t队列最大数\t");
			logger.info(corePoolSize + "\t\t" + maxPoolSize + "\t\t" + maxQueueSize + "\t\t");
			logger.info("活动线程数\t队列长度\t\t虚拟机内存(Byte)\t\t剩余内存(Byte)\t\t最大内存\t\t内存使用率(%)");
			debug();
		}
	}

	/**
	 * (1)execute-->beforeExecute-->Runner.run-->afterExecute-->END<br>
	 * (2)execute--offer:false-->RejectedExecutionHandler.rejectedExecution--> END<br>
	 */

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		pauseLock.lock();
		try {
			while (paused)
				unpaused.await();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			// t.interrupt();
		} finally {
			pauseLock.unlock();
		}
		super.beforeExecute(t, r);

		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			d.setState(AbstractRunner.STATE_RUN);
			d.beforeExecute();
		}

		this.debug();
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			d.setState(AbstractRunner.STATE_DONE);
			d.afterExecute();
		}
		this.debug();

		this.removeActinve(r);
	}

	@Override
	public void execute(Runnable r) {
		this.putActive(r);

		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			d.setState(AbstractRunner.STATE_WAIT);
		}

		super.execute(r);
	}

	protected void debug() {
		int activeCount = this.getActiveCount();
		int queueSize = this.getQueue().size();

		Runtime rt = Runtime.getRuntime();
		long fm = rt.freeMemory();
		long tm = rt.totalMemory();
		long mm = rt.maxMemory();

		if (this._activeCount != activeCount || this._queueSize != queueSize) {
			this._activeCount = activeCount;
			this._queueSize = queueSize;
			if (logger.isDebugEnabled())
				logger.info(this._activeCount + "\t\t" + this._queueSize + "\t\t" + tm + "\t\t"
						+ fm + "\t\t" + mm + "\t" + (100 - fm * 100 / tm));
		}
	}

	/**
	 * 停止运行
	 * 
	 */
	public void pause() {
		pauseLock.lock();
		try {
			paused = true;
		} finally {
			pauseLock.unlock();
		}
	}

	/**
	 * 继续运行
	 * 
	 */
	public void resume() {
		pauseLock.lock();
		try {
			paused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}

	public boolean isPaused() {
		return paused;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected synchronized Runnable putActive(Runnable r) {
		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			return this.activeCache.put(d.getName(), d);
		}
		return null;
	}

	protected synchronized Runnable removeActinve(Runnable r) {
		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			return this.activeCache.remove(d.getName());
		}
		return null;
	}

	public synchronized Runnable getActinve(Runnable r) {
		if (r instanceof AbstractRunner) {
			AbstractRunner d = (AbstractRunner) r;
			return this.activeCache.get(d.getName());
		}
		return null;
	}

	public synchronized Collection<AbstractRunner> allActinve() {
		return this.activeCache.values();
	}

}
