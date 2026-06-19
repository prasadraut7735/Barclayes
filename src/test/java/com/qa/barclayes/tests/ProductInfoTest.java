package com.qa.barclayes.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.barclayes.base.BaseTest;

public class ProductInfoTest extends BaseTest {

	@BeforeClass
	public void productInfoSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object getData() {
		return new Object[][] {
			{"MacBook","MacBook Air",4},
			{"MacBook","MacBook Pro",4},
			{"MacBook","MacBook",5},
		};
	}

	@Test(dataProvider = "getData")
	public void productImageCountTest(String product, String subProduct,int imageCount) {
		searchResult = accPage.doSearch(product);
		productInfoPage = searchResult.selectProduct(subProduct);
		int actImage = productInfoPage.getProductImageCount();
		Assert.assertEquals(actImage, imageCount);
	}

	@Test
	public void productMetaTest() {
		searchResult = accPage.doSearch("MacBook");
		productInfoPage = searchResult.selectProduct("MacBook Air");
		Map<String, String> actMataData = productInfoPage.getProductMetaData();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actMataData.get("Brand"), "Apple");
		softAssert.assertEquals(actMataData.get("Product Code"), "Product 17");
		softAssert.assertEquals(actMataData.get("Reward Points"), "700");
		softAssert.assertEquals(actMataData.get("Availability"), "Out Of Stock");
		softAssert.assertAll();
	}

	@Test
	public void productPriceData() {
		searchResult = accPage.doSearch("MacBook");
		productInfoPage = searchResult.selectProduct("MacBook Air");
		Map<String, String> actMataData = productInfoPage.getProductPriceData();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(actMataData.get("Product Price"), "$1,202.00");
		softAssert.assertEquals(actMataData.get("ExaData"), "$1,000.00");
		softAssert.assertAll();
	}
}
