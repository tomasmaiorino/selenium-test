package com.tsm.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomas on 2/15/17.
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class LoginConfig {

    @Getter @Setter
    @Value("${login.test.url}")
    private String loginTestUrl;

    @Getter @Setter
    @Value("${login.test.name}")
    private String loginTestName;

    @Getter @Setter
    @Value("${login.test.form.element.id}")
    private String loginTestFormElementId;

    @Getter @Setter
    @Value("${login.test.form.element.name}")
    private String loginTestFormElementName;

    //user name
    @Getter @Setter
    @Value("${login.test.username.invalid}")
    private String loginTestUsernameInvalid;

    @Getter @Setter
    @Value("${login.test.username.valid}")
    private String loginTestUsernameValid;

    @Getter @Setter
    @Value("${login.test.username.element.class}")
    private String loginTestUsernameElementClass;

    @Getter @Setter
    @Value("${login.test.username.element.name}")
    private String loginTestUsernameElementName;

    @Getter @Setter
    @Value("${login.test.username.element.id}")
    private String loginTestUsernameElementId;

    //password
    @Getter @Setter
    @Value("${login.test.password.valid}")
    private String loginTestPasswordValid;

    @Getter @Setter
    @Value("${login.test.password.invalid}")
    private String loginTestPasswordInvalid;

    @Getter @Setter
    @Value("${login.test.password.element.class}")
    private String loginTestPasswordElementClass;

    @Getter @Setter
    @Value("${login.test.password.element.id}")
    private String loginTestPasswordElementId;

    @Getter @Setter
    @Value("${login.test.password.element.name}")
    private String loginTestPasswordElementName;

    @Getter @Setter
    @Value("${login.test.waitTimeout}")
    private Integer loginTestWaitTimeout;

    @Getter @Setter
    @Value("${login.test.valid.expectedPresenceElement}")
    private String loginTestValidExpectedPresenceElement;
    
    @Getter @Setter
    @Value("${login.test.invalid.expectedPresenceElement}")
    private String loginTestInvalidExpectedPresenceElement;
    
    
    @Getter @Setter
    @Value("${login.test.invalid.expectedPresenceElementText}")
    private String loginTestInvalidExpectedPresenceElementText;
}
