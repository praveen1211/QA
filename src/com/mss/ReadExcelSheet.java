package com.mss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelSheet {
	org.apache.poi.ss.usermodel.Workbook workbook;
	org.apache.poi.ss.usermodel.Sheet sheet;
File file;
FileInputStream fileInputstream;
public ReadExcelSheet(String properties){
	try {
		file= new File(properties);
		fileInputstream = new FileInputStream(properties);
		workbook= WorkbookFactory.create(fileInputstream);
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public String readData(String index, int row, int cell){
	sheet = workbook.getSheet(index);
	Row rows=sheet.getRow(row);
	Cell cell1=rows.getCell(cell);
	String data;
	if(cell1 != null){
	
		if(cell1.getCellType()==cell1.CELL_TYPE_NUMERIC){
		return NumberToTextConverter.toText(cell1.getNumericCellValue());
		}
		else{
		data=cell1.toString();
		return data;
		}
	}
	else{
		data= "empty";
		return data;
	}
}

public int sheetCount(){
	int numSheets=workbook.getNumberOfSheets();
	return numSheets;
}
public int count(String index){
	sheet = workbook.getSheet(index);
	//System.out.println("sheet:"+sheet);
	int totalrows=sheet.getLastRowNum();
	return totalrows;
}
public void removeCells(String name, int cellNumber) throws Exception{
	                   //scenarios,3
	//System.out.println("name:"+name+"cellNumber"+cellNumber);
	sheet=workbook.getSheet(name);
	CellStyle style=workbook.createCellStyle();
	for (int rows=1;rows<=count(name);rows++){
		Row getRow=sheet.getRow(rows);
		Cell cell1=getRow.getCell(cellNumber);
		if(cell1!=null){
		getRow.removeCell(cell1);
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		cell1= sheet.getRow(rows).createCell(cellNumber);
		cell1.setCellStyle(style);
		FileOutputStream dest= new FileOutputStream(file);
		workbook.write(dest);
		}
	}
	
}
}
