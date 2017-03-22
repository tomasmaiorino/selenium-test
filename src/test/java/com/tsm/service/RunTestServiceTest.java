package com.tsm.service;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.FireFoxTestDriver;
import com.tsm.model.ScenarioTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;



import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by tomas on 3/22/17.
 */
public class RunTestServiceTest {

    @Mock
    private ManageDrivers manageDrivers;

    @InjectMocks
    private RunTestService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupTest_NullScenarioTest_ShouldThrowException() {
        // Set up
        ScenarioTest scenarioTest = null;

        // Assertions
        service.setupTest(scenarioTest, Collections.singletonList("chrome"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupTest_EmptyDriversGiven_ShouldThrowException() {
        // Set up
        ScenarioTest scenarioTest = new ScenarioTest();

        // Assertions
        service.setupTest(scenarioTest, Collections.emptyList());
    }

    @Test
    public void setupTest_ValidStartBrowserDriversGiven_ShouldLoadDriver() {
        // Set up
        ScenarioTest scenarioTest = new ScenarioTest();
        scenarioTest.setStartBrowser(true);
        List<String> drivers = Collections.singletonList("chrome");
        List<BaseTestDriver> baseDrivers = Collections.singletonList(new FireFoxTestDriver());
        // Assertions
        when(manageDrivers.validDrivers(drivers)).thenReturn(true);
        when(manageDrivers.loadDrivers(drivers, scenarioTest)).thenReturn(baseDrivers);

        // Do test
        List<BaseTestDriver> returns = service.setupTest(scenarioTest, drivers);

        // Assertions
        Assert.assertNotNull(returns);
        Assert.assertEquals(baseDrivers, returns);

        verify(manageDrivers).validDrivers(drivers);
        verify(manageDrivers).loadDrivers(drivers, scenarioTest);
    }

    @Test
    public void setupTest_ValidNotStartBrowserDriversGiven_ShouldLoadDriver() {
        // Set up
        ScenarioTest scenarioTest = new ScenarioTest();
        List<String> drivers = Collections.singletonList("chrome");
        List<BaseTestDriver> baseDrivers = Collections.singletonList(new FireFoxTestDriver());
        // Assertions

        when(manageDrivers.loadDrivers(drivers, scenarioTest)).thenReturn(baseDrivers);

        // Do test
        List<BaseTestDriver> returns = service.setupTest(scenarioTest, drivers);

        // Assertions
        Assert.assertNotNull(returns);
        Assert.assertEquals(baseDrivers, returns);

        verify(manageDrivers, times(0)).validDrivers(drivers);
        verify(manageDrivers).loadDrivers(drivers, scenarioTest);
    }

}
