package com.tsm.model;

import java.util.List;

import lombok.Data;

@Data
public class ScenarioTest {

	private FormTest formTest;
	
	private List<SeleniumAttributeTest> attributesTest;
	
	private List<AttributeTest> validationAttributesTest;
	
	private String nameTest;
	
	private String urlTest;
	
	private boolean startBrowser;

}
