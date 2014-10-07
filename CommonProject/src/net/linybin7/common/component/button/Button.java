package net.linybin7.common.component.button;

/**
 * °´Å¥
 * @author JackenCai
 *
 */
public class Button {
	//º¯Êý
	private String function;
	
	//±êÇ©
	private String label;
   private String id;
   private int funcProp;
   private String funcCode;
   private int buttonType;//add;edit;del;search
	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFuncProp() {
		return funcProp;
	}

	public void setFuncProp(int funcProp) {
		this.funcProp = funcProp;
	}

	public String getFuncCode() {
		return funcCode;
	}

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
		if(funcCode.endsWith("1"))buttonType=1;
		if(funcCode.endsWith("2"))buttonType=2;
		if(funcCode.endsWith("3"))buttonType=3;
		if(funcCode.endsWith("4"))buttonType=4;
		if(funcCode.endsWith("5"))buttonType=5;
		if(funcCode.endsWith("6"))buttonType=6;
	}

	public int getButtonType() {
		return buttonType;
	}

	public void setButtonType(int buttonType) {
		this.buttonType = buttonType;
	}
	
	
}
