package net.linybin7.pub.analysis.service;

import org.apache.log4j.Logger;

import net.linybin7.pub.analysis.runner.AnalysisRunner;
import net.linybin7.pub.analysis.support.AnalysisConsole;
import net.linybin7.pub.analysis.support.AnalysisParam;
import net.linybin7.pub.analysis.support.Owner;
import net.linybin7.pub.analysis.support.RunnerProgress;

/**
 * ÒÝÐÅ¿Æ¼¼
 * 
 * @author WuLinbin
 * 
 */
public class AnalysisServiceImpl implements AnalysisService {
	private static final Logger logger = Logger.getLogger(AnalysisServiceImpl.class);

	private AnalysisConsole analysisConsole;

	public AnalysisConsole getAnalysisConsole() {
		return analysisConsole;
	}

	public void setAnalysisConsole(AnalysisConsole analysisConsole) {
		this.analysisConsole = analysisConsole;
	}

	@Override
	public AnalysisRunner getAnalysisRunner(Owner owner) {

		return analysisConsole.getAnalysisRunner(owner);
	}

	@Override
	public RunnerProgress getRunnerProgress(Owner owner) {
		return analysisConsole.getRunnerProgress(owner);
	}

	@Override
	public AnalysisRunner start(Owner owner, Class<?> executerClass, AnalysisParam param) {
		return analysisConsole.start(owner, executerClass, param);
	}

	@Override
	public AnalysisRunner stop(Owner owner) {
		return analysisConsole.stop(owner);
	}

}
