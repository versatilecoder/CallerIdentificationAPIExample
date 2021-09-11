package com.app.ibcaller.controller;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.ibcaller.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpamControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserDetailsServiceImpl userDetailService;

	@Mock
	UserDetails userDetails;

	@Test
	public void UpdateSpam() throws Exception {

		Mockito.when(userDetailService.spamByContact(Mockito.anyString())).thenReturn(1);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/markSpam/99")
				.accept(MediaType.APPLICATION_JSON).param("name", "pranav").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void addToSpam() throws Exception {

		Mockito.when(userDetailService.spamByContact(Mockito.anyString())).thenReturn(0);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/markSpam/99")
				.accept(MediaType.APPLICATION_JSON).param("name", "pranav").contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		String content = result.getResponse().getContentAsString();
		assertEquals("Spam Added!!", content);
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

	}

}
