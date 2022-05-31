package haufe.challenge.pages;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import haufe.challenge.commons.CommonActions;
import haufe.challenge.driver.DriverHandler;
import haufe.challenge.utils.PropertyReader;

public class CountryPage {

//	private WebDriver driver;
	private CommonActions commonActions = new CommonActions(DriverHandler.getDriver());
	
	private PropertyReader propReader = new PropertyReader();
	Properties prop = propReader.init_prop();
	
	@FindBy(xpath="//select")
	private WebElement ddCountry;
	
	@FindBy(xpath="//input[@type='checkbox']")
	private WebElement cbTnC;
	
	@FindBy(xpath="//button[text()='Proceed']")
	private WebElement btnProceed;
	
	public CountryPage(WebDriver driver) {
//		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void selectCountry(String value) {
		commonActions.selectByValue(ddCountry, value);
	}
	
	public void waitUntilCountryDropdownIsVisible() {
		commonActions.waitUntilElementDisplayed(ddCountry);
	}

	
	public void checkTnC() {
		commonActions.clickElement(cbTnC);
	}
	
	public void clickProceedButton() {
		commonActions.clickElement(btnProceed);
	}
	
	public boolean returnSuccessMessageElement(String value) {
		String xpath = "//*[contains(text(),'"+value+"')]";
		return commonActions.waitForElementLocatedByXpath(xpath);
	}
	
	public boolean returnInvalidPromoError(String value) {
		String xpath = "//span[text()='"+value+"']";
		return commonActions.waitForElementLocatedByXpath(xpath);
	}
	
}
