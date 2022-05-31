package hooks;

import java.util.Properties;

import org.openqa.selenium.WebDriver;

import haufe.challenge.driver.DriverHandler;
import haufe.challenge.utils.PropertyReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

	private WebDriver driver;
	private DriverHandler driverHandler;
	
	private PropertyReader propReader;
	
	Properties prop;
	
	@Before(order=0)
	public void suiteSetup() throws Exception {
		try {
			propReader = new PropertyReader();
			prop = propReader.init_prop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Before(order=1)
	public void setUpBrowser() throws Exception {
		driverHandler = new DriverHandler();
		driver = driverHandler.initDriver(prop.getProperty("browser"));
		driver.manage().window().maximize();
	}
	
	@After
	public void tearDown() {
		System.out.println("Teardown executed");
		driver.quit();
	}
	
}
