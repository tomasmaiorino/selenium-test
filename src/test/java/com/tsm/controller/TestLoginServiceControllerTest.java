package com.tsm.controller;

import static org.mockito.Mockito.when;

import java.util.Collections;

import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tsm.SeleniumTestApplication;
import com.tsm.model.TestDone;
import com.tsm.model.TestResult;
import com.tsm.service.LoginBaseTestService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeleniumTestApplication.class)
@WebAppConfiguration
public class TestLoginServiceControllerTest {

	@InjectMocks
	private TestLoginServiceController sc;

	@Mock
	private LoginBaseTestService service;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MockitoAnnotations.initMocks(this);
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}
	
	private TestDone createTestDone(String message, Integer status, String testedDriver, String testedUrl, String testedService) {
		TestDone td = new TestDone();
		td.setMessage(message);
		td.setStatus(status);
		td.setTestedDriver(testedDriver);
		td.setTestedService(testedService);
		td.setTestedUrl(testedUrl);
		return td;
	}

	@Test
	public void testLoginTest() throws Exception{
		TestResult test = new TestResult();
		TestDone testDone = createTestDone("", 200, "firefox", "http://localhost", "login");
		test.setTestsDone(Collections.singletonList(testDone));
		sc.setLoginTestService(service);
		when(service.runTest()).thenReturn(test);
		ResponseEntity<TestResult> testResult = sc.testLogin();
		Assert.assertEquals(HttpStatus.OK_200, testResult.getStatusCode().value());
		Assert.assertEquals(test.getTestsDone(), testResult.getBody().getTestsDone());
		Assert.assertEquals(test.getTestsDone().get(0).getStatus(), testResult.getBody().getTestsDone().get(0).getStatus());
		//mockMvc.perform(get("/testing/login")).andExpect(status().isOk());

		/*
		 assertThat(test, allOf(
		            hasProperty("status", is("NAME")),
		            hasProperty("active", is("ACTIVE"))));
		
		Assert.assertTrue(true);
		*/
	}
}
