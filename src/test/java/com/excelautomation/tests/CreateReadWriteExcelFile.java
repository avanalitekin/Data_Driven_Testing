package com.excelautomation.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import com.excelautomation.utilities.ConfigurationReader;
import com.excelautomation.utilities.TestBase;
import com.github.javafaker.Faker;

public class CreateReadWriteExcelFile {

	@Test
	public void createExcelFile() throws IOException {
		String path = "./resources/countries_test_data/excel_file_created.xlsx";
	
		FileOutputStream fos = new FileOutputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet1 = workbook.createSheet("countries");
		XSSFSheet sheet2 = workbook.createSheet("jobs");
		for (int i = 0; i < 1000; i++) {
			Row row = sheet1.createRow(i);
		}
		
		for (Row row : workbook.getSheet("countries")) {
			
				row.createCell(0);
				row.getCell(0).setCellValue("Ali");
				row.createCell(1);
				row.getCell(1).setCellValue("Tekin");
				row.createCell(2);
			
			}
		
		workbook.write(fos);
		fos.close();
		workbook.close();
		
	}
	
//	@Test
	public void readExelFilePractice() throws IOException {
		String path="./resources/countries_test_data/countries.xlsx";
		FileInputStream input=new FileInputStream(path);
		XSSFWorkbook workbook=new XSSFWorkbook(input);
		XSSFSheet sheet1=workbook.getSheetAt(0);
		Row row0=sheet1.getRow(0);
		Row row1=sheet1.getRow(1);
		
		Cell cell1=row0.getCell(0);
		Cell cell2=row0.getCell(1);
		
		Cell cell3=row1.getCell(0);
		Cell cell4=row1.getCell(1);
		
		System.out.println("Cell1: "+cell1.toString());
		System.out.println("Cell2: "+cell2.toString());
		
		System.out.println("Cell3: "+cell3.toString());
		System.out.println("Cell4: "+cell4.toString());
		
		for(Row row:sheet1) {
			for(Cell cell:row) {
				System.out.print(cell.toString()+" ");
			}
			System.out.println();
		}
		Map<String, String> countriesAndCapital=new HashMap<>();
		for(int i=0; i<sheet1.getLastRowNum();i++) {
			String country=sheet1.getRow(i).getCell(0).toString();
			String capital=sheet1.getRow(i).getCell(1).toString();
			countriesAndCapital.put(country, capital);
		}
		System.out.println("countriesAndCapital.size(): "+countriesAndCapital.size());
		System.out.println("countriesAndCapitalList: "+countriesAndCapital);
		
		
	}

//	@Test
	public void readExelFile() throws IOException {
		String path = "./resources/countries_test_data/countries.xlsx";

		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet sheet = workbook.getSheetAt(0);

		Cell cell1 = sheet.getRow(0).getCell(0);
		Cell cell2 = sheet.getRow(0).getCell(1);

		System.out.println("cell1: " + cell1.toString());
		System.out.println("cell2: " + cell2.toString());

		Map<String, String> countyCapital = new HashMap<>();
		for (Row row : sheet) {
			for (Cell cell : row) {
				System.out.print(cell.toString() + " ================== ");
			}
			System.out.println();
		}

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			String country = sheet.getRow(i).getCell(0).toString();
			String capital = sheet.getRow(i).getCell(1).toString();
			countyCapital.put(country, capital);
		}
		System.out.println(countyCapital);

	}

//	@Test
	public void writeExelFile() throws IOException {
		String path = "./resources/countries_test_data/countries.xlsx";
		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet sheet = workbook.getSheetAt(0);

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			if (sheet.getRow(i).getCell(0).toString().equals(ConfigurationReader.getProperty("country"))) {
				System.out.println(
						"Capital of " + ConfigurationReader.getProperty("country") + " ==== " + sheet.getRow(i).getCell(1).toString());
			}
		}

		if (sheet.getRow(0).getCell(2) == null) {
			sheet.getRow(0).createCell(2);
			sheet.getRow(0).getCell(2).setCellValue("Continent");
		}

		if (sheet.getRow(1).getCell(2) == null) {
			sheet.getRow(1).createCell(2);
			sheet.getRow(1).getCell(2).setCellValue("North America");
		}

		FileOutputStream out = new FileOutputStream(path);
		workbook.write(out);
		out.close();
		workbook.close();
		input.close();
	}
	

}
