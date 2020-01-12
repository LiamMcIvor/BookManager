package com.bae.manager.selenium.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bae.manager.selenium.constants.Constants;
import com.bae.manager.selenium.pages.IndexPage;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class FrontEndTests {
	
	private WebDriver driver;
	
	@Before
	public void startup() throws Exception {
		System.setProperty(Constants.PROPERTY, Constants.PATH);
		this.driver = new ChromeDriver();	
		this.driver.get(Constants.HOST);
	}
	
	@After
	public void teardown() throws Exception {
		this.driver.quit();
	}
	
	@Test
	public void changePageTest() throws InterruptedException {
		IndexPage indexPage = PageFactory.initElements(this.driver, IndexPage.class);
		
	}

}
