package net.linybin7.pub.analysis.support;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-13-上午09:31:18 <br>
 * @description <br>
 *              TODO
 **/
public interface Executor {

	/**
	 * 接收执行参数
	 * 
	 * @param analysisParam
	 */
	public void setAnalysisParam(AnalysisParam analysisParam);

	/**
	 * 接收User（执行用户）
	 * 
	 * @param suer
	 */
	public void setOwner(Owner owner);

	/**
	 * 执行的方法
	 */
	public void execute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * 执行被拒绝时回调的方法
	 */
	public void rejectedExecute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * 执行完成时回调的方法
	 */
	public void afterExecute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * 始执行前调用的方法
	 */
	public void beforeExecute(RunnerProgress runnerProgress) throws Exception;

}
