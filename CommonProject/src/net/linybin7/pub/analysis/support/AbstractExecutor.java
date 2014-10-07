package net.linybin7.pub.analysis.support;

import net.linybin7.core.frame.bo.User;

/**
 * 逸信科技 <br>
 * 
 * @author WuLinbin <br>
 * @since 2011-2011-11-30-上午09:32:30 <br>
 * @description <br>
 *              TODO
 **/
public abstract class AbstractExecutor implements Executor {

	private AnalysisParam analysisParam;
	private Owner owner;

	@Override
	public void setAnalysisParam(AnalysisParam arg0) {
		this.analysisParam = arg0;
	}

	@Override
	public void setOwner(Owner arg0) {
		this.owner = arg0;
	}

	public Object get(String key) {
		return this.analysisParam.get(key);
	}

	public AnalysisParam getAnalysisParam() {
		return analysisParam;
	}

	public Owner getOwner() {
		return owner;
	}

	public User getUser() {
		return this.owner.getUser();
	}

}
