package com.excelautomation.tests;

import static com.excelautomation.utilities.ConfigurationReader.getProperty;
import static com.excelautomation.utilities.Driver.getDriver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.excelautomation.utilities.ConfigurationReader;
import com.excelautomation.utilities.Driver;
import com.excelautomation.utilities.ExcelUtility;
import com.excelautomation.utilities.TestBase;

public class MPGTest extends TestBase {

	@Test(priority = 1)
	public void gasMileageDDT() throws Exception {
		Driver.getDriver().get(ConfigurationReader.getProperty("mileage_cal_url"));
		String path = "./resources/mileage_calculator_test_data/EclipseMileageCalculator.xlsx";
		FileInputStream input = new FileInputStream(path);
		XSSFWorkbook workbook = new XSSFWorkbook(input);
		XSSFSheet sheet = workbook.getSheetAt(0);

		extentTest = extentReport.createTest("Mileage calculator test");

		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
			XSSFRow currentRow = sheet.getRow(rowNum);

			double cOR = currentRow.getCell(1).getNumericCellValue();
			pages.mileage().currentOdoReading.clear();
			pages.mileage().currentOdoReading.sendKeys(String.valueOf(cOR));

			double pOR = currentRow.getCell(2).getNumericCellValue();
			pages.mileage().prevOdoReading.clear();
			pages.mileage().prevOdoReading.sendKeys(String.valueOf(pOR));

			double gasAmount = currentRow.getCell(3).getNumericCellValue();
			pages.mileage().gasAddedToTank.clear();
			pages.mileage().gasAddedToTank.sendKeys(String.valueOf(gasAmount));

			pages.mileage().calculateButton.click();

			DecimalFormat decimalFormat = new DecimalFormat("#0.00");

			double expectedMPGDouble = (cOR - pOR) / gasAmount;

			String expectedMPG = decimalFormat.format(expectedMPGDouble);

			String[] actualMPG = pages.mileage().mpgOnPage.getText().split(" ");

			if (currentRow.getCell(4) == null) {
				currentRow.createCell(4);
			}
			currentRow.getCell(4).setCellValue(expectedMPG);

			if (currentRow.getCell(5) == null) {
				currentRow.createCell(5);
			}
			currentRow.getCell(5).setCellValue(actualMPG[0]);

			System.out.println("actual MPG: " + actualMPG[0]);

			System.out.println("expected MPG: " + expectedMPG);

			extentTest.info("verifying mpg expected matches mpg actual");

			Assert.assertEquals(actualMPG[0], expectedMPG);
		}
		FileOutputStream out = new FileOutputStream(path);
		workbook.write(out);
		out.close();
		workbook.close();
		input.close();
	}

//	@Test(priority = 0)
	public void gasMileageTest() {
		Driver.getDriver().get(ConfigurationReader.getProperty("mileage_cal_url"));

		extentTest = extentReport.createTest("Mileage calculator test");

		double cOR = 123000;
		pages.mileage().currentOdoReading.clear();
		pages.mileage().currentOdoReading.sendKeys(String.valueOf(cOR));

		double pOR = 122550;
		pages.mileage().prevOdoReading.clear();
		pages.mileage().prevOdoReading.sendKeys(String.valueOf(pOR));

		double gasAmount = 20;
		pages.mileage().gasAddedToTank.clear();
		pages.mileage().gasAddedToTank.sendKeys(String.valueOf(gasAmount));

		pages.mileage().calculateButton.click();

		DecimalFormat decimalFormat = new DecimalFormat("#0.00");

		double expectedMPGDouble = (cOR - pOR) / gasAmount;

		String expectedMPG = decimalFormat.format(expectedMPGDouble);

		String[] actualMPG = pages.mileage().mpgOnPage.getText().split(" ");

		System.out.println("actual MPG: " + actualMPG[0]);

		System.out.println("expected MPG: " + expectedMPG);

		extentTest.info("verifying mpg expected matches mpg actual");

		Assert.assertEquals(actualMPG[0], expectedMPG);
	}

//	@Test(priority = 2)
	public void gasMileageDDT2() throws Exception {
		Driver.getDriver().get(ConfigurationReader.getProperty("mileage_cal_url"));
		String filePath = ConfigurationReader.getProperty("milege_calculator_path");
		String sheetName = "data2";
		ExcelUtility excelData = new ExcelUtility(filePath, sheetName);

		extentTest = extentReport.createTest("Mileage calculator test");

		for (int rowNum = 1; rowNum < excelData.rowCount(); rowNum++) {
			Row currentRow = excelData.getRow(rowNum);

			double cOR = currentRow.getCell(1).getNumericCellValue();
			pages.mileage().currentOdoReading.clear();
			pages.mileage().currentOdoReading.sendKeys(String.valueOf(cOR));

			double pOR = currentRow.getCell(2).getNumericCellValue();
			pages.mileage().prevOdoReading.clear();
			pages.mileage().prevOdoReading.sendKeys(String.valueOf(pOR));

			double gasAmount = currentRow.getCell(3).getNumericCellValue();
			pages.mileage().gasAddedToTank.clear();
			pages.mileage().gasAddedToTank.sendKeys(String.valueOf(gasAmount));

			pages.mileage().calculateButton.click();

			DecimalFormat decimalFormat = new DecimalFormat("#0.00");

			double expectedMPGDouble = (cOR - pOR) / gasAmount;

			String expectedMPG = decimalFormat.format(expectedMPGDouble);

			String[] actualMPG = pages.mileage().mpgOnPage.getText().split(" ");

			if (currentRow.getCell(4) == null) {
				currentRow.createCell(4);
			}
			currentRow.getCell(4).setCellValue(expectedMPG);

			if (currentRow.getCell(5) == null) {
				currentRow.createCell(5);
			}
			currentRow.getCell(5).setCellValue(actualMPG[0]);

			System.out.println("actual MPG: " + actualMPG[0]);

			System.out.println("expected MPG: " + expectedMPG);

			extentTest.info("verifying mpg expected matches mpg actual");

			Assert.assertEquals(actualMPG[0], expectedMPG);
		}
		excelData.saveWorkBook();
	}

}
