package com.jft.tests;

import org.openqa.selenium.By;
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
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.id("com.livesafe.activities:id/bLogIn")).click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.id("com.livesafe.activities:id/bEmail")).click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.id("com.livesafe.activities:id/editText")).sendKeys("juliotamariz@gmail.com");
		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}

}
