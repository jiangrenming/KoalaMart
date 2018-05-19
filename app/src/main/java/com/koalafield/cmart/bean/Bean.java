package com.koalafield.cmart.bean;
/**
 * 颜色选择类
 * @author Administrator
 *
 */
public class Bean { 
	//商品属性是否选中
	private int states;//状态 3种 0 选中  1 未选中 2不可选 
	//商品属性名
	private String name;
	private int id;//商品的Id
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public int getStates() {
		return states;
	}
	public void setStates(int states) {
		this.states = states;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
