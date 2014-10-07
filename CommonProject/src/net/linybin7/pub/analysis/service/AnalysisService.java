package net.linybin7.pub.analysis.service;

import net.linybin7.pub.analysis.runner.AnalysisRunner;
import net.linybin7.pub.analysis.support.AnalysisParam;
import net.linybin7.pub.analysis.support.Owner;
import net.linybin7.pub.analysis.support.RunnerProgress;

/**
 * ���ſƼ�
 * 
 * @author WuLinbin
 * 
 *         -------------------------------------------------------------------- 2011-05-25 11:00
 *         WuLinbin ��1���޸�֧�ֶ��û�ͬʱ������2������ע�ͣ�3���ع����(���ܷ���)����
 * 
 */
public interface AnalysisService {

	/**
	 * ����/���������߳�
	 * 
	 * @param User
	 * @return
	 */
	public AnalysisRunner start(Owner owner, Class<?> executerClass, AnalysisParam param);

	/**
	 * ����/ֹͣ�߳�
	 * 
	 * @param User
	 * @return <br>
	 *         TODO:δʵ��
	 */
	public AnalysisRunner stop(Owner owner);

	/**
	 * ��ȡ���ܷ����߳�
	 * 
	 * @param user
	 * @return
	 */
	public AnalysisRunner getAnalysisRunner(Owner owner);

	/**
	 * ��ȡ�������
	 * 
	 * @param groupId
	 * @return
	 */
	public RunnerProgress getRunnerProgress(Owner owner);

}
