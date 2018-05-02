package com.mss;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

public class GettingReports extends Extents
{
	
	
protected static final Logger logger = Logger.getLogger(GettingReports.class);

	
	Properties properties;
public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
public String gettingReports(String excelFile,String propertyFile, String repPath,String repName,String fname,String projType,String testingType
		,ReadExcelSheet readExcelSheet,String scenarios) throws Exception{ 
	WebDriver driver=null;
	String driverName=null;
	int mainsheet=0;
	
	
	 ExtentTest extentTest=null;
		//PropertyConfigurator.configure("E:/QA_workspace/QAFramework/log4j.properties");
		String browser="chrome";
		startExtent(repPath,repName,fname,projType,testingType);
		if(browser.equalsIgnoreCase("firefox")){
			URL server = new URL("https://www.interface.com/US/en-US/global");
			 
			   /* DesiredCapabilities capabilities = new DesiredCapabilities();
			    capabilities.setBrowserName("firefox");
			  driver = new RemoteWebDriver(server, capabilities);*/
			driver= new FirefoxDriver();
			driverName="Firefox Browser";
			mainsheet=3;
		}
		else if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Browser\\Chrome\\chromedriver.exe");
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--disable-extensions");
			options.addArguments("--test-type");
			
			options.addArguments("disable-infobars");
			//disableing Save password popup for chrome
			Map<String,Object> prefs=new HashMap<String,Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);

			
			driver= new ChromeDriver(options);
			driverName="Chrome Browser";
			mainsheet=3;
		}
       		else if(browser.equalsIgnoreCase("internet")){
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\Browser\\IE\\IEDriverServer.exe");
			
			InternetExplorerDriver IE= new InternetExplorerDriver();
			
			driver= new InternetExplorerDriver();
			driverName="Internet Browser";
			mainsheet=5;
		}
		
		else{
			logger.error("browser is incorrect");
		}
		Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = caps.getBrowserName();
		String browserVersion = caps.getVersion();
		System.out.println("Browser Name: "+browserName+"\n"+"Browser Version: "+browserVersion);
		String os=System.getProperty("os.name").toUpperCase();
		System.out.println(os);
	driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		
	ReadPropertyFile readPropertyFile=new ReadPropertyFile();
	properties= readPropertyFile.getPath(propertyFile);
	//System.out.println(properties+"gettingReports.getProperties()"+getProperties());
	try {
			//ReadExcelSheet readExcelSheet=new ReadExcelSheet(excelFile);
			logger.info("path:"+propertyFile);
			GettingData gettingData= new GettingData(driver,properties,excelFile, repPath);
			//String scenarios=properties.getProperty("mainsheet");
			//logger.info(scenarios);
			//logger.info("count: "+readExcelSheet.count(scenarios));
			//readExcelSheet.removeCells(scenarios,3);
			/*for(int row=1;row<=readExcelSheet.count(scenarios);row++){
			if(readExcelSheet.readData(scenarios, row, 2).equals("Y")){
				  
				readExcelSheet.removeCells(readExcelSheet.readData(scenarios, row, 0),5);
			}
		}*/
		for(int resultRow=1;resultRow<=readExcelSheet.count(scenarios);resultRow++){
			String required=readExcelSheet.readData(scenarios, resultRow, 2);
			String testCaseNum=readExcelSheet.readData(scenarios, resultRow, 1);
			if(required.equals("Y")){
				String testCase=readExcelSheet.readData(scenarios, resultRow, 0);
				logger.info(testCase);
				System.out.println("testcase=="+testCase);
				//HttpSession session = request.getSession();
			//	session.setAttribute("testCase", testCase);
			//	JOptionPane.showMessageDialog(null,testCase,"confirmation",JOptionPane.ERROR_MESSAGE);
				extentTest=extentReports1.startTest(testCaseNum+" -- "+testCase);
				int count= readExcelSheet.count(testCase);
				WriteExcelSheet writeExcelSheet = new WriteExcelSheet(excelFile);
				writeExcelSheet.writeData(scenarios,resultRow,mainsheet,"Pass",true);
				int pass=0,fail=0;
				for(int row=1;row<=count;row++){
					
					String result=gettingData.operation( readExcelSheet.readData(testCase, row, 0) ,readExcelSheet.readData(testCase, row, 1), readExcelSheet.readData(testCase, row, 2), readExcelSheet.readData(testCase, row, 3), readExcelSheet.readData(testCase, row, 4), row,testCase,resultRow,driverName,extentTest);
				
					if(result.equalsIgnoreCase("not verify")){
						break;
					}
				}
				
						}
						/*else if(!(required.equals("")||required.equals("empty"))){
							String testCase=readExcelSheet.readData(scenarios, resultRow, 0);
							logger.info(testCase);
							logger.info(required);
							WriteExcelSheet excel = new WriteExcelSheet(excelFile);
							excel.writeData(scenarios, resultRow, mainsheet, "Invalid Input", false);
						}*/
			
					endExtent(extentTest);
		            }
		logger.info("Test execution is completed");
						driver.quit();
						
					} catch (Exception exception) {
						logger.error("Exception at reading excel");
						driver.quit();
						exception.printStackTrace();
					}
					finally{
						//endExtent();
					    if(browser.equalsIgnoreCase("chrome"))
					    {
					    	
						System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Browser\\Chrome\\chromedriver.exe");
						//System.setProperty("webdriver.chrome.driver", "C:\\CRM_Interface\\WebDrivers\\chromedriver.exe");
						ChromeOptions options=new ChromeOptions();
						options.addArguments("--disable-extensions");                                      
						options.addArguments("--test-type");
						driver= new ChromeDriver(options);
						driver.manage().window().maximize();
						driver.get(folder);
						driver.quit();
						//driver.close();
					    }
					    else
					    {
					    	driver= new FirefoxDriver();
					    	driver.manage().window().maximize();
							driver.get(folder); 
							driver.quit();
							//driver.close();
					    }
					    }
						try {
                        // Desktop.getDesktop().open(new File(prop.getProperty("path")));
							
						} catch (Exception exception) {
							exception.printStackTrace();
						}
						return folder.substring(folder.lastIndexOf("\\")+1);
						//return folder;
					}
                 
			
	} 
	
	

		
	
	

