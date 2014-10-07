package net.linybin7.pub.analysis.support;

/**
 * ���ſƼ� <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-7-13-����09:31:18 <br>
 * @description <br>
 *              TODO
 **/
public interface Executor {

	/**
	 * ����ִ�в���
	 * 
	 * @param analysisParam
	 */
	public void setAnalysisParam(AnalysisParam analysisParam);

	/**
	 * ����User��ִ���û���
	 * 
	 * @param suer
	 */
	public void setOwner(Owner owner);

	/**
	 * ִ�еķ���
	 */
	public void execute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * ִ�б��ܾ�ʱ�ص��ķ���
	 */
	public void rejectedExecute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * ִ�����ʱ�ص��ķ���
	 */
	public void afterExecute(RunnerProgress runnerProgress) throws Exception;

	/**
	 * ʼִ��ǰ���õķ���
	 */
	public void beforeExecute(RunnerProgress runnerProgress) throws Exception;

}
