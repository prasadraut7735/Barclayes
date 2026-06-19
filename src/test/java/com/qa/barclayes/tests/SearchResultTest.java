package com.qa.barclayes.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.barclayes.base.BaseTest;

public class SearchResultTest extends BaseTest {

	@BeforeClass
	public void resultSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void productResultCountTest() {
		searchResult = accPage.doSearch("MacBook");
		Assert.assertEquals(searchResult.getSearchResultCount(), 3);
	}

	@Test
	public void selectProductTest() {
		searchResult = accPage.doSearch("MacBook");
		productInfoPage = searchResult.selectProduct("MacBook Pro");
		Assert.assertEquals(productInfoPage.getProductHeaderText(), "MacBook Pro");
	}
}
