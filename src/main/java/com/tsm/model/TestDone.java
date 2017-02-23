package com.tsm.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by tomas on 2/21/17.
 */
public class TestDone {

    @Getter @Setter
    private String testedUrl;

    @Getter @Setter
    private String testedService;

    @Getter @Setter
    private String testedDriver;

    @Getter @Setter
    private Integer status;

    @Getter @Setter
    private String message;
}


