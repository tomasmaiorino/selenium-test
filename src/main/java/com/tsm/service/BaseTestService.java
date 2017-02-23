package com.tsm.service;

import com.tsm.config.BaseTestDriver;
import com.tsm.model.TestResult;

import java.util.List;

public abstract class BaseTestService {

	public abstract List<BaseTestDriver> initialLoad();
	
	public abstract TestResult runTest();
	
	public abstract boolean wasTestSuccessful(Object object);
	
}
