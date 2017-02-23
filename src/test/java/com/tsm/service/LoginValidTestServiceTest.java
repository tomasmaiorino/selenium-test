package com.tsm.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

public class LoginValidTestServiceTest {
	
	@InjectMocks
	private LoginBaseTestService loginTest;
	
	@Mock
	private MyConfig myConfig;

	@Mock
	private LoadDriversTest loadDriversTest;
	
	private List<BaseTestDriver> baseTestDriver = getBaseTestDriver("firefox");
	
	private List<BaseTestDriver> baseTestDriverTwoDrives = getBaseTestDriver("firefox", "chrome");
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private List<BaseTestDriver> getBaseTestDriver(String...driversName) {
		List<BaseTestDriver> baseTestDriver = new ArrayList<>();
		for(String s : driversName) {
			if (s.equals("firefox")) {
				FireFoxTestDriver fireFoxTestDriver = new FireFoxTestDriver();
				fireFoxTestDriver.setDriverName("firefox");
				fireFoxTestDriver.setSystemPropertyName("greckdrive");
				fireFoxTestDriver.setSystemPropertyValue("/home/driver");
				baseTestDriver.add(fireFoxTestDriver);
			} else if (s.equals("chrome")) {
				ChromeTestDriver chromeTest = new ChromeTestDriver();
				chromeTest.setDriverName("chrome");
				chromeTest.setSystemPropertyName("chromeDrive");
				chromeTest.setSystemPropertyValue("/home/driver");
				baseTestDriver.add(chromeTest);
			}
		}
		return baseTestDriver;
	}
	
	@Test
	public void testInitialLoad() {
		List<String> driversToTest = new ArrayList<>();
		when(myConfig.getLoginDriversToTest()).thenReturn(driversToTest);
		when(loadDriversTest.getTestDriversByName(driversToTest)).thenReturn(baseTestDriver);
		
		Assert.assertEquals(baseTestDriver, loginTest.initialLoad());
		Assert.assertEquals(1, loginTest.initialLoad().size());
	}
	
	public void testRunTest(){
		Assert.assertTrue(true);
	}

}
