package com.tsm.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.ChromeTestDriver;
import com.tsm.config.FireFoxTestDriver;
import com.tsm.config.MyConfig;
import com.tsm.model.ScenarioTest;


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

    private List<BaseTestDriver> baseTestDrivers;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    public BaseTestDriver getDriver(String driverName, boolean startBrowser) {
    	BaseTestDriver bd = null;
    	if (driverName.equals("chrome")) {
			bd = createChromeTestDriver(startBrowser);
    	} else if (driverName.equals("firefox")) {
			bd = createFirefoxTestDriver(startBrowser);
    	}
    	
    	return bd;
    }
    
    private BaseTestDriver createFirefoxTestDriver(boolean startBrowser) {
    	FireFoxTestDriver fireFox = new FireFoxTestDriver();
    	fireFox.setStartBrowser(startBrowser);
    	fireFox.setDriverName("fireFox");
    	fireFox.setSystemPropertyName("fireFoxDriver");
    	fireFox.setSystemPropertyValue("fireFoxDriver");
    	if (startBrowser) {
    		fireFox.setWebDriver(null);
    	} else {
    		fireFox.setWebDriver(null);
    	}
		return fireFox;
	}


	private BaseTestDriver createChromeTestDriver(boolean startBrowser) {
    	ChromeTestDriver chrome = new ChromeTestDriver();
    	chrome.setStartBrowser(startBrowser);
    	chrome.setDriverName("chrome");
    	chrome.setSystemPropertyName("chromeDriver");
    	chrome.setSystemPropertyValue("chromeDriver");
    	if (startBrowser) {
    		chrome.setWebDriver(null);
    	} else {
    		chrome.setWebDriver(null);
    	}
		return chrome;
	}


	public List<BaseTestDriver> baseDrivers (String...drivers) {
    	List<BaseTestDriver> baseDrivers = new ArrayList<>();
    	for(String d : drivers) {
    		BaseTestDriver base = getDriver(d, false);
    		baseDrivers.add(base);
    	}
    	return baseDrivers;    	
    }
	
	@Test
    public void shouldLoadDriversWithStartBrowser() {
		 baseTestDrivers = baseDrivers("chrome", "firefox");
    	 List<String> driversToTest = getDriversToTest("chrome", "firefox");
         when(myConfig.getDriversToTest()).thenReturn(driversToTest);
         when(loadDriversTest.getTestDriversByName(driversToTest)).thenReturn(baseTestDrivers);
    	
        ScenarioTest scenarioTest = new ScenarioTest();
        scenarioTest.setNameTest("Login");
        scenarioTest.setStartBrowser(true);
        
        List<BaseTestDriver> baseDrives = runTestService.loadDrivers(scenarioTest);
        Assert.assertNotNull(baseDrives);
        Assert.assertEquals(2, baseDrives.size());
        Assert.assertTrue(baseDrives.get(0).isStartBrowser());
        verify(loadDriversTest, times(1)).getTestDriversByName(driversToTest);
        verify(myConfig, times(1)).getDriversToTest();
	}
    
	@Test
    public void shouldLoadDriversWithNoStartBrowser() {
		 baseTestDrivers = baseDrivers("chrome");
    	 List<String> driversToTest = getDriversToTest("chrome");
         when(myConfig.getDriversToTest()).thenReturn(Arrays.asList(new String[]{"chrome"}));
         when(loadDriversTest.getTestDriversByName(driversToTest)).thenReturn(baseTestDrivers);
    	
        ScenarioTest scenarioTest = new ScenarioTest();
        scenarioTest.setNameTest("Login");
        scenarioTest.setStartBrowser(false);
        
        List<BaseTestDriver> baseDrives = runTestService.loadDrivers(scenarioTest);
        Assert.assertNotNull(baseDrives);
        Assert.assertEquals(1, baseDrives.size());
        Assert.assertFalse(baseDrives.get(0).isStartBrowser());
        verify(loadDriversTest, times(1)).getTestDriversByName(driversToTest);
        verify(myConfig, times(1)).getDriversToTest();

//        scenarioTest.setUrlTest("http://localhost:8080/login");
//        
//        AttributeTest attributeTest = new AttributeTest();
//        attributeTest.setName("username");
//        attributeTest.setValue("testUser");
//
//        AttributeTest attributeTest2 = new AttributeTest();
//        attributeTest2.setName("password");
//        attributeTest2.setValue("123456");
//
//        List<SeleniumAttributeTest> attributes = new ArrayList<>();
//        attributes.add(attributeTest);
//        attributes.add(attributeTest2);
//
//        FormTest formTest = new FormTest();
//        formTest.setName("username");
//        formTest.setValue("testUser");

//        scenarioTest.setAttributesTest(attributes);
//        scenarioTest.setFormTest(formTest);
    }

    private List<String> getDriversToTest(String...drivers) {
        List<String> dri = new ArrayList<>();
        for(String s : drivers) {
            dri.add(s);
        }
        return dri;
    }

}
