package com.tsm.service;

import com.tsm.SeleniumTest;

import java.util.Map;

/**
 * Created by tomas on 2/15/17.
 */
public class RunTest {

    public void runTest(SeleniumTest seleniumTest) {

        Map<String, String> webdriverDriver =
                (Map<String, String>) seleniumTest.getGeneralConfig().get("webdriverDriver");
        //set system property
        webdriverDriver.entrySet().forEach(w -> System.setProperty(w.getKey(), w.getValue()));

        seleniumTest.getServicesToTest().entrySet().forEach(s -> seleniumTest.runWebCall(s.getKey()));

    }
}
