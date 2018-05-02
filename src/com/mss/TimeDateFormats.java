package com.mss;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TimeDateFormats {
	public String timeform(){
	DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	Date date = new Date();
	String timeformat=dateFormat.format(date);
	return timeformat;
}
public String timeReport(){
	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
	Date date = new Date();
	System.out.println("date="+date);
	System.out.println("dateFormat="+dateFormat);
	String timereport=dateFormat.format(date);
	System.out.println("time="+timereport);
	return timereport;
}


}
