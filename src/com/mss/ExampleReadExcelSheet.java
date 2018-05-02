package com.mss;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExampleReadExcelSheet {
XSSFWorkbook wb;
XSSFSheet sheet;
public ExampleReadExcelSheet(String path){
	try {
		File f= new File(path);
		FileInputStream fis = new FileInputStream(f);
		wb= new XSSFWorkbook(fis);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
public String readData(int index, int row, int cell){
	sheet = wb.getSheetAt(index);
	Row rows=sheet.getRow(row);
	Cell cell1=rows.getCell(cell);
	String data;
	if(cell1 != null){
		data=cell1.toString();
		return data;
	}
	else{
		data= null;
		return data;
	}
}

public int sheetCount(){
	int numSheets=wb.getNumberOfSheets();
	return numSheets;
}
public int count(int index){
	sheet = wb.getSheetAt(index);
	int totalrows=sheet.getLastRowNum();
	return totalrows;
}

}
