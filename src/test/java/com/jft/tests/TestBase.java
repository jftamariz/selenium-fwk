package com.jft.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.jft.framework.ConfigLoader;
import com.jft.framework.WebDriverBase;

public class TestBase {

	protected WebDriver driver = null;
	protected String env = null;
	protected String nameSection = null;
	protected String runtype = null;
	protected String browser = null;
	protected String proxyname = null;
	protected String proxyport = null;
	
	@BeforeClass(alwaysRun=true)
	public void setUp(){

		runtype = ConfigLoader.getProperty("drivertype").toLowerCase();
		browser = ConfigLoader.getProperty("browser").toLowerCase();
		proxyport = ConfigLoader.getProperty("proxyport");
		System.out.println(" [Info] - drivertype: " + runtype);
		System.out.println(" [Info] - browser: " + browser);
		System.out.println(" [Info] - proxyname: " + proxyname);
		System.out.println(" [Info] - proxyport: " + proxyport);
		
		if(runtype.equals("local")){
			
			driver = WebDriverBase.getLocalWebDriver(browser.toLowerCase(), "","");
		
		}
		else if (runtype.equals("remote")){
			driver = WebDriverBase.getRemoteWebDriver(browser.toLowerCase(),ConfigLoader.getProperty("grid.url"), "", "");
			//driver = WebDriverBase.getRemoteWebDriver(browser,"http://172.16.171.65:4445/wd/hub", proxy, proxyport);
			
		}else{
			System.out.println("[ERROR} invalid runtype config.properties");
		}
	}
	
    @AfterClass(alwaysRun=true)
    public void tearDown(){
    	
	   	if(driver != null){

	       	driver.quit();
	   	}
	   	
	   	driver.quit();	 
   	}
	
}
