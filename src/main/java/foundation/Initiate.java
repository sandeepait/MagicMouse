package foundation;



import static org.testng.Assert.fail;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class Initiate {

	public static WebDriver myDriver;
	public static Properties config = new Properties();
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static FileInputStream fis;


	@BeforeMethod
	public void initializeTest() {

		if (myDriver == null) {

			// Defining and loading configuration file
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				log.debug("Config File not found");
			}

			try {
				config.load(fis);
				log.debug("Config File Loaded");
			} catch (IOException e) {
				e.printStackTrace();
				log.debug("Config File not Loaded");
			}

			String browser = config.getProperty("browser");
			String baseUrl = config.getProperty("baseUrl");
			String sImplicitWait = config.getProperty("implicitWait");
			
			int implicitWait =0;
			int browserDefined = 0;

			// defining the webDriver based on the browser defined in properties file
			if (!(browser.equals("") || baseUrl.equals(""))) {

				if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver",
							System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
					myDriver = new ChromeDriver();
					browserDefined = 1;
					log.debug("Chrome Browser Launched!");
				} else if (browser.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver",
							System.getProperty("user.dir") + "\\src\\test\\resources\\Executables\\geckodriver.exe");
					myDriver = new FirefoxDriver();
					browserDefined = 1;
					log.debug("Firefox Browser Launched!");
				} else if (browser.equalsIgnoreCase("internetexplorer")) {
					System.setProperty("webdriver.ie.driver",
							System.getProperty("user.dir") + "\\src\\test\\resources\\Executables\\IEDriverServer.exe");
					myDriver = new FirefoxDriver();
					browserDefined = 1;
					log.debug("Intenet Explorer Browser Launched!");
				} else {
					
					log.debug("This browser is not handeled in the code:- " + browser);
					Assert.fail("This browser is not handeled in the code:- " + browser);
				}
			} else {
				
				log.debug("No browser/url specified in the configuration file. Please specify a browser/url");
				Assert.fail("No browser/url specified in the configuration file. Please specify a browser/url");
			}

			if (browserDefined == 1) {
				myDriver.manage().window().maximize();
				
				if (!sImplicitWait.equals("")) {
					implicitWait = Integer.parseInt(sImplicitWait);
					myDriver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
				}	
				myDriver.get(baseUrl);
				log.debug("Navigated to the Web Page: " + myDriver.getTitle());
			}

		}

	}
	
	@AfterMethod
	public void tearDown() {
		myDriver.quit();
		log.debug("Browser Closed");
		
	}

}
