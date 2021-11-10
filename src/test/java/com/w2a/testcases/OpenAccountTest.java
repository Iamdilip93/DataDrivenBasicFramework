package com.w2a.testcases;


import org.testng.annotations.Test;

import com.w2a.base.TestBase;
import com.w2a.utilities.TestUtil;

public class OpenAccountTest extends TestBase {

	@Test(dataProviderClass =TestUtil.class,dataProvider = "dp" )
	public void openAccountTest(String customer,String currency)  {
		
		
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
