package com.mss;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.TimeoutException;
import static org.junit.Assert.fail; 
import org.openqa.selenium.support.ui.WebDriverWait;


public class GettingData extends Extents {
	protected static final Logger logger = Logger.getLogger(GettingData.class);
	static WebDriver driver;
	boolean condition= false;
	int testsheet, mainsheet;
	Properties properties;
	GettingReports gettingReports=new GettingReports();
	String excelFile;
	String repPath;
	 WebDriverWait wait; 

	
	public GettingData(WebDriver driver,Properties properties,String excelFile,String repPath){
		this.repPath=repPath;
		this.driver=driver;
		this.properties=properties;
		this.excelFile=excelFile;
			}
	public String operation(String stepNumber, String keyword, String objectName, String objectType, String value,int row, String testCase, int resultRow,String driverName,ExtentTest extentTest ) {
		try{
			switch(keyword){
			case "GOTOURL":
				
				
				driver.manage().window().maximize();
				Thread.sleep(4000);
				driver.navigate().to(properties.getProperty(objectName));
				
				condition= true;
				setData(condition,row,testCase,resultRow,driverName);
				extentTest.log(LogStatus.PASS, stepNumber,"User is able to access url");
				logger.info("The user is able to access URL");
	          break;
	          
			case "WRITETEXT":
				try{
				WebElement writetext=driver.findElement(this.getObject(objectName,objectType));
				highlight(writetext);
				fluentWait(this.getObject(objectName,objectType)).clear();
				fluentWait(this.getObject(objectName,objectType)).sendKeys(value);
				condition= true;
				setData(condition,row,testCase,resultRow,driverName);
				extentTest.log(LogStatus.PASS, stepNumber,objectName+" is entered");
				}
				catch(Exception exception){
				
				}
				break;
				
			case "CLICK":
				try
				{
//					clickAnElementByLinkText("Logout");		
				WebElement click=driver.findElement(this.getObject(objectName,objectType));
                 //for IE
//				WebElement click=driver.findElement(this.getObject(objectName,objectType)).sendKeys("\n");
				
				
				

				logger.info("prop="+properties);
				logger.info("object name="+objectName);
				logger.info("object type="+objectType);
				logger.info("click="+click);
				highlight(click);
//				JSClick(click);
				Thread.sleep(500);
			fluentWait(this.getObject(objectName,objectType)).click();
			logger.info("get object==="+getObject(objectName,objectType));
			Thread.sleep(500);
			}
			catch(Exception exception1){
			logger.error("exception"+exception1.getMessage());
				
			//	fluentWait(this.getObject(properties,objectName,objectType)).click();
			}
				condition= true;
				setData(condition,row,testCase,resultRow,driverName);
				extentTest.log(LogStatus.PASS, stepNumber,objectName+"'s clicking function is working properly");
				break;
				
			 case "POPUP":
					int popup_number=Integer.parseInt(value);
					popups(driver,popup_number);
					Thread.sleep(500);
					condition= true;
					Thread.sleep(500);
					setData(condition,row,testCase,resultRow,driverName);
					extentTest.log(LogStatus.PASS, stepNumber, "New "+objectName+" page is opened");
					break;
					
				case "UNPOPUP":
					Thread.sleep(500);
					int newpopup=Integer.parseInt(value);
					Thread.sleep(500);
					driver.close();
					popups(driver,newpopup);
					Thread.sleep(500);
					condition= true;
					Thread.sleep(500);
					setData(condition,row,testCase,resultRow,driverName);
					extentTest.log(LogStatus.PASS, stepNumber, objectName+" is working properly" );
					break;
					
			case "SELECT":
				logger.info(value);
				WebElement selected=driver.findElement(this.getObject(objectName,objectType));
				highlight(selected);
				Select select=new Select(selected);
				if(value.contains("_")){
					String[] spilts=value.split("_");
					value = " ";
					for(int a=0;a<spilts.length;a++){
						value=value+spilts[a]+" ";
						logger.info(value);
					}
				}
				select.selectByVisibleText(value.trim());
			    condition= true;
				setData(condition,row,testCase,resultRow,driverName);
				extentTest.log(LogStatus.PASS, stepNumber, objectName+" is selected "+value);
				break;
				
			case "ALERT":
					Thread.sleep(2000);
					driver.switchTo().alert().accept();
					condition= true;
					setData(condition,row,testCase,resultRow,driverName);
					extentTest.log(LogStatus.PASS, stepNumber, objectName+" is accepted");
					break;
					
				case "LOADING":
					WebElement load=driver.findElement(this.getObject(objectName,objectType));
					highlight(load);
					Thread.sleep(500);
					load.click();
					Thread.sleep(5000);
					condition= true;
					setData(condition,row,testCase,resultRow,driverName);
					extentTest.log(LogStatus.PASS, stepNumber, objectName+"'s clicking function is working properly");
					break;
					
				case "ACTION":
					try{
					Actions action = new Actions(driver);
				    WebElement webElement = driver.findElement(this.getObject(objectName,objectType));
				    
				    highlight(webElement);
				    action.moveToElement(webElement).build().perform();
				    condition= true;
					setData(condition,row,testCase,resultRow,driverName);
					if(value.equalsIgnoreCase("Detours") || value.equalsIgnoreCase("Screen") ){
						mark(webElement);
						demand(stepNumber,"Mouse over on "+objectName,"pass",extentTest);
						nomark(webElement);
					             }
					      else
					    	  extentTest.log(LogStatus.PASS, stepNumber, objectName+" is enabled");	
				   Thread.sleep(500);
					}catch (NoSuchElementException e) {
						logger.info("exception raised"+e.getMessage());
				    }
					
					break;
				
				case "SCROLL":
					JavascriptExecutor javascript = ((JavascriptExecutor) driver);
				    WebElement element = driver.findElement(this.getObject(objectName,objectType));
				    javascript.executeScript("arguments[0].scrollIntoView(true);",element);
				    highlight(element);
				    condition= true;
					setData(condition,row,testCase,resultRow,driverName);
					extentTest.log(LogStatus.INFO, stepNumber, objectName+"' page scrolled");
					break;
					
				case "SLEEP":
					float changeToFloat=Float.parseFloat(value);
					int changeToInt=(int) changeToFloat;
					Thread.sleep(changeToInt);
					break;
				
					/* Added by Sunita */
				case "VALIDATION":
					if(!value.equalsIgnoreCase("screen")){
					WebElement valid=fluentWait(this.getObject(objectName, objectType));
					highlight(valid);
					Select se=new Select(valid);
					String validate=se.getFirstSelectedOption().getText();
					JavascriptExecutor js1=(JavascriptExecutor)driver;
					js1.executeScript("alert('"+validate+"');");
					Thread.sleep(5000);
					condition= true;
					setData(condition,row,testCase,resultRow,driverName);
					demand(stepNumber,"Selected text is  "+validate,"pass",extentTest);
					driver.switchTo().alert().accept();
					}
					else{
						String alertData=driver.switchTo().alert().getText();
						condition= true;
						setData(condition,row,testCase,resultRow,driverName);
						demand(stepNumber,"Alert text is  "+alertData,"pass",extentTest);
						driver.switchTo().alert().accept();
					}
					break;
					
		case "SELECT FIELDS":
					String selection=properties.getProperty(objectName);
					selection=selection.replace("--",value);
					System.out.println(value);
					System.out.println(selection);
					WebElement option=driver.findElement(By.xpath(selection));
					highlight(option);
					JavascriptExecutor js=(JavascriptExecutor)driver;
					js.executeScript("arguments[0].click();", option);
					condition= true;
					setData(condition,row,testCase,resultRow,driverName);
//					setData(condition,p,row,sh,resultRow,driverName);
					extentTest.log(LogStatus.PASS, stepNumber, objectName+" is selected "+value);
					break;

					/* Edited by Sunita */
				case "SCREEN ENABLE":
					Thread.sleep(2000);
					int paraCount=0;
					String[] screenobject=objectName.split(";");
					String[] screentype=objectType.split(";");
					for(int r=0;r<screenobject.length;r++){
					By parameter=this.getObject(screenobject[r],screentype[r]);
					if(isElementPresent(parameter)){
						WebElement enable = driver.findElement(parameter);
						highlight(enable);
						mark(enable);
						paraCount++;
					}
					else{
						condition= true;
						setData(condition,row,testCase,resultRow,driverName);
					}
				
					}
					if(paraCount==screenobject.length)
					demand(stepNumber, "Selected elements are present","pass", extentTest);
					else
						demand(stepNumber, "Some elements are not present. Please check once.","fail", extentTest);
					for(int r=0;r<screenobject.length;r++){
						By parameter=this.getObject(screenobject[r],screentype[r]);
						if(isElementPresent(parameter)){
							WebElement enable = driver.findElement(parameter);
							nomark(enable);
						}
					}
					break;

					
				case "ENABLE":
					By endproducts=this.getObject(objectName,objectType);
					if(isElementPresent(endproducts)){
						WebElement visible = driver.findElement(this.getObject(objectName,objectType));
				//	WebDriverWait wd=(WebDriverWait) new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='result-0']/a")));
						highlight(visible);
						 Thread.sleep(500);
						visible.click();
						condition=true;
						if(value.equalsIgnoreCase("Screen")){
							mark(visible);
							demand(stepNumber, objectName+" is clicked","pass", extentTest);
							nomark(visible);
						}
						else
							extentTest.log(LogStatus.PASS, stepNumber, objectName+" is enabled");	
					}
					else{
						extentTest.log(LogStatus.PASS, stepNumber, objectName+" is disabled");
					}
					setData(condition,row,testCase,resultRow,driverName);
					break;
					
					case "LIST":
						By list=this.getObject(objectName,objectType);
						String other=value.substring(value.indexOf("@")+1,value.indexOf("."));
						String substring=value.substring(0, value.indexOf("_"));
						int size=Integer.parseInt(other);
						if(isElementPresent(list)){
							WebElement prodlist = driver.findElement(this.getObject(objectName,objectType));
							List<WebElement> listImages = null;
							listImages=prodlist.findElements(By.tagName(substring));
							int total=listImages.size();
							int images=0;
							String main=value.substring(value.indexOf(".")+1,value.indexOf("-"));
							String mainNum=value.substring(value.indexOf("-")+1);
							int mainNumber=Integer.parseInt(mainNum);
							if(value.contains(main)){
									listImages=prodlist.findElements(By.tagName(main));
									images=listImages.size();
							}
							if(total/size==images/mainNumber){
							if(value.contains("screen")){
								condition= true;
								setData(condition,row,testCase,resultRow,driverName);
								mark(prodlist);
								demand(stepNumber, "Number of images are "+total/size, "pass", extentTest);
								nomark(prodlist);
							}
							else{
								condition= true;
								setData(condition,row,testCase,resultRow,driverName);
								extentTest.log(LogStatus.PASS, stepNumber, "Number of images are "+total/size);
							}
							}
							else{
								condition= false;
								setData(condition,row,testCase,resultRow,driverName);
								mark(prodlist);
								demand(stepNumber, "Number of images are "+total/size+" but showing images are "+images/mainNumber, "fail", extentTest);
								nomark(prodlist);
							}
						}
						else{
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
							extentTest.log(LogStatus.PASS, stepNumber, objectName+" is disabled");
						}
						
						break;
						
					case "SCROLL END":
						float scrollFloat=Float.parseFloat(value);
						int scrollInt=(int) scrollFloat;
						for(int i=0;i<=scrollInt;i++){
						 Actions actions = new Actions(driver);
						 actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform();
						 Thread.sleep(1000);
						}
						break;
					case "FRAME":
						int frame_size=driver.findElements(By.tagName("iframe")).size();
						logger.info(frame_size);
						
							try{
								WebElement fr=driver.findElement(this.getObject(objectName,objectType));
							driver.switchTo().frame(fr);
							logger.info("frame to specific element");
							}
							catch(NoSuchFrameException ns){
								logger.info("continue procedure");
								
						}
						break;
case "MAIN":
						driver.switchTo().defaultContent();
						break;
						
					case "TAB":
						driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + value);
						// Thread.sleep(2000);
						break;

						
				
			/*		case "VERIFY TEXT":								
						// for verifying text in application
						WebElement verify=fluentWait(this.getObject(objectName,objectType));
						highlight(verify);
						String actual_text=verify.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_text);
						System.out.println(value);
						if(actual_text.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.INFO, stepNumber,objectName+" is showing "+actual_text);
							demand(stepNumber,"Selected Text is present on page "+value,"info",extentTest);
							return "verify";
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							if(isElementPresent(this.getObject(objectName,objectType))){
								mark(verify);
							demand(stepNumber,objectName+" is not showing "+actual_text,"fail",extentTest);
							nomark(verify);
							return "not verify";
							}
							else if(true){
								demand(stepNumber,objectName+" is not  showing "+actual_text,"fail",extentTest);	
								return "not verify";
							}
							
						}
						
						break;
				
						*/
						
					 case "VERIFY TEXT":                    
	                        int totalObjects=0;
	                        String display="";
	                        // for verifying text in application
	                        String objects[]=objectName.split(";");
	                        String locators[]=objectType.split(";");
	                        String text[]=value.split(";");
	                        
//	                        clickAnElementByLinkText("Logout");
	                        for(int i=0;i<objects.length;i++){
	                            try{
	                        WebElement verify=fluentWait(this.getObject(objects[i],locators[i]));
	                        highlight(verify);
	                        String actual_text=verify.getText().trim();
	                        text[i]=text[i].replace("_", " ");
	                        System.out.println("Actual Text -->"+actual_text);
	                        System.out.println(value);
	                        if(actual_text.contains(text[i].trim())){
	                            display=display+actual_text+"\n";
	                            mark(verify);
	                            totalObjects++;
	                        }
	                        else{
	                            if(isElementPresent(this.getObject(objects[i],locators[i]))){
	                                mark(verify);
	                            }
	                        }
	                        }catch(Exception e){
	                            System.out.println("its failed");
	                            e.printStackTrace();
	                        }
	                        }
	                        if(totalObjects==objects.length){
	                            demand(stepNumber,"Selected text is available on page and  "+display,"info",extentTest);
	                            for(int j=0;j<objects.length;j++){
	                                if(isElementPresent(this.getObject(objects[j],locators[j]))){
	                                WebElement verify1=fluentWait(this.getObject(objects[j],locators[j]));
	                                nomark(verify1);
	                                }
	                            }
	                        }else{
	                            demand(stepNumber,"Failed! Expected content '"+value+ " ' is not available.","fail",extentTest);
	                            return "not verify";
	                        }
	                        break;
						
					case "IDENTIFY ID":
//						clickAnElementByLinkText("Logout");
						By id=By.id(value);
						
						if(isElementPresent(id)){
							WebElement idn=driver.findElement(id);
							highlight(idn);
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							mark(idn);
							demand(stepNumber,"Selected Id is available on page"+value,"info",extentTest);
//							nomark(idn);
							
//							extentTest.log(LogStatus.INFO, stepNumber, objectName+"Selected Id is available on page");
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Selected Element is not available on page "+value,"fail",extentTest);
							return "not verify";
						}
						break;

					case "IDENTIFY XPATH":
						By xpath=By.xpath(value);
						if(isElementPresent(xpath)){
							WebElement xpn=driver.findElement(xpath);
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
							mark(xpn);
							demand(stepNumber,"Application is having "+value,"pass",extentTest);
//							nomark(xpn);
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Application is having "+value,"fail",extentTest);
						}
						break;
						
						
						/* Author- Aklakh*/
					case "VERIFY ELEMENT":      
//						clickAnElementByLinkText("Logout");
                        int totalObj=0;
                        String displayObj="";
                        // for verifying text in application
                        String objectsE[]=objectName.split(";");
                        String locatorsE[]=objectType.split(";");
                        String textE[]=value.split(";");
                    
                        for(int i=0;i<objectsE.length;i++){
                            try{
                        WebElement verify=fluentWait(this.getObject(objectsE[i],locatorsE[i]));
                        highlight(verify);
                        String actual_text=verify.getText().trim();
                        textE[i]=textE[i].replace("_", " ");
                        System.out.println("Actual Text -->"+actual_text);
                        System.out.println(value);
                        if(actual_text.contains(textE[i].trim())){
                        	displayObj=displayObj+actual_text+"\n";
                            mark(verify);
                            totalObj++;
                        }
                        else{
                            if(isElementPresent(this.getObject(objectsE[i],locatorsE[i]))){
                                mark(verify);
                            }
                        }
                        }catch(Exception e){
                            System.out.println("its failed");
                            e.printStackTrace();
                        }
                        }
                        if(totalObj==objectsE.length){
                            demand(stepNumber,"Selected text is available on page and  "+displayObj,"info",extentTest);
                            for(int j=0;j<objectsE.length;j++){
                                if(isElementPresent(this.getObject(objectsE[j],locatorsE[j]))){
                                WebElement verify1=fluentWait(this.getObject(objectsE[j],locatorsE[j]));
                                nomark(verify1);
                                }
                            }
                        }else{
                            demand(stepNumber,"Failed! Expected content '"+value+ " ' is not available.","fail",extentTest);
//                            return "not verify";
                        }
                        break;
					
					
					case "IDENTIFY NEG ID":
						By negId=By.id(value);
						if(isElementPresent(negId)){
							WebElement idn=driver.findElement(negId);
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							mark(idn);
							demand(stepNumber,"No Result Found - "+value,"info",extentTest);
//							nomark(idn);
							
//							extentTest.log(LogStatus.INFO, stepNumber, objectName+"Selected Id is available on page");
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Selected Element is not available on page "+value,"warning",extentTest);
						}
						break;
					case "VERIFY SKU":	
//						clickAnElementByLinkText("Logout");
						// for verifying text in application
						WebElement verifySKU=fluentWait(this.getObject(objectName,objectType));
						highlight(verifySKU);
						String actual_textSKU=verifySKU.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_textSKU);
						System.out.println(value);
						if(actual_textSKU.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.INFO, stepNumber,objectName+" is showing "+actual_textSKU);
							demand(stepNumber,"SKU info  "+value,"info",extentTest);
							 nomark(verifySKU);
						}
						else{
//							condition= false;
//							setData(condition,row,testCase,resultRow,driverName);
//							if(isElementPresent(this.getObject(objectName,objectType))){
//								mark(verify);
//							demand(stepNumber,objectName+" is showing "+actual_text,"fail",extentTest);
//							nomark(verify);
//							return "not verify";
//							}
//							else if(true){
//								demand(stepNumber,objectName+" is showing "+actual_text,"fail",extentTest);	
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Selected SKU  is not available "+value,"fail",extentTest);
							return "not verify";
							}
													
