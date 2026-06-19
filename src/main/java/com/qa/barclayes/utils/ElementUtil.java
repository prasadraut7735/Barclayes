package com.qa.barclayes.utils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.barclayes.exception.BrowserException;

public class ElementUtil {
	private WebDriver driver;
	private Actions act;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		act = new Actions(driver);
	}

	public WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	public void doClear(By locator) {
		getElement(locator).clear();
	}

	public void nullCheck(CharSequence text) {
		if (text == null || text.length() == 0) {
			throw new BrowserException("======NULL TEXT=====");
		}
	}

	public void doSendkys(By locator, CharSequence text) {
		doClear(locator);
		nullCheck(text);
		getElement(locator).sendKeys(text);
	}

	public void doSendKeys(By locator, String text) {
		doClear(locator);
		nullCheck(text);
		getElement(locator).sendKeys(text);
	}

	public void doClick(By locator) {
		getElement(locator).click();
	}

	public String getElementText(By locator) {
		String text = getElement(locator).getText();
		if (text == null || text.length() == 0) {
			System.out.println("=====NULL TEXT=====");
			return null;
		} else {
			System.out.println("Element text is: " + text);
			return text;
		}
	}

	public boolean isElementDisplayed(By locator) {
		try {
			getElement(locator).isDisplayed();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed");
			return false;
		}
	}

	public boolean isElementSelected(By locator) {
		try {
			getElement(locator).isSelected();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not selected");
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			getElement(locator).isEnabled();
			return true;
		} catch (NoSuchElementException e) {
			System.out.println("Element is not enabled");
			return false;
		}
	}

	public String doElementGetAttribute(By locator, String attributeName) {
		nullCheck(attributeName);
		String atrValue = getElement(locator).getDomAttribute(attributeName);
		System.out.println("DOM attribute is: " + atrValue);
		return atrValue;
	}

	public String doElementGetProperty(By locator, String propertyName) {
		nullCheck(propertyName);
		String propertyValue = getElement(locator).getDomProperty(propertyName);
		System.out.println("DOM property is: " + propertyValue);
		return propertyValue;
	}

	// ============== Find Elements Util==================//
	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		int count = getElements(locator).size();
		System.out.println("Element count is: " + count);
		return count;
	}

	public List<String> getElementTextList(By locator) {
		List<String> textList = new ArrayList<>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			textList.add(text);
		}
		return textList;
	}

	public List<String> getElementTextList1(By locator) {
		List<String> textList = new ArrayList<String>();
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			textList.addAll(Arrays.asList(text.split("\\R")));
		}
		System.out.println("Text list is: " + textList);
		return textList;
	}

	public boolean isElementPresent(By locator) {
		if (getElements(locator).size() == 1) {
			System.out.println("Element is displayed");
			return true;
		} else {
			System.out.println("Element is not displayed");
			return false;
		}
	}

	public boolean checkElementDisplayed(By locator, int expEleCount) {
		if (getElements(locator).size() == expEleCount) {
			System.out.println("Element is displayed");
			return true;
		} else {
			System.out.println("Element is not displayed");
			return false;
		}
	}

	public boolean doSearchMethod(By searchField, By locator, String searchKey, String expKey) {
		doSendKeys(searchField, searchKey);
		List<WebElement> eleList = getElements(locator);
		boolean flag = false;
		if (eleList.size() == 0) {
			throw new NullPointerException("=====NO SUGGETIONS FOR MENTIONED SEARCHKEY====");
		} else {
			for (WebElement e : eleList) {
				String text = e.getText();
				if (text.contains(expKey)) {
					e.click();
					flag = true;
					break;
				}
			}
		}
		if (flag) {
			System.out.println("Suggestion matched");
			return true;
		} else {
			System.out.println("Suggestions not matched");
			return false;
		}
	}

	public boolean clickedElementMenthod(By locator, String clickText) {
		List<WebElement> eleList = getElements(locator);
		boolean flag = false;
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.contains(clickText)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Select base Drop-down util
	 */

	public Select getSelect(By locator) {
		return new Select(getElement(locator));
	}

	public boolean doSelectByIndex(By locator, int index) {
		Select select = getSelect(locator);
		try {
			select.selectByIndex(index);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean doSelectByVisibleText(By locator, String text) {
		Select select = getSelect(locator);
		try {
			select.selectByVisibleText(text);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean doSelectByValue(By locator, String value) {
		Select select = getSelect(locator);
		try {
			select.selectByValue(value);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public int getDropDownOptionsCount(By locator) {
		return getSelect(locator).getOptions().size();
	}

	public List<String> getDropDownTextList(By locator) {
		List<String> textList = new ArrayList<>();
		List<WebElement> eleList = getSelect(locator).getOptions();
		for (WebElement e : eleList) {
			String text = e.getText();
			textList.add(text);
		}
		return textList;
	}

	/**
	 * Select drop-down using select class
	 */

	public boolean selectDropDown(By locator, String valueToBeSelected) {
		List<WebElement> eleList = getSelect(locator).getOptions();
		boolean flag = false;
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.contains(valueToBeSelected)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	public boolean compareSelectBasedDropdownOptionsWithExpected(By locator, String... expectList) {
		List<WebElement> eleList = getSelect(locator).getOptions();
		List<String> actTextList = new ArrayList<String>();
		for (WebElement e : eleList) {
			String text = e.getText();
			actTextList.add(text);
		}
		if (actTextList.contains(expectList)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Select drop-down without select class
	 */
	public boolean doSelectNonSelectBasedDropdownValue(By locator, String valueToBeSelected) {
		boolean flag = false;
		List<WebElement> eleList = getElements(locator);
		for (WebElement e : eleList) {
			String text = e.getText();
			if (text.contains(valueToBeSelected)) {
				e.click();
				flag = true;
				break;
			}
		}
		if (flag) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method is used to select the multiple value from Drop-down: 1. Single
	 * selection is allowed 2. Multiple Selection is allowed 3. Selection of all is
	 * also possible.
	 * 
	 * @param selectDropDown
	 * @param dropDownList
	 * @param valueToBeSelected
	 */

	public void multiSelectDropDown(By locator, By selectDropdown, String... expectedList) {
		doClick(locator);
		List<WebElement> eleList = getElements(locator);
		if (expectedList[0] == "All") {
			for (WebElement e : eleList) {
				e.click();
			}
		} else {
			for (WebElement e : eleList) {
				String text = e.getText();
				for (String value : expectedList) {
					if (text.contains(value)) {
						e.click();
						break;
					}
				}
			}
		}
	}

	/**
	 * Actions Utils
	 * 
	 * @return
	 */

	public Actions doMoveToElement(By locator) {
		return act.moveToElement(getElement(locator));
	}

	public void doActionsClick(By locator) {
		act.click(getElement(locator)).build().perform();
	}

	public void doActionsSendkey(By locator, CharSequence text) {
		act.sendKeys(text).build().perform();
	}

	public void doActionsSendkey(By locator, String text) {
		act.sendKeys(text).build().perform();
	}

	public void doActiobsSendKeysWithPause(By locator, CharSequence text, int pauseTimeOut) {
		char ch[] = text.toString().toCharArray();
		for (char e : ch) {
			act.sendKeys(getElement(locator), String.valueOf(e)).pause(Duration.ofMillis(pauseTimeOut)).build()
					.perform();
		}
	}

	public void handleParentSubMenu(By parentMeny, By subMenu) {
		doMoveToElement(parentMeny).click(getElement(subMenu)).build().perform();
	}

	public void hanle4LevelMenuHandling(By selectmenu, By level1, By level2, By level3) {
		doClick(selectmenu);
		doMoveToElement(level1).build().perform();
		act.pause(200);
		doMoveToElement(level2).build().perform();
		act.pause(200);
		doClick(level3);
	}

	/**
	 * Wait Utils
	 */
	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param timeout
	 * @param locator
	 */

	public void waitForElementPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param timeout
	 * @param locator
	 */
	public WebElement waitForElementVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void clickWithWait(By locator, int timeout) {
		waitForElementVisible(locator, timeout).click();
	}

	/**
	 * An expectation for checking an element is visible and enabled such that you
	 * can click it.
	 * 
	 * @param locator
	 * @param timeout
	 */
	public void clickWhenReady(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}

	public void sendkeyswithWait(By locator, int timeout, String valueToBeEntered) {
		waitForElementVisible(locator, timeout).sendKeys(valueToBeEntered);
	}

	public void waitForAllElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public List<WebElement> waitForAllElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	/**
	 * Wait alert utility
	 */
	public Alert waitForAlert(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(int timeout) {
		waitForAlert(timeout).accept();
	}

	public void dismissAlert(int timeout) {
		waitForAlert(timeout).dismiss();
	}

	public String getAlertText(int timeout) {
		return waitForAlert(timeout).getText();
	}

	/**
	 * Wait For Title utility
	 */
	public boolean waitForTitleContains(int timeout, String fractionTitle) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.titleContains(fractionTitle));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean waitForTitle(int timeout, String title) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.titleIs(title));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getTitleIs(int timeout, String fractionTitle) {
		if (waitForTitleContains(timeout, fractionTitle)) {
			return driver.getTitle();
		} else {
			throw new BrowserException("=====TITLE NOT MATCHED=====");
		}
	}

	/**
	 * Wait For URL utility
	 */
	public boolean waitForUrl(String url, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.urlToBe(url));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public boolean waitForUrlContains(String fractionUrl, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.urlContains(fractionUrl));
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String getUrl(String expfractionUrl, int timeout) {
		if (waitForUrlContains(expfractionUrl, timeout)) {
			return driver.getCurrentUrl();
		} else {
			throw new BrowserException("======TITLE NOT MATCHED======");
		}
	}

	/**
	 * Wait For Frame utility
	 */
	public void waitForFrame(int timeout, int index) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}

	public void waitForFrame(int timeout, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	public void waitForFrame(int timeout, String frameName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
	}

	public void waitForFrame(int timeout, WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
	}

	/**
	 * Wait Window utility
	 */
	public boolean waitForWindow(int timeout, int expectedNumberOfWindow) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		try {
			return wait.until(ExpectedConditions.numberOfWindowsToBe(expectedNumberOfWindow));
		} catch (TimeoutException e) {
			throw new BrowserException("===NUMBER OF WINDOW NOT MATCHED===");
		}
	}

	/**
	 * Fluent Wait
	 */
	/**
	 * An expectation for checking that an element is present on the DOM of a page
	 * and visible. Visibility means that the element is not only displayed but also
	 * has a height and width that is greater than 0.
	 * 
	 * @param locator
	 * @param timeout
	 * @param pollingTime
	 * @return
	 */
	public WebElement waitForVisibilityOfElementWithPollingInterval(By locator, int pollingInterval, int timeout) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingInterval)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("====ELEMENT NOT FOUND EXCEPTION===");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * An expectation for checking that an element is present on the DOM of a page.
	 * This does not necessarily mean that the element is visible.
	 * 
	 * @param locator
	 * @param timeout
	 * @param pollingTime
	 * @return
	 */
	public void waitForPresenceOfElementWithPollingInterval(By locator, int timeout, int pollingInterval) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(pollingInterval)).ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class).withMessage("===ELEMENT NOT FOUND===");
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public boolean isPageLoaded(int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		String flag = wait.until(ExpectedConditions.jsReturnsValue("document.readyState=='complete'")).toString();
		return Boolean.parseBoolean(flag);
	}
}
