package TestClasses;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.SeleniumUtility;
import webpages.ImdbPage;
import webpages.WikiPage;

public class MovieInfoTest extends SeleniumUtility {

	public static WebDriver driver = null;
	static String imdburl,wikiurl, movie,browser,wikiReleaseDate,wikiCountry,imdbReleaseDate,imdbCountry;
	ImdbPage imdb;
	WikiPage wiki;
	
	@BeforeTest
	@Test
	public void readData()
	{
		imdburl=readPropFile("imdbUrl");
		wikiurl=readPropFile("wikiUrl");
		browser=readPropFile("browser");
		movie=readPropFile("movie");	
		System.out.println(movie);
	}
	@Test(priority=1)
	public void openWebPageAndFetchData()
	{
		driver = setUp(browser, imdburl);
		imdb=new ImdbPage(driver);
		imdb.searchMovieName(movie);
		imdbReleaseDate=imdb.getReleaseDate();
		imdbCountry=imdb.getCountry();
		System.out.println("IMDB details"+imdbReleaseDate+" "+imdbCountry);
		imdb.cleanUp();
		
		driver= setUp(browser,wikiurl);
		wiki=new WikiPage(driver);
		wiki.searchMovieName(movie);
		wikiReleaseDate=wiki.getReleaseDate();
		wikiCountry=wiki.getCountry();
		System.out.println("wiki details"+wikiReleaseDate+" "+wikiCountry);
		wiki.cleanUp();
	}
	@Test(priority=2)
	public void compareReleaseDate()
	{
		Assert.assertEquals(imdbReleaseDate, wikiReleaseDate);
	}
	@Test(priority=2)
	public void compareCountry()
	{
		Assert.assertEquals(imdbCountry, wikiCountry);
	}
}
