package com.mss;
import java.io.File;

import junit.framework.Protectable;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


public class Extents {
	 String folder="";
	 ExtentReports extentReports1;
	// protected static ExtentTest extentTest;
	
	protected void startExtent(String repPath,String repName,String fname,String projType,String testingType){
		
	  folder=repPath+"\\Reports\\"+fname+"\\"+repName+"_"+projType+"_"+testingType+"_"+new TimeDateFormats().timeReport()+".html";
	  extentReports1= new ExtentReports(folder,true);
	 // System.out.println("repName in extents="+repName);
	 String time=new TimeDateFormats().timeReport();
	 System.out.println("new time="+time);
	  String newFilename;
	  File f = new File(folder);
	  System.out.println("new file is="+f);
	  int version = 1;
	  while (f.exists())
	  {
	         // newFilename=f + version;
	         File f1 = new File(folder + version);
	          version++;
	          System.out.println("new+ file is="+f1);
	          System.out.println("version="+version);
	  }
	  
	}
	protected void endExtent(ExtentTest extentTest){
		// end test
		extentReports1.endTest(extentTest);
        
        // calling flush writes everything to the log file
		extentReports1.flush();
		
		
	}
	//extentReports1.close();
}
