package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import foundation.Initiate;

public class AccessoriesPage {
	
	WebDriver driver;
    WebDriverWait explicitWait;
    int explicitWaitTime = 10;
	
	
	//constructor to get the page
	public AccessoriesPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	//Elements
	@FindBy(xpath="//form[@action='http://store.demoqa.com/products-page/product-category/accessories/magic-mouse/']//div[@class='input-button-buy']//input[@type='submit']")
	WebElement addToCartMagicMouse;
	
	@FindBy(xpath="//a[@title='Checkout']")
	WebElement checkOut;
	
	@FindBy(xpath="//a[@title='Checkout']//em[contains(text(),1)]")
	WebElement checkOutItem;
	
	//functions to perform actions
	public WebElement returnaddToCartMagicMouse() {
		return addToCartMagicMouse;	
	}
	
	
	public void checkOut() {
		
		explicitWait = new WebDriverWait(driver, explicitWaitTime);
		try {
		
			explicitWait.until(ExpectedConditions.visibilityOf(checkOutItem));
		explicitWait.until(ExpectedConditions.visibilityOf(checkOut)).click();
		}catch(NoSuchElementException e) {
			 Initiate.log.error("Element checkOutItem/checkOut not found", e);
			 Assert.fail("Element checkOutItem/checkOut not found");
			 
		}
		
		
	}
	
	public void clickOnAddToCart(WebElement webElemet) {
		explicitWait = new WebDriverWait(driver, explicitWaitTime);
		try {
		explicitWait.until(ExpectedConditions.visibilityOf(webElemet)).click();
		}catch(NoSuchElementException e) {
			Initiate.log.error("Element not found from purchse screen", e);
			 Assert.fail("Element not found"+webElemet); 
		}
		
	}
	
}
