package com.tsm.model;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

public class ActionAttributeTest extends SeleniumAttributeTest {
	
    @NotNull (message = "missing_action")
	@Getter @Setter
	private String action;
	
	@Getter @Setter
	private boolean runBeforeSetValue;
}
