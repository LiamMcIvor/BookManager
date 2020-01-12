package com.bae.manager.selenium.constants;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
	public static final String PATH = "C:\\Users\\Luke\\git\\BookManager\\src\\test\\resources\\chromedriver.exe";
	public static final String PROPERTY = "webdriver.chrome.webdriver";
	
	@Value("${local.server.port")
	public static int port;
	
	public static final String HOST = "http://localhost:" + port;
	
	public static final String INDEX = "/index.html";
	
	public static final String ADD_BOOKS = "/add-books.html";
	
	public static final String UPDATE_DELETE_BOOKS = "/update-books.html";

}
