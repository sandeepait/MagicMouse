package ethoca_project;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codoid.products.exception.FilloException;

import foundation.Initiate;
import pages.AccessoriesPage;
import pages.CheckOutPage;
import pages.HomePage;
import pages.TransactionResultsPage;

public class BuyMagicMouse extends Initiate {

	@Parameters({ "customerId" })
	@Test(priority = 1, groups = { "Functional", "Regression" })
	public void positiveE2ETestCase(String customerId) throws FilloException {

		HomePage myHomePage = new HomePage(myDriver);
		log.debug("HomePage object created");
		AccessoriesPage myAccPage = new AccessoriesPage(myDriver);
		log.debug("Accessories page object created");
		CheckOutPage myCheckOutPage = new CheckOutPage(myDriver);
		log.debug("Checkout page object created");
		TransactionResultsPage myCheckTranResultsPage = new TransactionResultsPage(myDriver);
		log.debug("Transaction Results page object created");

		//Clicking on Accessories
		myHomePage.clickOnAccessories();
		log.debug("Accessories page launched");

		//Add to Cart
		WebElement magicMouse = myAccPage.returnaddToCartMagicMouse();
		myAccPage.clickOnAddToCart(magicMouse);
		log.debug("clicked on AddToCart");

		//clicking on checkout
		myAccPage.checkOut();
		log.debug("clicked on checkout");
		WebElement addedQuantityElement = myCheckOutPage.returnAddedQuantityElement();
		String Quantity = myCheckOutPage.returnAddedQuanity(addedQuantityElement);
		
		//Assert Quantity on checkout screen
		Assert.assertTrue(Quantity.equals("1"));
		System.out.println("Quantity" + Quantity);
		log.debug("Quantity on the checkout Page" + Quantity);

		//Click on Continue button on CheckoutPage
		WebElement continueButton = myCheckOutPage.returnContinueButtonElement();
		myCheckOutPage.clickOnButton(continueButton);
		log.debug("Click on the Button" + continueButton);

		//Screen with contact details and purchase button appears
		String titleOnPurchageScreen = myCheckOutPage.returnTitle();
		log.debug("Purchase screen appearted with text:- " + titleOnPurchageScreen);

		//Fill out Contact Details
		myCheckOutPage.getContactDetails(customerId);

		//Click on Purchase Button
		WebElement purchaseElement = myCheckOutPage.returnPurchaseButtonElement();
		myCheckOutPage.clickOnButton(purchaseElement);

		//Transaction Result Pages appears?
		String transResultsText = myCheckTranResultsPage.returnTransResultsText();
		Assert.assertTrue(transResultsText.contains("Transaction Results"));
		log.debug("Transaction Screen is appearing");
		
		//Purchase Success?
		String purchaseResultsText = myCheckTranResultsPage.purchaseSuccessText();
		Assert.assertTrue(purchaseResultsText.contains("Thank you for purchasing with ONLINE STORE, any items to be shipped will be processed as soon as possible, any items that can be downloaded can be downloaded using the links on this page. All prices include tax and postage and packaging where applicable."));
		log.debug("Purchase has been successful");

	}

}
