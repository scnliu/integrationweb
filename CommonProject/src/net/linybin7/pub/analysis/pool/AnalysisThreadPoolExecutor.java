package net.linybin7.pub.analysis.pool;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import net.linybin7.pub.analysis.runner.AnalysisRunner;

/**
 * 逸信科技
 * 
 * @author WuLinbin
 * 
 */
public class AnalysisThreadPoolExecutor extends ThreadPoolExecutor {
	private static final Logger logger = Logger.getLogger(AnalysisThreadPoolExecutor.class);
	private int actCnt = -1;

	private int qsize = 0;

	private boolean isPaused = true;

	private ReentrantLock pauseLock = new ReentrantLock();

	private Condition unpaused = pauseLock.newCondition();

	public AnalysisThreadPoolExecutor(int corePoolSize, int maxPoolSize, int maxQueueSize) {
		super(corePoolSize, maxPoolSize, 0L, TimeUnit.MILLISECONDS, new AnalysisQueue<Runnable>(
				maxQueueSize, new Comparator<Runnable>() {
					public int compare(Runnable o1, Runnable o2) {
						return 0;
					}
				}), new AnalysisRejectedExecutionHandler());

		if (true) {
			logger.debug("---------------------线程池配置参数-------------------");
			logger.debug("核心线程数\t最大线程数\t队列最大数\t");
			logger.debug(corePoolSize + "\t\t" + maxPoolSize + "\t\t" + maxQueueSize + "\t\t");
			logger.debug("活动线程数\t队列长度\t\t虚拟机内存(Byte)\t\t剩余内存(Byte)\t\t最大内存\t\t内存使用率(%)");
			debug();
		}
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		pauseLock.lock();
		try {
			while (isPaused)
				unpaused.await();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
			// t.interrupt();
		} finally {
			pauseLock.unlock();
		}
		super.beforeExecute(t, r);

		if (r instanceof AnalysisRunner) {
			AnalysisRunner d = (AnalysisRunner) r;
			// d.setState(AnalysisRunner.STATE_RUNNING);
		}

		debug();
	}

	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if (r instanceof AnalysisRunner) {
			AnalysisRunner d = (AnalysisRunner) r;
			// d.setState(AnalysisRunner.STATE_SUCCESS);
		}

		debug();
	}

	@Override
	public void execute(Runnable r) {
		super.execute(r);
	}

	public void debug() {
		int act = this.getActiveCount();
		int qsize = this.getQueue().size();

		Runtime rt = Runtime.getRuntime();
		long fm = rt.freeMemory();
		long tm = rt.totalMemory();
		long mm = rt.maxMemory();

		if (this.actCnt != act || this.qsize != qsize) {
			this.actCnt = act;
			this.qsize = qsize;
			if (true)
				logger.debug(this.actCnt + "\t\t" + this.qsize + "\t\t" + tm + "\t\t" + fm + "\t\t"
						+ mm + "\t" + (100 - fm * 100 / tm) + "");

		}
	}

	/**
	 * 停止运行
	 * 
	 */
	public void pause() {
		pauseLock.lock();
		try {
			isPaused = true;
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
			isPaused = false;
			unpaused.signalAll();
		} finally {
			pauseLock.unlock();
		}
	}
}

class AnalysisQueue<E> extends PriorityBlockingQueue<E> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnalysisQueue(int initialCapacity, Comparator<E> comparator) {
		super(initialCapacity, comparator);
	}

}

class AnalysisRejectedExecutionHandler implements RejectedExecutionHandler {
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

	}
}
