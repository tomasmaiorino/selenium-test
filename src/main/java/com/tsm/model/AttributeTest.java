package com.tsm.model;

import lombok.Getter;
import lombok.Setter;

public class AttributeTest extends  SeleniumAttributeTest {
	
	@Getter @Setter
	private boolean validation;
	
	//html | text | has_element
	@Getter @Setter
	private String validationType;

	@Getter @Setter
	private boolean checkList;

	@Getter @Setter
	private String validationContent;

}
