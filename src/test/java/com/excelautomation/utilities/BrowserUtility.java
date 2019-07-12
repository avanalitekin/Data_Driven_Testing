package com.excelautomation.utilities;

import com.excelautomation.utilities.Driver;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.Map.Entry;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public abstract class BrowserUtility {

    public static void sleep(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    
        public static String getScreenshot(String screenshotName) {

        String screenshotTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String targetPath = "./test-output/Screenshots/" + screenshotName + screenshotTime + ".png";
        File destination = new File(targetPath);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException ioe) {

        }
        return targetPath;

    }

    public static String selectRandomElementName(List<WebElement> list, int random) {

        return list.get(random).getText();

    }
    public static int getRandomNumber(int max) {
        Random random=new Random();
        return random.nextInt(max);
    }

    public static String getRandomXpath(String xpath) {
        Random random=new Random();
        List<WebElement> elements=Driver.getDriver().findElements(By.xpath(xpath));
        return "("+xpath+")["+random.nextInt(elements.size())+"]";
    }
    public static String getSpecificXpath(String xpath, int number) {
        return "("+xpath+")["+number+"]";
    }

    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                break;
            } else {
                return;
            }
        }
        if (!Driver.getDriver().getTitle().equals(targetTitle)) {
            Driver.getDriver().switchTo().window(origin);
        }

    }

    public static void hover(WebElement element) {
        Actions action = new Actions(Driver.getDriver());
        action.moveToElement(element).perform();
    }

    public static List<String> getElementsListAsText(List<WebElement> list) {
        List<String> elementList = new ArrayList<>();
        for (WebElement element : list) {
            elementList.add(element.getText().trim());
//			System.out.println(element.getText().trim());
        }
        return elementList;
    }

    public static List<String> getElementsListAsTextInnerHTML(List<WebElement> list) {
        List<String> elementList = new ArrayList<>();
        for (WebElement element : list) {
            elementList.add(element.getAttribute("innerHTML").trim());
//			System.out.println(element.getText().trim());
        }
        return elementList;
    }
    public static int getElementsListSize(List<WebElement> list) {
        return list.size();
    }

    public static double getSumFromStringElements(List<WebElement> list) {
        double sum=0;
        for (WebElement element : list) {
            if(element.getText().length()>0) {
                sum=Double.valueOf(element.getText().trim().substring(1));
            }
        }
        return sum;
    }

    public static Entry<Integer, String> returnRandomElementNameIndexText(By locator, WebDriver driver) {
        List<WebElement> elementsList=driver.findElements(locator);
        Map<Integer,String> elementsMap = new TreeMap<>();
        for(int i=0; i<elementsList.size();i++) {
            elementsMap.put(i, elementsList.get(i).getText());
        }
        Random random=new Random();
        int randomIndex=random.nextInt(elementsList.size());
        Map<Integer, String> map=new TreeMap<Integer, String>();
        map.put(randomIndex, elementsMap.get(randomIndex));
        return map.entrySet().iterator().next();
    }

    public static List<String> getElementsText(By locator, WebDriver driver) {
        List<WebElement> elements = driver.findElements(locator);
        List<String> elementList = new ArrayList<>();
        for (WebElement element : elements) {
            elementList.add(element.getText());
        }
        return elementList;
    }

    public static WebElement waitForVisibilty(WebElement element, int timeToWaitInSec, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeToWaitInSec, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickabilty(WebElement element, int timeToWaitInSec,WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeToWaitInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickabilty(By locator, int timeToWaitInSec) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeToWaitInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPageToLoad(int timeoutInSec) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("waiting for  page to load...");
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), timeoutInSec);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println("Timeout waiting for Page Load Request to complete after " + timeoutInSec + " seconds");
        }
    }

    public static WebElement fluentWait(final WebElement webElement, int timeoutInSec) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(timeoutInSec)).pollingEvery(Duration.ofMillis(500))
                .ignoring(NoSuchElementException.class);
        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });
        return element;
    }

    public static void verifyElementDisplayed(By by) {
        try {
            Assert.assertTrue(Driver.getDriver().findElement(by).isDisplayed(), "Element not visible: " + by);
        } catch (NoSuchElementException e) {
            fail("Element not found:" + by);
        }
    }

    public static void verifyElementDisplayed(WebElement element) {
        try {
            Assert.assertTrue(element.isDisplayed(), "Element not visible: " + element);
        } catch (NoSuchElementException e) {
            fail("Element not found: " + element);
        }
    }

    public static void waitForStaleElement(WebElement element) {
        int y = 0;
        while (y <= 15) {
            if (y == 1)
                try {
                    element.isDisplayed();
                    break;
                } catch (StaleElementReferenceException st) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (WebDriverException we) {
                    y++;
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public static WebElement selectRandomTextFromDropdown(Select select) {
        Random random = new Random();
        List<WebElement> weblist = select.getOptions();
        int optionIndex = random.nextInt(weblist.size());
        return weblist.get(optionIndex);
    }

    public static void selectByValue(Select select,WebElement element, String value) {
        WebElement returnElement=null;
        select=new Select(element);
        select.selectByValue(value);


    }

    public static void selectByValue(WebElement element,String value) {
        WebElement returnElement=null;
        Select select=new Select(element);
        select.selectByValue(value);
    }

    public static void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].scrollIntoView(true)", element);
    }

    public static void doubleClick(WebElement element) {
        new Actions(Driver.getDriver()).doubleClick(element).build().perform();
    }

    public static void selectCheckBox(WebElement element, boolean check) {
        if (check) {
            if (!element.isSelected()) {
                element.click();
            }
        } else {
            if (element.isSelected()) {
                element.click();
            }
        }
    }
}
