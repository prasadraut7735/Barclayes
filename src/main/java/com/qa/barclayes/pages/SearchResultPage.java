package com.qa.barclayes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.barclayes.utils.ElementUtil;

public class SearchResultPage {
	private WebDriver driver;
	private By resultCount = By.cssSelector("div.product-thumb");
	private ElementUtil eleUtil;

	public SearchResultPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public int getSearchResultCount() {
		return eleUtil.getElementsCount(resultCount);
	}

	public ProductInfoPage selectProduct(String productName) {
		eleUtil.doClick(By.linkText(productName));
		return new ProductInfoPage(driver);
	}
}
