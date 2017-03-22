package com.tsm.service;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.GeneralConfig;
import com.tsm.model.ScenarioTest;
import com.tsm.model.TestResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by tomas on 3/21/17.
 */

public class RunTestService {

    @Autowired
    @Getter
    @Setter
    private ManageDrivers manageDrivers;

    @Autowired
    @Getter
    @Setter
    private GeneralConfig generalConfig;

    public TestResult runTest(final ScenarioTest scenarioTest, final List<String> drivers) {
        Assert.notNull(scenarioTest, "ScenarioTest must not be null!");
        Assert.notEmpty(drivers, "Drivers must not be empty!");
        //
        List<BaseTestDriver> baseTestDrivers = setupTest(scenarioTest, drivers);
        return null;
    }

    protected List<BaseTestDriver> setupTest(ScenarioTest scenarioTest, List<String> drivers) {
        Assert.notNull(scenarioTest, "ScenarioTest must not be null!");
        Assert.notEmpty(drivers, "Drivers must not be empty!");
        if (scenarioTest.isStartBrowser()) {
            Assert.isTrue(manageDrivers.validDrivers(drivers), "Invalid drivers.");
        }
        return manageDrivers.loadDrivers(drivers, scenarioTest);
    }
}
