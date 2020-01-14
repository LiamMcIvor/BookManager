package com.bae.manager.selenium.pages.components;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Navbar {
	
	@FindBy(id = "addBooks")
	private WebElement addBooksButton;

	@FindBy(id = "updateDeleteBooks")
	private WebElement updateDeleteBooksButton;
	
	@FindBy(id = "index")
	private WebElement indexButton;
	
	public void navigateToAddBooks() {
		this.addBooksButton.click();
	}
	
	public void navigateToUpdateBooks() {
		this.updateDeleteBooksButton.click();
	}
	
	public void navigateToIndex() {
		this.indexButton.click();
	}

}
