package com.tsm.config;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.gargoylesoftware.htmlunit.BrowserVersion;

/**
 * Created by tomas on 2/19/17.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class FireFoxTestDriver extends BaseTestDriver {

    @Getter @Setter
    @Value("${general.driver.name.firefox}")
    private String driverName;

    @Getter @Setter
    @Value("${general.driver.system.property.name.firefox}")
    private String systemPropertyName;

    @Getter @Setter
    @Value("${general.driver.system.property.value.firefox}")
    private String systemPropertyValue;

    @Setter
    private WebDriver webDriver;

    @Setter @Getter
    private boolean startBrowser;

    public WebDriver getWebDriver(boolean enableJavascript) {
        if (!isStartBrowser()) {
    		return new HtmlUnitDriver(BrowserVersion.FIREFOX_45, !enableJavascript);
    	} else {
            return new FirefoxDriver();
    	}
    }
}
