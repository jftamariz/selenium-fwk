package com.jft.framework;

public enum BrowserType {

	FIREFOX("firefox"),
	IE("ie"),
	SAFARI("safari"),
	CHROME("chrome"),
	ANDROID("android"),
	IPHONE("iphone");
	
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
