package com.qa.barclayes.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.chaintest.domain.Embed;
import com.aventstack.chaintest.plugins.ChainTestListener;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.EmbeddedAttachment;
import com.qa.barclayes.factory.DriverFactory;
import com.qa.barclayes.pages.AccountPage;
import com.qa.barclayes.pages.LoginPage;
import com.qa.barclayes.pages.ProductInfoPage;
import com.qa.barclayes.pages.RegistrationPage;
import com.qa.barclayes.pages.SearchResultPage;

//@Listeners(ChainTestListener.class)
public class BaseTest {
	protected WebDriver driver;
	protected Properties prop;
	protected DriverFactory df;
	protected LoginPage loginPage;
	protected AccountPage accPage;
	protected SearchResultPage searchResult;
	protected ProductInfoPage productInfoPage;
	protected RegistrationPage registerPage;

	@Parameters({ "browser" })
	@BeforeTest
	public void SetUp(@Optional("Chrome") String browserName) {
		df = new DriverFactory();
		prop = df.initProperties();
		if (browserName != null) {
			prop.setProperty("browser", browserName);
		}
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
	}

	@AfterMethod
	public void getFailedScreenshot(ITestResult result) {
		if (!result.isSuccess()) {
			ChainTestListener.embed(DriverFactory.getScreenshotFile(), "image/png");
		}
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}
