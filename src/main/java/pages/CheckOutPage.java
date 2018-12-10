package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

import foundation.Initiate;

public class CheckOutPage {
	
	WebDriver driver;
    WebDriverWait explicitWait;
    int explicitWaitTime = 10;
	
	
	//constructor to get the page
	public CheckOutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}

	//Elements
	@FindBy(xpath="//form[@class='adjustform qty']//input[@name='quantity']")
	WebElement addedQuantityElement;
	
	@FindBy(xpath="//span[contains(text(),'Continue')]")
	WebElement continueButton;
	
	@FindBy(xpath="//*[contains(text(), 'Calculate Shipping Price')]")
	WebElement titleOnPurchase;
	
	//*[contains(text(), 'Calculate Shipping Price')]

	
	@FindBy(xpath="//input[@title='billingfirstname']")
	WebElement firstNameElement;
	
	@FindBy(xpath="//input[@title='billinglastname']")
	WebElement lastNameElement;
	
	@FindBy(xpath="//textarea[@title='billingaddress']")
	WebElement addressElement;
	
	@FindBy(xpath="//input[@title='billingcity']")
	WebElement cityElement;
	
	@FindBy(xpath="//input[@title='billingstate']")
	WebElement stateElement;
	
	@FindBy(xpath="//select[@title='billingcountry']")
	WebElement countryElement;
	
	@FindBy(xpath="//input[@title='billingpostcode']")
	WebElement postCodeElement;
	
	@FindBy(xpath="//input[@title='billingphone']")
	WebElement billingphone;
	
	@FindBy(xpath="//input[@title='billingemail']")
	WebElement billingEmail;
	
	@FindBy(xpath="//input[@name='shippingSameBilling']")
	WebElement shippingSameBillingElement;
	
	@FindBy(xpath="//input[@value='Purchase']")
	WebElement purchaseElement;

	//functions to perform actions
	public WebElement returnAddedQuantityElement() {	
		return addedQuantityElement;	
	}
	
	public WebElement returnContinueButtonElement() {	
		return continueButton;	
	}
	
	public WebElement returnPurchaseButtonElement() {	
		return purchaseElement;	
	}
	
	public void clickOnButton(WebElement element) {	
		try {
		element.click();
		}catch(NoSuchElementException e) {
			
			Initiate.log.error("Element not found"+element, e);
			Assert.fail("Element not found"+element);
			 
		}
	}
	
	public String returnAddedQuanity(WebElement element) {
		String addedQuantity=null;
	
		try {
		 addedQuantity = element.getAttribute("value");
		}catch(NoSuchElementException e) {
			 
			 Initiate.log.error("Element not found"+addedQuantity, e);
			 Assert.fail("Element not found"+addedQuantity);
			 
		}
		
		return addedQuantity;
	}
	
	public String returnTitle() {
		explicitWait = new WebDriverWait(driver, explicitWaitTime);
		String title = null;
		try {
		explicitWait.until(ExpectedConditions.visibilityOf(titleOnPurchase)).getText();
		}catch(NoSuchElementException e) {
			 
			 Initiate.log.error("Element not found"+titleOnPurchase, e);
			 Assert.fail("Element not found"+titleOnPurchase);
			 
		}
		return title;
	}
	
	public void getContactDetails(String customerId) throws FilloException {
		Fillo fillo = new Fillo();
		Connection connection = fillo.getConnection(System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\testDataFile.xlsx");
		String strQuery = "select * from contactDetails where CustomerId ='"+customerId+"'";
		
		Initiate.log.debug("strQuery"+strQuery);
		Recordset recordset = connection.executeQuery(strQuery);
		
		Initiate.log.debug("Number of records"+recordset.getCount());
		
		String FirstName = null, LastName=null,	Address=null, City=null, State=null, Country=null, Phone=null, ShippingSameBilling=null;
		String BillingEmail = null;

		
		while (recordset.next()) {
			
			 FirstName = recordset.getField("FirstName");
			 LastName = recordset.getField("LastName");
			 Address = recordset.getField("Address");
			 City = recordset.getField("City");
			 State = recordset.getField("State");
			 Country = recordset.getField("Country");
			 Phone = recordset.getField("Phone");
			 ShippingSameBilling = recordset.getField("ShippingSameBilling");
			 BillingEmail = recordset.getField("BillingEmail");
		}

		recordset.close();
		connection.close();
		
		try {
		firstNameElement.sendKeys(FirstName);
		lastNameElement.sendKeys(LastName);
		addressElement.sendKeys(Address);
		cityElement.sendKeys(City);
		stateElement.sendKeys(State);
		billingphone.sendKeys(Phone);
		billingEmail.sendKeys(BillingEmail);
		
		Select countryDropdown = new Select(countryElement);
		countryDropdown.selectByVisibleText(Country);
		
		if(ShippingSameBilling.equals("Y")) {
			if(!shippingSameBillingElement.isSelected())
			shippingSameBillingElement.click();
		}
		
		}catch(NoSuchElementException e) {
			 Initiate.log.error("Element not found from purchse screen", e);
			 Assert.fail("Element not found from purchse screen");
		}
		Initiate.log.debug("All the details on the purchase screen have been entered");
	}
}
