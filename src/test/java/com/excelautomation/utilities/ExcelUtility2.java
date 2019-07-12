package com.excelautomation.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

/*
 * This is a utility for reading from writing to excel files.
 * it works with xls and xlsx files.
 */
public class ExcelUtility2 {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private String path;
	private FileOutputStream saveExcelFileAs;

	public ExcelUtility2(String path, String sheetName) {
		this.path = path;
		try {
			FileInputStream excelFile = new FileInputStream(path);
			workbook = new XSSFWorkbook(excelFile);
			sheet = workbook.getSheet(sheetName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getCellData(int rowNum, int columnNum) {
		Cell cell;
		try {
			cell = sheet.getRow(rowNum).getCell(columnNum);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cell.toString();
	}

	public String[][] getDataArray() {
		String[][] dataArray = new String[rowCount()][columnCount()];
		for (int i = 0; i < rowCount(); i++) {
			for (int j = 0; j < columnCount(); j++) {
				dataArray[i][j] = getCellData(i, j).toString();
			}
		}
		return dataArray;
	}

	public List<String> columnNames() {
		List<String> columNames = new ArrayList<String>();
		for (Cell cell : sheet.getRow(0)) {
			columNames.add(cell.toString());
		}
		return columNames;
	}

	public List<Map<String, String>> getAllData() {
		List<Map<String, String>> allData = new ArrayList<Map<String, String>>();
		for (int i = 1; i < rowCount(); i++) {
			Map<String, String> rowMap = new HashMap<String, String>();
			for (int j = 0; j < columnCount(); j++) {
				rowMap.put(columnNames().get(j), sheet.getRow(i).getCell(j).toString());

			}
			allData.add(rowMap);
		}
		return allData;
	}

	public void setCellData(String value, int rowIndex, int columnIndex) throws Exception {
		if (sheet.getRow(rowIndex).getCell(columnIndex) == null) {
			sheet.getRow(rowIndex).createCell(columnIndex);
//			sheet.getRow(rowIndex).getCell(columnIndex).setCellValue(value);
		}
		sheet.getRow(rowIndex).getCell(columnIndex).setCellValue(value);
		saveExcelFile();
	}

	public void setCellData(String value, int rowIndex, String columnName) throws Exception {
		int columnIndex = columnNames().indexOf(columnName);
		setCellData(value, rowIndex, columnIndex);
	}

	public int rowCount() {
		return sheet.getLastRowNum() + 1;
	}

	public int columnCount() {
		return sheet.getRow(0).getLastCellNum();
	}

	public Row getRow(int index) {
		return sheet.getRow(index);
	}

	public void saveExcelFile() throws Exception {
		saveExcelFileAs = new FileOutputStream(path);
		workbook.write(saveExcelFileAs);
		workbook.close();
		saveExcelFileAs.close();
	}

}