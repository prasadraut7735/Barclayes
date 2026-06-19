package com.qa.barclayes.pages;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.barclayes.utils.ElementUtil;

public class ProductInfoPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	private By productHeader = By.tagName("h1");
	private By totalCount = By.cssSelector(".thumbnails>li");
	private By metaData = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][1]/li");
	private By priceData = By.xpath("//div[@id='content']//ul[@class='list-unstyled'][2]/li");

	Map<String, String> productMap;

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeaderText() {
		return eleUtil.getElementText(productHeader);
	}

	public int getProductImageCount() {
		return eleUtil.getElementsCount(totalCount);
	}

	public Map<String, String> getProductMetaData() {
		productMap = new LinkedHashMap<>();
		List<WebElement> eleList = eleUtil.getElements(metaData);
		for (WebElement e : eleList) {
			String text = e.getText();
			String meta[] = text.split(":");
			String key = meta[0].trim();
			String value = meta[1].trim();
			productMap.put(key, value);
		}
		return productMap;
	}

	public Map<String, String> getProductPriceData() {
		productMap = new LinkedHashMap<String, String>();
		List<WebElement> eleList = eleUtil.getElements(priceData);
		String productPrice = eleList.get(0).getText().trim();
		String exPrice = eleList.get(1).getText().split(":")[1].trim();
		productMap.put("Product Price", productPrice);
		productMap.put("ExaData", exPrice);
		return productMap;
	}
}
