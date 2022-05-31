package haufe.challenge.driver;

import org.openqa.selenium.WebDriver;

import haufe.challenge.utils.LoggingUtil;
import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverHandler {
	
	public WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();
	
	/**
	 * This method is used to create driver instance based on required browser
	 */
	public WebDriver initDriver(String browser) {
		
			switch(browser) {
			
			case "chrome":
				tdriver.set(WebDriverManager.chromedriver().create());
				LoggingUtil.getLogger().info("Chrome browser launched");
				break;
				
			default:
				LoggingUtil.getLogger().warn("Check browser name in properties file");
				break;
			}
			
			return getDriver();
		
	}
	
	/**
	 * This method is used to get current driver instance
	 */
	public static WebDriver getDriver() {
		return tdriver.get();
	}
}
