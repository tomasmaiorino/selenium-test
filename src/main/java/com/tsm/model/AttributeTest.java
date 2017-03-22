package com.tsm.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class AttributeTest extends  SeleniumAttributeTest {
	
	@Getter @Setter
	private boolean validation;
	
	//html | text | has_element
	@NotNull (message = "missing_validation_type")
	@Getter @Setter
	private String validationType;

	@Getter @Setter
	private boolean checkList;

	@Getter @Setter
	private String validationContent;

}
