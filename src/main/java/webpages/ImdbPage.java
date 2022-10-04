package webpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.SeleniumUtility;

public class ImdbPage extends SeleniumUtility{
	
	public ImdbPage(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	
	@FindBy(xpath="//input[@id='suggestion-search']")
	private WebElement searchBox;
	
	@FindBy(xpath="//div[@class='sc-d2740ffb-2 STkQo searchResult__constTitle']")
	private List<WebElement> suggestionList;
	
	@FindBy(xpath="//span[text()='Details']")
	private WebElement details;
	
	@FindBy(xpath="//li[@data-testid='title-details-releasedate']//a[@class='ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link']")
	private WebElement releaseDate;
	
	@FindBy(xpath="//li[@data-testid='title-details-origin']//a[@class='ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link']")
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
