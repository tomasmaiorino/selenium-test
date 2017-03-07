package com.tsm.service;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.MyConfig;
import com.tsm.model.AttributeTest;
import com.tsm.model.FormTest;
import com.tsm.model.ScenarioTest;
import com.tsm.model.SeleniumAttributeTest;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by tomas on 3/1/17.
 */
public class RunTestServiceTest {

    @Mock
    private MyConfig myConfig;

    @Mock
    private LoadDriversTest loadDriversTest;

    @InjectMocks
    private RunTestService runTestService;

    @Mock
    private List<BaseTestDriver> loadDriversMock;

    @Mock
    private BaseTestDriver firefoxDriver;

    @Mock
    private BaseTestDriver chromeDriver;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        List<String> driversToTest = getDriversToTest("firefox", "chrome");
        when(myConfig.getDriversToTest()).thenReturn(Arrays.asList(new String[]{"firefox"}));
        when(loadDriversMock.get(0)).thenReturn(firefoxDriver);
        when(loadDriversMock.get(1)).thenReturn(chromeDriver);


        when(firefoxDriver.isStartBrowser()).thenReturn(true);

        when(chromeDriver.isStartBrowser()).thenReturn(true);

        when(loadDriversTest.getTestDriversByName(driversToTest)).thenReturn(loadDriversMock);
    }


    public void shouldInitialLoadWithNoStartBrowser() {
        when(firefoxDriver.isStartBrowser()).thenReturn(false);
        when(chromeDriver.isStartBrowser()).thenReturn(false);

        ScenarioTest scenarioTest = new ScenarioTest();
        scenarioTest.setUrlTest("http://localhost:8080/login");
        scenarioTest.setStartBrowser(false);

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
    }

    private List<String> getDriversToTest(String...drivers) {
        List<String> dri = new ArrayList<>();
        for(String s : drivers) {
            dri.add(s);
        }
        return dri;
    }

}
