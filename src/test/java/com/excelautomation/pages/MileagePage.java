package com.excelautomation.pages;

import static com.excelautomation.utilities.Driver.*;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MileagePage {
	
	public MileagePage() {PageFactory.initElements(getDriver(), this);}
	
	
	@FindBy(id="uscodreading")
	public WebElement currentOdoReading;
	
	@FindBy(id="uspodreading")
	public WebElement prevOdoReading;
	
	@FindBy(id="usgasputin")
	public WebElement gasAddedToTank;
	
	@FindBy(id="usgasprice")
	public WebElement gasPrice;
	
	@FindBy(xpath="(//*[@alt='Calculate'])[1]")
	public WebElement calculateButton;
	
	@FindBy(xpath="//b[contains(text(),'miles per gallon')]")
	public WebElement mpgOnPage;
	
}
