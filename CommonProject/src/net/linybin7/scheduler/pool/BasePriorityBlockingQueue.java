package net.linybin7.scheduler.pool;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-23-上午09:19:27 <br>
 * @description <br>
 *              TODO
 **/
public class BasePriorityBlockingQueue<E> extends PriorityBlockingQueue<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int maxCapacity = 0;

	public BasePriorityBlockingQueue(int initialCapacity, Comparator<E> comparator) {
		super(initialCapacity, comparator);
		this.maxCapacity = initialCapacity;
	}

	@Override
	public boolean offer(E e) {
		// 队列达到最大值时，拒绝接收新的runner
		if (this.size() >= maxCapacity) {

			return false;
		}
		return super.offer(e);
	}

}
