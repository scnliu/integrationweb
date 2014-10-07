package net.linybin7.scheduler.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.linybin7.scheduler.adapter.AbstractRunner;
import net.linybin7.scheduler.task.AbstractTask;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-4-11-上午11:55:38 <br>
 * @description <br>
 *              TODO
 **/
public class BaseMonitor extends TimerTask {
	private Timer timer;
	private Watcher watcher;
	private long delay;

	private long period;
	private int cacheCapacity;
	private boolean pause = true;
	private boolean start = false;

	private LinkedHashMap<String, AbstractRunner> watchCache = new LinkedHashMap<String, AbstractRunner>();

	public BaseMonitor(Watcher watcher, long delay, long period, int cacheCapacity) {
		super();
		this.watcher = watcher;
		this.delay = delay;
		this.period = period;
		this.cacheCapacity = cacheCapacity;
		this.timer = new Timer();
	}

	public synchronized AbstractRunner addWatch(AbstractRunner runner) {
		if (watchCache.get(runner.getName()) != null)
			return null;
		if (watchCache.size() >= this.cacheCapacity) {
			AbstractRunner head = (AbstractRunner) watchCache.values().toArray()[0];
			watchCache.remove(head.getName());
		}
		watchCache.put(runner.getName(), runner);
		return runner;
	}

	public synchronized AbstractRunner remove(AbstractRunner runner) {
		return watchCache.remove(runner.getName());
	}

	public synchronized AbstractRunner[] all() {
		AbstractRunner[] all = watchCache.values().toArray(new AbstractRunner[] {});
		return Arrays.copyOfRange(all, 0, all.length);
	}

	public synchronized AbstractRunner[] clear() {
		AbstractRunner[] runners = watchCache.values().toArray(new AbstractRunner[] {});
		watchCache.clear();
		return runners;
	}

	public synchronized AbstractRunner get(String key) {
		return watchCache.get(key);
	}

	public synchronized int size() {
		return watchCache.size();
	}

	@Override
	public void run() {
		if (!this.isPause()) {
			AbstractRunner[] runners = all();
			if (this.watcher != null) {
				if (runners.length > 0) {

					List<AbstractRunner> neverWatch = new ArrayList<AbstractRunner>();
					for (AbstractRunner runner : runners) {
						AbstractTask task = runner.getTask();
						watcher.watch(task);
						if (watcher.keepWatching(task)) {
						} else {
							neverWatch.add(runner);
						}
					}
					abandon(neverWatch);
				}
			} else {
				this.clear();
			}
		}
	}

	private void abandon(List<AbstractRunner> neverWatch) {
		for (AbstractRunner runner : neverWatch) {
			this.remove(runner);
		}

	}

	public synchronized void pause() {
		pause = true;
	}

	public synchronized void resume() {
		pause = false;
	}

	public synchronized boolean isPause() {
		return pause;
	}

	public synchronized void start() {
		if (start)
			return;
		this.timer.schedule(this, delay, period);
		this.resume();
		start = true;
	}

	public synchronized boolean isStart() {
		return start;
	}

	public long getDelay() {
		return delay;
	}

	public long getPeriod() {
		return period;
	}

	public int getCacheCapacity() {
		return cacheCapacity;
	}

	public Watcher getWatcher() {
		return watcher;
	}

	public void setWatcher(Watcher watcher) {
		this.watcher = watcher;
	}
}
