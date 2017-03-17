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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
    private ManageDrivers mockManageDrivers;

    @Mock
    private GeneralConfig mockGeneralConfig;

    @Mock
    private ScenarioTest mockScenarioTest;

    @Mock
    private List<BaseTestDriver> mockBaseDrivers = new ArrayList<>();

    @Mock
    private FireFoxTestDriver mockFireFoxTestDriver;

    @Mock
    private ChromeTestDriver mockChromeTestDriver;

    @InjectMocks
    private RunTestService run;

    @Mock
    private WebElement mockElement;

    @Mock
    private FirefoxDriver mockFirefoxDriver;

    @Mock
    private ChromeDriver mockChromeDriver;

    @Mock
    private HtmlUnitDriver mockHtmlDriver;

    @Mock
    private By mockBy;

    @Mock
    private WebDriverWait mockWait;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        List<String> driversName = Arrays.asList("chrome");
        when(mockGeneralConfig.getDriversToTest()).thenReturn(driversName);
        ScenarioTest scenarioTest = buildScenarioTest();
        when(mockManageDrivers.loadDrivers(driversName, scenarioTest)).thenReturn(mockBaseDrivers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void runTest_NullScenarioTest_ShouldThrowException() {
        // Set up
        ScenarioTest scenarioTest = null;

        // Assertions
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

        // Assertions
        Assert.assertNull(run.getWebElement(mockHtmlDriver, selenium));
    }

    // FIREFOX
    @Test(expected = IllegalArgumentException.class)
    public void doesTest_NullScenarioGiven_ShouldThrowException() {
        ScenarioTest scenarioTest = null;

        // Assertions
        run.doesTest(scenarioTest, mockFireFoxTestDriver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullSeleniumAttributeTestGiven_ShouldThrowException() {
        SeleniumAttributeTest attribute = null;

        // Assertions
        run.getWebElement(new HtmlUnitDriver(), attribute);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullWebdriverTestGiven_ShouldThrowException() {
        WebDriver driver = null;

        // Assertions
        run.getWebElement(driver, new SeleniumAttributeTest());
    }

    @Test
    public void getWebElement_NoneElementGivenUsingFireFoxeDriver_ShouldReturnNull() {
        SeleniumAttributeTest selenium = new AttributeTest();

        // Assertions
        Assert.assertNull(run.getWebElement(mockFirefoxDriver, selenium));
    }

    @Test
    public void getWebElement_Valid_ByIdGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setId("user");
        when(mockFireFoxTestDriver.getWebDriver(false)).thenReturn(mockFirefoxDriver);
        when(mockFirefoxDriver.findElement(By.id(selenium.getId()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockFirefoxDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByNameGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setName("login");
        when(mockFireFoxTestDriver.getWebDriver(false)).thenReturn(mockFirefoxDriver);
        when(mockFirefoxDriver.findElement(By.name(selenium.getName()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockFirefoxDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByClassNameGivenFirefoxDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setCssClass("login");
        when(mockFireFoxTestDriver.getWebDriver(false)).thenReturn(mockFirefoxDriver);
        when(mockFirefoxDriver.findElement(By.className(selenium.getCssClass()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockFirefoxDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    // CHROME
    @Test(expected = IllegalArgumentException.class)
    public void doesTest_NullScenarioGiven_ChromeDriver_ShouldThrowException() {
        ScenarioTest scenarioTest = null;

        // Assertions
        run.doesTest(scenarioTest, mockChromeTestDriver);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullSeleniumAttributeTestGiven_ChromeDriver_ShouldThrowException() {
        SeleniumAttributeTest attribute = null;

        // Assertions
        run.getWebElement(new HtmlUnitDriver(), attribute);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getWebElement_NullWebdriverTestGiven_ChromeDriver_ShouldThrowException() {
        WebDriver driver = null;

        // Assertions
        run.getWebElement(driver, new SeleniumAttributeTest());
    }

    @Test
    public void getWebElement_NoneElementGivenUsingChromeDriver_ShouldReturnNull() {
        SeleniumAttributeTest selenium = new AttributeTest();

        // Assertions
        Assert.assertNull(run.getWebElement(mockChromeDriver, selenium));
    }

    @Test
    public void getWebElement_Valid_ByIdGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setId("user");
        when(mockChromeDriver.findElement(By.id(selenium.getId()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockChromeDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByNameGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setName("login");
        when(mockChromeDriver.findElement(By.name(selenium.getName()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockChromeDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    @Test
    public void getWebElement_Valid_ByClassNameGivenChromeDriver() {
        SeleniumAttributeTest selenium = new AttributeTest();
        selenium.setCssClass("login");
        when(mockChromeDriver.findElement(By.className(selenium.getCssClass()))).thenReturn(mockElement);

        WebElement element = run.getWebElement(mockChromeDriver, selenium);

        // Assertions
        Assert.assertNotNull(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeAction_NullWebdriverGiven_ShouldThrowException() {
        WebDriver driver = null;

        // Assertions
        run.executeAction(driver, new ActionAttributeTest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void executeAction_NullActionAttributeTestGiven_ShouldThrowException() {
        ActionAttributeTest action = null;

        // Assertions
        run.executeAction(new HtmlUnitDriver(), action);
    }

    @Test
    public void executeAction_ActionAttributeExecuted() {
        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("click");
        action.setId("act");
        when(mockChromeDriver.findElement(By.id(action.getId()))).thenReturn(mockElement);

        run.executeAction(mockChromeDriver, action);

        // Assertions
        verify(mockElement).click();
    }

    @Test
    public void executeAction_ActionInvalid() {
        ActionAttributeTest action = new ActionAttributeTest();
        action.setAction("selected");
        action.setId("act");
        when(mockChromeDriver.findElement(By.id(action.getId()))).thenReturn(mockElement);

        run.executeAction(mockChromeDriver, action);

        // Assertions
        verify(mockElement, times(0)).click();
        verify(mockChromeDriver, times(1)).findElement(By.id(action.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void configureRequest_EmptySeleniumAttributeGiven_ShouldThrowException() {
        WebDriver driver = new HtmlUnitDriver();

        // Assertions
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
        when(mockChromeDriver.findElement(By.id(action.getId()))).thenReturn(actionElement);
        when(mockChromeDriver.findElement(By.id(attribute.getId()))).thenReturn(mockElement);

        run.configureRequest(Collections.singletonList(attribute), mockChromeDriver);

        InOrder inOrder = Mockito.inOrder(mockChromeDriver, actionElement, mockElement);
        // Assertions
        inOrder.verify(actionElement, times(1)).click();
        inOrder.verify(mockChromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(mockElement, times(1)).sendKeys(attribute.getValue());

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
        when(mockChromeDriver.findElement(By.id(action.getId()))).thenReturn(actionElement);
        when(mockChromeDriver.findElement(By.id(attribute.getId()))).thenReturn(mockElement);

        InOrder inOrder = Mockito.inOrder(mockChromeDriver, actionElement, mockElement);

        run.configureRequest(Collections.singletonList(attribute), mockChromeDriver);

        // Assertions
        inOrder.verify(mockChromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(mockElement, times(1)).sendKeys(attribute.getValue());
        inOrder.verify(actionElement, times(1)).click();
    }

    @Test
    public void configureRequest_WithNoAction_UsingIdGiven_Valid() {
        AttributeTest attribute = new AttributeTest();
        attribute.setId("login");
        attribute.setValue("user@user.com");

        when(mockChromeDriver.findElement(By.id(attribute.getId()))).thenReturn(mockElement);

        InOrder inOrder = Mockito.inOrder(mockChromeDriver, mockElement);

        run.configureRequest(Collections.singletonList(attribute), mockChromeDriver);

        // Assertions
        inOrder.verify(mockChromeDriver, times(1)).findElement(By.id(attribute.getId()));
        inOrder.verify(mockElement, times(1)).sendKeys(attribute.getValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitForm_NullWebdriverGiven_ShouldThrowException() {
        WebDriver driver = null;
        run.submitForm(driver, new SeleniumAttributeTest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void submitForm_NullFormAttribute_ShouldThrowException() {
        SeleniumAttributeTest formAttribute = null;
        run.submitForm(mockFirefoxDriver, formAttribute);
    }

    @Test
    public void submitForm_ValidAttributeGiven() {
        FormTest attribute = new FormTest();
        attribute.setId("login");

        when(mockChromeDriver.findElement(By.id(attribute.getId()))).thenReturn(mockElement);

        InOrder inOrder = Mockito.inOrder(mockChromeDriver, mockElement);

        run.submitForm(mockChromeDriver, attribute);

        // Assertions
        inOrder.verify(mockChromeDriver).findElement(By.id(attribute.getId()));
        inOrder.verify(mockElement).submit();
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExpectedElement_NullAttributeTestGiven_ShouldThrowException() {
        AttributeTest attributeTest = null;

        // Assertions
        run.getExpectedElement(attributeTest);
    }

    @Test
    public void getExpectedElement_NullNoneAttributeGiven_ShouldReturnNull() {
        AttributeTest attributeTest = new AttributeTest();

        // Assertions
        Assert.assertNull(run.getExpectedElement(attributeTest));
    }

    @Test
    public void getExpectedElement_ShouldReturnById() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setId("act");
        By by = run.getExpectedElement(attributeTest);

        // Assertions
        Assert.assertNotNull(by);
        Assert.assertEquals(By.id(attributeTest.getId()), by);
    }

    @Test
    public void getExpectedElement_ShouldReturnByName() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setName("act");
        By by = run.getExpectedElement(attributeTest);

        // Assertions
        Assert.assertNotNull(by);
        Assert.assertEquals(By.name(attributeTest.getName()), by);
    }

    @Test
    public void getExpectedElement_ShouldReturnByCss() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setCssClass("act");
        By by = run.getExpectedElement(attributeTest);

        // Assertions
        Assert.assertNotNull(by);
        Assert.assertEquals(By.className(attributeTest.getCssClass()), by);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wasTestSuccessfull_NullByElementValidationTypeHtmlGiven_ShouldThrowException() {
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("html");

        // Assertions
        run.wasTestSuccessful(Collections.emptyList(), attributeTest, null);
    }

    @Test
    public void wasTestSuccessfull_ValidValuesGiven_ShouldReturnNotSuccess() {
        String htmlText = "<span></span>";
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("html");
        attributeTest.setValue("<p>");
        List<WebElement> results = Collections.singletonList(mockElement);
        By byElement = By.id("id");
        when(mockElement.findElement(byElement)).thenReturn(mockElement);
        when(mockElement.getText()).thenReturn(htmlText);

        InOrder inOrder = Mockito.inOrder(mockElement);

        boolean result = run.wasTestSuccessful(results, attributeTest, byElement);

        // Assertions
        Assert.assertFalse(result);
        inOrder.verify(mockElement).findElement(byElement);
        inOrder.verify(mockElement).getText();
    }

    @Test
    public void wasTestSuccessfull_ValidValuesGiven_ShouldReturnSuccess() {
        // Set up
        String htmlText = "<span></span>";
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("html");
        attributeTest.setValue(htmlText);
        List<WebElement> results = Collections.singletonList(mockElement);
        By byElement = By.id("id");

        // Expectaions
        when(mockElement.findElement(byElement)).thenReturn(mockElement);
        when(mockElement.getText()).thenReturn(htmlText);

        InOrder inOrder = Mockito.inOrder(mockElement);

        // Do test
        boolean result = run.wasTestSuccessful(results, attributeTest, byElement);

        // Assertions
        Assert.assertTrue(result);
        inOrder.verify(mockElement).findElement(byElement);
        inOrder.verify(mockElement).getText();
    }

    @Test
    public void wasTestSuccessfull_ValidTextValuesGiven_ShouldReturnSuccess() {
        // Set up
        String htmlText = "<span></span>";
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("text");
        attributeTest.setValue(htmlText);
        List<WebElement> results = Collections.singletonList(mockElement);

        // Expectaions
        when(mockElement.getText()).thenReturn(htmlText);

        // Do test
        boolean result = run.wasTestSuccessful(results, attributeTest, null);

        Assert.assertTrue(result);
        verify(mockElement).getText();
    }

    @Test
    public void wasTestSuccessfull_ValidTextValuesGiven_ShouldReturnFalse() {
        // Set up
        String htmlText = "Text";
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("text");
        attributeTest.setValue("Hello");
        List<WebElement> results = Collections.singletonList(mockElement);

        // Expectaions
        when(mockElement.getText()).thenReturn(htmlText);

        // Do test
        boolean result = run.wasTestSuccessful(results, attributeTest, null);

        // Assertions
        Assert.assertFalse(result);
        verify(mockElement).getText();
    }

    @Test
    public void wasTestSuccessfull_ValidValuesGiven_HasElement_ShouldReturnSuccess() {
        // Set up
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("hasElement");
        List<WebElement> results = Collections.singletonList(mockElement);

        // Do test
        boolean result = run.wasTestSuccessful(results, attributeTest, null);

        // Assertions
        Assert.assertTrue(result);
        verifyNoMoreInteractions(mockElement);
    }

    @Test
    public void wasTestSuccessfull_ValidValuesGiven_HasElement_ShouldReturnFalse() {
        // Set up
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setValidationType("hasElement");
        List<WebElement> results = Collections.emptyList();

        // Do test
        boolean result = run.wasTestSuccessful(results, attributeTest, null);

        // Assertions
        Assert.assertFalse(result);
        verifyNoMoreInteractions(mockElement);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResultElements_NullBaseTestDriverGiven_ShouldThrowException() {
        BaseTestDriver baseTestDriver = null;
        run.getResultElements(baseTestDriver, new HtmlUnitDriver(), new AttributeTest());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getResultElements_ShouldReturnElementList() {
        // Set up

        BaseTestDriver baseTestDriver = null;
        List<WebElement> elements = Collections.singletonList(mockElement);
        AttributeTest attributeTest = new AttributeTest();
        attributeTest.setId("id");
        // Expectations
        when(mockGeneralConfig.getTestWaitTimeout()).thenReturn(1);
        when(mockFireFoxTestDriver.getWebDriverWait(mockFirefoxDriver, mockGeneralConfig.getTestWaitTimeout())).thenReturn(mockWait);
        when(mockWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(attributeTest.getId())))).thenReturn(elements);

        List<WebElement> results = run.getResultElements(baseTestDriver, mockFirefoxDriver, attributeTest);
        Assert.assertNotNull(results);
        Assert.assertTrue(!results.isEmpty());

        InOrder inOrder = Mockito.inOrder(mockFirefoxDriver, mockFireFoxTestDriver);

        inOrder.verify(mockFireFoxTestDriver).getWebDriverWait(mockFirefoxDriver, mockGeneralConfig.getTestWaitTimeout());
        inOrder.verify(when(mockWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(attributeTest.getId())))));
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
