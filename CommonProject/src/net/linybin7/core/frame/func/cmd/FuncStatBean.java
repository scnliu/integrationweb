package net.linybin7.core.frame.func.cmd;

import net.linybin7.core.frame.bo.Func;

public class FuncStatBean {

	private Func func;
	private int nums;
	private String parentName;
	public void setFunc(Func func) {
		this.func = func;
	}
	public Func getFunc() {
		return func;
	}
	public void setNums(int nums) {
		this.nums = nums;
	}
	public int getNums() {
		return nums;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentName() {
		return parentName;
	}
}
