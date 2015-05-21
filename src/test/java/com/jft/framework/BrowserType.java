package com.jft.framework;

public enum BrowserType {

	FIREFOX("firefox"),
	IE("ie"),
	SAFARI("safari"),
	CHROME("chrome"),
	ANDROID("android"),
	IPHONE("iphone"),
	ANDROIDNATIVE("androidnative");
	
	private final String browser;
	
	BrowserType(String browser){
		this.browser = browser;
	}
	
	public String getBrowser(){
		return browser;
	}
	
	@Override
	public String toString(){
		return browser;
	}

}
