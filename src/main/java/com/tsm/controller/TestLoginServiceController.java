package com.tsm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tsm.config.MyConfig;
import com.tsm.model.TestResult;
import com.tsm.service.LoginBaseTestService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tomas on 2/15/17.
 */
@RestController
@Slf4j
public class TestLoginServiceController {

    @Getter @Setter
    @Autowired
    private LoginBaseTestService loginTestService;
    
    @Getter @Setter
    @Autowired
    private MyConfig myConfig;

    @RequestMapping(value ="/testing/login", method = RequestMethod.GET)
    public ResponseEntity<TestResult> testLogin() {
    	log.info("testLoginValid ->");
    	TestResult testResult = loginTestService.runTest();
    	log.info("testLoginValid <-");
        return new ResponseEntity<>(testResult, HttpStatus.OK);
    }
    
}
