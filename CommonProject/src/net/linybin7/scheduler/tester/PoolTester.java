package net.linybin7.scheduler.tester;

import java.util.Properties;

import net.linybin7.common.util.StringUtils;
import net.linybin7.scheduler.pool.BaseThreadPoolExecutor;
import net.linybin7.scheduler.util.PropertiesUtil;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-3-23-上午09:50:08 <br>
 * @description <br>
 *              TODO
 **/
public class PoolTester {

	public static void main(String[] args) {
		Properties properties = PropertiesUtil.loadDefaultProperties();

		String sCorePoolSize = properties.getProperty("pool.corePoolSize");
		String sMaxPoolSize = properties.getProperty("pool.maxPoolSize");
		String sMaxQueueSize = properties.getProperty("pool.maxQueueSize");

		int corePoolSize = StringUtils.getInt(sCorePoolSize, 1);
		int maxPoolSize = StringUtils.getInt(sMaxPoolSize, 1);
		int maxQueueSize = StringUtils.getInt(sMaxQueueSize, 10);

		BaseThreadPoolExecutor threadPool = new BaseThreadPoolExecutor(corePoolSize, maxPoolSize,
				maxQueueSize);
		threadPool.resume();

		for (int i = 0; i < 20; i++)
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println("running ....");
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
	}
}
