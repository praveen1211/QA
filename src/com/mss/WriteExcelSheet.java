package com.mss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class WriteExcelSheet {
	org.apache.poi.ss.usermodel.Workbook workbook;
	org.apache.poi.ss.usermodel.Sheet sheet;
	File file;
	FileInputStream fileInputStream;
public WriteExcelSheet(String path){
	try {
		file= new File(path);
		fileInputStream= new FileInputStream(file);
		workbook=  WorkbookFactory.create(fileInputStream);
		
	} catch (Exception exception) {
		// TODO Auto-generated catch block
		exception.printStackTrace();
	}
	
}
public void writeData(String index,int row, int cell, String result, boolean status){
	try {
		CellStyle style=workbook.createCellStyle();
		Font font=workbook.createFont();
		 font.setBoldweight(org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD);

		
		if(status){
		font.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(font);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		}
		else if(result.contains("Invalid")){
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.getIndex());
		}
		else{
			style.setFont(font);
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
		}
		
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		sheet=workbook.getSheet(index);
		Cell cell1= sheet.getRow(row).createCell(cell);
		cell1.setCellValue(result);
		cell1.setCellStyle(style);
		FileOutputStream dest= new FileOutputStream(file);
		workbook.write(dest);
	} catch (IOException exception) {
		// TODO Auto-generated catch block
		exception.printStackTrace();
	}
	
}
public void countOfResult(String sheetName,int row, int cell, int count){
	try {
		sheet=workbook.getSheet(sheetName);
		Cell cell1= sheet.getRow(row).createCell(cell);
		cell1.setCellValue(count);
		FileOutputStream destination= new FileOutputStream(file);
		workbook.write(destination);
	} catch (Exception exception) {
		// TODO Auto-generated catch block
		exception.printStackTrace();
	}
}
}
