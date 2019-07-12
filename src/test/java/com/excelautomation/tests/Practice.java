package com.excelautomation.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

public class Practice {
	
//	@Test
	public void rowAndColumnCount() throws Exception {
		String path="./resources/employees_app_test_data/EmployeesTestData.xlsx";
		FileInputStream input=new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		int maxRowIndex=sheet1.getLastRowNum();
		
		System.out.println("maxRowIndex: "+maxRowIndex);
		
		int columnCount=sheet1.getRow(0).getLastCellNum();
				
		System.out.println("columnCount: "+columnCount);
		
		System.out.println("last cell: "+sheet1.getRow(maxRowIndex).getCell(columnCount-1).toString());
		
		workbook.close();
		input.close();
	}
	
//	@Test
	public void getColumnNames() throws Exception {
		String path="./resources/employees_app_test_data/EmployeesTestData.xlsx";
		FileInputStream input=new  FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		List<String> columnNames=new ArrayList<String>();
		for(Cell cell:sheet1.getRow(0)) {
			columnNames.add(cell.toString());
			
		}
		System.out.println("column names: "+columnNames);
		workbook.close();
		input.close();
	}
	
//	@Test
	public void getDataArray() throws Exception {
		String path="./resources/employees_app_test_data/EmployeesTestData.xlsx";
		FileInputStream input=new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		String[][] dataArray=new String[sheet1.getLastRowNum()+1][sheet1.getRow(0).getLastCellNum()];
		for(int row=0; row<=sheet1.getLastRowNum();row++) {
			for (int column=0; column<sheet1.getRow(0).getLastCellNum();column++) {
				dataArray[row][column]=sheet1.getRow(row).getCell(column).toString();
			}
		}
		
		System.out.println("Array row length: "+dataArray.length);

		System.out.println("Array column length: "+dataArray[0].length);
		System.out.println("last array information: "+Arrays.toString(dataArray[dataArray.length-1]));
//		System.out.println(Arrays.deepToString(dataArray));
	
		
	}
	
//	@Test
	public void setCellData() throws Exception {
		String name=new Faker().name().name();
		String lastName=new Faker().name().lastName();
		String path="./resources/practice/practice.xlsx";
		FileOutputStream out=new FileOutputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet1=workbook.createSheet("practice");
		Row row1=sheet1.createRow(0);
		Row row2=sheet1.createRow(1);
		Cell cell1=sheet1.getRow(0).createCell(0);
		cell1.setCellValue("First_name");
		Cell cell2=sheet1.getRow(0).createCell(1);
		cell2.setCellValue("Last_Name");
		Cell cell3=sheet1.getRow(1).createCell(0);
		cell3.setCellValue(name);
		Cell cell4=sheet1.getRow(1).createCell(1);
		cell4.setCellValue(lastName);
		
		workbook.write(out);
		workbook.close();
		out.close();		
		
	}
	
//	@Test
	public void getColumnIndexFromName() throws Exception {
		String path="./resources/employees_app_test_data/EmployeesTestData.xlsx";
		FileInputStream input=new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		Row row=sheet1.getRow(0);
		
		/*cell names: first_name
cell names: last_name
cell names: Role
cell names: Salary
cell names: email
cell names: gender
cell names: Education
cell names: Skills*/
		List<String> columnNames=new ArrayList<String>();
		for (Cell cell:row) {
			System.out.println("cell names: "+cell.toString());
			columnNames.add(cell.toString());
		}
		
		int columnIndex=columnNames.indexOf("Skills");
		
		System.out.println("Skills column index: "+columnIndex);
		
		
	}
	
	@Test
	public void getMapList() throws Exception {
		String path="./resources/employees_app_test_data/EmployeesTestData.xlsx";
		FileInputStream input=new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		List<String> columNames=new ArrayList<String>();
		Row row0=sheet1.getRow(0);
		for(Cell cell:row0) {
			columNames.add(cell.toString());
		}
//		System.out.println("columns: "+columNames);
		List<Map<String,String>> allData=new  ArrayList<Map<String,String>>();
		for(int i=1; i<=sheet1.getLastRowNum();i++) {
			Map<String,String> rowMap=new HashMap<String,String>();
			Row row=sheet1.getRow(i);
			for (Cell cell:row) {
				int columnIndex=cell.getColumnIndex();
				rowMap.put(columNames.get(columnIndex), cell.toString());				
			}
			allData.add(rowMap);
		}
		System.out.println(allData);
		
		workbook.close();
		input.close();
		
	}

}
