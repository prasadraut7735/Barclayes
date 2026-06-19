package com.qa.barclayes.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.barclayes.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	// private locators
	private final By email = By.id("input-email");
	private final By pwd = By.id("input-password");
	private final By loginBtn = By.xpath("//input[@type='submit']");
	private final By fgtPwd = By.linkText("Forgotten Password");
	private final By register = By.linkText("Register");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	@Step("Login page title")
	public String getLoginPageTitle() {
		return driver.getTitle();
	}

	public String getLoginPageUrl() {
		return driver.getCurrentUrl();
	}

	public boolean isForgetPwdLinkExist() {
		return eleUtil.isElementDisplayed(fgtPwd);
	}

	public AccountPage doLogin(String username, String password) {
		eleUtil.doSendKeys(email, username);
		eleUtil.doSendKeys(pwd, password);
		eleUtil.doClick(loginBtn);
		return new AccountPage(driver);
	}

	public RegistrationPage navigateToRegistrationPage() {
		eleUtil.doClick(register);
		return new RegistrationPage(driver);
	}
}
