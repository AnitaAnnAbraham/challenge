package stepDefinitions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;

import haufe.challenge.commons.CommonActions;
import haufe.challenge.driver.DriverHandler;
import haufe.challenge.pages.CartPage;
import haufe.challenge.pages.HomePage;
import haufe.challenge.utils.LoggingUtil;
import haufe.challenge.utils.PropertyReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class Cart {
	
	private HomePage homePage = new HomePage(DriverHandler.getDriver());
	private CartPage cartPage = new CartPage(DriverHandler.getDriver());
	private CommonActions commonActions = new CommonActions(DriverHandler.getDriver());
	
	private PropertyReader propReader = new PropertyReader();

	Properties prop = propReader.init_prop();
	Random random = new Random();
	
	List<Map<String,String>> desiredShoppingList;
	List<String> wantedProductList = new ArrayList<String>();
	List<String> unwantedProductList = new ArrayList<String>();
	List<String> updatedCartList = new ArrayList<String>();
	
	@When("User adds several products with random quantities")
	public void user_clicks_add_to_cart_button_for_required_item(DataTable dataTable) {
		try {
			desiredShoppingList = dataTable.asMaps(String.class,String.class);
		    for(Map<String, String> dl : desiredShoppingList) {
		    	homePage.enterDesiredQuantity(dl.get("product"), String.valueOf(random.nextInt(10-1+1)+1));
		    	homePage.clickDesiredAddButton(dl.get("product"));
		    	wantedProductList.add(dl.get("product"));
		    }
		}
		catch(InterruptedException e) {
			LoggingUtil.getLogger().error("InterruptedException " +e.getMessage());
		}
		catch(Exception e) {
			LoggingUtil.getLogger().error("Exception " +e.getMessage());
		}
	}

	@Then("User should have option to remove unwanted items from cart")
	public void user_should_have_option_to_remove_unwanted_items_from_cart(DataTable dataTable) throws InterruptedException {
	    try {
			List<Map<String,String>> unwantedItems = dataTable.asMaps(String.class,String.class);
		    for(Map<String, String> dl : unwantedItems) {
		    	cartPage.removeUnwantedItems(dl.get("product"));
		    	unwantedProductList.add(dl.get("product"));
		    	wantedProductList.remove(dl.get("product"));
		    }
	    }
	    catch(NoSuchElementException e) {
	    	LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
	    }
	    catch(ElementNotInteractableException e) {
	    	LoggingUtil.getLogger().error("ElementNotInteractableException occured at " +e.getMessage());
	    }
	    catch(Exception e) {
	    	LoggingUtil.getLogger().error("NoSuchElementException occured at " +e.getMessage());
	    }
	}
	
}
