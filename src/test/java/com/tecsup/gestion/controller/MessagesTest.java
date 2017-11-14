package com.tecsup.gestion.controller;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
@WebAppConfiguration
public class MessagesTest {

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ApplicationContext context;

	@Before
	public void setup() throws Exception {
	}

	@Test
	public void validateMessage() {

		String msg = context.getMessage("Range.employee.salary", null, Locale.US);

		Assert.assertEquals("Name entered is invalid. It must be between {2} and {1}.", msg);
		
	}

}
