//package com.bae.manager.selenium.tests;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.Dimension;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.bae.manager.selenium.constants.Constants;
//import com.bae.manager.selenium.pages.IndexPage;
//import com.bae.manager.selenium.pages.UpdateDeleteBooksPage;
//import com.bae.manager.selenium.pages.components.Form;
//import com.bae.manager.selenium.pages.components.Navbar;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//public class FrontEndTests {
//	
//	@LocalServerPort
//	private int port;
//	
//	private WebDriver driver;
//
//	private IndexPage indexPage;
//	
//	private UpdateDeleteBooksPage updatePage;
//	
//	private Navbar navbar;
//	
//	private Form form;
//	
//	private String title = "Good Omens";
//	private String testAuthor1 = "Terry Pratchett";
//	private String testAuthor2 = "Neil Gaiman";
//	private String owned = "OWNED";
//	private String completion = "READING";
//	private String series = "N/A";
//	private String timesRead = "25";
//	
//	@Value("${server.servlet.context-path}")
//	private String CONTEXT;
//	
//	private WebDriverWait wait;
//	
//	private String location;
//		
//	@Before
//	public void startup() throws Exception {
//		System.setProperty(Constants.PROPERTY, Constants.PATH);
//		ChromeOptions options = new ChromeOptions();
//		options.setHeadless(true);
//		this.driver = new ChromeDriver(options);	
//		this.driver.manage().window().setSize(new Dimension(1600, 700));
//		this.driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
//		this.indexPage = PageFactory.initElements(this.driver, IndexPage.class);
//		this.updatePage = PageFactory.initElements(this.driver, UpdateDeleteBooksPage.class);
//		this.navbar = PageFactory.initElements(this.driver, Navbar.class);
//		this.form = PageFactory.initElements(this.driver, Form.class);
//		this.location = Constants.HOST + port + CONTEXT;
//		this.wait = new WebDriverWait(this.driver, 10L);
//	}
//	
//	@After
//	public void teardown() throws Exception {
//		this.driver.close();
//	}
//	
//	@Test
//	public void changePageTest() {
//		
//		this.driver.get(this.location);
//		this.navbar.navigateToIndex();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.INDEX);
//		
//		this.navbar.navigateToAddBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.ADD_BOOKS);
//		this.navbar.navigateToAddBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.ADD_BOOKS);
//		this.navbar.navigateToIndex();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.INDEX);
//		
//		this.navbar.navigateToUpdateBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.UPDATE_DELETE_BOOKS);
//		this.navbar.navigateToUpdateBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.UPDATE_DELETE_BOOKS);
//		this.navbar.navigateToIndex();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.INDEX);
//		
//		this.navbar.navigateToAddBooks();
//		this.navbar.navigateToUpdateBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.UPDATE_DELETE_BOOKS);
//		this.navbar.navigateToAddBooks();
//		assertEquals(driver.getCurrentUrl(), this.location + Constants.ADD_BOOKS);
//	}
//	
//	@Test
//	public void aGetBooks() {
//		this.driver.get(this.location);
//		assertEquals("There Are Currently No Saved Books", this.indexPage.getEmptyBookHeader());
//		
//		this.navbar.navigateToUpdateBooks();
//		assertEquals("There Are Currently No Saved Books", this.updatePage.getEmptyBooksHeader());
//	}
//	
//	@Test
//	public void addAndDeleteBooksTest() throws InterruptedException {
//		this.driver.get(this.location + Constants.ADD_BOOKS);
//		
//		this.form.enterTitle(this.title);
//		this.form.enterAuthor(this.testAuthor1);
//		this.form.enterAuthor(this.testAuthor2);
//		this.form.enterSeries(this.series);
//		this.form.selectOwnedRadio();
//		this.form.selectReadingRadio();
//		this.form.enterTimesRead(this.timesRead);
//		this.form.submit();
//		
//		this.wait.until(ExpectedConditions.alertIsPresent());
//		assertEquals(this.title + " Has Been Created", this.driver.switchTo().alert().getText());
//		
//		this.driver.switchTo().alert().accept();
//		
//		this.navbar.navigateToUpdateBooks();
//		
//		List<String> tableRow1Data = this.updatePage.getTableRow1();
//		
//		assertTrue(tableRow1Data.contains(this.title));		
//		assertTrue(tableRow1Data.contains(this.series));		
//		assertTrue(tableRow1Data.contains(this.owned));		
//		assertTrue(tableRow1Data.contains(this.completion));		
//		assertTrue(tableRow1Data.contains(this.timesRead));
//		assertTrue(tableRow1Data.contains(this.testAuthor2 + ", " + this.testAuthor1));
//		
//		this.updatePage.clickRow1();
//		this.updatePage.clickDeleteRow1();
//		assertEquals("Are you sure you want to delete\n" +this.title + "\nfrom your book collection?", this.updatePage.getDeleteText());
//		this.updatePage.clickDeleteConfirmRow1();
//		this.wait.until(ExpectedConditions.alertIsPresent());
//		assertEquals(this.title + " Has Been Deleted", this.driver.switchTo().alert().getText());
//		this.driver.switchTo().alert().accept();
//		assertEquals("There Are Currently No Saved Books", this.updatePage.getEmptyBooksHeader());
//	}
//	
//	@Test
//	public void addAndUpdateBookTest() throws InterruptedException {
//		this.driver.get(this.location + Constants.ADD_BOOKS);
//		
//		this.form.enterTitle(this.title);
//		this.form.enterAuthor(this.testAuthor1);
//		this.form.enterAuthor(this.testAuthor2);
//		this.form.enterSeries(this.series);
//		this.form.selectOwnedRadio();
//		this.form.selectReadingRadio();
//		this.form.enterTimesRead(this.timesRead);
//		this.form.submit();
//		
//		this.wait.until(ExpectedConditions.alertIsPresent());
//		this.driver.switchTo().alert().accept();
//		
//		this.navbar.navigateToUpdateBooks();
//
//		this.title = "The Colour of Magic";
//		this.series = "DiskWorld";
//		this.timesRead = "20";
//		this.owned = "WISHLIST";
//		this.completion = "TO_READ";
//		
//		this.updatePage.clickRow1();
//		this.updatePage.updateButton();
//		this.updatePage.removeAuthor1();
//		this.form.enterTitle(this.title);
//		this.form.enterSeries(this.series);
//		this.form.enterTimesRead(this.timesRead);
//		this.form.selectWishlistRadio();
//		this.form.selectToReadRadio();
//		this.form.submit();
//		
//		this.wait.until(ExpectedConditions.alertIsPresent());
//		assertEquals(this.title + " Has Been Updated", this.driver.switchTo().alert().getText());
//		this.driver.switchTo().alert().accept();
//		
//		List<String> tableRow1Data = this.updatePage.getTableRow1();
//
//		assertTrue(tableRow1Data.contains(this.title));		
//		assertTrue(tableRow1Data.contains(this.series));		
//		assertTrue(tableRow1Data.contains(this.owned));		
//		assertTrue(tableRow1Data.contains(this.completion));		
//		assertTrue(tableRow1Data.contains(this.timesRead));
//		assertTrue(tableRow1Data.contains(this.testAuthor2));
//	}
//	
//	
//
//}
