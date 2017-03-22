package com.tsm.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ScenarioTest {

    private FormTest formTest;

    private List<SeleniumAttributeTest> attributesTest;

    @NotNull (message = "missing_validation_attributes")
    private List<AttributeTest> validationAttributesTest;

    @NotNull(message = "missing_name_test")
    private String nameTest;

    @NotNull(message = "missing_url_test")
    private String urlTest;

    private boolean startBrowser;

    private boolean disableJavascript;

}
