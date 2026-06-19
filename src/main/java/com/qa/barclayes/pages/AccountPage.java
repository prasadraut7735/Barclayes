package com.qa.barclayes.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.barclayes.utils.ElementUtil;

public class AccountPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// By locator
	private final By headers = By.xpath("//div[@id='content']/h2");
	private final By search = By.cssSelector("input[name='search']");
	private final By searchIcon = By.cssSelector(".input-group-btn>button[type='button']");

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getAccountPageTitle() {
		return driver.getTitle();
	}

	public String getAccountPageUrl() {
		return driver.getCurrentUrl();
	}

	public List<String> getAccPageHeader() {
		return eleUtil.getElementTextList(headers);
	}

	public SearchResultPage doSearch(String searchKey) {
		eleUtil.doClick(search);
		eleUtil.doSendkys(search, searchKey);
		eleUtil.doClick(searchIcon);
		return new SearchResultPage(driver);
	}
}
