package com.tsm;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by tomas on 2/14/17.
 */
@Component
public class SeleniumTest {

    @Getter @Setter
    private WebDriver webDriver;

    @Getter @Setter
    private Map<Object, Object> generalConfig;

    @Getter @Setter
    private Map<String, List<ItemTest>> itemTest;

    @Getter @Setter
    private Map<Object, Object> pageCallResult;

    @Getter @Setter
    private Map<String, String> servicesToTest;


    public void runWebCall(String service) {
        List<ItemTest> itensTest = itemTest.get(service);

        // Go to the Google Suggest home page
        webDriver.get(servicesToTest.get(service));

        for(ItemTest item : itensTest) {
            WebElement elem = webDriver.findElement(item.getBy());
            elem.sendKeys(item.getByValue());
        }
        long end = System.currentTimeMillis() + 15000;
        WebDriverWait wait = new WebDriverWait(webDriver, 10);
        List<WebElement> resultsLinks = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("headerMenuDropwdownItemLink"))
        );
        pageCallResult.put(service, resultsLinks);
        webDriver.quit();
    }
}
