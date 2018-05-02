package com.mss;
import java.io.File;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Screenshot {
	String imagepath;
public Screenshot(WebDriver driver, String dest,String repPath){
	imagepath="D:\\screen\\"+dest+".png";
	try {
		TakesScreenshot takeScreenshot=(TakesScreenshot)driver;
		File file=takeScreenshot.getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(file, new File(imagepath) );
	} catch (Exception exception) {
		// TODO Auto-generated catch block
		exception.printStackTrace();
	}
}
}
