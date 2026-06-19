package com.qa.barclayes.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.barclayes.utils.ElementUtil;

public class RegistrationPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailID = By.id("input-email");
	private By telephoneNmb = By.id("input-telephone");
	private By pwd = By.id("input-password");
	private By cfmPwd = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	private By agreeCheckbox = By.name("agree");
	private By continueBtn = By.cssSelector("input[type='submit']");
	private By successMessage = By.cssSelector("div#content h1");
	private By logout = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public boolean userRegistraion(String fName, String lName, String email, String telephone, String password,
			String isSubscribe) {
		eleUtil.doSendKeys(firstName, fName);
		eleUtil.doSendKeys(lastName, lName);
		eleUtil.doSendKeys(emailID, email);
		eleUtil.doSendKeys(telephoneNmb, telephone);
		eleUtil.doSendKeys(pwd, password);
		eleUtil.doSendKeys(cfmPwd, password);
		if (isSubscribe.contains("Yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}
		eleUtil.doClick(agreeCheckbox);
		eleUtil.doClick(continueBtn);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
			String successText = eleUtil.getElementText(successMessage);
			if (successText.contains("Your Account Has Been Created!")) {
				eleUtil.doClick(logout);
				eleUtil.doClick(registerLink);
				return true;
			}
		} catch (TimeoutException e) {
			System.out.println("Element not found");
		}
		return false;
	}
}
