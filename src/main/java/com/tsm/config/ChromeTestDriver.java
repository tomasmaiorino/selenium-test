package com.tsm.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.gargoylesoftware.htmlunit.BrowserVersion;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomas on 2/19/17.
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ChromeTestDriver extends BaseTestDriver {

	@Getter
	@Setter
	@Value("${general.driver.name.chrome}")
	private String driverName;

	@Getter
	@Setter
	@Value("${general.driver.system.property.name.chrome}")
	private String systemPropertyName;

	@Getter
	@Setter
	@Value("${general.driver.system.property.value.chrome}")
	private String systemPropertyValue;

	@Setter
	private WebDriver webDriver;

	@Setter @Getter
	private boolean startBrowser;

	public WebDriver getWebDriver() {
		if (!isStartBrowser()) {
			return new HtmlUnitDriver(BrowserVersion.CHROME, true);
		} else {
			return new FirefoxDriver();
		}
	}

}
