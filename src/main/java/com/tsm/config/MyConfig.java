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
public class MyConfig {

    @Getter @Setter
    @Value("#{'${general.driversToTest.service.login}'.split(',')}")
    private List<String> loginDriversToTest = new ArrayList<>();

}
