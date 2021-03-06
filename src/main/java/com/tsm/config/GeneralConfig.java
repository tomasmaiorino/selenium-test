package com.tsm.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomas on 2/15/17.
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties
public class GeneralConfig {

    @Getter @Setter
    @Value("#{'${general.driversToTest}'.split(',')}")
    private List<String> driversToTest = new ArrayList<>();

    @Getter @Setter
    @Value("${general.test.waitTimeout}")
    private Integer testWaitTimeout;

}
