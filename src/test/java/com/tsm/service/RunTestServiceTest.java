package com.tsm.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.ChromeTestDriver;
import com.tsm.config.FireFoxTestDriver;
import com.tsm.config.GeneralConfig;
import com.tsm.model.ActionAttributeTest;
import com.tsm.model.AttributeTest;
import com.tsm.model.FormTest;
import com.tsm.model.ScenarioTest;
import com.tsm.model.SeleniumAttributeTest;

/**
 * Created by tomas on 3/9/17.
 */
public class RunTestServiceTest {

    @Mock
    private ManageDrivers manageDrivers;

    @Mock
    private GeneralConfig generalConfig;

    @Mock
    private ScenarioTest scenarioTest;

    @Mock
    private List<BaseTestDriver> baseDrivers = new ArrayList<>();

    @Mock
    private FireFoxTestDriver fireFoxTestDriver;

    @Mock
    private ChromeTestDriver chromeTestDriver;

    @InjectMocks
    private RunTestService run;

    @Mock
    private WebElement element;

    @Mock
    private FirefoxDriver firefoxDriver;

    @Mock
    private ChromeDriver chromeDriver;

    @Mock
    private HtmlUnitDriver htmlDriver;

    @Mock
    private By by;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<String> driversName = Arrays.asList("chrome");
        when(generalConfig.getDriversToTest()).thenReturn(driversName);
        ScenarioTest scenarioTest = buildScenarioTest();
        when(manageDrivers.loadDrivers(driversName, scenarioTest)).thenReturn(baseDrivers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void runTest_NullScenarioTest_ShouldThrowException() {
        ScenarioTest scenarioTest = null;
        run.runTest(scenarioTest);
    }

    @Test(expected = IllegalArgumentException.class)
    public void doesTest_NullDriverGiven_ShouldThrowException() {
        BaseTestDriver driver = null;
        run.doesTest(buildScenarioTest(), driver);
    }

    @Test
    public void getWebElement_NoneElementGivenUsingHtmlUnitDriver_ShouldReturnNull() {
        SeleniumAttributeTest selenium = new AttributeTest();
        Assert.assertNull(run.getWebElement(htmlDriver, selenium));
    }

    // FIREFOX
    @Test(expected = IllegalArgumentException.class)
    public void doesTest_NullScenarioGiven_ShouldThrowException() {
        ScenarioTest scenarioTest = null;
        run.doesTest(scenarioTest, fireFoxTestDriver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullSeleniumAttributeTestGiven_ShouldThrowException() {
        SeleniumAttributeTest attribute = null;
        run.getWebElement(new HtmlUnitDriver(), attribute);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullWebdriverTestGiven_ShouldThrowException() {
        WebDriver driver = null;
        run.getWebElement(driver, new SeleniumAttributeTest());
    }

    @Test
    public void getWebElement_NoneElementGivenUsingFireFoxeDriver_ShouldReturnNull() {
        SeleniumAttributeTest selenium = new AttributeTest();
        Assert.assertNull(run.getWebElement(firefoxDriver, selenium));
    }

    @Test
    public void getWebElement_Valid_ByIdGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setId("user");
        when(fireFoxTestDriver.getWebDriver(false)).thenReturn(firefoxDriver);
        when(firefoxDriver.findElement(By.id(selenium.getId()))).thenReturn(element);

        WebElement element = run.getWebElement(firefoxDriver, selenium);

        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByNameGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setName("login");
        when(fireFoxTestDriver.getWebDriver(false)).thenReturn(firefoxDriver);
        when(firefoxDriver.findElement(By.name(selenium.getName()))).thenReturn(element);

        WebElement element = run.getWebElement(firefoxDriver, selenium);

        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByClassNameGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setCssClass("login");
        when(fireFoxTestDriver.getWebDriver(false)).thenReturn(firefoxDriver);
        when(firefoxDriver.findElement(By.className(selenium.getCssClass()))).thenReturn(element);

        WebElement element = run.getWebElement(firefoxDriver, selenium);

        Assert.assertNotNull(element);
    }

    // CHROME
    @Test(expected = IllegalArgumentException.class)
    public void doesTest_NullScenarioGiven_ChromeDriver_ShouldThrowException() {
        ScenarioTest scenarioTest = null;
        run.doesTest(scenarioTest, chromeTestDriver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullSeleniumAttributeTestGiven_ChromeDriver_ShouldThrowException() {
        SeleniumAttributeTest attribute = null;
        run.getWebElement(new HtmlUnitDriver(), attribute);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullWebdriverTestGiven_ChromeDriver_ShouldThrowException() {
        WebDriver driver = null;
        run.getWebElement(driver, new SeleniumAttributeTest());
    }

    @Test
    public void getWebElement_NoneElementGivenUsingChromeDriver_ShouldReturnNull() {
        SeleniumAttributeTest selenium = new AttributeTest();
        Assert.assertNull(run.getWebElement(chromeDriver, selenium));
    }

    @Test
    public void getWebElement_Valid_ByIdGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setId("user");
        when(chromeDriver.findElement(By.id(selenium.getId()))).thenReturn(element);

        WebElement element = run.getWebElement(chromeDriver, selenium);

        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByNameGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setName("login");
        when(chromeDriver.findElement(By.name(selenium.getName()))).thenReturn(element);

        WebElement element = run.getWebElement(chromeDriver, selenium);

        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByClassNameGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setCssClass("login");
        when(chromeDriver.findElement(By.className(selenium.getCssClass()))).thenReturn(element);

        WebElement element = run.getWebElement(chromeDriver, selenium);

        Assert.assertNotNull(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeAction_NullWebdriverGiven_ShouldThrowException() {
        WebDriver driver = null;
        run.executeAction(driver, new ActionAttributeTest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeAction_NullActionAttributeTestGiven_ShouldThrowException() {
        ActionAttributeTest action = null;
        run.executeAction(new HtmlUnitDriver(), action);
    }

    @Test
    public void executeAction_ActionAttributeExecuted() {
        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("click");
        action.setId("act");
        when(chromeDriver.findElement(By.id(action.getId()))).thenReturn(element);

        run.executeAction(chromeDriver, action);
        verify(element).click();
    }

    @Test
    public void executeAction_ActionInvalid() {
        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("selected");
        action.setId("act");
        when(chromeDriver.findElement(By.id(action.getId()))).thenReturn(element);

        run.executeAction(chromeDriver, action);
        verify(element, times(0)).click();
        verify(chromeDriver, times(1)).findElement(By.id(action.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void configureRequest_EmptySeleniumAttributeGiven_ShouldThrowException() {
        WebDriver driver = new HtmlUnitDriver();
        run.configureRequest(Collections.emptyList(), driver);
    }

    @Test
    public void configureRequest_WithValidActionBeforeSetValueUsingIdGiven_Valid() {
        AttributeTest attribute = new AttributeTest();
        attribute.setId("login");
        attribute.setValue("user@user.com");

        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("click");
        action.setId("act");
        action.setRunBeforeSetValue(true);

        attribute.setActionAttributeTest(action);

        WebElement actionElement = mock(WebElement.class);
        when(chromeDriver.findElement(By.id(action.getId()))).thenReturn(actionElement);
        when(chromeDriver.findElement(By.id(attribute.getId()))).thenReturn(element);

        run.configureRequest(Collections.singletonList(attribute), chromeDriver);

        InOrder inOrder = Mockito.inOrder(chromeDriver, actionElement, element);

        inOrder.verify(actionElement, times(1)).click();
        inOrder.verify(chromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(element, times(1)).sendKeys(attribute.getValue());

        verifyNoMoreInteractions(actionElement);
    }

    @Test
    public void configureRequest_WithValidActionAfterSetValueUsingIdGiven_Valid() {
        AttributeTest attribute = new AttributeTest();
        attribute.setId("login");
        attribute.setValue("user@user.com");

        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("click");
        action.setId("act");

        attribute.setActionAttributeTest(action);

        WebElement actionElement = mock(WebElement.class);
        when(chromeDriver.findElement(By.id(action.getId()))).thenReturn(actionElement);
        when(chromeDriver.findElement(By.id(attribute.getId()))).thenReturn(element);

        InOrder inOrder = Mockito.inOrder(chromeDriver, actionElement, element);
        
        run.configureRequest(Collections.singletonList(attribute), chromeDriver);
        inOrder.verify(chromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(element, times(1)).sendKeys(attribute.getValue());
        inOrder.verify(actionElement, times(1)).click();
    }

    @Test
    public void configureRequest_WithNoAction_UsingIdGiven_Valid() {
        AttributeTest attribute = new AttributeTest();
        attribute.setId("login");
        attribute.setValue("user@user.com");

        when(chromeDriver.findElement(By.id(attribute.getId()))).thenReturn(element);

        InOrder inOrder = Mockito.inOrder(chromeDriver, element);

        run.configureRequest(Collections.singletonList(attribute), chromeDriver);
        inOrder.verify(chromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(element, times(1)).sendKeys(attribute.getValue());
        verifyNoMoreInteractions(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitForm_NullWebdriverGiven_ShouldThrowException() {
        WebDriver driver = null;
        run.submitForm(driver, new SeleniumAttributeTest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitForm_NullFormAttribute_ShouldThrowException() {
        SeleniumAttributeTest formAttribute = null;
        run.submitForm(firefoxDriver, formAttribute);
    }

    @Test
    public void submitForm_ValidAttributeGiven() {
        FormTest attribute = new FormTest();
        attribute.setId("login");

        when(chromeDriver.findElement(By.id(attribute.getId()))).thenReturn(element);

        InOrder inOrder = Mockito.inOrder(chromeDriver, element);

        run.submitForm(chromeDriver, attribute);

        inOrder.verify(chromeDriver).findElement(By.id(attribute.getId()));
        inOrder.verify(element).submit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExpectedElement_NullAttributeTestGiven_ShouldThrowException() {
        AttributeTest attributeTest = null;
        run.getExpectedElement(attributeTest);
    }

    @Test
    public void getExpectedElement_NullNoneAttributeGiven_ShouldReturnNull() {
        AttributeTest attributeTest = new AttributeTest();
        Assert.assertNull(run.getExpectedElement(attributeTest));
    }

    @Test
    public void getExpectedElement_ShouldReturnById() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setId("act");
        By by = run.getExpectedElement(attributeTest);
        Assert.assertNotNull(by);
        Assert.assertEquals(By.id(attributeTest.getId()), by);
    }

    @Test
    public void getExpectedElement_ShouldReturnByName() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setName("act");
        By by = run.getExpectedElement(attributeTest);
        Assert.assertNotNull(by);
        Assert.assertEquals(By.name(attributeTest.getName()), by);
    }

    @Test
    public void getExpectedElement_ShouldReturnByCss() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setCssClass("act");
        By by = run.getExpectedElement(attributeTest);
        Assert.assertNotNull(by);
        Assert.assertEquals(By.className(attributeTest.getCssClass()), by);
    }

    private ScenarioTest buildScenarioTest() {
        ScenarioTest scenarioTest = new ScenarioTest();
        scenarioTest.setUrlTest("http://localhost:8080/login");

        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setName("username");
        attributeTest.setValue("testUser");

        AttributeTest attributeTest2 = new AttributeTest();
        attributeTest2.setName("password");
        attributeTest2.setValue("123456");

        List<SeleniumAttributeTest> attributes = new ArrayList<>();
        attributes.add(attributeTest);
        attributes.add(attributeTest2);

        FormTest formTest = new FormTest();
        formTest.setName("username");
        formTest.setValue("testUser");

        scenarioTest.setAttributesTest(attributes);
        scenarioTest.setFormTest(formTest);

        return scenarioTest;
    }
}