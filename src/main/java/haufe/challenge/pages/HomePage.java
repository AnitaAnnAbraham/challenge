package haufe.challenge.pages;

import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import haufe.challenge.commons.CommonActions;
import haufe.challenge.driver.DriverHandler;
import haufe.challenge.utils.PropertyReader;

public class HomePage {
	
	private WebDriver driver;
	private CommonActions commonActions = new CommonActions(DriverHandler.getDriver());
	
	private PropertyReader propReader = new PropertyReader();
	Properties prop = propReader.init_prop();
	
	@FindBy(css="a.cart-icon")
	private WebElement lnkCart;
	
	@FindBy(css="button.added")
	private WebElement addedButton;
	
	@FindBy(xpath="//button[text()='PROCEED TO CHECKOUT']")
	private WebElement btnCheckout;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private WebElement getCustomAddButton(String itemName) {
		WebElement btnCustomAdd = driver.findElement(By.xpath("//h4[contains(text(),'"+itemName+"')]//parent::div//button[text()='ADD TO CART']"));
		return btnCustomAdd;
	}
	
	private WebElement getCustomQuantityField(String itemName) {
			WebElement txtCustomQuantity = driver.findElement(By.xpath("//h4[contains(text(),'"+itemName+"')]//parent::div//input"));
			return txtCustomQuantity;
	}

	public void launchHome() {
		driver.get(prop.getProperty("homeUrl"));
	}
	
	public String getPageUrl() {
		return commonActions.returnPageUrl();
	}
	
	public void click_btnCheckout() throws InterruptedException {
		commonActions.clickElement(btnCheckout);
	}
	
	public void enterDesiredQuantity(String itemName,String quantity) throws InterruptedException {
		commonActions.clearText(getCustomQuantityField(itemName));
		commonActions.sendKeys(getCustomQuantityField(itemName), quantity);
	}
	
	public void clickDesiredAddButton(String itemName) throws InterruptedException {
		commonActions.clickElement(getCustomAddButton(itemName));
		commonActions.waitUntilDissapear(addedButton);
	}
	
	public void goToCart() {
		commonActions.clickElement(lnkCart);
	}
	
	public boolean checkProceedButtonState() {
		return commonActions.isElementEnabled(btnCheckout);
	}
	
	public CartPage proceedToCheckout() {
		commonActions.clickElement(btnCheckout);
		return new CartPage(driver);
	}
	
}
