package net.linybin7.pub.analysis.service;

import net.linybin7.pub.analysis.runner.AnalysisRunner;
import net.linybin7.pub.analysis.support.AnalysisParam;
import net.linybin7.pub.analysis.support.Owner;
import net.linybin7.pub.analysis.support.RunnerProgress;

/**
 * 逸信科技
 * 
 * @author WuLinbin
 * 
 *         -------------------------------------------------------------------- 2011-05-25 11:00
 *         WuLinbin （1）修改支持多用户同时分析（2）完善注释（3）重构相关(智能分析)代码
 * 
 */
public interface AnalysisService {

	/**
	 * 创建/启动智能线程
	 * 
	 * @param User
	 * @return
	 */
	public AnalysisRunner start(Owner owner, Class<?> executerClass, AnalysisParam param);

	/**
	 * 销毁/停止线程
	 * 
	 * @param User
	 * @return <br>
	 *         TODO:未实现
	 */
	public AnalysisRunner stop(Owner owner);

	/**
	 * 获取智能分析线程
	 * 
	 * @param user
	 * @return
	 */
	public AnalysisRunner getAnalysisRunner(Owner owner);

	/**
	 * 获取处理进度
	 * 
	 * @param groupId
	 * @return
	 */
	public RunnerProgress getRunnerProgress(Owner owner);

}
