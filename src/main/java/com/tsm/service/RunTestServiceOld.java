package com.tsm.service;

import java.util.ArrayList;
import java.util.List;

import com.tsm.model.*;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.GeneralConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomas on 3/9/17.
 */
@Slf4j
public class RunTestServiceOld {

    @Autowired
    @Getter
    @Setter
    private ManageDrivers manageDrivers;

    @Autowired
    @Getter
    @Setter
    private GeneralConfig generalConfig;

    private WebDriverWait wait;

    public TestResult runTest(ScenarioTest scenarioTest) {
        Assert.notNull(scenarioTest, "ScenarioTest must not be null!");
        TestResult result = new TestResult();
        List<TestDone> testsDone = new ArrayList<>();

        List<BaseTestDriver> baseDrivers = manageDrivers.loadDrivers(generalConfig.getDriversToTest(), scenarioTest);

        for (BaseTestDriver driver : baseDrivers) {
            testsDone.add(doesTest(scenarioTest, driver));
        }
        return result;
    }

    public void executeAction(WebDriver driver, ActionAttributeTest attributeTest) {
        Assert.notNull(attributeTest, "ActionAttributeTest must not be null");
        Assert.notNull(driver, "WebDriver must not be null");
        WebElement ele = getWebElement(driver, attributeTest);
        if (attributeTest.getAction().equals("click")) {
            ele.click();
        }
    }

    public TestDone doesTest(ScenarioTest scenarioTest, BaseTestDriver baseDriver) {
        Assert.notNull(baseDriver, "BaseTestDriver must not be null");
        Assert.notNull(scenarioTest, "ScenarioTest must not be null");
        WebDriver driver = baseDriver.getWebDriver(scenarioTest.isDisableJavascript());
        driver.get(scenarioTest.getUrlTest());

        configureRequest(scenarioTest.getAttributesTest(), driver);

        submitForm(driver, scenarioTest.getFormTest());

        for (AttributeTest validationAttribute : scenarioTest.getValidationAttributesTest()) {
            List<WebElement> resultElements = getResultElements(baseDriver, driver, validationAttribute);
//             boolean wasTestSucceful = wasTestSuccessful(resultElements, validationAttribute, byElement);
        }

        return null;
    }

    public void submitForm(WebDriver driver, SeleniumAttributeTest formTest) {
        if (formTest != null) {
            WebElement ele = getWebElement(driver, formTest);
            ele.submit();
        } else {
            log.info("There is not form to submit!");
        }
    }

    public WebElement getWebElement(WebDriver driver, SeleniumAttributeTest attr) {
        log.info("getWebElement ->");

        Assert.notNull(attr, "SeleniumAttributeTest must not be null");
        Assert.notNull(driver, "WebDriver must not be null");

        WebElement ele = null;
        if (StringUtils.isNoneBlank(attr.getId())) {
            ele = driver.findElement(By.id(attr.getId()));
        } else if (StringUtils.isNoneBlank(attr.getName())) {
            ele = driver.findElement(By.name(attr.getName()));
        } else if (StringUtils.isNoneBlank(attr.getCssClass())) {
            ele = driver.findElement(By.className(attr.getCssClass()));
        }
        log.info("getWebElement <-");
        return ele;
    }

    public void configureRequest(List<SeleniumAttributeTest> attributeTests, WebDriver driver) {
        Assert.notEmpty(attributeTests, "attributeTests must not be empty!");
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

    public By getExpectedElement(AttributeTest attr) {
        Assert.notNull(attr, "AttributeTest must not be null!");
        if (StringUtils.isNoneBlank(attr.getId())) {
            return By.id(attr.getId());
        } else if (StringUtils.isNoneBlank(attr.getName())) {
            return By.name(attr.getName());
        } else if (StringUtils.isNoneBlank(attr.getCssClass())) {
            return By.className(attr.getCssClass());
        }
        return null;
    }

    public boolean wasTestSuccessful(List<WebElement> results, AttributeTest attributeTest, By byElement) {

        if (attributeTest.getValidationType().equals("html")) {
            Assert.notNull(byElement,
                "By must not be null if the test type validation is html.");
        }

        if (attributeTest.getValidationType().equals("html")) {
            if (results != null && !results.isEmpty()) {
                return results.get(0).findElement(byElement).getText().equals(attributeTest.getValue());
            }
        } else if (attributeTest.getValidationType().equals("hasElement")) {
            return results != null && !results.isEmpty();
        } else if (attributeTest.getValidationType().equals("text")) {
            return results.get(0).getText().equals(attributeTest.getValue());
        }
        return false;
    }

    public List<WebElement> getResultElements(BaseTestDriver baseTestDriver, WebDriver driver, AttributeTest attributeTest) {
        Assert.notNull(baseTestDriver, "BaseTestDriver must not be null!");
        wait = baseTestDriver.getWebDriverWait(driver, generalConfig.getTestWaitTimeout());
        By byElement = getExpectedElement(attributeTest);

        if (attributeTest.getActionAttributeTest() != null) {
            log.info("There an action to be execute (wasTestSuccessful): "
                + attributeTest.getActionAttributeTest().getAction());
            executeAction(driver, attributeTest.getActionAttributeTest());
        }

        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(byElement));
    }
}
