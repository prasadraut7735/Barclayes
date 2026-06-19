package com.qa.barclayes.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.barclayes.exception.BrowserException;

public class DriverFactory {

	private WebDriver driver;
	private Properties prop;
	public static Logger log = LogManager.getLogger(DriverFactory.class);

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {
		String browserName = prop.getProperty("browser");
		System.out.println("Browser name is: " + browserName);

		OptionsManager op = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(op.getChromeOptions()));
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(op.getFirefoxOptions()));
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(op.getEdgeOptions()));
			break;
		case "safari":
			tlDriver.set(new SafariDriver());
			break;
		default:
			throw new BrowserException("======INVALID BROWSER NAME======");
		}
		getDriver().get(prop.getProperty("url"));
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

//	public static File getScreenshotFile() {
//		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
//	}

//	public static String getScreenshotFile() {
//		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
//	}

	public static byte[] getScreenshotFile() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
	}

	public Properties initProperties() {
		prop = new Properties();
		String envName = System.getProperty("env");
		FileInputStream ip = null;

		try {
			if (envName == null) {
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				System.out.println("Test case running on: " + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "sit":
					ip = new FileInputStream("./src/test/resources/config/sit.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				default:

					System.out.println("Please pass the correct environment name...");
					throw new BrowserException("======INVALID ENVIRONMENT=====");
				}
			}

			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
