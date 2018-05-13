package com.koalafield.cmart.utils;

/**
 * FragmentManager存储Fragment用的tag的枚举
 * tag表示Fragment的完整类名
 */

public enum FragmentTag {

	TAG_HOME("com.koalafield.cmart.ui.fragment.HomeFragment"),
	TAG_GOODS("com.koalafield.cmart.ui.fragment.CategryFragment"),
	TAG_TRUCK("com.koalafield.cmart.ui.fragment.CartFragment"),
	TAG_MY("com.koalafield.cmart.ui.fragment.PersonFragment");
	
	String tag;
	
	private FragmentTag(String tag){
		this.tag = tag;
	}
	
	public String getTag(){
		return tag;
	}
}
