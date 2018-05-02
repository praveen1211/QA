package com.mss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Confirm {
	protected static final Logger LOG = Logger.getLogger(Confirm.class);
	
	public void passdata() throws Exception{
		
		ExampleReadExcelSheet er= new ExampleReadExcelSheet("D:/office/Testing_excel.xlsx");
		
		int rows=er.count(0);
		String users[][]=new String[rows][2];
		for(int i=0;i<rows;i++){
		users[i][0]=er.readData(0, i, 0);
		LOG.info(users[i][0]);
		users[i][1]=er.readData(0, i, 1);
		LOG.info(users[i][1]);
		}
		
	} 
	public void pass1() throws Exception{
		File src= new File("D:/office/Testing_excel.xlsx");
		FileInputStream fis= new FileInputStream(src);
		XSSFWorkbook wb= new XSSFWorkbook(fis);
		XSSFSheet sheet1= wb.getSheetAt(0);
		int count=sheet1.getLastRowNum();
		//String users[][]=new String[count][2];
		
		for(int j = 0; j < sheet1.getLastRowNum(); j++){
//			sheet1.removeRow(sheet1.getRow(1));
//			sheet1.removeRow(sheet1.getRow(4));
//			System.out.println(isEmpty(sheet1.getRow(j)));
//			System.out.println(sheet1.getRow(j).getPhysicalNumberOfCells());
		    if(sheet1.getRow(j) == null){
		        sheet1.shiftRows(j + 1, sheet1.getLastRowNum(), -1);
		        LOG.info(j);
		        j--;//Adjusts the sweep in accordance to a row removal
		        FileOutputStream dest= new FileOutputStream(src);
		    	wb.write(dest);
		    }
		}
		for(int i=0;i<=sheet1.getLastRowNum();i++){
//		Cell cell= row.getCell(0);
//		if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
//			continue;
//		}
		String user= sheet1.getRow(i).getCell(0).getStringCellValue();
		String pasw= sheet1.getRow(i).getCell(1).getStringCellValue();
		LOG.info(user+"    "+pasw);
		//new ExcelReading("D:/office/Testing_excel.xlsx",i,2,0,"Pass",true);
		}
//		wb.close();
	}
	public boolean isEmpty(XSSFRow row) {
		
		if(row == null){
			LOG.info("i m failure");
			return true;
	}
		else
		return false;
	}
	public void randomnames() throws Exception{
		ExampleReadExcelSheet er= new ExampleReadExcelSheet("D:/office/Testing_excel.xlsx");
		Random random= new Random();
		int rows=er.count(0);
//		System.out.println(er.getExcelData(0, Integer.parseInt(RandomStringUtils.randomNumeric(1)), 0)+"."+er.getExcelData(0, Integer.parseInt(RandomStringUtils.randomNumeric(1)), 1));
		int randomInteger = random.nextInt(30);
		LOG.info(er.readData(0, random.nextInt(9), 0)+"."+er.readData(0, random.nextInt(9), 1));
		
		LOG.info("pseudo random int in a range : " + randomInteger);

	}
	public static void main(String args[]) throws Exception{
		Confirm d= new Confirm();
		d.passdata();
	}
}
