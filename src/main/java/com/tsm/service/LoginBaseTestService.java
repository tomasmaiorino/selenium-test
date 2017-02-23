package com.tsm.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.LoginConfig;
import com.tsm.config.MyConfig;
import com.tsm.model.TestDone;
import com.tsm.model.TestResult;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomas on 2/15/17.
 */
@Service
@Slf4j
public class LoginBaseTestService extends BaseTestService {

	@Getter
	@Setter
	@Autowired
	private MyConfig myConfig;

	@Getter
	@Setter
	@Autowired
	private LoginConfig loginConfig;

	@Getter
	@Setter
	@Autowired
	private LoadDriversTest loadDriversTest;

	@Override
	public List<BaseTestDriver> initialLoad() {
		log.debug("initial load ->");
		List<String> driversNames = myConfig.getLoginDriversToTest();
		List<BaseTestDriver> drivers = loadDriversTest.getTestDriversByName(driversNames);
		drivers.forEach(dr -> System.setProperty(dr.getSystemPropertyName(), dr.getSystemPropertyValue()));
		log.debug("initial load <-");
		return drivers;
	}

	@Override
	public TestResult runTest() {
		log.info("runTest ->");
		TestResult result = new TestResult();
		boolean success = true;
		List<BaseTestDriver> drivers = initialLoad();
		for (BaseTestDriver baseDriver : drivers) {
			WebDriver driver = null;
			try {
				log.info("Testing driver: " + baseDriver.getDriverName());
				log.debug("Doing get call: " + loginConfig.getLoginTestUrl());
				driver = baseDriver.getWebDriver();
				driver.get(loginConfig.getLoginTestUrl());
				
				WebElement username = driver
						.findElement(getByUsername(loginConfig));

				WebElement password = driver
						.findElement(getByPassword(loginConfig));

				username.sendKeys(loginConfig.getLoginTestUsernameValid());
				password.sendKeys(loginConfig.getLoginTestPasswordValid());
				log.info("Submitting form.");
				submitForm(username, driver, loginConfig);
				log.info("Looking for: " + loginConfig.getLoginTestValidExpectedPresenceElement());

				TestDone testDone = new TestDone();
				testDone.setTestedUrl(loginConfig.getLoginTestUrl());
				testDone.setTestedService(loginConfig.getLoginTestName());
				testDone.setTestedDriver(baseDriver.getDriverName());
				if (wasTestSuccessful(driver)) {
					testDone.setStatus(HttpStatus.SC_OK);
				} else {
					success = false;
					testDone.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
				}
				result.getTestsDone().add(testDone);
			} catch (Exception e) {
				log.error("Error trying test driver: " + baseDriver.getDriverName(), e);
				success = false;
			} finally {
				if (driver != null ) {
					driver.quit();
				}
			}
		}
		result.setStatus(success ? HttpStatus.SC_OK : HttpStatus.SC_INTERNAL_SERVER_ERROR);
		log.info("runTest <-");
		return result;
	}

	public void submitForm(WebElement formElement, WebDriver driver, LoginConfig loginConfig) {
		if (StringUtils.isNoneBlank(loginConfig.getLoginTestFormElementId())) {
			formElement = driver.findElement(By.id(loginConfig.getLoginTestFormElementId()));
		} else if (StringUtils.isNoneBlank(loginConfig.getLoginTestFormElementName())) {
			formElement = driver.findElement(By.id(loginConfig.getLoginTestFormElementName()));
		}
		formElement.submit();
	}

	@Override
	public boolean wasTestSuccessful(Object object) {
		WebDriver driver = (WebDriver) object;
		WebDriverWait wait = new WebDriverWait(driver, loginConfig.getLoginTestWaitTimeout());
		List<WebElement> resultsLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
				By.className(loginConfig.getLoginTestValidExpectedPresenceElement())));

		return resultsLinks != null && !resultsLinks.isEmpty();
	}

	private By getByUsername(LoginConfig loginConfig) {
		if (StringUtils.isNoneBlank(loginConfig.getLoginTestUsernameElementId())) {
			return By.id(loginConfig.getLoginTestUsernameElementId());
		} else if (StringUtils.isNoneBlank(loginConfig.getLoginTestUsernameElementName())) {
			return By.name(loginConfig.getLoginTestUsernameElementName());
		} else {
			return By.className(loginConfig.getLoginTestUsernameElementClass());
		}
	}

	private By getByPassword(LoginConfig loginConfig) {
		if (StringUtils.isNoneBlank(loginConfig.getLoginTestPasswordElementId())) {
			return By.id(loginConfig.getLoginTestPasswordElementId());
		} else if (StringUtils.isNoneBlank(loginConfig.getLoginTestPasswordElementName())) {
			return By.name(loginConfig.getLoginTestPasswordElementName());
		} else {
			return By.className(loginConfig.getLoginTestPasswordElementClass());
		}
	}


}
