package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import foundation.Initiate;

public class HomePage {
	
	WebDriver driver;
	
	//constructor to get the page
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	//Elements
	@FindBy(how = How.ID, using = "logo")
	WebElement logo;
	
	@FindBy(linkText="Product Category")
	WebElement productCategory;
	
	@FindBy(linkText="Accessories")
	WebElement accessories;
	
	//functions to perform actions
	
	public void clickOnAccessories() {
		try {
		Actions mouseOver = new Actions(driver);
		Action mouseOverProduct = mouseOver.moveToElement(productCategory).build();
		mouseOverProduct.perform();
		accessories.click();
		}catch(NoSuchElementException e) {
			Initiate.log.error("Element not found on Home Page", e);
			Assert.fail("Element not found on Home Page");
		}
	}
	
	public void clickLogo() {
		try {
		logo.click();
		}catch(NoSuchElementException e) {
			Initiate.log.error("Element not found on Home Page", e);
			Assert.fail("Element not found on Home Page");
		}
	}

}
