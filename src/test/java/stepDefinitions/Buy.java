package stepDefinitions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;

import haufe.challenge.driver.DriverHandler;
import haufe.challenge.pages.CartPage;
import haufe.challenge.pages.CountryPage;
import haufe.challenge.pages.HomePage;
import haufe.challenge.utils.LoggingUtil;
import haufe.challenge.utils.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class Buy {

private HomePage homePage = new HomePage(DriverHandler.getDriver());
private CartPage cartPage;
private CountryPage countryPage;
private PropertyReader propReader = new PropertyReader();

List<Map<String,String>> desiredShoppingList;
Properties prop = propReader.init_prop();
	
	@Given("^User navigates to greenkart home page$")
	public void user_is_on_Home_Page() throws Throwable {
		try {
			homePage.launchHome();	
		}
		catch(WebDriverException e) {
			LoggingUtil.getLogger().error("WebDriver exception occured at " +e.getMessage());
			throw e;
		}
		catch(NullPointerException e) {
			LoggingUtil.getLogger().error("WebDriver exception occured at " +e.getMessage());
			throw e;
		}
	}
	
	@Then("User is taken to home page")
	public void user_is_able_to_view_available_items() {
		String actualUrl = homePage.getPageUrl();
		String expectedUrl = prop.getProperty("homeUrl");
		Assert.assertEquals(expectedUrl, actualUrl);
	}

	@When("User adds several products with required quantities")
	public void user_clicks_add_to_cart_button_for_required_item(DataTable dataTable) throws InterruptedException {
		try {
			desiredShoppingList = dataTable.asMaps(String.class,String.class);
		    for(Map<String, String> dl : desiredShoppingList) {
		    	homePage.enterDesiredQuantity(dl.get("product"), dl.get("quantity"));
		    	homePage.clickDesiredAddButton(dl.get("product"));
		    }
		}
		catch(Exception e) {
			LoggingUtil.getLogger().error("Exception at " +e.getMessage());
		}
	}
	
	@Then("User navigates to cart")
	public void user_navigates_to_cart() {
		try {
		homePage.goToCart();
		}
		catch(NoSuchElementException e) {
			LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
		}
	}

	@Then("User should be able to proceed with checkout")
	public void user_should_be_able_to_proceed_with_checkout() throws InterruptedException {
	    Assert.assertTrue(homePage.checkProceedButtonState());
	    try {
	    	cartPage = homePage.proceedToCheckout();
		    cartPage.waitForPlaceOrderButtonToBeVisible();
	    }
	    catch(Exception e) {
			LoggingUtil.getLogger().error("Exception occured at " +e.getMessage());
		}
	}

	@Then("Item list and quanity should be reflected in cart")
	public void item_quanity_should_be_reflected_in_cart() throws InterruptedException {
		Map<String, String> expectedData = new HashMap<String,String>();
		Map<String, String> actualItemQuanity = null;
		try {
			for(Map<String, String> dl : desiredShoppingList) {
		    	cartPage.getQuantityValues(dl.get("product"));
		    	expectedData.put(dl.get("product"), dl.get("quantity"));
		    }
		    actualItemQuanity = cartPage.getActualQuantityValues();
		    LoggingUtil.getLogger().info("Actual and expected" +actualItemQuanity+ " and " +expectedData);
		}
		catch(Exception e) {
			LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
		}
	    
	    Assert.assertTrue(expectedData.equals(actualItemQuanity));
	}
	
	@Then("User should be able to proceed with order placement")
	public void user_should_be_able_to_proceed_with_order_placement() throws InterruptedException {
		Assert.assertTrue(cartPage.checkPlaceOrderButtonState());
		try {
		    countryPage = cartPage.clickPlaceOrder();
		    countryPage.waitUntilCountryDropdownIsVisible();
			}
		catch(Exception e) {
			LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
		}
	}
	
	@Then("User should be taken to terms and conditions page")
	public void user_should_be_taken_to_tncPage() {
		String actualUrl = cartPage.getPageUrl();
	    String expectedUrl = prop.getProperty("tnCPageUrl");
	    LoggingUtil.getLogger().info("Actual and expected urls " +actualUrl+ " and " +expectedUrl );
	    Assert.assertEquals(expectedUrl, actualUrl);
	}
	
	@Then("^User selects country as \"([^\"]*)\"$")
	public void user_selects_country(String country) {
		try {
			countryPage.selectCountry(country);
		}
		catch(Exception e) {
			LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
		}
	}
	
	@Then("User checks terms and conditions")
	public void user_checks_tnC() {
		try {
			countryPage.checkTnC();
		}
		catch(Exception e) {
			LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
		}
	}
	
	@Then("User should be able to complete order placement")
	public void user_should_be_able_to_complete_order_placement() throws InterruptedException {
		try {
			countryPage.clickProceedButton();
		}
	    catch(Exception e) {
	    	LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
	    }
	}
	
	@Then("Order successful message should be displayed")
	public void order_success_message_should_be_displayed() {
		String expectedText = prop.getProperty("successMessage");
		Assert.assertTrue(countryPage.returnSuccessMessageElement(expectedText));
	}
	
	@Then("^User applies invalid promo code as \"([^\"]*)\"$")
	public void user_applies_invalid_promo_code(String promoCode) throws InterruptedException {
	    try {
	    	cartPage.enterPromoCode(promoCode);
		    cartPage.applyPromoCode();
	    }
	    catch(Exception e) {
	    	LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
	    }
	}
	
	@Then("User should get invalid promo code error")
	public void user_should_get_invalid_promo_code_error() throws InterruptedException {
		String expectedText = null;
		try {
		 expectedText = prop.getProperty("invalidPromoMessafe");
		}
		catch(Exception e) {
	    	LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
	    }
		Assert.assertTrue(cartPage.returnInvalidPromoMessage(expectedText));
	}
	
	
}
