package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import foundation.Initiate;

public class TransactionResultsPage {

	WebDriver driver;
	WebDriverWait explicitWait;
	int explicitWaitTime = 10;

	// constructor to get the page
	public TransactionResultsPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// Elements
	@FindBy(xpath = "//h1[contains(text(), 'Transaction Results')]")
	WebElement transResultsTextElement;

	@FindBy(xpath = "//p[contains(text(), 'Thank you for purchasing with ONLINE STORE')]")
	WebElement purchaseSuccessElement;

	// functions to perform actions
	public String returnTransResultsText() {
		String transResultsText = null;

		try {
			transResultsText = transResultsTextElement.getText();
			Initiate.log.debug("transResultsTextElement text:-" + transResultsText);
		} catch (NoSuchElementException e) {
			Initiate.log.error("Element not found from Transaction Results screen", e);
			Assert.fail("Element not found from Transaction Results screen");

		}

		return transResultsText;
	}

	public String purchaseSuccessText() {
		String purchaseResultsText = null;

		try {
			purchaseResultsText = purchaseSuccessElement.getText();
			Initiate.log.debug("purchaseSuccessElement text:-" + purchaseResultsText);
		} catch (NoSuchElementException e) {
			Initiate.log.error("Element not found from Transaction Results screen", e);
			Assert.fail("Element not found from Transaction Results screen");

		}

		return purchaseResultsText;
	}

}
