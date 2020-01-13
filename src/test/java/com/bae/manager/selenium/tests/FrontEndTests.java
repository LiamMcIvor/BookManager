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
import com.bae.manager.selenium.pages.IndexPage;
import com.bae.manager.selenium.pages.components.Navbar;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FrontEndTests {
	
	@LocalServerPort
	private int port;
	
	private WebDriver driver;
		
	@Before
	public void startup() throws Exception {
		System.setProperty(Constants.PROPERTY, Constants.EXPLICIT_PATH);
		this.driver = new ChromeDriver();	
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@After
	public void teardown() throws Exception {
		this.driver.close();
	}
	
	@Test
	public void changePageTest() {
		IndexPage indexPage = PageFactory.initElements(this.driver, IndexPage.class);
		Navbar navbar = PageFactory.initElements(this.driver, Navbar.class);
		this.driver.get(Constants.HOST + port);
		navbar.navigateToAddBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.ADD_BOOKS);
		navbar.navigateToIndex();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.INDEX);
		
		navbar.navigateToUpdateBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.UPDATE_DELETE_BOOKS);
		navbar.navigateToIndex();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.INDEX);
		
		navbar.navigateToAddBooks();
		navbar.navigateToUpdateBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.UPDATE_DELETE_BOOKS);
		navbar.navigateToAddBooks();
		assertEquals(driver.getCurrentUrl(), Constants.HOST + port + Constants.ADD_BOOKS);
	}
	
	@Test
	public void addBooksTest() throws InterruptedException{
		this.driver.get(Constants.HOST + port);
		Thread.sleep(20000L);

	}

}
