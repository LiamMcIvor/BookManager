package com.bae.manager.selenium.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateDeleteBooksPage {
	
	@FindBy(xpath = "/html/body/div[1]/h3")
	private WebElement emptyBooksHeader;
	
	@FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div[2]/table/tbody/tr[1]")
	private WebElement tableEntry1;
	
	@FindBy(id = "delete")
	private WebElement deleteButton;
	
	@FindBy(id = "update")
	private WebElement updateButton;
	
	@FindBy(id = "deleteConfirm")
	private WebElement confirmDeleteButton;
	
	@FindBy(id = "deletePopupText")
	private WebElement deleteText;
	
	@FindBy(xpath = "/html/body/div[2]/form/div[3]/span/span[1]/span/ul/li[1]/span")
	private WebElement removeAuthor1button;	
	
	public String getEmptyBooksHeader() {
		return this.emptyBooksHeader.getText();
	}
	
	public List<String> getTableRow1() {
		List<WebElement> tableCells = tableEntry1.findElements(By.tagName("td"));
		List<String> tableCellData = new ArrayList<>();
		for (WebElement cellData : tableCells) {
			tableCellData.add(cellData.getText());
		}
		return tableCellData;
	}
	
	public void clickRow1() {
		this.tableEntry1.click();
	}

	public void clickDeleteRow1() {
		this.deleteButton.click();
	}
	
	public void clickDeleteConfirmRow1() {
		this.confirmDeleteButton.click();
	}
	
	public String getDeleteText() {
		return this.deleteText.getText();
	}
	
	public void updateButton() {
		this.updateButton.click();
	}
	
	public void removeAuthor1() {
		this.removeAuthor1button.click();		
	}
}