						break;
						
					case "VERIFY SKUQTY":	
//						clickAnElementByLinkText("Logout");
						// for verifying text in application
						WebElement verifySKUQty=fluentWait(this.getObject(objectName,objectType));
						highlight(verifySKUQty);
						String actual_textSKUQty=verifySKUQty.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_textSKUQty);
						System.out.println(value);
						if(actual_textSKUQty.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.INFO, stepNumber,objectName+" is showing "+actual_textSKU);
							demand(stepNumber,"Reached SKU quantity Limit ->  "+value,"warning",extentTest);
							nomark(verifySKUQty);
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Selected SKU  is not available "+value,"fail",extentTest);
							return "not verify";
							}
													
						break;
						
					case "VERIFY NOSKU":	
//						clickAnElementByLinkText("Logout");
						// for verifying text in application
						WebElement verifyNoSKU=fluentWait(this.getObject(objectName,objectType));
						highlight(verifyNoSKU);
						String actual_textNoSKU=verifyNoSKU.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_textNoSKU);
						System.out.println(value);
						if(actual_textNoSKU.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.INFO, stepNumber,objectName+" is showing "+actual_textSKU);
							demand(stepNumber,"No SKU are available ->  "+value,"warning",extentTest);
							nomark(verifyNoSKU);
						}
						else{
							condition= false;
							setData(condition,row,testCase,resultRow,driverName);
							demand(stepNumber,"Selected SKU  is not available "+value,"fail",extentTest);
							return "not verify";
							}
													
						break;
						
					case "VERIFY LOGIN":								
						// for verifying text in application
						
						WebElement verifyLogin=fluentWait(this.getObject(objectName,objectType));
						if(!(verifyLogin.isDisplayed())){
							verifyLogin.isDisplayed();
						}
						highlight(verifyLogin);
						String actual_textLogin=verifyLogin.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_textLogin);
						System.out.println(value);
						if(actual_textLogin.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.WARNING, stepNumber,objectName+" is entered wrong userid or password "+actual_textLogin);
							demand(stepNumber,"User entered wrong userid or password  "+value,"warning",extentTest);
							return "verify";
						}
						else{
							System.out.println("in else "+value);
//							condition= false;
//							setData(condition,row,testCase,resultRow,driverName);
//							if(isElementPresent(this.getObject(objectName,objectType))){
//								mark(verify);
//							demand(stepNumber,objectName+" is showing "+actual_text,"fail",extentTest);
//							nomark(verify);
//							return "not verify";
//							}
//							else if(true){
//								demand(stepNumber,objectName+" is showing "+actual_text,"fail",extentTest);	
//								return "not verify";
							}
													
						break;
						
					case "VERIFY ERROR":								
						// for verifying text in application
						WebElement verifyError=fluentWait(this.getObject(objectName,objectType));
						highlight(verifyError);
						String actual_error=verifyError.getText().trim();
						value=value.replace("_", " ");
						System.out.println("Actual Text -->"+actual_error);
						System.out.println(value);
						if(actual_error.contains(value.trim())){
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
//							extentTest.log(LogStatus.INFO, stepNumber,objectName+" is showing "+actual_text);
							demand(stepNumber,"Error on page "+value,"warning",extentTest);
							return "verify";
						}
						else{
//							condition= false;
//							setData(condition,row,testCase,resultRow,driverName);
//							if(isElementPresent(this.getObject(objectName,objectType))){
//								mark(verifyError);
//							demand(stepNumber,objectName+" is not showing "+actual_error,"fail",extentTest);
//							nomark(verifyError);
//							return "not verify";
//							}
//							else if(true){
//								demand(stepNumber,objectName+" is not  showing "+actual_error,"fail",extentTest);	
//								return "not verify";
//							}
							
						}
						
						break;
						
					case "FEATUREDLIST":
						By FList=this.getObject(objectName,objectType);
						String FOther=value.substring(value.indexOf("@")+1,value.indexOf("."));
						String FSubstring=value.substring(0, value.indexOf("_"));
						int FSize=Integer.parseInt(FOther);
						if(isElementPresent(FList)){
							WebElement prodlist = driver.findElement(this.getObject(objectName,objectType));
							List<WebElement> listImages = null;
							listImages=prodlist.findElements(By.tagName(FSubstring));
							int total=listImages.size();
							int images=0;
							String main=value.substring(value.indexOf(".")+1,value.indexOf("-"));
							String mainNum=value.substring(value.indexOf("-")+1);
							int mainNumber=Integer.parseInt(mainNum);
							if(value.contains(main)){
									listImages=prodlist.findElements(By.tagName(main));
									images=listImages.size();
							}
							if(total/FSize==images/mainNumber){
							if(value.contains("screen")){
								condition= true;
								setData(condition,row,testCase,resultRow,driverName);
								mark(prodlist);
								demand(stepNumber, "Number of images are "+total/FSize, "pass", extentTest);
								nomark(prodlist);
							}
							else{
								condition= true;
								setData(condition,row,testCase,resultRow,driverName);
								extentTest.log(LogStatus.PASS, stepNumber, "Number of images are "+total/FSize);
							}
							}
							else{
								condition= false;
								setData(condition,row,testCase,resultRow,driverName);
								mark(prodlist);
								demand(stepNumber, "Number of images are "+total/FSize+" but showing images are "+images/mainNumber, "fail", extentTest);
								nomark(prodlist);
							}
						}
						else{
							condition= true;
							setData(condition,row,testCase,resultRow,driverName);
							extentTest.log(LogStatus.PASS, stepNumber, objectName+" is disabled");
						}
						
						break;
						
						default:
							new Screenshot(driver, new TimeDateFormats().timeform(),repPath);
							break;
							
					}
					}
		
		catch(NoSuchElementException noElement){
			condition=false;
			setData(condition,row,testCase,resultRow, driverName);
			
			try {
				Thread.sleep(2000);
				mark(driver.findElement(this.getObject(objectName,objectType)));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			demand(stepNumber, objectName+" is failed.","fail", extentTest);
			try {
				nomark(driver.findElement(this.getObject(objectName,objectType)));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			noElement.printStackTrace();
		}
		
		catch(Exception exception){
			condition=false;
			setData(condition,row,testCase,resultRow,driverName);
			demand(stepNumber, objectName+" is failed.","fail",extentTest);
			exception.printStackTrace();
		}
		return "complete";
	}
	
	public By getObject( String objectName, String objectType) {
		
//		LOG.info("objectType"+objectType);
		if(objectType.equalsIgnoreCase("ID")){
			return By.id(properties.getProperty(objectName));
		}
		else if(objectType.equalsIgnoreCase("LINK")){
			return By.linkText(properties.getProperty(objectName));
		}
		else if(objectType.equalsIgnoreCase("CLASS")){
			return By.className(properties.getProperty(objectName));
		}
		else if(objectType.equalsIgnoreCase("XPATH")){
		//	LOG.info("objectType123"+objectType);
			return By.xpath(properties.getProperty(objectName));
		
		}
		else if(objectType.equalsIgnoreCase("CSS")){
			return By.cssSelector(properties.getProperty(objectName));
		}
		else if(objectType.equalsIgnoreCase("NAME")){
			return By.name(properties.getProperty(objectName));
		}
		return null;
	}
	
	
	public void checkForNoAlert() { 
       
        try { 
            wait.until(ExpectedConditions.alertIsPresent()); 
            fail("Alert is showing and not expected to popup"); 
        } catch (TimeoutException te) { 
 
          System.out.println("Timeout exception-->"+te);  ; // OK we don't want to see the alert 
        } 
 
    } 
	public void popups(WebDriver driver,int window){
		ArrayList<String> windowHandles=new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(windowHandles.get(window));
		driver.manage().window().maximize();
		logger.info(driver.getTitle());
	}

	
	public WebElement fluentWait(final By locator){
		Wait<WebDriver> wait= new FluentWait<WebDriver>(driver)
			.withTimeout(30, TimeUnit.SECONDS)
			.pollingEvery(50, TimeUnit.MILLISECONDS)
			.ignoring(NoSuchElementException.class);
		
		WebElement webElement= wait.until(new Function<WebDriver, WebElement>(){
			public WebElement apply(WebDriver driver){
				return driver.findElement(locator);
			}
		});
		/*WebElement myDynamicElement = 
				(new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.id("usrUTils")));*/
		return webElement;
	}
		
	public void setData(boolean result,int flag, String sheetindex,int resultRow,String browser){
		//System.out.println("set data gettingReports.getProperties()"+properties);
		String string=excelFile;
		WriteExcelSheet writeExcel = new WriteExcelSheet(string);
		if(browser.contains("Firefox")){
			testsheet=5;
			mainsheet=3;
		}
		else if(browser.contains("Chrome")){
			testsheet=5;
			mainsheet=3;
		}
		else if(browser.contains("Internet")){
			testsheet=7;
			mainsheet=5;
		}
	if(result){
      logger.info(flag);
		writeExcel.writeData(sheetindex,flag,testsheet,"Pass",result);
		
	}
		else{
			writeExcel.writeData(sheetindex,flag,testsheet,"Fail",result);
			writeExcel.writeData("SCENARIOS",resultRow,mainsheet,"Fail",result);
			
		}
	}

	public void highlight(WebElement element1) throws Exception {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red');",element1);
		//Thread.sleep(500);
		javascript.executeScript("arguments[0].setAttribute('style','border: solid 2px white');",element1);
	}

	public void mark(WebElement element1) throws Exception{
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("arguments[0].setAttribute('style','border: solid 2px red');",element1);
	}

	public void nomark(WebElement element1) throws Exception{
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("arguments[0].setAttribute('style','border: none');",element1);
	}
		
	public boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException exception) {
	      return false;
	    }
	  }

	public void demand(String step,String any,String result,ExtentTest extentTest){
		Screenshot screenshot=new Screenshot(driver, new TimeDateFormats().timeform(),repPath);
		//String img = extentTest.addScreenCapture(screenshot.imagepath);
		String path=screenshot.imagepath;
//		String img = extentTest.addScreenCapture("http://172.17.0.151:8080//QAFramework//screen//"+path.substring(path.lastIndexOf("\\")+1,path.length()));
		
		String img = extentTest.addScreenCapture(path);
				if(result.equalsIgnoreCase("pass")){
			extentTest.log(LogStatus.PASS, step, any+" The page screenshot: \n"+img );
		}
		else if(result.equalsIgnoreCase("fail")){
			extentTest.log(LogStatus.FAIL, step, any+" The page screenshot: \n"+img );
		}
		else if(result.equalsIgnoreCase("warning")){
			extentTest.log(LogStatus.WARNING, step, any+" The page screenshot: \n"+img );
		}
		else if(result.equalsIgnoreCase("info")){
			extentTest.log(LogStatus.INFO, step, any+" The page screenshot: \n"+img );
		}
	}
	
	public static void JSClick(WebElement element){
		((JavascriptExecutor) driver).executeScript("arguments[0].SendKeys(Keys.enter)", element);
	}

//	public void clickAnElementByLinkText(String linkText) {
//	    wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(linkText)));
//	    driver.findElement(By.linkText(linkText)).click();
//	}

	}

			
	
		
		
		
		
		
		
		
		
		
		
		
		
		