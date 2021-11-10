package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;

public class AddCustomerTest extends TestBase {

	@Test(dataProvider = "getData")
	public void addCustomerTest(String firstName,String lastName,String postCode,String alertText) throws InterruptedException {
		
		click("addCustBtn_CSS");
		type("firstname_CSS",firstName);
		type("lastname_XPATH",lastName);
		type("postcode_CSS",postCode);
		click("addBtn_CSS");
		Thread.sleep(2000);	
		Alert alert=wait.until(ExpectedConditions.alertIsPresent());
		
		Assert.assertTrue(alert.getText().contains(alertText));
		Thread.sleep(2000);
		alert.accept();
		Thread.sleep(2000);
		
		Assert.fail("Customer Not Added Successfully  !!!");
	}
	
	
	@DataProvider
	public Object[][] getData(){
		
		String sheetName="AddCustomerTest";
		
		int rows=excel.getRowCount(sheetName);
		int cols=excel.getColumnCount(sheetName);
		
		Object[][] data=new Object[rows-1][cols];
		
		for(int rowNum=2;rowNum<=rows;rowNum++) {
			
			for (int colNum = 0; colNum < cols; colNum++) {
				
				//data[0][0]
				data[rowNum-2][colNum] =excel.getCellData(sheetName, colNum, rowNum);
			}	
			
		}
			
		return data;
		
	}
	
}
