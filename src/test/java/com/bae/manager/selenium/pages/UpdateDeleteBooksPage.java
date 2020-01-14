package com.bae.manager.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateDeleteBooksPage {
	
	@FindBy(xpath = "/html/body/div[1]/h3")
	private WebElement emptyBooksHeader;
	
	
	public String getEmptyBooksHeader() {
		return this.emptyBooksHeader.getText();
	}

}
