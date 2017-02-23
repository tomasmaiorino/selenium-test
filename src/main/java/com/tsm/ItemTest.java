package com.tsm;

import lombok.Data;
import org.openqa.selenium.By;

/**
 * Created by tomas on 2/15/17.
 */
@Data
public class ItemTest {

    private By by;

    private String byValue;

    private String byKey;
}
