package com.mss;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile{
	public Properties getPath(String file3) throws Exception{
		//Properties p=getDataProp();
		File file= new File(file3);
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties= new Properties();
		properties.load(fileInput);
		return properties;
	}
}