package com.tsm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomas on 2/15/17.
 */
public class TestResult {

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private String errorMessage;

    @Setter
    private List<TestDone> testsDone;

    @Getter @Setter
    private Map<String, String> scenariosTested;
    
    public List<TestDone> getTestsDone() {
    	if (testsDone == null) {
    		testsDone = new ArrayList<>();
    	}
    	return testsDone; 
    }
}
