package com.jft.framework;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;




public class WebDriverBase {
	
	/*	
	 * Method that returns configured Selenium WebDriver supporting GRID (remote)
	 */
	public static WebDriver getRemoteWebDriver (String browser, String hubUrl, String proxy, String proxyport)
	{
		WebDriver webdriver = null;
		DesiredCapabilities desiredcaps = null;
		BrowserType browsertype;
		
		if (hubUrl == null || hubUrl.length() == 0){
			System.err.println(" [Error] - Invalid Hub URL - Setting local WebDriver");
			return getLocalWebDriver(browser, "","");
		}
		
		desiredcaps = new DesiredCapabilities();
		desiredcaps.setJavascriptEnabled(true);
		
		// get browser name
		browsertype = getBrowserType(browser);
	
		// set 
		webdriver = setRemoteWebDriver(browsertype, hubUrl, proxy, proxyport);
		
		return webdriver;
		
	}
	
	
	/*	
	 * Method that returns configured Selenium WebDriver supporting LOCAL setup
	 */
	public static WebDriver getLocalWebDriver ( String browser, String proxy, String proxyport)
	{
		WebDriver webdriver = null;
		
		BrowserType browsertype;
		
		// get browser name
		browsertype = getBrowserType(browser);
		
		// Set path for IE and Chrome drivers based on System platform
		setSystemProperties(browsertype);
		
		// Set Local WebDriver based on Browser Type and System platform
		webdriver = setLocalWebDriver(browsertype, proxy, proxyport);
		
		return webdriver;
		
	}
	
	
	/*	
	 * Method that returns configured Selenium WebDriver with desired capabilities based on browser
	 * and proxy settings.
	 */
	private static WebDriver setLocalWebDriver(BrowserType browser, String proxyname, String proxyport)
	{
		WebDriver localDriver = null;
		DesiredCapabilities dc = null;
		org.openqa.selenium.Proxy proxy = null;
	
		switch(browser)
		{
		case IE:
			localDriver = new InternetExplorerDriver();
			
			break;
			
		case CHROME:
			if(!proxyname.equals("")){
				proxy = new org.openqa.selenium.Proxy(); 
				proxy.setHttpProxy(proxyname+":"+proxyport);
				proxy.setSslProxy(proxyname+":"+proxyport); 
				proxy.setFtpProxy(proxyname+":"+proxyport); 
				dc = DesiredCapabilities.chrome();
				dc.setCapability(CapabilityType.PROXY, proxy); 
				localDriver = new ChromeDriver(dc);
			}else{
				localDriver = new ChromeDriver();
			}
			break;
		case FIREFOX:
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderlist",2);
			profile.setPreference("browser.download.manager.showWenStarting", false);
			profile.setPreference("browser.download.dir", ConfigLoader.getProperty("downloadfolder"));
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk","Application/pdf");
			profile.setPreference("pdfjs.disabled",true);
				
			if(!proxyname.equals("")){
				proxy = new org.openqa.selenium.Proxy(); 
				proxy.setHttpProxy(proxyname+":"+proxyport);
				proxy.setSslProxy(proxyname+":"+proxyport); 
				proxy.setFtpProxy(proxyname+":"+proxyport); 
				dc = DesiredCapabilities.firefox();
				dc.setCapability(FirefoxDriver.PROFILE, profile);
				dc.setCapability(CapabilityType.PROXY, proxy); 
				localDriver = new FirefoxDriver(dc);
			}else{
				localDriver = new FirefoxDriver();
			}
			
			
			break;
		case SAFARI:
			localDriver = new SafariDriver();
			break;
		case ANDROID:
			System.out.println(" [Info-APCap] - Android device selected");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "android phone");
		    capabilities.setCapability("app", "Chrome");
		    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
		    capabilities.setCapability(CapabilityType.VERSION, "4.2.1");
		    capabilities.setCapability("platformName", "Android");
		    try {
		    	//localDriver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				localDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		    	//localDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4725/wd/hub"), capabilities);
		    	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
				break;
		    
		case IPHONE:
			System.out.println(" [Info-APCap] - iPhone device selected - local");
			DesiredCapabilities capabilitiesiphone = new DesiredCapabilities();
			capabilitiesiphone.setCapability(CapabilityType.BROWSER_NAME, "Safari");
			capabilitiesiphone.setCapability(CapabilityType.VERSION, "7.1");
			capabilitiesiphone.setCapability("deviceName", "iPhone");
			capabilitiesiphone.setCapability("platformName", "iOS");
		    try {
				localDriver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilitiesiphone);
		    	//localDriver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		    	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		    break;
			
			
			default:
			System.out.println(" [WARN] - Selected Default driver setting - FirefoxDriver()");
			localDriver = new FirefoxDriver();
		}

		return localDriver;
	}
	
