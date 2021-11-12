package com.w2a.testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
	public void openAccountTest(String customer, String currency) throws InterruptedException {
		
		
		//runmode---Y
				if (!TestUtil.isTestRunnable("openAccountTest", excel)) {
					
					throw new SkipException("Skipping The Test "+"openAccountTest".toUpperCase()+" as Runmode is No");
					
				}
		
		click("openaccount_CSS");
		select("customer_CSS", customer);
		select("currency_CSS", currency);
		click("process_CSS");

		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		Thread.sleep(2000);
		alert.accept();

	}

	/*
	 * @DataProvider public Object[][] getData(){
	 * 
	 * String sheetName="OpenAccountTest";
	 * 
	 * int rows=excel.getRowCount(sheetName); int
	 * cols=excel.getColumnCount(sheetName);
	 * 
	 * Object[][] data=new Object[rows-1][cols];
	 * 
	 * for(int rowNum=2;rowNum<=rows;rowNum++) {
	 * 
	 * for (int colNum = 0; colNum < cols; colNum++) {
	 * 
	 * //data[0][0] data[rowNum-2][colNum] =excel.getCellData(sheetName, colNum,
	 * rowNum); }
	 * 
	 * }
	 * 
	 * return data;
	 * 
	 * }
	 */
}
