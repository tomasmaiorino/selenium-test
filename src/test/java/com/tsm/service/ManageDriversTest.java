package com.tsm.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
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
import com.tsm.model.ScenarioTest;

/**
 * Created by tomas on 3/10/17.
 */
public class ManageDriversTest {

	@Mock
	private ChromeTestDriver chromeTestDriver;

	@Mock
	private FireFoxTestDriver fireFoxTestDriver;

	@InjectMocks
	private ManageDrivers manageDrivers;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnBaseTestDrivers() {
		Assert.assertNotNull(manageDrivers.getBaseTestDrivers());
		Assert.assertEquals(2, manageDrivers.getBaseTestDrivers().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void baseDriveByName_NullNameGiven_ShouldThrowException() {
		// Set Up
		String name = null;
		// Do test
		manageDrivers.getBaseTestDriverByName(name);
	}

	@Test(expected = IllegalArgumentException.class)
	public void baseDriveByName_EmptylNameGiven_ShouldThrowException() {
		// Set Up
		String name = "";
		// Do test
		manageDrivers.getBaseTestDriverByName(name);
	}

	@Test(expected = IllegalArgumentException.class)
	public void baseDriveByName_InvalidNameGiven_ShouldThrowException() {
		// Set Up
		String name = "safari";

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");

		// Do test
		manageDrivers.getBaseTestDriverByName(name);
	}

	@Test
	public void baseDriveByName_ValidNameGiven() {
		// Set Up
		String name = "chrome";

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");

		// Do test
		BaseTestDriver base = manageDrivers.getBaseTestDriverByName(name);
		Assert.assertEquals(name, base.getDriverName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void loadDrivers_EmptylDriversNameGiven_ShouldThrowException() {
		// Do test
		manageDrivers.loadDrivers(Collections.emptyList(), new ScenarioTest());
	}

	@Test(expected = IllegalArgumentException.class)
	public void loadDrivers_NullScenarioGiven_ShouldThrowException() {
		// Set Up
		List<String> driversName = Collections.singletonList("chrome");
		// Do test
		manageDrivers.loadDrivers(driversName, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void loadDrivers_InvalidDriverNameGiven_ShouldThrowException() {
		// Set Up
		List<String> driversName = Collections.singletonList("safari");

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");

		// Do test
		manageDrivers.loadDrivers(driversName, new ScenarioTest());
	}

	@Test
	public void loadDrivers_Valid_NotStartBrowser_ShouldReturnOneBaseDriver() {
		// Set Up
		List<String> driversName = Collections.singletonList("firefox");
		ScenarioTest scenarioTest = new ScenarioTest();

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(chromeTestDriver.getSystemPropertyName()).thenReturn("chromeName");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");
		when(fireFoxTestDriver.getSystemPropertyName()).thenReturn("firefoxName");

		// Do test
		List<BaseTestDriver> drivers = manageDrivers.loadDrivers(driversName, scenarioTest);

		// Assertions
		Assert.assertNotNull(drivers);
		Assert.assertEquals(1, drivers.size());
		Assert.assertEquals(driversName.get(0), drivers.get(0).getDriverName());
		Assert.assertNull(System.getProperty(chromeTestDriver.getSystemPropertyName()));
		Assert.assertNull(System.getProperty(fireFoxTestDriver.getSystemPropertyName()));
	}
	
	@Test
	public void loadDrivers_Valid_StartBrowser_ShouldReturnOneBaseDriver() {
		// Set Up
		List<String> driversName = Arrays.asList(new String[]{"firefox","chrome"});
		ScenarioTest scenarioTest = new ScenarioTest();
		scenarioTest.setStartBrowser(true);

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(chromeTestDriver.getSystemPropertyName()).thenReturn("chromeName");
		when(chromeTestDriver.getSystemPropertyValue()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");
		when(fireFoxTestDriver.getSystemPropertyName()).thenReturn("firefoxName");
		when(fireFoxTestDriver.getSystemPropertyValue()).thenReturn("firefox");

		// Do test
		List<BaseTestDriver> drivers = manageDrivers.loadDrivers(driversName, scenarioTest);

		// Assertions
		Assert.assertNotNull(drivers);
		Assert.assertEquals(2, drivers.size());
		Assert.assertEquals(driversName.get(0), drivers.get(0).getDriverName());		
		Assert.assertEquals(chromeTestDriver.getSystemPropertyValue(), System.getProperty(chromeTestDriver.getSystemPropertyName()));
		Assert.assertEquals(fireFoxTestDriver.getSystemPropertyValue(), System.getProperty(fireFoxTestDriver.getSystemPropertyName()));
		verify(chromeTestDriver).setStartBrowser(scenarioTest.isStartBrowser());
		verify(fireFoxTestDriver).setStartBrowser(scenarioTest.isStartBrowser());
	}

	@Test(expected = IllegalArgumentException.class)
	public void validrivers_InvalidDriverNameGiven_ShouldThrowException() {
		// Set Up
		List<String> driversName = Collections.emptyList();
		// Do test
		manageDrivers.validDrivers(driversName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validDrivers_InValidDriverNameGiven_ShouldThrowException() {
		// Set Up
		String name = "safari";

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");

		manageDrivers.validDrivers(Collections.singletonList(name));

	}

	@Test
	public void validDrivers_notExistDriverGiven_ShouldReturnFalse() {
		// Set Up
		List<String> driversName = Arrays.asList(new String[]{"chrome", "firefox"});

		// Expectations
		when(chromeTestDriver.getDriverName()).thenReturn("chrome");
		when(chromeTestDriver.getSystemPropertyName()).thenReturn("chromeName");
		when(chromeTestDriver.getSystemPropertyValue()).thenReturn("chrome");
		when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");
		when(fireFoxTestDriver.getSystemPropertyName()).thenReturn("firefoxName");
		when(fireFoxTestDriver.getSystemPropertyValue()).thenReturn("firefox");

		// Do test
		Assert.assertFalse(manageDrivers.validDrivers(driversName));
	}

}
