package com.tsm.service;

import com.tsm.config.BaseTestDriver;
import com.tsm.config.MyConfig;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by tomas on 3/1/17.
 */
public class RunTestServiceTest {

    @Mock
    private MyConfig myConfig;

    @Mock
    private LoadDriversTest loadDriversTest;

    @InjectMocks
    private RunTestService runTestService;

    @Mock
    private List<BaseTestDriver> loadDriversMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        List<String> driversToTest = getDriversToTest("firefox", "chrome");
        when(myConfig.getDriversToTest()).thenReturn(Arrays.asList(new String[]{"firefox"}));
        when(loadDriversTest.getTestDriversByName(driversToTest)).thenReturn(loadDriversMock);
    }


    private List<String> getDriversToTest(String...drivers) {
        List<String> dri = new ArrayList<>();
        for(String s : drivers) {
            dri.add(s);
        }
        return dri;
    }

}
