package com.w2a.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

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
