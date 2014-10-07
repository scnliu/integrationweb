package net.linybin7.pub.data.bo;

import java.io.Serializable;

public class DataCheck implements Serializable{
	private int lineNum;
	private String errorInfo;
	private String errorCol;
	private String errorData;
	private int[] colNum;
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public String getErrorCol() {
		return errorCol;
	}
	public void setErrorCol(String errorCol) {
		this.errorCol = errorCol;
	}
	public String getErrorData() {
		return errorData;
	}
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	public int[] getColNum() {
		return colNum;
	}
	public void setColNum(int[] colNum) {
		this.colNum = colNum;
	}
	
}
