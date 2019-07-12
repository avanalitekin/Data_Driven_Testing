package com.excelautomation.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.excelautomation.utilities.ConfigurationReader.*;
import static com.excelautomation.utilities.Driver.*;

public abstract class TestBase {
	protected WebDriver driver;
	protected Pages pages;

	protected static ExtentReports extentReport;
	private static ExtentHtmlReporter extentHtmlReporter;
	protected static ExtentTest extentTest;

	@BeforeMethod(alwaysRun = true)
	public void setupMethod() {
		driver = Driver.getDriver();
		pages = new Pages();
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	@BeforeTest(alwaysRun = true)
	public void setUpTest() {
		extentReport = new ExtentReports();
		// this is our custom location of the report that will be generated
		// report will be generated in the current project inside folder: test-output
		// report file name: report.html
		String filePath = "./target/test-output/report.html";

//        windows users pls correct ur path:
//        String filePath = System.getProperty("user.dir") + "\\test-output\\report.html";

		// initialize the htmlReporter with the path to the report
		extentHtmlReporter = new ExtentHtmlReporter(filePath);

		// we attach the htmlreport to our report
		extentReport.attachReporter(extentHtmlReporter);

		extentReport.setSystemInfo("Environment", "Staging");
		extentReport.setSystemInfo("Browser", getProperty("browser"));
		extentReport.setSystemInfo("OS", System.getProperty("os.name"));

		extentReport.setSystemInfo("QA Engineer", "AT");

		extentHtmlReporter.config().setDocumentTitle("Data Driven Testing");
		extentHtmlReporter.config().setReportName("Data Driven Testing Report");

//        htmlReporter.config().setTheme(Theme.DARK);

	}

	@AfterTest(alwaysRun = true)
	public void tearDownTest() {
		extentReport.flush();
	}

	@AfterMethod(alwaysRun = true)
	public void tearDownMethod(ITestResult result) throws IOException {

		// if any test fails, it can detect it,
		// take a screen shot at the point and attach to report
		if (result.getStatus() == ITestResult.FAILURE) {
			String screenshotLocation = BrowserUtility.getScreenshot(result.getName());
			extentTest.fail(result.getName());
			extentTest.addScreenCaptureFromPath(screenshotLocation);
			extentTest.fail(result.getThrowable());

		} else if (result.getStatus() == ITestResult.SKIP) {
			extentTest.skip("Test Case Skipped: " + result.getName());
		} else {
			String screenshotLocation = BrowserUtility.getScreenshot(result.getName());
			extentTest.pass(result.getName());
			extentTest.addScreenCaptureFromPath(screenshotLocation);

		}
		closeDriver();
	}

}
