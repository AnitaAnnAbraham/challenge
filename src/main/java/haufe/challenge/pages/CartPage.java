package haufe.challenge.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import haufe.challenge.commons.CommonActions;
import haufe.challenge.driver.DriverHandler;
import haufe.challenge.utils.LoggingUtil;
import haufe.challenge.utils.PropertyReader;

public class CartPage {
	
	private WebDriver driver;
	private CommonActions commonActions = new CommonActions(DriverHandler.getDriver());
	private PropertyReader propReader = new PropertyReader();
	
	private Map<String, String> actualMap = new HashMap<String, String>();
	Properties prop = propReader.init_prop();
	
	@FindBy(xpath="//button[text()='Place Order']")
	private WebElement btnPlaceOrder;

	@FindBy(xpath="//table[@id='productCartTables']/tbody//p[contains(@class,'product')]")
	private List<WebElement> cartProducts;
	 
	@FindBy(css="input.promoCode")
	private WebElement txtPromoCode;
	
	@FindBy(css="button.promoBtn")
	private WebElement btnApplyPromo;
	
	public CartPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	private WebElement getCustomQuantity(String itemName) {
		WebElement txtCustomQuantity = driver.findElement(By.xpath("//p[contains(text(),'"+itemName+"')]/parent::td/following-sibling::td/p"));
		return txtCustomQuantity;
	}
	
	private WebElement getCustomCloseButton(String itemName) {
		WebElement txtCustomQuantity = driver.findElement(By.xpath("//p[contains(text(),'"+itemName+"')]//following::a[contains(@class,'remove')]"));
		return txtCustomQuantity;
	}
	
	public void getQuantityValues(String itemName) {
		commonActions.waitUntilElementClickable(btnPlaceOrder);
		String quantity = getCustomQuantity(itemName).getText();
		setActualQuantityValues(itemName,quantity);
	}
	
	public void setActualQuantityValues(String key, String value) {
		actualMap.put(key, value);
	}
	
	public Map<String, String> getActualQuantityValues() {
		return actualMap;
	}
	
	public String getPageUrl() {
		return commonActions.returnPageUrl();
	}
	
	public void waitForPlaceOrderButtonToBeVisible() {
		commonActions.waitUntilElementDisplayed(btnPlaceOrder);
	}
	
	public boolean checkPlaceOrderButtonState() {
		return commonActions.isElementEnabled(btnPlaceOrder);
	}

	public CountryPage clickPlaceOrder() {
		commonActions.clickElement(btnPlaceOrder);
		return new CountryPage(driver);
	}
	
	public void removeUnwantedItems(String itemName) {
		commonActions.clickElement(getCustomCloseButton(itemName));
	}
	
	public List<String> getCartProductList() {
		List<String> productList = new ArrayList<>();
		for(WebElement productRow : cartProducts) {
			String productName = (productRow.getText().split("-"))[0].trim();
			productList.add(productName);
		}
		LoggingUtil.getLogger().info("Cart product list" +productList);
		return productList;
	}
	
	public void enterPromoCode(String promoCode) {
		commonActions.sendKeys(txtPromoCode, promoCode);
	}
	
	public void applyPromoCode() {
		commonActions.clickElement(btnApplyPromo);
	}
	
	public boolean returnInvalidPromoMessage(String value) {
		String xpath = "//*[contains(text(),'"+value+"')]";
		return commonActions.waitForElementLocatedByXpath(xpath);
	}
	
}
