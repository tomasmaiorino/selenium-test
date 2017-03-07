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
import com.tsm.config.MyConfig;
import com.tsm.model.ActionAttributeTest;
import com.tsm.model.AttributeTest;
import com.tsm.model.ScenarioTest;
import com.tsm.model.SeleniumAttributeTest;
import com.tsm.model.TestDone;
import com.tsm.model.TestResult;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * Created by tomas on 2/23/17.
 */
@Service
@Slf4j
public class RunTestService {

	@Getter
	@Setter
	@Autowired
	private MyConfig myConfig;

	@Getter
	@Setter
	@Autowired
	private LoadDriversTest loadDriversTest;

	public List<BaseTestDriver> initialLoad(ScenarioTest scenarioTest) {
		log.info(String.format("%s initial load ->", scenarioTest.getNameTest()));
		List<String> driversNames = myConfig.getDriversToTest();
		// loadDriversTest.reloadDrives();
		List<BaseTestDriver> drivers = loadDriversTest.getTestDriversByName(driversNames);
		drivers.forEach(dr -> log.info(String.format("driver(%s) system property name (%s) system property value(%s)",
				dr.getDriverName(), dr.getSystemPropertyName(), dr.getSystemPropertyValue())));
		for(BaseTestDriver dr : drivers) {
			//System.setProperty(dr.getSystemPropertyName(), dr.getSystemPropertyValue());
			dr.setStartBrowser(scenarioTest.isStartBrowser());
		}
		//drivers.forEach(dr -> System.setProperty(dr.getSystemPropertyName(), dr.getSystemPropertyValue()));
		log.info(String.format("%s initial load <-", scenarioTest.getNameTest()));
		return drivers;
	}

	public TestResult runTest(ScenarioTest scenarioTest) {
		log.info(String.format("runTest (%s)->", scenarioTest.getNameTest()));
		TestResult result = new TestResult();
		boolean success = true;
			List<BaseTestDriver> drivers = initialLoad(scenarioTest);
			for (BaseTestDriver baseDriver : drivers) {
				WebDriver driver = baseDriver.getWebDriver();
				try {
					log.info("Testing driver: " + baseDriver.getDriverName() + " ->");
					log.debug("Doing get call: " + scenarioTest.getUrlTest());
					log.debug("getting url");
					driver.get(scenarioTest.getUrlTest());
					log.debug("configuring url");
					configureRequest(scenarioTest.getAttributesTest(), driver);

					log.info("Submitting form.");
					submitForm(driver, scenarioTest.getFormTest());

					TestDone testDone = new TestDone();
					testDone.setTestedUrl(scenarioTest.getUrlTest());
					testDone.setTestedService(scenarioTest.getNameTest());
					testDone.setTestedDriver(baseDriver.getDriverName());

					if (wasAllTestsSuccessful(driver, scenarioTest.getValidationAttributesTest())) {
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
					if (driver != null) {
						driver.close();
					}
					log.info("Testing driver: " + baseDriver.getDriverName() + " <-");
				}
			}

		result.setStatus(success ? HttpStatus.SC_OK : HttpStatus.SC_INTERNAL_SERVER_ERROR);
		log.info("runTest <-");
		return result;
	}

	private void submitForm(WebDriver driver, SeleniumAttributeTest formTest) {
		WebElement ele = getWebElement(driver, formTest);
		ele.submit();
	}

	private boolean wasAllTestsSuccessful(WebDriver driver, List<AttributeTest> attributeTest) {
		boolean success = true;
		for (SeleniumAttributeTest attr : attributeTest) {
			success = wasTestSuccessful(driver, (AttributeTest) attr);
			if (!success) {
				return success;
			}
		}
		return success;
	}

	private void executeAction(WebDriver driver, ActionAttributeTest attributeTest) {
		WebElement ele = getWebElement(driver, attributeTest);
		if (attributeTest.getAction().equals("click")) {
			ele.click();
		}
	}

	private boolean wasTestSuccessful(WebDriver driver, AttributeTest attributeTest) {
		WebDriverWait wait = new WebDriverWait(driver, myConfig.getTestWaitTimeout());
		By byElement = getExpectedElement(attributeTest);

		if (attributeTest.getActionAttributeTest() != null) {
			log.info("There an action to be execute (wasTestSuccessful): "
					+ attributeTest.getActionAttributeTest().getAction());
			executeAction(driver, attributeTest.getActionAttributeTest());
		}

		List<WebElement> resultsLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
		if (attributeTest.getValidationType().equals("html")) {
			if (resultsLinks != null && !resultsLinks.isEmpty()) {
				return resultsLinks.get(0).findElement(byElement).getText().equals(attributeTest.getValue());
			}
		} else if (attributeTest.getValidationType().equals("hasElement")) {
			return resultsLinks != null && !resultsLinks.isEmpty();
		} else if (attributeTest.getValidationType().equals("text")) {
			return resultsLinks.get(0).getText().equals(attributeTest.getValue());
		}
		return false;
	}

	private By getExpectedElement(AttributeTest attr) {
		if (StringUtils.isNoneBlank(attr.getId())) {
			return By.id(attr.getId());
		} else if (StringUtils.isNoneBlank(attr.getName())) {
			return By.name(attr.getName());
		} else if (StringUtils.isNoneBlank(attr.getCssClass())) {
			return By.className(attr.getCssClass());
		}
		return null;
	}

	private void configureRequest(List<SeleniumAttributeTest> attributeTests, WebDriver driver) {
		for (SeleniumAttributeTest attr : attributeTests) {
			boolean hasAction = attr.getActionAttributeTest() != null;

			if (hasAction && attr.getActionAttributeTest().isRunBeforeSetValue()) {
				log.info("There an action to be execute before (configureRequest): "
						+ attr.getActionAttributeTest().getAction());
				executeAction(driver, attr.getActionAttributeTest());
			}

			WebElement ele = getWebElement(driver, attr);
			ele.sendKeys(attr.getValue());

			if (hasAction && !attr.getActionAttributeTest().isRunBeforeSetValue()) {
				log.info("There an action to be execute after (configureRequest): "
						+ attr.getActionAttributeTest().getAction());
				executeAction(driver, attr.getActionAttributeTest());
			}
		}
	}

	private WebElement getWebElement(WebDriver driver, SeleniumAttributeTest attr) {
		WebElement ele = null;
		if (StringUtils.isNoneBlank(attr.getId())) {
			ele = driver.findElement(By.id(attr.getId()));
		} else if (StringUtils.isNoneBlank(attr.getName())) {
			ele = driver.findElement(By.name(attr.getName()));
		} else if (StringUtils.isNoneBlank(attr.getCssClass())) {
			ele = driver.findElement(By.className(attr.getCssClass()));
		}
		return ele;
	}
}
