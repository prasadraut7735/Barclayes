package com.qa.barclayes.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.barclayes.base.BaseTest;
import com.qa.barclayes.utils.ExcelUtil;

public class RegistrationTest extends BaseTest {

	@BeforeClass
	public void registrationSetUp() {
		registerPage = loginPage.navigateToRegistrationPage();
	}

	@DataProvider
	public Object excelData() {
		return ExcelUtil.getExcelData("Register");
	}

	@Test(dataProvider = "excelData")
	public void userRegistration(String fname, String lName, String email, String telephone, String password,
			String isSubscribe) {
		Assert.assertTrue(registerPage.userRegistraion(fname, lName, email, telephone, password, isSubscribe));
	}
}
