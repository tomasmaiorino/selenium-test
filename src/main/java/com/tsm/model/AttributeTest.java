package com.tsm.model;

import lombok.Data;

@Data
public class AttributeTest extends  SeleniumAttributeTest{
	
	private boolean validation;
	
	//html | text | has_element
	private String validationType;

	private boolean checkList;
	
	private String validationContent;
}
