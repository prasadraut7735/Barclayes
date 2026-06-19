package com.qa.barclayes.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.barclayes.base.BaseTest;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;

@Epic("CPD-41d4: Implement Login Page")
@Story("")
public class LoginTest extends BaseTest {

	@Test
	public void loginTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, "Account Login");
	}

	@Description("")
	@Owner("Prasad Raut")
	@Test
	public void loginUrlTest() {
		String actUrl = loginPage.getLoginPageUrl();
		Assert.assertTrue(actUrl.contains("route=account/login"));
	}

	@Test
	public void forgetPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgetPwdLinkExist());
	}

	@Test(priority = Byte.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), "My Account");
	}
}
