package net.linybin7.core.frame.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * ϵͳ
 * 
 * 
 */
public class Sys {
	private String id;

	private String name;

	private String context;

	private int order;

	private List<Func> funcs = new ArrayList<Func>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public List<Func> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Func> funcs) {
		this.funcs = funcs;
	}

}
