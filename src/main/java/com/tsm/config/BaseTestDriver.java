package com.tsm.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by tomas on 2/19/17.
 */
public abstract class BaseTestDriver {

    public abstract String getDriverName();

    public abstract String getSystemPropertyName();

    public abstract String getSystemPropertyValue();

    public abstract WebDriver getWebDriver(boolean enableJavascript);

    public abstract boolean isStartBrowser();

    public abstract void setStartBrowser(boolean startBrowser);
    
    public abstract WebDriverWait getWebDriverWait(WebDriver webDriver, long timeout);
    
}
