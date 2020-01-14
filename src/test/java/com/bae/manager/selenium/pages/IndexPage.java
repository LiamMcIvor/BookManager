package com.bae.manager.selenium.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage {
	
	@FindBy(xpath = "/html/body/div/h3")
	private WebElement emptyBookHeader;
	
	
	public String getEmptyBookHeader() {
		return this.emptyBookHeader.getText();
	}
	
}
