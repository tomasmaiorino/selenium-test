package com.tsm.model;

import lombok.Getter;
import lombok.Setter;

public class ActionAttributeTest extends SeleniumAttributeTest {
	
	@Getter @Setter
	private String action;
	
	@Getter @Setter
	private boolean runBeforeSetValue;
}
