package webpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumUtility;

public class WikiPage extends SeleniumUtility {
	
	public WikiPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//input[@id='searchInput']")
	private WebElement searchBox;
	
	@FindBy(xpath="//div[@class='suggestions-result']")
	private List<WebElement> suggestionList;
	
	@FindBy(xpath="//th[@class='infobox-label']//div[text()='Release date']//ancestor::tr//descendant::li")
	private WebElement releaseDate;
	
	@FindBy(xpath="//th[@class='infobox-label' and text()='Country']//following-sibling::td[@class='infobox-data']")
	private WebElement country;
	
	public void searchMovieName(String movie)
	{
		typeInput(searchBox, movie);
		clickOnElementInList(suggestionList,movie);
	}
	public String getReleaseDate()
	{
		String date=getText(releaseDate);		
		return convertToDate(date);
	}
	public String getCountry()
	{
		return getText(country);
		
	}
	
	
}
