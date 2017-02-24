package com.tsm.controller;

import com.tsm.config.MyConfig;
import com.tsm.model.ScenarioTest;
import com.tsm.model.TestResult;
import com.tsm.service.RunTestService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by tomas on 2/15/17.
 */
@RestController
@Slf4j
public class TestServiceController {

    @Getter @Setter
    @Autowired
    private RunTestService runTestService;
    
    @Getter @Setter
    @Autowired
    private MyConfig myConfig;

    @RequestMapping(value ="/testing", method = RequestMethod.POST)
    public ResponseEntity<TestResult> testing(@RequestBody ScenarioTest scenarioTest) {
    	log.info("testing ->");
    	TestResult testResult = runTestService.runTest(scenarioTest);
    	log.info("testing <-");
        return new ResponseEntity<>(testResult, HttpStatus.OK);
    }
    
}
