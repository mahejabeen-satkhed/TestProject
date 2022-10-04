package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

// Base class or utility class
public class SeleniumUtility {
	public static WebDriver driver = null;
	protected Actions action;

//Method to setup browser
	
	public WebDriver setUp(String browser, String url) {
		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		if (browser.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		if (browser.equalsIgnoreCase("ie")) {
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
		}

		if (browser.equalsIgnoreCase("opera")) {
			WebDriverManager.operadriver().setup();
			driver = new OperaDriver();
		}

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.get(url);

		return driver;
	}
	//Method to return WebDriver 
	public WebDriver getDriver() {
		return driver;
	}
	//Method to read properties file 
	public String readPropFile(String Key) {
		Properties properties = new Properties();
		try {
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/resources/testdata/userdata.properties");
			properties.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties.getProperty(Key);
	}
	//Method to type input in field
	public void typeInput(WebElement element, String input) {
		waitForElementDisplayed(element);
		element.clear();
		element.sendKeys(input);
	}
	//Method to get text of web element
	public String getText(WebElement element)
	{
		waitForElementDisplayed(element);
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView()", element);
		return element.getText();	
	}
	
	public String convertToDate(String releasedate)
	{
		String d=releasedate;
		d=d.replace(",", "");
		System.out.println(d);
		LocalDate date;
		if(d.contains("("))
		{
			d=d.split("\\(")[0];
			d=d.trim();
			System.out.println(d);
		}
		if(Character.isDigit(d.charAt(0)))
		{
		
			//String string = "2 January, 2010";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH);
			date = LocalDate.parse(d, formatter);
			System.out.println(date);
		}
		else
		{
			//String string = "January 2, 2010";
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy", Locale.ENGLISH);
			date = LocalDate.parse(d, formatter);
			System.out.println(date);
			
		}
		return date.toString();
	}
	
//Method to click on web element
	public void clickOnElement(WebElement element) {
		waitForElementToBeClickable(element);
		element.click();
	}
	
	public void clickOnElementInList(List<WebElement> elements,String movie) {
		//waitForElementToBeClickable(element);
		for(WebElement e:elements)
		{
			if(e.getText().contains(movie))
			{
				waitForElementToBeClickable(e);
				e.click();
				break;
			}
		}
	}	

	/**
	 * Method to get the title of current page
	 */
	public String getCurrentTitleOfApplication() {
		return driver.getTitle();
	}

	/**
	 * Method to get the current url of the application
	 */
	public String getCurrentUrlOfApplication() {
		return driver.getCurrentUrl();
	}

	public boolean isElementExist(WebElement element) {
		waitForElementDisplayed(element);
		return element.isDisplayed();
	}

	/**
	 * Method to refresh Page
	 */
	protected void refreshPage() {
		driver.navigate().refresh();
	}
	/**
	 * Method to wait for an element till it's not display .
	 */
	public void waitForElementDisplayed(WebElement element) {
		new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Method to wait for an element till it's not clickable.
	 * 
	 */
	public void waitForElementToBeClickable(WebElement element) {

		new WebDriverWait(driver, 60).until(ExpectedConditions.elementToBeClickable(element));
	}
	/**
	 * Method to close the driver.
	 */
	public void cleanUp() {
		driver.close();
	}

}
