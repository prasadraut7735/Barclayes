package com.qa.barclayes.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.qa.barclayes.constants.AppConstants.*;
import com.qa.barclayes.base.BaseTest;

public class AccountsTest extends BaseTest {

	@BeforeClass
	public void accSetup() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@Test
	public void accTitleTest() {
		String actTitle = accPage.getAccountPageTitle();
		Assert.assertEquals(actTitle, "My Account");
	}

	@Test
	public void accUrlTest() {
		String actUrl = accPage.getAccountPageUrl();
		Assert.assertTrue(actUrl.contains("route=account/account"));
	}

	@Test
	public void accHeaderTest() {
		List<String> actHeader = accPage.getAccPageHeader();
		Assert.assertEquals(actHeader, EXPECTED_HEADERLIST);
	}

	@Test
	public void productSearchTest() {
		searchResult = accPage.doSearch("MacBook");
		Assert.assertEquals(searchResult.getSearchResultCount(), 3);
	}

}
