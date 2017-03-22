package com.tsm.model;

import lombok.Getter;
import lombok.Setter;

public class AttributeTested extends  AttributeTest {

    @Getter @Setter
    private String status;

    @Getter @Setter
    private String errorMessage;

}
