package com.w2a.testcases;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class BankManagerLoginTest extends TestBase{
	
	@Test
	public void loginAsBankManager() throws InterruptedException, IOException {
		
		verfyEquals("abc","xyz");
		Thread.sleep(2000);
		log.debug("Inside Login Test");
		click("bmlBtn_CSS");
		Thread.sleep(2000);
		
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))),"Login Not Successfull  !!!");
		
		log.debug("Login Successfully Executed");
		
		Assert.fail("Login not Successfull");
		
				
	}

}