	/*	
	 * Method that returns configured Selenium WebDriver with desired capabilities based on browser
	 * and proxy settings.
	 */
	public static RemoteWebDriver setRemoteWebDriver(BrowserType browser, String hubURL,  String proxyname, String proxyport){
		DesiredCapabilities capabilities = null;
		RemoteWebDriver remoteDriver = null;
		org.openqa.selenium.Proxy proxy = null;
		
		switch(browser)
		{
		case IE:
			capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
			break;
		case FIREFOX:
			if(!proxyname.equals("")){
			
				proxy = new org.openqa.selenium.Proxy(); 
				proxy.setHttpProxy(proxyname+":"+proxyport);
				proxy.setSslProxy(proxyname+":"+proxyport); 
				proxy.setFtpProxy(proxyname+":"+proxyport); 
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(CapabilityType.PROXY, proxy); 
			}else{
				capabilities = DesiredCapabilities.firefox();
			}
			    try {
					//remoteDriver = new RemoteWebDriver(new URL("http://172.16.171.65:4445/wd/hub"), capabilities);
			    	remoteDriver = new RemoteWebDriver(new URL("http://192.168.0.13:4444/wd/hub"), capabilities);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
	
			break;
			
		case CHROME:
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);
			break;
			
		case SAFARI:
            capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
			break;
			
		case IPHONE:
			System.out.println(" [Info-APCap] - iPhone device selected - remote");
			DesiredCapabilities capabilitiesiphone = new DesiredCapabilities();
			capabilitiesiphone.setCapability(CapabilityType.BROWSER_NAME, "Safari");
			capabilitiesiphone.setCapability(CapabilityType.VERSION, "7.1");
			capabilitiesiphone.setCapability("deviceName", "iPhone");
			capabilitiesiphone.setCapability("platformName", "iOS");
			capabilitiesiphone.setCapability("udid", "1a2e57c018827c67ce56c1e8f00f9656e2dcbb30");
		    try {
				remoteDriver = new RemoteWebDriver(new URL("http://192.168.0.13:4444/wd/hub"), capabilitiesiphone);
	
		    	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		    break;
		    
		case ANDROID:
			System.out.println(" [Info-APCap] - iPhone device selected - remote");
			//TODO:  need iphone implementation 
			break;
			
		default:
		System.out.println(" - Selected Default driver setting for RemoteWebDriver - FirefoxDriver()");
		remoteDriver = new FirefoxDriver();
		
		}
		
		
		return remoteDriver;
	}
	
	
	public static DesiredCapabilities setDesiredCapabilities(BrowserType browser)
	{
		
		DesiredCapabilities capabilities = null;
		
		switch(browser)
		{
		case IE:
			capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
            capabilities.setCapability("requireWindowFocus", true);
			break;
		case FIREFOX:
			capabilities = DesiredCapabilities.firefox();
			break;
			
		case CHROME:
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
            HashMap<String, String> chromePreferences = new HashMap<String, String>();
            chromePreferences.put("profile.password_manager_enabled", "false");
            capabilities.setCapability("chrome.prefs", chromePreferences);
			break;
			
		case SAFARI:
            capabilities = DesiredCapabilities.safari();
            capabilities.setCapability("safari.cleanSession", true);
			break;
			
		
		}
		return capabilities;
	}
	

	private static BrowserType getBrowserType(String desiredBrowser)
	{
		BrowserType browser = null;
		
		for (BrowserType b : BrowserType.values())
		{
			if(b.toString().toLowerCase().equals(desiredBrowser)){
				browser = b;
			}
			
		}
		
		if (browser == null){
			System.err.println("[Error] - invalid browser type.  Using Firefox");
			browser = BrowserType.FIREFOX;
		}
		
		return browser;
	}
	
	
	/*
	 * Method that sets location path of IE and Chrome driver. 
	 * 		These browsers required additional driver/server 
	 * 		based on platform being used.
	 * 		Change path to desired location.
	 */
	private static void setSystemProperties(BrowserType browser)
	{		
		
		switch(browser)
		{
		case IE:
			if(PlatformTool.isPlatform("windows")){
				if(PlatformTool.isArchitecture("x86_64")||PlatformTool.isArchitecture("amd64")){
					String pathIEDriver64 = getDriverFilePath("driver.ie.64");
					System.setProperty("webdriver.ie.driver",pathIEDriver64);
				}else{
					String pathIEDriver32 = getDriverFilePath("driver.ie.32");
					System.setProperty("webdriver.ie.driver",pathIEDriver32);
				}
			}else if (PlatformTool.isPlatform("mac")){
				System.err.println("[Error] IE not supported in OSX");
			}else{
				System.err.println("[ERROR] invalid OS");
			}
			break;
		
		
		case CHROME:
			if(PlatformTool.isArchitecture("x86_64")|| PlatformTool.isArchitecture("amd64")){
				if(PlatformTool.isPlatform("windows")){

					String pathChromeDriverWindows = getDriverFilePath("driver.chrome.wondows");
					System.setProperty("webdriver.chrome.driver",pathChromeDriverWindows);
				}
				else if(PlatformTool.isPlatform("mac")){
					
					String pathChromeDriverMac = getDriverFilePath("driver.chrome.mac32");
					System.setProperty("webdriver.chrome.driver",pathChromeDriverMac);
					System.out.println(" Chrome driver Mac: " + pathChromeDriverMac);
					}
				else if (PlatformTool.isPlatform("linux")){
					String pathChromeDriverLinux64 = getDriverFilePath("driver.chrome.linux64");
					System.setProperty("webdriver.chrome.driver",pathChromeDriverLinux64);
				}
			}
			else{
				// chromedriver_win32.zip was used for both 32-bit and 64-bit windows systems
				if(PlatformTool.isPlatform("windows")){
					String pathChromeDriverWindows = getDriverFilePath("driver.chrome.wondows");
					System.setProperty("webdriver.chome.driver",pathChromeDriverWindows);
				}
				// chromedriver_mac32.zip was used for both 32-bit and 64-bit mac systems
				else if(PlatformTool.isPlatform("mac")){
					String pathChromeDriverMac = getDriverFilePath("driver.chrome.mac32");
						System.setProperty("webdriver.chome.driver",pathChromeDriverMac);
					}
				else if (PlatformTool.isPlatform("linux")){
					String pathChromeDriverLinux32 = getDriverFilePath("driver.chrome.linux32");
					System.setProperty("webdriver.chrome.driver",pathChromeDriverLinux32);
				}
			}
			
			break;
		}
		
	}
	
	
	public static String getDriverFilePath(String configKey)
	{
		String driverPath = ConfigLoader.getProperty(configKey);
		
		if (driverPath == null || driverPath.isEmpty())
		{
			System.err.println("[ERROR] No file path was provided for driver "+ configKey);
			System.err.println("Verify config.properties file and assign value for " + configKey);
			return null;
		}else{
			
			File f = new File(driverPath);
			
			if(f.exists()){
				return driverPath;
			}else{
				System.err.println("[ERROR] No driver file was found at - " + driverPath);
				return null;
			}
		}
	}

}
