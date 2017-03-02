package com.tsm.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.ChromeTestDriver;
import com.tsm.config.FireFoxTestDriver;


public class LoadDriversTestTest {

	@Mock
	private FireFoxTestDriver fireFoxTestDriver;

	@Mock
	private ChromeTestDriver chromeTestDriver; 
	
	@InjectMocks
	private LoadDriversTest loadDriversTest;
	
	@Before
	public void setup(){
		 MockitoAnnotations.initMocks(this);
		 when(fireFoxTestDriver.getDriverName()).thenReturn("firefox");
		 when(chromeTestDriver.getDriverName()).thenReturn("chrome");
	}
	
	@Test
	public void shouldReturnTestDriver() {
		BaseTestDriver db = loadDriversTest.getTestDriverByName("firefox");
		Assert.assertNotNull(db);
		Assert.assertEquals(fireFoxTestDriver.getDriverName(), db.getDriverName());
		db = null;
		db = loadDriversTest.getTestDriverByName("chrome");
		Assert.assertNotNull(db);
		Assert.assertEquals(chromeTestDriver.getDriverName(), db.getDriverName());
	}
	
	@Test
	public void shouldReturnNull() {
		BaseTestDriver db = loadDriversTest.getTestDriverByName("safari");
		Assert.assertNull(db);
	}
	
	@Test
	public void shouldReturnListTestDriver() {
		List<BaseTestDriver> db = loadDriversTest.getTestDriversByName(Collections.singletonList("firefox"));
		Assert.assertNotNull(db);
		Assert.assertEquals(1, db.size());
		Assert.assertEquals(fireFoxTestDriver.getDriverName(), db.get(0).getDriverName());
		db = null;
		db = loadDriversTest.getTestDriversByName(Collections.singletonList("chrome"));;
		Assert.assertNotNull(db);
		Assert.assertEquals(1, db.size());
		Assert.assertEquals(chromeTestDriver.getDriverName(), db.get(0).getDriverName());
	}
	
	@Test
	public void shouldReturnOneListItemTestDriver() {
		List<String> drivers = new ArrayList<>();
		drivers.add("firefox");
		drivers.add("safari");
		List<BaseTestDriver> db = loadDriversTest.getTestDriversByName(drivers);
		Assert.assertNotNull(db);
		Assert.assertEquals(drivers.size() - 1, db.size());
		Assert.assertEquals(fireFoxTestDriver.getDriverName(), db.get(0).getDriverName());
		db = null;
		drivers.add("chrome");
		db = loadDriversTest.getTestDriversByName(drivers);
		Assert.assertNotNull(db);
		Assert.assertEquals(drivers.size() - 1, db.size());
		Assert.assertEquals(chromeTestDriver.getDriverName(), db.get(1).getDriverName());
	}

	@Test
	public void shouldReturnNullForInvalidDriver () {
		Assert.assertNull(loadDriversTest.getTestDriverByName("notValidDriver"));
	}
}
