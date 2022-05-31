package haufe.challenge.commons;

import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import haufe.challenge.utils.LoggingUtil;

/**
 * This class contains common methods which can be used across multiple classes
 */
public class CommonActions {
	
	private WebDriver driver;
	
	public CommonActions(WebDriver driver) {
		this.driver = driver;
	}
	
	/**
	 * This method performs element click using WebDriver click method
	 */
	public void clickElement(WebElement element) {
			element.click();
			LoggingUtil.getLogger().info("Clicked element" +element);
	}
	
	/**
	 * This method performs clears element using WebElement clear method
	 */
	public void clearText(WebElement element) {
		element.clear();
	}
	
	/**
	 * This method sends value to field using WebElement send keys method
	 */
	public void sendKeys(WebElement element, String text) {
		element.sendKeys(text);
		LoggingUtil.getLogger().info("Entered value " +text+ " into " +element);
	}
	
	/**
	 * This method returns current page URL
	 */
	public String returnPageUrl() {
		String url = driver.getCurrentUrl();
		LoggingUtil.getLogger().info("Retrieved url as" +url);
		return url;
	}
	
	/**
	 * Explicit wait until passed element is clickable
	 */
	public void waitUntilElementClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(element));
		LoggingUtil.getLogger().info("Element found to clickable " +element);
	}
	
	/**
	 * Explicit wait until passed element is displayed on page
	 */
	public void waitUntilElementDisplayed(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfAllElements(element));
		LoggingUtil.getLogger().info("Element found to visible " +element);
	}
	
	/**
	 * Explicit wait until passed element dissapears from page
	 */
	public void waitUntilDissapear(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOf(element));
		LoggingUtil.getLogger().info("Element dissapeared " +element);
	}
	
	/**
	 * Returns true or false based on whether element is enabled or not
	 */
	public boolean isElementEnabled(WebElement element) {
		boolean enabledOrNot = false;
		try {
			enabledOrNot = element.isEnabled();
			return enabledOrNot;
		}
		catch(NoSuchElementException e){
			LoggingUtil.getLogger().warn("Element not found " +element);
			return false;
		}
		//return element.isEnabled();
	}
	
	/**
	 * Selects value from drop down using value
	 */
	public void selectByValue(WebElement element, String value) {
		Select select = new Select(element);
		select.selectByValue(value);
		LoggingUtil.getLogger().info("Value of drop down " +element+ "selected as" +value);
	}
	
	/**
	 * Waits for visibility of element and returns true or false based on whether element gets visible
	 */
	public boolean waitForElementLocatedByXpath(String xpath) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			LoggingUtil.getLogger().info("Found the element having xpath" +xpath);
			return true;
		}
		catch(TimeoutException e) {
			LoggingUtil.getLogger().error("Timeout waiting for element " +e.getMessage());
			return false;
		}
	}
    
    /**
	 * Set text box value using JavascriptExecutor
	 */
    public void setValueUsingJs(WebElement element,String value) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].value='"+value+"';", element);
		LoggingUtil.getLogger().info("Element value set using js" +element+ " as " +value);
    }
    
    /**
	 * Performs element click using JavascriptExecutor
	 */
    public void elementClickUsingJs(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click;", element);
		LoggingUtil.getLogger().info("Element clicked using js" +element);
    }
    
    /**
	 * Scrolls element into view using JavascriptExecutor
	 */
    public void scrollIntoViewUsingjs(WebElement element) {
    	JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		LoggingUtil.getLogger().info("Scrolled into view using js" +element);
    }
}