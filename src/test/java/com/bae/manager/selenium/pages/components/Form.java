package com.bae.manager.selenium.pages.components;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Form {
	
	@FindBy(id = "titleField")
	private WebElement titleInput;
	
	@FindBy(xpath = "/html/body/div/form/div[2]/span/span[1]/span/ul/li/input")
	private WebElement authorInput;
	
	@FindBy(id = "series")
	private WebElement seriesInput;
	
	@FindBy(id = "wishlist")
	private WebElement wishlistRadio;
	
	@FindBy(id = "owned")
	private WebElement ownedRadio;
	
	@FindBy(id = "read")
	private WebElement readRadio;
	
	@FindBy(id = "reading")
	private WebElement readingRadio;
	
	@FindBy(id = "toRead")
	private WebElement toReadRadio;
	
	@FindBy(id = "timesRead")
	private WebElement timesReadInput;
	
	@FindBy(id = "formSubmit")
	private WebElement submitButton;
	
	
	public void enterTitle(String title) {
		this.titleInput.clear();
		this.titleInput.sendKeys(title);
	}
	
	public void enterAuthor(String authorName) {
		this.authorInput.sendKeys(authorName);
		this.authorInput.sendKeys(Keys.ENTER);
	}
	
	public void enterSeries(String series) {
		this.seriesInput.clear();
		this.seriesInput.sendKeys(series);
	}
	
	public void enterTimesRead(String timesRead) {
		this.timesReadInput.clear();
		this.timesReadInput.sendKeys(timesRead);
	}
	
	public void selectOwnedRadio() {
		this.ownedRadio.click();
	}
	
	public void selectWishlistRadio() {
		this.wishlistRadio.click();
	}
	
	public void selectReadRadio() {
		this.readRadio.click();
	}
	
	public void selectReadingRadio() {
		this.readingRadio.click();
	}
	
	public void selectToReadRadio() {
		this.toReadRadio.click();
	}
	
	public void submit() {
		this.submitButton.click();
	}

}
