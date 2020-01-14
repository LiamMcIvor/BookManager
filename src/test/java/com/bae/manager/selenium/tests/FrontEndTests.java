package com.bae.manager.selenium.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.bae.manager.selenium.constants.Constants;
import com.bae.manager.selenium.pages.AddBooksPage;
import com.bae.manager.selenium.pages.Form;
import com.bae.manager.selenium.pages.IndexPage;
import com.bae.manager.selenium.pages.UpdateDeleteBooksPage;
import com.bae.manager.selenium.pages.components.Navbar;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FrontEndTests {
	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;

	private IndexPage indexPage;
	
	private UpdateDeleteBooksPage updatePage;
	
	private AddBooksPage addPage;
	
	private Navbar navbar;
	
	private Form form;
		
	@Before
	public void startup() throws Exception {
		System.setProperty(Constants.PROPERTY, Constants.EXPLICIT_PATH);
		this.driver = new ChromeDriver();	
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.indexPage = PageFactory.initElements(this.driver, IndexPage.class);
		this.addPage = PageFactory.initElements(this.driver, AddBooksPage.class);
		this.updatePage = PageFactory.initElements(this.driver, UpdateDeleteBooksPage.class);
		this.navbar = PageFactory.initElements(this.driver, Navbar.class);
		this.form = PageFactory.initElements(this.driver, Form.class);
	}
	
	@After
	public void teardown() throws Exception {
		this.driver.close();
	}
	
	@Test
	public void changePageTest() {
		
		this.driver.get(Constants.HOST + port);
		this.navbar.navigateToIndex();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.INDEX);
		
		this.navbar.navigateToAddBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.ADD_BOOKS);
		this.navbar.navigateToAddBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.ADD_BOOKS);
		this.navbar.navigateToIndex();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.INDEX);
		
		this.navbar.navigateToUpdateBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.UPDATE_DELETE_BOOKS);
		this.navbar.navigateToUpdateBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.UPDATE_DELETE_BOOKS);
		this.navbar.navigateToIndex();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.INDEX);
		
		this.navbar.navigateToAddBooks();
		this.navbar.navigateToUpdateBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.UPDATE_DELETE_BOOKS);
		this.navbar.navigateToAddBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.ADD_BOOKS);
	}
	
	@Test
	public void getBooks() {
		this.driver.get(Constants.HOST + port);
		assertEquals("There Are Currently No Saved Books", this.indexPage.getEmptyBookHeader());
		
		this.navbar.navigateToUpdateBooks();
		assertEquals("There Are Currently No Saved Books", this.updatePage.getEmptyBooksHeader());
	}
	
	@Test
	public void addAndDeleteBooksTest() throws InterruptedException {
		this.driver.get(Constants.HOST + port + Constants.ADD_BOOKS);
		
		String title = "Good Omens";
		String testAuthor1 = "Terry Pratchett";
		String testAuthor2 = "Neil Gaiman";
		String series = "N/A";
		String timesRead = "25";
		
		this.form.enterTitle(title);
		this.form.enterAuthor(testAuthor1);
		this.form.enterAuthor(testAuthor2);
		this.form.enterSeries(series);
		this.form.selectOwnedRadio();
		this.form.selectReadingRadio();
		this.form.enterTimesRead("25");
		this.form.submit();
		
		Thread.sleep(2000L);
		assertEquals(title + " Has Been Created", this.driver.switchTo().alert().getText());
		
		this.driver.switchTo().alert().accept();
		
		this.navbar.navigateToUpdateBooks();
		
		
		Thread.sleep(10000L);
	}
	
	

}
