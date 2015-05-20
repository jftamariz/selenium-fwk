package com.jft.tests;

import org.testng.annotations.Test;

public class LiveSafeTest extends TestBase{
	
	//@Test
	public void LiveSafeHomepageTest(){
		driver.switchTo().window("WEBVIEW");
		System.out.println(" [Info] - Opening LiveSafe ");
		driver.get("http://www.livesafemobile.com");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void LiveSafeAndroidTest(){
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.switchTo().window("WEBVIEW");
		System.out.println(" [Info] - Opening LiveSafe Android App ");
		//driver.get("http://www.livesafemobile.com");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
