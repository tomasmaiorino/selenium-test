package com.tsm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.ChromeTestDriver;
import com.tsm.config.FireFoxTestDriver;
import com.tsm.model.ScenarioTest;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomas on 3/10/17.
 */
@Component
public class ManageDrivers {

    @Setter
    private List<BaseTestDriver> baseTestDrivers = new ArrayList<>();

    @Autowired
    @Getter
    @Setter
    private ChromeTestDriver chromeTestDriver;

    @Autowired
    @Getter
    @Setter
    private FireFoxTestDriver fireFoxTestDriver;

    public List<BaseTestDriver> getBaseTestDrivers() {
        if (baseTestDrivers.isEmpty()) {
            baseTestDrivers.add(chromeTestDriver);
            baseTestDrivers.add(fireFoxTestDriver);
        }
        return baseTestDrivers;
    }

    public BaseTestDriver getBaseTestDriverByName(String driverName) {
        Assert.hasText(driverName, "DriverName must not be null!");
        try {
            return getBaseTestDrivers().stream().filter(b -> b.getDriverName().equals(driverName)).findFirst().get();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("Invalid driver name(%s).", driverName));
        }
    }
    
    public List<BaseTestDriver> loadDrivers(List<String> driversName, ScenarioTest scenarioTest) {
    	Assert.notNull(scenarioTest, "Scenario must not be null!");
    	Assert.notEmpty(driversName, "DriversName must not be empty!");
    	List<BaseTestDriver> drivers = new ArrayList<>();
    	for(String driverName : driversName) {
    		BaseTestDriver base = getBaseTestDriverByName(driverName);
    		if (scenarioTest.isStartBrowser()) {
    			System.setProperty(base.getSystemPropertyName(), base.getSystemPropertyValue());
    		} 
    		base.setStartBrowser(scenarioTest.isStartBrowser());
    		drivers.add(base);
    	}
    	return drivers;
    }
}
