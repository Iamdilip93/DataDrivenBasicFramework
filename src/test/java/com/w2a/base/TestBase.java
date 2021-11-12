package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import com.w2a.utilities.TestUtil;

public class TestBase {
	
	
	/*All The initialization done here
	 * 
	 *  WebDriver
	 *  Properties
	 *  Logs - log4j jar ,log4j.properties,Logger class
	 *  ExtentReports
	 *  DB
	 *  Excel
	 *  Mail 
	 *  ReportNG
	 *  Jenkins
	 *   
     */
	
	public static WebDriver driver=null;
	public static Properties config=new Properties();
	public static Properties OR=new Properties();
	public static FileInputStream fis;
	public static Logger log=Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel=new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\resources\\Excel\\testdata.xlsx");
	public static WebDriverWait wait;
	public ExtentReports rep=ExtentManager.getInstance();
	public static ExtentTest test;
	public static String browser;
	
	@BeforeSuite
	public void setUp()  {
		
		if(driver==null) {
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config File is Loaded !!!!!!!!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		    try {
				fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    try {
				OR.load(fis);
				log.debug("OR File is Loaded !!!!!!!!");
			} catch (IOException e) {
				e.printStackTrace();
			}

		    
		    
		    if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty() ) {
		    	browser=System.getenv("browser");
		    }else {
				browser=config.getProperty("browser");
			}
		    
		    config.setProperty("browser", browser);
		    
		    if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
		    	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\chromedriver.exe");
		    	driver=new ChromeDriver();
		    	log.debug("Chrome Launched !!!!!!!!");
		    }else if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
		    	System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\geckodriver.exe");
		    	driver=new FirefoxDriver();
		    }else if(config.getProperty("browser").equalsIgnoreCase("ie")) {
		    	System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\IEDriverServer.exe");
		    	driver=new InternetExplorerDriver();
		    }else if(config.getProperty("browser").equalsIgnoreCase("edge")) {
		    	System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\Executables\\msedgedriver.exe");
		    	driver=new EdgeDriver();
		    }else {
		    	System.out.println("Entered Wrong Browser");
		    	System.exit(0);
		    }
		
		    driver.get(config.getProperty("testsiteurl"));
		    log.debug("Navigated To ---->"+config.getProperty("testsiteurl"));
		    driver.manage().window().maximize();
		    driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.Wait")),TimeUnit.SECONDS); 
		    wait=new WebDriverWait(driver, 10);
		    
		    
		}
		
	}
	
	
	
	public void click(String locator) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		test.log(LogStatus.INFO, "Clicking on : " + locator);
	}

	public void type(String locator, String value) {

		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}

		test.log(LogStatus.INFO, "Typing in : " + locator + " entered value as " + value);

	}
	
	
	static WebElement  dropdown;
	public void select(String locator,String value) {
		
		if (locator.endsWith("_CSS")) {
			dropdown=driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown=driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown=driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select=new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting From DropDown : " + locator + " value as " + value);
		
	}
	
	public static void verfyEquals(String expected,String actual) throws IOException {
		
		try {
			
			Assert.assertEquals(actual, expected);
		} catch (Throwable e) {
			TestUtil.captureScreenshot();
			
			//ReportNG
			Reporter.log("<br>"+"Verification Failure : "+e.getMessage()+"<br>");
			Reporter.log("<a target =\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=300 width=300></img></a>");
			Reporter.log("<br>");
			Reporter.log("<br>");
			
			//ExtentReports
			test.log(LogStatus.FAIL, "Verification FAILED With Exception : "+e.getMessage());
			test.log(LogStatus.FAIL,test.addScreenCapture(TestUtil.screenshotName));

			
		}
		
	}
	
	
	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
		
	}
	
	
	
	@AfterSuite
	public void tearDown() {
		if(driver!=null) {
			driver.quit();
		}
		
		log.debug("Test Execution Completed  !!!! ");
	}
	
}
